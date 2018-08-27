import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Simulator {
	private String mPath, mFile, mSaveTo;
	public String getmPath() {
		return mPath;
	}

	public void setmPath(String mPath) {
		this.mPath = mPath;
	}

	public String getSaveTo() {
		return mSaveTo;
	}

	public void setSaveTo(String mSaveTo) {
		this.mSaveTo = mSaveTo;
	}
	private TradeArray mTrades;
	private SymbolTester mTester;
	public Simulator (String p, String symbols){
		mFile = symbols;
		mPath = p;
		mTrades = new TradeArray(10000);
	}
	
	TradeArray getTrades() {
		return mTrades;
	}
	
	public boolean run() {
		//open the symbol file 
		try {
			File symFile = new File(mPath + "/" + mFile);
			if (!symFile.exists()) {
				System.out.println(mPath + "/" + mFile + " does not exist.");
				return false;
			}
			BufferedReader br = new BufferedReader(new FileReader(symFile));
			
			//read one symbol at a time
			String symbol;
			
			Vector<String> Symbols = new Vector<String>();
			
			while((symbol = br.readLine()) != null) {
				Symbols.add(symbol);
			}
			//Close the Buffered Reader
			br.close();
			
			
			TradeArray tempTrades = null;
			int Risk[] = {0, 1, 2, 5, 10, 15, 20};
			int k = 0;
			
			//Run once with 0, 0
			while(k < Symbols.size() && (symbol = Symbols.get(k)) != null) {
				mTester = new SymbolTester(symbol, mPath);
				tempTrades = mTester.test(0, 0);
				mTrades.Add(tempTrades);
				
				k++;
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(mPath + "\\" + mSaveTo + "_Stats.csv"));

			//Reset k
			k = 0;
			//Write the headers of the CSV File
			bw.write("Number of Trades, Number of Long Trades, Number of Short Trades, Number of Winners, Number of Losers," +
					 "Number of Long Winners, Number of Long Losers, Number of Short Winners, Number of Short Losers, Average PL," +
					 "Average Long PL, Average Short PL, Max PL, Min PL, Average Days, Target, Risk\n");
			
			bw.write(mTrades.getStats().toCsvString()+","+ 0 +","+ 0 + "\n");
//			this.printToFile(mSaveTo + "-" + "Same_Day" + ".txt", mPath);
			
			mTrades =  new TradeArray(1000);
			
			for (int i = 0; i < Risk.length; i++) {
				for (int j = 0; j < Risk.length; j++) {
					while(k < Symbols.size() && (symbol = Symbols.get(k)) != null) {
						mTester = new SymbolTester(symbol, mPath);
						tempTrades = mTester.test(Risk[i], Risk[j]);
						mTrades.Add(tempTrades);
						
						k++;
					}
					//Reset k
					k = 0;
					//Save every pair of percentages (Stoploss and Target) to a file
					
					//Get the Stats and print them to a file
//					this.printToFile(mSaveTo + "-" + Risk[i] + "%" + Risk[j] + "%" + ".txt", mPath);
//					System.out.println("Stats saved to " + mSaveTo + "-" + Risk[i] + "%" + Risk[j] + "%" + ".txt");
					
					bw.write(mTrades.getStats().toCsvString()+","+Risk[i] +","+Risk[j] + "\n");
					
					//Reset mTrades for the next pair of percentages
					mTrades =  new TradeArray(1000);
				}
				//create a symbolTester
			}
			//Close the bufferedReader
			bw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return true;
	}
	
	public Stats getStats() {
		return mTrades.getStats();
	}
	public void printStats() {
		System.out.println((mTrades.getStats()).toString());
	}
	public void printToFile(String f, String p) {
		//TODO: Print in a .csv??
		Stats St = mTrades.getStats();
		St.setFileName(f, p);
		St.printToFile();
	}
	
	
}