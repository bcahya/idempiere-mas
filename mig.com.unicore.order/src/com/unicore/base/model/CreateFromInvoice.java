package com.unicore.base.model;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MCurrency;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoicePaySchedule;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrderPaySchedule;
import org.compiere.model.MProduct;
import org.compiere.model.MRMALine;
import org.compiere.model.MUOMConversion;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

public class CreateFromInvoice extends org.compiere.grid.CreateFromInvoice
{

	public CreateFromInvoice(GridTab mTab)
	{
		super(mTab);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getWindow()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 *  Save - Create Invoice Lines
	 *  @return true if saved
	 */
	public boolean save(IMiniTable miniTable, String trxName)
	{
		//  Invoice
		int C_Invoice_ID = ((Integer)getGridTab().getValue("C_Invoice_ID")).intValue();
		MInvoice invoice = new MInvoice (Env.getCtx(), C_Invoice_ID, trxName);
		if (log.isLoggable(Level.CONFIG)) log.config(invoice.toString());

		if (p_order != null)
		{
			invoice.setOrder(p_order);	//	overwrite header values
			invoice.saveEx();
		}

		if (m_rma != null)
		{
			invoice.setM_RMA_ID(m_rma.getM_RMA_ID());
			invoice.saveEx();
		}

//		MInOut inout = null;
//		if (m_M_InOut_ID > 0)
//		{
//			inout = new MInOut(Env.getCtx(), m_M_InOut_ID, trxName);
//		}
//		if (inout != null && inout.getM_InOut_ID() != 0
//			&& inout.getC_Invoice_ID() == 0)	//	only first time
//		{
//			inout.setC_Invoice_ID(C_Invoice_ID);
//			inout.saveEx();
//		}

		//  Lines
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				MProduct product = null;
				//  variable values
				BigDecimal QtyEntered = (BigDecimal)miniTable.getValueAt(i, 1);              //  1-Qty

				KeyNamePair pp = (KeyNamePair)miniTable.getValueAt(i, 2);   //  2-UOM
				int C_UOM_ID = pp.getKey();
				//
				pp = (KeyNamePair)miniTable.getValueAt(i, 3);               //  3-Product
				int M_Product_ID = 0;
				if (pp != null)
					M_Product_ID = pp.getKey();
				//
				int C_OrderLine_ID = 0;
				pp = (KeyNamePair)miniTable.getValueAt(i, 5);               //  5-OrderLine
				if (pp != null)
					C_OrderLine_ID = pp.getKey();
				int M_InOutLine_ID = 0;
				pp = (KeyNamePair)miniTable.getValueAt(i, 6);               //  6-Shipment
				if (pp != null)
					M_InOutLine_ID = pp.getKey();
				//
				int M_RMALine_ID = 0;
				pp = (KeyNamePair)miniTable.getValueAt(i, 7);               //  7-RMALine
				if (pp != null)
					M_RMALine_ID = pp.getKey();

				//	Precision of Qty UOM
				int precision = 2;
				if (M_Product_ID != 0)
				{
					product = MProduct.get(Env.getCtx(), M_Product_ID);
					precision = product.getUOMPrecision();
				}
				QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_DOWN);
				//
				if (log.isLoggable(Level.FINE)) log.fine("Line QtyEntered=" + QtyEntered
					+ ", Product_ID=" + M_Product_ID
					+ ", OrderLine_ID=" + C_OrderLine_ID + ", InOutLine_ID=" + M_InOutLine_ID);

				//	Create new Invoice Line
				MInvoiceLine invoiceLine = new MInvoiceLine (invoice);
				invoiceLine.setM_Product_ID(M_Product_ID, C_UOM_ID);	//	Line UOM
				invoiceLine.setQty(QtyEntered);							//	Invoiced/Entered
				BigDecimal QtyInvoiced = null;
				if (M_Product_ID > 0 && product.getC_UOM_ID() != C_UOM_ID) {
					QtyInvoiced = MUOMConversion.convertProductFrom(Env.getCtx(), M_Product_ID, C_UOM_ID, QtyEntered);
				}
				if (QtyInvoiced == null)
					QtyInvoiced = QtyEntered;
				invoiceLine.setQtyInvoiced(QtyInvoiced);

				//  Info
				MOrderLine orderLine = null;
				if (C_OrderLine_ID != 0)
					orderLine = new MOrderLine (Env.getCtx(), C_OrderLine_ID, trxName);
				//
				MRMALine rmaLine = null;
				if (M_RMALine_ID > 0)
					rmaLine = new MRMALine (Env.getCtx(), M_RMALine_ID, null);
				//
				MInOutLine inoutLine = null;
				if (M_InOutLine_ID != 0)
				{
					inoutLine = new MInOutLine (Env.getCtx(), M_InOutLine_ID, trxName);
					if (orderLine == null && inoutLine.getC_OrderLine_ID() != 0)
					{
						C_OrderLine_ID = inoutLine.getC_OrderLine_ID();
						orderLine = new MOrderLine (Env.getCtx(), C_OrderLine_ID, trxName);
					}
				}
				else if (C_OrderLine_ID > 0)
				{
					String whereClause = "EXISTS (SELECT 1 FROM M_InOut io WHERE io.M_InOut_ID=M_InOutLine.M_InOut_ID AND io.DocStatus IN ('CO','CL'))";
					MInOutLine[] lines = MInOutLine.getOfOrderLine(Env.getCtx(),
						C_OrderLine_ID, whereClause, trxName);
					if (log.isLoggable(Level.FINE)) log.fine ("Receipt Lines with OrderLine = #" + lines.length);
					if (lines.length > 0)
					{
						for (int j = 0; j < lines.length; j++)
						{
							MInOutLine line = lines[j];
							if (line.getQtyEntered().compareTo(QtyEntered) == 0)
							{
								inoutLine = line;
								M_InOutLine_ID = inoutLine.getM_InOutLine_ID();
								break;
							}
						}
//						if (inoutLine == null)
//						{
//							inoutLine = lines[0];	//	first as default
//							M_InOutLine_ID = inoutLine.getM_InOutLine_ID();
//						}
					}
				}
				else if (M_RMALine_ID != 0)
				{
					String whereClause = "EXISTS (SELECT 1 FROM M_InOut io WHERE io.M_InOut_ID=M_InOutLine.M_InOut_ID AND io.DocStatus IN ('CO','CL'))";
					MInOutLine[] lines = MInOutLine.getOfRMALine(Env.getCtx(), M_RMALine_ID, whereClause, null);
					if (log.isLoggable(Level.FINE)) log.fine ("Receipt Lines with RMALine = #" + lines.length);
					if (lines.length > 0)
					{
						for (int j = 0; j < lines.length; j++)
						{
							MInOutLine line = lines[j];
							if (rmaLine.getQty().compareTo(QtyEntered) == 0)
							{
								inoutLine = line;
								M_InOutLine_ID = inoutLine.getM_InOutLine_ID();
								break;
							}
						}
						if (rmaLine == null)
						{
							inoutLine = lines[0];	//	first as default
							M_InOutLine_ID = inoutLine.getM_InOutLine_ID();
						}
					}

				}
				//	get Ship info
				
				//	Shipment Info
				if (inoutLine != null)
				{
					invoiceLine.setShipLine(inoutLine);		//	overwrites
				}
				else {
					log.fine("No Receipt Line");
					//	Order Info
					if (orderLine != null)
					{
						invoiceLine.setOrderLine(orderLine);	//	overwrites
					}
					else
					{
						log.fine("No Order Line");
						invoiceLine.setPrice();
						invoiceLine.setTax();
					}

					//RMA Info
					if (rmaLine != null)
					{
						invoiceLine.setRMALine(rmaLine);		//	overwrites
					}
					else
						log.fine("No RMA Line");
				}
				invoiceLine.saveEx();
			}   //   if selected
		}   //  for all rows

		if (p_order != null) {
			invoice.setPaymentRule(p_order.getPaymentRule());
			invoice.setC_PaymentTerm_ID(p_order.getC_PaymentTerm_ID());
			invoice.saveEx();
			invoice.load(invoice.get_TrxName()); // refresh from DB
			// copy payment schedule from order if invoice doesn't have a current payment schedule
			MOrderPaySchedule[] opss = MOrderPaySchedule.getOrderPaySchedule(invoice.getCtx(), p_order.getC_Order_ID(), 0, invoice.get_TrxName());
			MInvoicePaySchedule[] ipss = MInvoicePaySchedule.getInvoicePaySchedule(invoice.getCtx(), invoice.getC_Invoice_ID(), 0, invoice.get_TrxName());
			if (ipss.length == 0 && opss.length > 0) {
				BigDecimal ogt = p_order.getGrandTotal();
				BigDecimal igt = invoice.getGrandTotal();
				BigDecimal percent = Env.ONE;
				if (ogt.compareTo(igt) != 0)
					percent = igt.divide(ogt, 10, BigDecimal.ROUND_HALF_UP);
				MCurrency cur = MCurrency.get(p_order.getCtx(), p_order.getC_Currency_ID());
				int scale = cur.getStdPrecision();
			
				for (MOrderPaySchedule ops : opss) {
					MInvoicePaySchedule ips = new MInvoicePaySchedule(invoice.getCtx(), 0, invoice.get_TrxName());
					PO.copyValues(ops, ips);
					if (percent != Env.ONE) {
						BigDecimal propDueAmt = ops.getDueAmt().multiply(percent);
						if (propDueAmt.scale() > scale)
							propDueAmt = propDueAmt.setScale(scale, BigDecimal.ROUND_HALF_UP);
						ips.setDueAmt(propDueAmt);
					}
					ips.setC_Invoice_ID(invoice.getC_Invoice_ID());
					ips.setAD_Org_ID(ops.getAD_Org_ID());
					ips.setProcessing(ops.isProcessing());
					ips.setIsActive(ops.isActive());
					ips.saveEx();
				}
				invoice.validatePaySchedule();
				invoice.saveEx();
			}
		}

		return true;
	}   //  saveInvoice

}
