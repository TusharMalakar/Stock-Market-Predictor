import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Trade {
	private float entryPrice; //Price at which the stock is bought (or sold in case short)
	//Definition of Short: Sell then buy
	private int numberOfShares; //How many shares were exchanged
	private Date entryDate; //When did you buy (or sold in case of short)
	private Date exitDate; //When did you close the trade
	private float exitPrice; //Price at which it is sold (or bought in case short)\
	private int BarCount = 0;
	private float target;
	private float stoploss;
	private Direction dir;
	private boolean on;
	
	public Trade(){
		dir = Direction.LONG;
		entryDate = null;
		entryPrice = 0;
		exitDate = null;
		exitPrice = 0;
		BarCount = 0;
		target = 0f;
		stoploss = 0f;
		on = true;
		numberOfShares = 0;
	}
	
	public Trade(Trade T) {
		dir = T.dir;
		entryDate = T.entryDate;
		entryPrice = T.entryPrice;
		exitDate = T.exitDate;
		exitPrice = T.exitPrice;
		BarCount = 0;
		on = T.on;
		numberOfShares = T.numberOfShares;
	}
	
	public Trade(float entryPrice, float exitPrice, int numberOfShares, Date entryDate, Date exitDate, Direction dir) {
		this.entryPrice = entryPrice;
		this.exitPrice = exitPrice;
		this.numberOfShares = numberOfShares;
		this.entryDate = entryDate;
		BarCount = 0;
		this.exitDate = exitDate;
		this.dir = dir;
		this.on = false;
	}
	
	public Trade(Direction d, Date day, float ePrice, int nShares, float t, float s) {
		dir = d;
		entryDate = day;
		entryPrice = ePrice;
		target = t;
		stoploss = s;
		BarCount = 0;
		exitDate = null;
		exitPrice = 0;
		on = true;
		numberOfShares = nShares;
	}
	
	public float getTarget() {
		return target;
	}

	public void setTarget(float target) {
		this.target = target;
	}

	public float getStop() {
		return stoploss;
	}

	public void setStop(float stoploss) {
		this.stoploss = stoploss;
	}

	public Trade(Date entryDate, float entryPrice, Direction dir) {
		this.entryDate = entryDate;
		this.exitDate = null;
		BarCount = 0;
		this.entryPrice = entryPrice;
		this.exitPrice = -1;
		this.numberOfShares = 1;
		this.dir = dir;
		this.on = true;
	}
	
	public boolean open(Direction d, Date day, float ePrice, int nShares) {
		dir = d;
		entryDate = day;
		entryPrice = ePrice;
		exitDate = null;
		exitPrice = 0;
		on = true;
		numberOfShares = nShares;
		return true;
	}
	
	public boolean open(Direction d, Date day, float ePrice) {
		dir = d;
		entryDate = day;
		entryPrice = ePrice;
		exitDate = null;
		exitPrice = 0;
		on = true;
		numberOfShares = 1;
		return true;
	}
	public boolean close(Date day, float ePrice) {
		if (on == false)
			return false;
		on = false;
		exitDate = day;
		exitPrice = ePrice;
		return true;
	}
	
	public void increaseBarCount() {
		BarCount++;
	}
	
	public int getBarCount() {
		return BarCount;
	}
	
	public void setBarCount(int b) {
		BarCount = b;
	}
	
	//Setters
	public void setEntryPrice(float entryPrice) {
		this.entryPrice = entryPrice;
	}

	public void setNumberOfShares(int numberOfShares) {
		this.numberOfShares = numberOfShares;
	}	
	
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	public void setExitPrice(float exitPrice) {
		this.exitPrice = exitPrice;
	}
	
	public void setDirection(String direc) {
		switch(direc.toLowerCase()) {
		case "long":
			dir = Direction.LONG;
			break;
		case "short":
			dir = Direction.SHORT;
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public void setDirection(Direction dir) {
		this.dir = dir;
	}
	
	//Getters
	public boolean isOn() {
		return on;
	}
	
	public boolean getOn() {
		return on;
	}
	
	public int getNumberOfShares() {
		return numberOfShares;
	}
	
	public float getEntryPrice() {
		return entryPrice;
	}
	
	public Date getEntryDate() {
		return entryDate;
	}
	
	public Date getExitDate() {
		return exitDate;
	}
	
	public float getExitPrice() {
		return exitPrice;
	}
	
	public Direction getDirection() {
		return dir;
	}
	
	public boolean isLong() {
		return dir == Direction.LONG;
	}
	
	public boolean isShort() {
		return dir == Direction.SHORT;
	}
	
	public void Open(Date entryDate, float entryPrice, Direction dir) {
		this.entryDate = entryDate;
		this.exitDate = null;
		this.entryPrice = entryPrice;
		this.exitPrice = -1;
		this.dir = dir;
		this.on = true;
	}
	
	public void Close(Date exitDate, float exitPrice) {
		this.exitDate = exitDate;
		this.exitPrice = exitPrice;
		this.on = false;
	}
	
	public boolean isClosed() {
		return on == false;
	}
	
	//Method to return profit or loss
	public float PL() { //negative for a loss and positive for a win.
		if (!isClosed()) {
			System.err.println("Cannot compute PL if trade is on");
			return -1f;
		}
		if (dir == Direction.SHORT) {
			return (entryPrice - exitPrice) * numberOfShares;
		}
		//Long
		return (exitPrice - entryPrice) * numberOfShares;
	}
	
	public float PercentPL() { //Returns the winnings or loss in percent
		if (!isClosed()) {
			System.err.println("Cannot compute PL if trade is on");
			return -1f;
		}
		if(dir == Direction.LONG) {//If long
			//(Sell price - Buy price) / Buy price * 100
			return ((exitPrice - entryPrice)/entryPrice)*100;
		}
		//If short
		//(Sell price - Buy price) / Sell price * 100
		return ((entryPrice - exitPrice)/entryPrice)*100;
	}

	public String toString() {
		String st = entryDate.toString() + ",  " + entryPrice + ", " + exitDate.toString() + ","
				+ exitPrice + ", " + dir + (PL() >= 0);
		return st;
	}


	
	
}	
