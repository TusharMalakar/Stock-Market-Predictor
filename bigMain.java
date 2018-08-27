
public class bigMain {
	public static void main(String[] args) {
		TradeArray stockTrades, indexTrades;
		String myPath = "C:\\Users\\Luis\\Documents\\MAC286";
		String fileStocks = "test.txt";
		
		//create a simulator for Stocks
		Simulator simStocks = new Simulator(myPath, fileStocks);
		simStocks.setSaveTo("stockStats");
		if (!simStocks.run()) {
			System.out.println("Problem in Simulator");
			System.exit(1);
		}
		//get the trades		
		
		//create a simulator for Indices 
		
		String fileIndices = "indices.txt";
		
		//create a simulator for Stocks
		Simulator simIndices = new Simulator(myPath, fileIndices);
		simIndices.setSaveTo("indecesStats");
		if (!simIndices.run()) {
			System.out.println("Problem in Simulator");
			System.exit(1);
		}
		//get the trades
	}
}