import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bar {
	Date date;
	String sym;
	float open;
	float high;
	float low;
	float close;
	float AdjClose;
	long volume = -999;//Default value in case of error parsing data
	
	public Bar(String bar){
		setBar(bar);
	}
	
	public Bar() {
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");
		}catch(ParseException e) {
			System.out.println("We got a problem, Date is not good!");
			System.exit(1);
		}
		open = 0f;
		high = 0f;
		low = 0f; 
		close = 0f;
		AdjClose = 0f;
		volume = 0;
	}

	public Date getDate() {
		return date;
	}
	
	public float getOpen() {
		return open;
	}
	
	public float getHigh() {
		return high;
	}
	
	public float getLow() {
		return low;
	}
	
	public float getClose() {
		return close;
	}
	
	public float getAdjClose() {
		return AdjClose;
	}
	
	public long getVolume() {
		return volume;
	}
	
	public float getRange() {
		return high - low;
	}
	
	public String getSym() {
		return sym;
	}
	
	public void setBar(String bar) {
		String[] arr = bar.split(","); 
		if (arr.length != 7) {
			System.err.println("The line " + bar + " is not a valid Bar");
		}
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(arr[0]);
		} catch (ParseException e) {
			try {
				date = new SimpleDateFormat("MM/dd/yyyy").parse(arr[0]);
			} catch (ParseException e1) {
				System.err.println("Invalid date" + arr[0]);
				return;
			}
		}
		try {
			open     = Float.parseFloat(arr[1].trim());
			high     = Float.parseFloat(arr[2].trim());
			low      = Float.parseFloat(arr[3].trim());
			close    = Float.parseFloat(arr[4].trim());
			AdjClose = Float.parseFloat(arr[5].trim());
			volume   = Long.parseLong(  arr[6].trim());
		}catch(NumberFormatException e) {
			System.out.println(arr[0] + arr[1] + arr[2] + arr[3]);
//			e.printStackTrace();
//			System.exit(-1);
		}
	}
	
	public String toString() {
		String st = date.toString()
				+ "," + open
				+ "," + high
				+ "," + low
				+ "," + close
				+ "," + AdjClose
				+ "," + volume;
		return st;
	}
	
	public void setSym(String s) {
		sym = s;
	}
	
	public void print() {
		String outputDate = new SimpleDateFormat("MM-dd-yyyy").format(date);
		
		System.out.println("Date: "     + outputDate);
		System.out.println("Open: "     + open      );
		System.out.println("High: "     + high      );
		System.out.println("low: "      + low       );
		System.out.println("close: "    + close     );
		System.out.println("AdjClose: " + AdjClose  );
		System.out.println("Volume: "   + volume    );
	}
}