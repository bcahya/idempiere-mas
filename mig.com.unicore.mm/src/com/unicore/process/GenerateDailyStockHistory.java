package com.unicore.process;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.unicore.model.MUNSDailyStockHistory;

/**
 * @author Toriq
 *
 */
public class GenerateDailyStockHistory extends SvrProcess {
	
	private Timestamp TanggalAkhir=null ;
	private Hashtable<String, StockCountInitializer> m_maptrx= null;
	
	@Override
	//persiapan mulai
	protected void prepare() {
		m_maptrx=getMapRecords();
		TanggalAkhir= new Timestamp(System.currentTimeMillis());
	}

	@Override
	//setelah pripare selesai jalankan do it
	protected String doIt() throws Exception {
		List<StockCountInitializer> listinitializer = getStockCountInitializers();
		for (StockCountInitializer initializer : listinitializer){
			BigDecimal endingStock = DB.getSQLValueBD(
					get_TrxName()
					, "SELECT EndingStock FROM UNS_DailyStockHistory as a where a.m_product_id=? "
							+ "and a.m_warehouse_id=? and a.datetrx=(select max(b.datetrx) "
							+ "from uns_dailystockhistory as b where b.datetrx<? "
							+ "and b.m_product_id=a.m_product_id and b.m_warehouse_id=a.m_warehouse_id)"
							, initializer.getProduc_id(), initializer.getWhs_id(),initializer.getDate());
			doGenerate(initializer, endingStock == null ? Env.ZERO : endingStock);
		}
		return  null;
	}
	
	MUNSDailyStockHistory doCreateHistory(StockCountInitializer initializer)
	{
		MUNSDailyStockHistory dailyhistory = new MUNSDailyStockHistory(getCtx(), 0, get_TrxName());
		dailyhistory.setDateTrx(initializer.getDate());
		dailyhistory.setAD_Org_ID(0);
		dailyhistory.setEndingStock(Env.ZERO);
		dailyhistory.setInitialBalance(Env.ZERO);
		dailyhistory.setInQty(Env.ZERO);
		dailyhistory.setM_Product_ID(initializer.getProduc_id());
		dailyhistory.setM_Warehouse_ID(initializer.getWhs_id() );
		dailyhistory.setOutQty(Env.ZERO);
		return dailyhistory;
	}
	
	//Proses Generate 
	private void doGenerate(StockCountInitializer initializer,BigDecimal endingStock) {
		if (initializer.getDate().after(TanggalAkhir)){
			return;
		}
		
		MUNSDailyStockHistory dailyhistory=MUNSDailyStockHistory.getToDate(get_TrxName(),initializer.getDate(),initializer.getProduc_id(),initializer.getWhs_id());
		if (dailyhistory == null){
			dailyhistory = doCreateHistory(initializer);
		}
		else if(dailyhistory.getDateTrx().compareTo(initializer.getDate()) < 0){
			initializer.setDate(TimeUtil.addDays(dailyhistory.getDateTrx(), 1));
			dailyhistory = doCreateHistory(initializer);
		}

		dailyhistory.setInitialBalance(endingStock);
		String key = new SimpleDateFormat("YYYY-MM-dd").format(initializer.getDate())+"-"+initializer.getProduc_id()+"-"+initializer.getWhs_id();
		StockCountInitializer stock = m_maptrx.get(key); 	
		if (stock != null){
			dailyhistory.setInQty(dailyhistory.getInQty().add(stock.getQtyin()));
			dailyhistory.setOutQty(dailyhistory.getOutQty().add(stock.getQtyout()));
		}
		dailyhistory.setEndingStock(dailyhistory.getInitialBalance().add(dailyhistory.getInQty().add(dailyhistory.getOutQty())));
		dailyhistory.save();
		doUpdateTrx(initializer, dailyhistory.get_ID());
		Timestamp newDate = TimeUtil.addDays(initializer.getDate(), 1);
		initializer.setDate(newDate);
		doGenerate(initializer,dailyhistory.getEndingStock());
	}
	
