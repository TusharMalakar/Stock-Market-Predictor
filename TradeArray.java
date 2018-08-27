import java.util.Collection;
import java.util.Vector;

public class TradeArray {
	private Vector<Trade> mTrades;
	
	public TradeArray(int s) {
		mTrades = new Vector<Trade>(s, 100);
	}
	
	public int getSize() {
		return mTrades.size();
	}
	
	public void Resize(int size) {
		mTrades.setSize(size);
	}
	
	public boolean isEmpty() {
		return mTrades.size() == 0;
	}
	
	public boolean isFull() {
		return mTrades.size() == mTrades.capacity();
	}
	
	public void AddTail(Trade element) {
		mTrades.add(element);
	}

	public void insertHead(Trade b) {
		mTrades.add(0, b);
	}
	
	public void Add(Trade element) {
		mTrades.add(element);
	}
	
	@SuppressWarnings("unchecked")
	public void Add(TradeArray elements) {
		for (int i = 0; i < elements.getSize(); i++) {
			mTrades.addElement(elements.At(i));
		}
	}
	
	public void AddHead(Trade element) {
		mTrades.add(0, element);
	}
	
	public void insert(int index, Trade element) {
		mTrades.add(index, element);
	}
	
	
	
	public void insert (TradeArray Ar){
		for (int i =0; i <Ar.getSize(); i++) {
			Trade temp = new Trade(Ar.At(i));
			this.insert(temp);
		}
	}
	
	public void insert(Trade b) {
		mTrades.add(b);
	}
	
	public Trade At(int index) {
		if (index < 0 || index >= mTrades.size())
			return null;
		return mTrades.elementAt(index);
	}
	
	public Trade RemoveHead() {
		return mTrades.remove(0);
	}
	
	public Trade Remove(int index) {
		return mTrades.remove(index);
	}
	
	public Trade remove() {
		return mTrades.remove(0);
	}
	
	public Trade RemoveTail() {
		return mTrades.remove(getSize()-1);
	}
	
	public Stats getStats() {

		Stats st = new Stats ();
		int totalTrades, numLongTrades, numShortTrades, numWinners, numLosers = 0;
		int numLongWinners, numLongLosers, numShortWinners, numShortLosers;
		totalTrades = numLongTrades = numShortTrades = numWinners = 0;
		numLongWinners= numLongLosers= numShortWinners= numShortLosers= 0;

		
		double totalSum, longSum, shortSum, maxPL, minPL, avrgPL, avrgShortPL, numOfDays, avrgLongPL = 0;
		totalSum = longSum = shortSum = maxPL= minPL= avrgPL = numOfDays = avrgShortPL = 0;
		
		double currentPL = 0;
		
		minPL = this.At(0).PercentPL(); //Start the minimum PL with the first element;
		for (int i =0; i<this.getSize(); i++) {
			Trade currentTrade = this.At(i);
			numOfDays += currentTrade.getBarCount();
			totalTrades++;
			currentPL = currentTrade.PercentPL();
			if(currentPL > 100) {
				System.out.println(currentTrade.getEntryDate() + " + " + currentTrade.getExitDate());
				System.out.println(currentTrade.getEntryPrice() + " // "+ currentTrade.getExitPrice());
			}
			totalSum+= currentPL;
			
			if(currentPL > maxPL) {
				maxPL = currentPL;
			}
			if (currentPL < minPL) {
				minPL = currentPL;
			}
			
			if(currentTrade.isLong()) {
				numLongTrades++;
				longSum += currentPL;
				if(currentTrade.PercentPL() >= 0) {
					numLongWinners++;
				}else {
					numLongLosers++;
				}
			}else if(currentTrade.isShort()) {
				numShortTrades++;
				shortSum += currentPL;
				if(currentTrade.PercentPL() >= 0) {
					numShortWinners++;
				}else {
					numShortLosers++;
				}
			}
		}
		numWinners = numLongWinners + numShortWinners;
		numLosers = numLongLosers + numShortLosers;
		
		
		numOfDays = numOfDays/totalTrades;
		avrgPL = totalSum/totalTrades;
		if (numLongTrades > 0)
			avrgLongPL = longSum/numLongTrades;
		if (numShortTrades > 0)
			avrgShortPL = (float)shortSum/(float)numShortTrades;
		
		st.setStats(totalTrades, numLongTrades, numShortTrades, numWinners, numLosers, numLongWinners, numLongLosers, numShortWinners, numShortLosers, avrgPL, avrgLongPL, avrgShortPL, maxPL, minPL, numOfDays);		
		return st;
	}
}
