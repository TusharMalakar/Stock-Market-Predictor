import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class DataArray {
	private Vector<Bar> mData;
	private String mSymbol, mPath;
	
	DataArray(String s, String p){
		mData = new Vector<Bar>(1000,100);
		mSymbol = s;
		mPath = p;
	}	
	
	DataArray(int initSize, String s, String p) {
		mData = new Vector<Bar>(initSize, 1000);
		mSymbol = s;
		mPath = p;
	}
	
	//Accessor
	public String getSymbol() {
		return mSymbol;
	}
	
	public Vector<Bar> getData() {
		return mData;
	}
	
	public String getPath() {
		return mPath;
	}
	
	public int getSize() {
		return mData.size();
	}
	
	//Mutators
	public void setPath(String s) {
		mPath = s;
	}

	public void setData(Vector<Bar> mData) {
		this.mData = mData;
	}
	
	public void setSymbol(String s) {
		mSymbol = s;
	}
	
	public void Resize(int size) {
		mData.setSize(size);
	}
	
	public boolean isEmpty() {
		return mData.size() == 0;
	}
	
	public boolean isFull() {
		return mData.size() == mData.capacity();
	}
	
	public void AddTail(Bar element) {
		mData.add(element);
	}
	
	public void AddHead(Bar element) {
		mData.add(0, element);
	}
	
	public void insert(int index, Bar element) {
		mData.add(index, element);
	}
	
	public Bar At(int index) {
		if (index < 0 || index >= mData.size())
			return null;
		return mData.elementAt(index);
	}
	
	public Bar RemoveHead() {
		return mData.remove(0);
	}
	
	public Bar Remove(int index) {
		return mData.remove(index);
	}
	
	public Bar RemoveTail() {
		return mData.remove(getSize()-1);
	}
	
	public int Load() {
		int count = 0;
		BufferedReader reader = null;
		//Open file path
		try {
			reader= new BufferedReader(new FileReader(mPath + "\\" + mSymbol + ".csv"));
			String line;
			//Skip headers
			reader.readLine();
			//While lines
			while((line = reader.readLine())!=null) {
				//Read a line
				
				//Create a Bar with line as input
				Bar temp = new Bar(line);
				if(temp.getVolume()!=-999) {
					temp.setSym(mSymbol);
					//Add Bar to the Vector
					mData.add(temp);
					//Increment count
					count++;
					
				}
			}
			
		} catch (FileNotFoundException e) {
		    System.err.println("Exception occurred trying to read " + mPath);
			e.printStackTrace();
		} catch(IOException e) {
			System.err.println("Error reading line");
		} finally { 
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
}