	private void doUpdateTrx(StockCountInitializer initializer, int hisid){
		String sql="update m_transaction set uns_dailystockhistory_id=? where m_product_id=? and movementdate=? and m_locator_id in(select m_locator_id from m_locator where m_warehouse_id=?)";
		int result = DB.executeUpdate(sql, new Object[]{hisid,initializer.getProduc_id(),initializer.getDate(),initializer.getWhs_id()}, false, get_TrxName());
		if (result== -1){
			throw new AdempiereException("Failed when updating transaction");
		}
	}
	//baca pertama mendapatkan data m_transaksi yang belum ada di dailystockhistory 
	private Hashtable<String, StockCountInitializer> getMapRecords() {
		String sqlMapRecord="select aa.trxdate , aa.product, sum(aa.qtyin) as qtyin, sum(aa.qtyout) as qtyout, gudang"
		          +" from (SELECT mt.movementdate as trxdate, mt.m_product_id as product, sum(mt.movementqty) as qtyin, 0 as qtyout, l.m_warehouse_id as gudang"
		          +" FROM m_transaction mt INNER JOIN m_locator l on l.m_locator_id = mt.m_locator_ID"
		          +" where mt.movementqty > 0 and mt.uns_dailystockhistory_id is null  group by mt.movementdate,mt.m_product_id, l.m_warehouse_id"
		          +" union"
		          +" SELECT mt.movementdate as trxdate, mt.m_product_id as product,0 as qtyin, sum(mt.movementqty) as qtyout, l.m_warehouse_id as gudang"
		          +" FROM m_transaction mt INNER JOIN m_locator l on l.m_locator_id = mt.m_locator_ID"
		          +" where mt.movementqty < 0 and mt.uns_dailystockhistory_id is null  group by mt.movementdate,mt.m_product_id, l.m_warehouse_id"
		          +") as aa group by aa.trxdate, aa.product, aa.gudang order by aa.trxDate";
		
		Hashtable<String, StockCountInitializer> maprecords = new Hashtable<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = DB.prepareStatement(sqlMapRecord, get_TrxName());
			rs = pstmt.executeQuery();
			while(rs.next()){
				StockCountInitializer trx = new StockCountInitializer(rs.getTimestamp(1),rs.getInt(2),rs.getInt(5),rs.getBigDecimal(4),rs.getBigDecimal(3));
				String key = new SimpleDateFormat("YYYY-MM-dd").format(trx.getDate()) +"-"+trx.getProduc_id()+"-"+trx.getWhs_id();
				maprecords.put(key, trx);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally{
			DB.close(rs, pstmt);
		}
		return maprecords;
	}
	
	private List<StockCountInitializer> getStockCountInitializers(){
		String sql ="select min(mt.movementdate), mt.m_product_id, l.m_warehouse_id from m_transaction mt"
				+" inner join m_locator l on l.m_locator_id = mt.m_locator_id"
				+" where uns_dailystockhistory_id is null "
				+" group by mt.m_product_id, l.m_warehouse_id";
		
		List<StockCountInitializer> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(new StockCountInitializer(
						rs.getTimestamp(1), rs.getInt(2), rs.getInt(3)));				
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally{
			DB.close(rs, pstmt);
		}		
		return list;
	}

}
class StockCountInitializer{
	private Timestamp m_date;
	private int m_product_id;
	private int m_whs_id;
	private BigDecimal m_qtyin;
	private BigDecimal m_qtyout;
	
	public StockCountInitializer(Timestamp date,int product_id, int whs_id ) {
		m_date = date;
		m_product_id = product_id;
		m_whs_id = whs_id;
	}
	
	public Timestamp getDate(){
		return m_date;
	}
	public int getProduc_id(){
		return m_product_id;
	}
	public int getWhs_id(){
		return m_whs_id;
	}
	
	public StockCountInitializer(Timestamp date,int product_id,int whs_id, BigDecimal qtyout,BigDecimal qtyin){
		m_date = date;
		m_product_id = product_id;
		m_whs_id = whs_id;
		m_qtyout = qtyout;
		m_qtyin = qtyin;
	}
	public BigDecimal getQtyin(){
		return m_qtyin;
	}
	public BigDecimal getQtyout(){
		return m_qtyout;
	}
	public void setDate(Timestamp date) {
		m_date= date;
		
	}
}

