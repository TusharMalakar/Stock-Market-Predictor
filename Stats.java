import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
public class Stats {
	private int numTrades, numLongTrades, numShortTrades, numWinners, numLosers;
	private int numLongWinners, numLongLosers, numShortWinners, numShortLosers;
	private double averagePL, averageLongPL, averageShortPL, maxPL, minPL, numOfDays;
	String mFile, mPath;
	
	public Stats(String f, String path) {
		mFile = f;
		mPath = path;
		numTrades= numLongTrades= numShortTrades= numWinners= numLosers= 0;
		numLongWinners= numLongLosers= numShortWinners= numShortLosers= 0;
		averagePL= averageLongPL= averageShortPL= maxPL= minPL = 0;
	}
	
	public void setStats(int nTrades, int nLTrades, int nSTrades, int nWin, int nLose,
			int nLWin, int nLLose, int nSWin, int nSLose, double avrgPL, double avrgLPL,
			double avrgSPL, double maxPL, double minPL, double numOfDays) {
		numTrades = nTrades;
		numLongTrades = nLTrades;
		numShortTrades = nSTrades;
		numWinners = nWin;
		numLosers = nLose;
		
		numLongWinners = nLWin;
		numLongLosers = nLLose;
		numShortWinners = nSWin;
		numShortLosers = nSLose;
		
		averagePL = avrgPL;
		averageLongPL = avrgLPL;
		averageShortPL = avrgSPL;
		this.numOfDays = numOfDays;
		this.maxPL = maxPL;
		this.minPL = minPL;
	}
	
	public int getNumTrades() {
		return numTrades;
	}

	public void setNumTrades(int numTrades) {
		this.numTrades = numTrades;
	}

	public int getNumLongTrades() {
		return numLongTrades;
	}

	public void setNumLongTrades(int numLongTrades) {
		this.numLongTrades = numLongTrades;
	}

	public int getNumShortTrades() {
		return numShortTrades;
	}

	public void setNumShortTrades(int numShortTrades) {
		this.numShortTrades = numShortTrades;
	}

	public int getNumWinners() {
		return numWinners;
	}

	public void setNumWinners(int numWinners) {
		this.numWinners = numWinners;
	}

	public int getNumLosers() {
		return numLosers;
	}

	public void setNumLosers(int numLosers) {
		this.numLosers = numLosers;
	}

	public int getNumLongWinners() {
		return numLongWinners;
	}

	public void setNumLongWinners(int numLongWinners) {
		this.numLongWinners = numLongWinners;
	}

	public int getNumLongLosers() {
		return numLongLosers;
	}

	public void setNumLongLosers(int numLongLosers) {
		this.numLongLosers = numLongLosers;
	}

	public int getNumShortWinners() {
		return numShortWinners;
	}

	public void setNumShortWinners(int numShortWinners) {
		this.numShortWinners = numShortWinners;
	}

	public int getNumShortLosers() {
		return numShortLosers;
	}

	public void setNumShortLosers(int numShortLosers) {
		this.numShortLosers = numShortLosers;
	}

	public double getAveragePL() {
		return averagePL;
	}

	public void setAveragePL(double averagePL) {
		this.averagePL = averagePL;
	}

	public double getAverageLongPL() {
		return averageLongPL;
	}

	public void setAverageLongPL(double averageLongPL) {
		this.averageLongPL = averageLongPL;
	}

	public double getAverageShortPL() {
		return averageShortPL;
	}

	public void setAverageShortPL(double averageShortPL) {
		this.averageShortPL = averageShortPL;
	}

	public double getMaxPL() {
		return maxPL;
	}

	public void setMaxPL(double maxPL) {
		this.maxPL = maxPL;
	}

	public double getMinPL() {
		return minPL;
	}

	public void setMinPL(double minPL) {
		this.minPL = minPL;
	}

	public String getmFile() {
		return mFile;
	}

	public void setmFile(String mFile) {
		this.mFile = mFile;
	}

	public String getmPath() {
		return mPath;
	}

	public void setmPath(String mPath) {
		this.mPath = mPath;
	}

	public void setFileName(String f, String path) {
		mFile = f;
		mPath = path;
	}
	
	public Stats() {
		mFile = "test.txt";
		mPath = "./";
		numTrades= numLongTrades= numShortTrades= numWinners= numLosers= 0;
		numLongWinners= numLongLosers= numShortWinners= numShortLosers= 0;
		averagePL= averageLongPL= averageShortPL= maxPL= minPL = 0;
	}
	
	public void addTrade(Trade t) {
		numTrades++;
		if (t.getDirection() == Direction.LONG) {
			numLongTrades++;
			
		}else if (t.getDirection() == Direction.SHORT) {
			
		}
	}
	
	public void addWinner(Trade t) {
		numTrades++;
		if (t.getDirection() == Direction.LONG) {
			numLongTrades++;
			numLongWinners++;
		}else if (t.getDirection() == Direction.SHORT) {
			
		}
	}
	
	public String toString() {
		String st = "";
		Field[] fields = Stats.class.getDeclaredFields();
		for(Field field : fields)
			try {
				st+=field.getName() + "= " + field.get(this) + "," + "\r\n";
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return st;
	}
	
	public String toCsvString() {
		String st = "";
		Field[] fields = Stats.class.getDeclaredFields();
		//This variable removes the last two fields, file and path.
		int lastIndex = fields.length - 2;
		for(Field field : fields)
			try {
				if (java.util.Arrays.asList(fields).indexOf(field) >= lastIndex) {
					break;
				}
				st+=field.get(this).toString() + (!field.equals(fields[lastIndex-1])?",":"");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return st;
	}
	
	public void printToFile() {
		BufferedWriter bw;
		try {
			//open the file as a bufferedWriter 
			bw = new BufferedWriter(new FileWriter(mPath + "\\" + mFile));
			System.out.println(mPath + "\\" + mFile);
			//print to file
			bw.write(this.toString());
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}