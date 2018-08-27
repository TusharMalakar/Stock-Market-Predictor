
public class Main {
	public static void main(String[] args) {
		DataArray da = new DataArray("GSPC", "C:/Users/Luis/Documents/^GSPC.csv");
		System.out.println("Items loaded: " + da.Load());
	}
}
