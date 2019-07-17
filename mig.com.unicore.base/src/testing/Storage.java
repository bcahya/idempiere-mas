package testing;

import java.util.*;
import java.io.*;

public class Storage
{
	// member variable
	public String m_code;
	public String m_name;
	public float m_quantity;

	//constructer;
	public Storage (String code,String name, float quantity)
	{
		m_code = code;
		m_name = name;
		m_quantity = quantity;

	}

	public static void main (String[] args) throws IOException
	{
		ArrayList<Storage> storageList = new ArrayList<Storage>();

		BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));

		while(true)
		{
			System.out.println("1. Input goods. ");
			System.out.println("2. View all goods. ");
			System.out.println("3. Search goods.");
			System.out.println("4. Exit.");
			System.out.println("  ");
			System.out.print("Input your choice: ");
			String selected = reader.readLine();

			//Runtime.getRuntime().exec("cls");

			if(selected.equals("1"))
			{
				System.out.println("\n\nPlease input your goods data");
				System.out.println("============================");
				System.out.println("  ");
				System.out.print("\nGoods code: ");
				String code = reader.readLine();
				System.out.print("\nGoods name: ");
				String name = reader.readLine();
				System.out.print("\nQuantity: ");
				String strQty = reader.readLine();
				float qty = Float.valueOf(strQty);
				System.out.println("\n  ");

				Storage storage = new Storage(code, name, qty);
				storageList.add(storage);
			}
			else if(selected.equals("2"))
			{
				System.out.printf("%-4s", "No.");
				System.out.printf("%-16s", "Code");
				System.out.printf("%-32s", "Name");
				System.out.printf("%20s", "Quantity");
				System.out.println("\n========================================================================");

				for(int i=0; i<storageList.size(); i++)
				{
					Storage storage = storageList.get(i);
					System.out.printf("%-4.4s", (i+1)+".");
					System.out.printf("%-16.16s", storage.m_code);
					System.out.printf("%-32.32s", storage.m_name);
					System.out.printf("%17.2f", storage.m_quantity);
				}
				System.out.println();

			}
			else if(selected.equals("3"))
			{

			}
			else if(selected.equals("4"))
			{
				System.exit(0);
			}
			else
			{
				System.out.println("Invalid Input!!");
			}

		}



	}

}