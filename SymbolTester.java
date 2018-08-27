import java.util.*;
public class SymbolTester {
	private TradeArray mTrades;
	private DataArray mData;
	private String mSymbol, mPath;
	
	public SymbolTester (String sym, String p) {
		mSymbol = sym;
		mPath = p;
		mTrades = new TradeArray(1000);
		mData = new DataArray(mSymbol, mPath);
	}
	
	
	
	/*Try these percentages
	 * 
	 * Risk		Target
	 * 2%		2%
	 * 2%		5%
	 * 2%		10%
	 * 5%		2%
	 * 5%		5%
	 * 5%		10%
	 * 10%		2%
	 * 10%		5%
	 * 10%		10%
	 * Close the same day
	 */
	private void manageTrade(Trade trade, int z) {
		int dataSize = mData.getSize();
		Bar current = null;
		while(true) {
			//TODO: Make sure this works
			trade.increaseBarCount();
			current = mData.At(z);
			if (trade.getDirection() == Direction.LONG) {
				if (z >= dataSize - 1) {//If we are at the end of the list of data\
					trade.Close(current.getDate(), current.getClose());
					mTrades.Add(trade);
					break;
				}else {
					if(trade.getTarget() == trade.getEntryPrice()) { //TODO: Remove this maybe??
						//We exit the same day using the close of that day
						trade.Close(current.getDate(), current.getClose());
						mTrades.Add(trade);
						break;
					}
					if(current.getHigh() > trade.getTarget()) { //If we surpassed our target
						if (current.getOpen() > trade.getTarget())
							trade.Close(current.getDate(), current.getOpen());
						else
							trade.Close(current.getDate(), trade.getTarget());
						mTrades.Add(trade);
						break;
					}else if (current.getLow() < trade.getStop()) { //If we went below our risk...
						if (current.getOpen() < trade.getStop())
							trade.Close(current.getDate(), current.getOpen());
						else
							trade.Close(current.getDate(), trade.getStop());
						mTrades.Add(trade);
						break;
					}
					//Else, continue searching...
				}
				z++;							
			}else {
				//TODO: Finish this
				if (z == dataSize - 1) {//If we are at the end of the list of data
					trade.Close(current.getDate(), current.getClose());
					mTrades.Add(trade);
					break;
				}else {
					if(trade.getTarget() == trade.getEntryPrice()) {
						//We exit the same day using the close of that day
						trade.Close(current.getDate(), current.getClose());
						mTrades.Add(trade);
						break;
					}
					if(current.getLow() < trade.getTarget()) { //If we surpassed our target
						if (current.getOpen() < trade.getTarget())
							trade.Close(current.getDate(), current.getOpen());
						else
							trade.Close(current.getDate(), trade.getTarget());
						mTrades.Add(trade);
						break;
					}else if (current.getHigh() > trade.getStop()) { //If we went below our risk...
						if (current.getOpen() > trade.getStop())
							trade.Close(current.getDate(), current.getOpen());
						else
							trade.Close(current.getDate(), trade.getStop());
						mTrades.Add(trade);
						break;
					}
					//Else, continue searching...
				}
				z++;
			}
		}
		if (trade.PercentPL() > 70) {
			System.err.println(current.getSym() + " - errr");
		}
	}

	public TradeArray test(float r, float t) {
		//open the file and load the bars
		if (mData.Load() <= 0) {
			System.out.println("Something is wrong openeing the file for symbol "+ mSymbol);
			return null;
		}
		
		final int nOfShares = 1;
		
		//Jack-In-The-Box Strategy uses Expansion Breakout (XBO). (60 days of data for the first pattern)
		int start = 60;
		
		Bar currentB = null;
		Trade currentT = null;
		
		Bar temp = null;
		Trade trade = null;
		float risk = 0f;
		float target = 0f;
		
		//This loops through all the Bars that we collected
		int dataSize = mData.getSize();
		for (int k = 60; k < dataSize; k++) {
			//check if pattern found
			temp = mData.At(k);

			//Long pattern
			int z = k; //Variable used to move forward in the bars without keeping track of the current bar (k)
			if(XBO(k) && isInsideDay(temp, mData.At(k+1))) {
				Bar current = null;
				float entryPrice = temp.getHigh() + 0.01f;

				if(mData.At(k+2).getHigh() > entryPrice) {
					z = k + 2;
				}else if (mData.At(k+3).getHigh() > entryPrice) {
					z = k + 3;
				}else {
					continue;
				}
				current = mData.At(z);
				//Open the trade
				//Initialize Target and Risk values for this trade
				risk =  entryPrice * (1 - r/100f);
				target = entryPrice * (1 + t/100f);
				
				trade = new Trade(Direction.LONG, current.getDate(), entryPrice, nOfShares, target, risk);
				manageTrade(trade, z);
				
				//Improves performance
				//Prevents unnecessary checks
				if (trade.isClosed()){
					continue;
				}
			}
			
			//Short pattern
			//TODO: Revise this
			if(ReverseXBO(k) && isInsideDay(temp, mData.At(k+1))) {
				Bar current = null;
				float entryPrice = temp.getHigh() + 0.01f;
				
				if(mData.At(k+2).getLow() < entryPrice) {
					z = k + 2;
				}else if (mData.At(k+3).getLow() < entryPrice) {
					z = k + 3;
				}else {
					continue;
				}
				
				current = mData.At(z);
				//Open the trade
				//Initialize Target and Risk values for this trade
				target =  entryPrice * (1 - t/100f);
				risk = entryPrice * (1 + r/100f);
				
				trade = new Trade(Direction.SHORT, current.getDate(), entryPrice, nOfShares, target, risk);
				manageTrade(trade, z);
			}
		}
		return mTrades;
	}
	
	public boolean XBO(int index) {
		Bar test = mData.At(index);
		for (int i = index - 59; i < index; i++) {
			Bar cur = mData.At(i); //Current Bar
			if (cur.getHigh() > test.getHigh()) {
				return false;
			}
			if (i > index-9) {
				//Check if the range is greater than the range of the last 9 days
				if (cur.getRange() > test.getRange()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean ReverseXBO(int index) {
		Bar test = mData.At(index);
		for (int i = index - 59; i < index; i++) {
			Bar cur = mData.At(i); //Current Bar
			if (cur.getLow() < test.getLow()) {
				return false;
			}
			if (i > index-10) {
				//Check if the range is greater than the range of the last 9 days
				if (cur.getRange() > test.getRange()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isInsideDay(Bar prev, Bar curr) {
		if (prev == null || curr == null)
			return false;
		if (curr.getHigh() <= prev.getHigh() && curr.getLow() >= prev.getLow()) {
			return true;
		}
		return false;
	}
	
	public boolean isOutsideDay(Bar prev, Bar curr) {
		if (prev == null || curr == null)
			return false;
		if (curr.getHigh() >= prev.getHigh() && curr.getLow() <= prev.getLow()) {
			return true;
		}
		return false;
	}
}