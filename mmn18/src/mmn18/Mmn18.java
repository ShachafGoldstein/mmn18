/**
 * 
 */
package mmn18;

import java.util.Iterator;

/**
 * @author Guy
 *
 */
public class Mmn18 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input = 	"+ JOHN DOE 123456789 1 300\n"
					+	"+ JOHN DOE 023456789 2 1000\n"
					+	"JOHN DOE 1 -1000\n"
					+	"? MAX\n"
					+	"+ JOHN DOE 023456787 3 -200\n"
					+	"? MINUS\n"
					+	"- 2\n"
					+	"? 1\n";
		
		for (String s : input.split("\n")) {
			System.out.println(executeAction(s));
		}
	}
	
	public static String executeAction(String action) {
		
		for (char c : action.toCharArray()) {
			
		}
		
		return "";
	}
}
