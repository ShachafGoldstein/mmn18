/**
 * 
 */
package mmn18;

import java.util.*;

/**
 * @author Guy
 *
 */
public final class TestCases {
	
	public static final String increasingInsertion; /* =
		"+ JOHN DOE 123456789 1 300\n"
	+	"+ JOHN DOE 023456789 2 2500\n"
	+	"+ JOHN DOE 023456789 3 10000\n"
	+	"+ JOHN DOE 023456789 4 6000\n"
	+	"+ JOHN DOE 023456789 5 400\n"
	+	"+ JOHN DOE 023456789 6 1600\n"
	+	"+ JOHN DOE 023456789 7 -5000\n"
	+	"+ JOHN DOE 023456789 8 -600\n"
	+	"+ JOHN DOE 023456789 9 1080\n"
	+	"+ JOHN DOE 023456789 10 400\n"
	+	"+ JOHN DOE 023456789 11 1600\n"
	+	"+ JOHN DOE 023456789 12 -5000\n"
	+	"+ JOHN DOE 023456789 13 -600\n"
	+	"+ JOHN DOE 023456789 14 1080\n"
	+	"+ JOHN DOE 023456789 15 -5000\n"
	+	"+ JOHN DOE 023456789 16 -600\n"
	+	"+ JOHN DOE 023456789 17 1080\n"
	+	"+ JOHN DOE 023456789 18 400\n"
	+	"+ JOHN DOE 023456789 19 1600\n"
	+	"+ JOHN DOE 023456789 20 -5000\n"
	+	"+ JOHN DOE 023456789 21 -600\n"
	+	"+ JOHN DOE 023456789 22 1080\n"
	+	"+ JOHN DOE 023456789 23 -3000\n"
	+	"+ JOHN DOE 023456789 24 -600\n"
	+	"+ JOHN DOE 023456789 25 1080\n";*/
	
	public static final String decreasingInsertion;
	public static final String increasingRemoval;
	public static final String decreasingRemoval;
	
	static {
		Random rnd = new Random();
		StringBuilder inc = new StringBuilder(), dec = new StringBuilder();
		String[] lines = new String[50];
		
		for (int i = 0; i < lines.length; ++i) {
			lines[i] = String.format("+ JOHN DOE %d %d %d\n", i, i, rnd.nextInt(10001) - 5000); 
		}
		
		for (String s : lines) {
			inc.append(s);
			dec.insert(0, s);
		}
		
		increasingInsertion = inc.toString();
		decreasingInsertion = dec.toString();
		
		inc.setLength(0);
		dec.setLength(0);
		
		for (String s : lines) {
			String acc = String.format("- %s\n", s.split(" ")[4]);
			inc.append(acc);
			dec.insert(0, acc);
		}
		
		increasingRemoval = inc.toString();
		decreasingRemoval = dec.toString();
		
		inc.setLength(0);
		dec.setLength(0);
		
		/*String[] lines = {
				"+ JOHN DOE 123456789 1 300\n",
				"+ JOHN DOE 023456789 2 2500\n",
				"+ JOHN DOE 023456789 3 10000\n",
				"+ JOHN DOE 023456789 4 6000\n",
				"+ JOHN DOE 023456789 5 400\n",
				"+ JOHN DOE 023456789 6 1600\n",
				"+ JOHN DOE 023456789 7 -5000\n",
				"+ JOHN DOE 023456789 8 -600\n",
				"+ JOHN DOE 023456789 9 1080\n",
				"+ JOHN DOE 023456789 10 400\n",
				"+ JOHN DOE 023456789 11 1600\n",
				"+ JOHN DOE 023456789 12 -5000\n",
				"+ JOHN DOE 023456789 13 -600\n",
				"+ JOHN DOE 023456789 14 1080\n",
				"+ JOHN DOE 023456789 15 -5000\n",
				"+ JOHN DOE 023456789 16 -600\n",
				"+ JOHN DOE 023456789 17 1080\n",
				"+ JOHN DOE 023456789 18 400\n",
				"+ JOHN DOE 023456789 19 1600\n",
				"+ JOHN DOE 023456789 20 -5000\n",
				"+ JOHN DOE 023456789 21 -600\n",
				"+ JOHN DOE 023456789 22 1080\n",
				"+ JOHN DOE 023456789 23 -3000\n",
				"+ JOHN DOE 023456789 24 -600\n",
				"+ JOHN DOE 023456789 25 1080\n"
			};*/
	}
}
