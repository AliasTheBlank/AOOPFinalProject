package BasisClasses;

public class StringFormatter {

	public String Format(Article article, FormatType type) {
		
		switch (type) {
		case IEEE: {
			
			return "";
		}
		
		case ACM: {
			
			return "";
		}
		
		case NJ: {
			
			return "";
		}
		
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
}
