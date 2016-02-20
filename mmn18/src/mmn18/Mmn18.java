/**
 * 
 */
package mmn18;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Guy
 *
 */
public class Mmn18 {
	
	private static RBTree tree = new RBTree();

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
		
		input = "+ JOHN DOE 123456789 1 300\n"
			+	"+ JOHN DOE 023456789 2 1000\n"
			+	"+ JOHN DOE 023456789 10 1000\n"
			+	"+ JOHN DOE 023456789 6 1000\n"
			+	"+ JOHN DOE 023456789 4 1000\n"
			+	"+ JOHN DOE 023456789 16 1000\n"
			+	"+ JOHN DOE 023456789 8 1000\n"
			+	"+ JOHN DOE 023456789 25 1000\n"
			+	"+ JOHN DOE 023456789 3 1000\n"
			+	"+ JOHN DOE 023456789 20 1000\n";
		
		for (String s : input.split("\n")) {
			System.out.println(executeAction(s));
		}
		
		System.out.println(tree.toLevelString());
		
		try {
			tree.getRoot().printTree(System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String executeAction(String action) {
		
		String retMsg = "";
		String[] vars = action.split(" ");
		
		switch (vars[0].charAt(0)) {
		// Insert new client
		case '+':
			retMsg = createClient(vars);
			break;
		// Remove client
		case '-':
			retMsg = removeClient(vars);
			break;
		// Queries
		case '?':
			retMsg = query(vars);
			break;
		// Change balance
		default:
			retMsg = changeBalance(vars);
			break;
		}
		
		return retMsg;
	}
	
	/**
	 * 
	 * @param vars
	 * @return
	 */
	public static String createClient(String[] vars) {
		// Create a new client object
		Client newClient = new Client(
								vars[1] + ' ' + vars[2],
								Long.parseLong(vars[3]),
								Long.parseLong(vars[4]),
								Long.parseLong(vars[5]));
		
		// Insert a new node to the tree with the new client object
		tree.insert(new RBTreeNode(RBTreeNode.nil,RBTreeNode.nil,RBTreeNode.nil,newClient,RBTreeNodeColor.RED));
		
		return String.format("New client %s(%d) was added with account %d and a balance of %d",
							newClient.getName(),
							newClient.getId(),
							newClient.getAccountNumber(),
							newClient.getBalance());
	}
	
	/**
	 * 
	 * @param vars
	 * @return
	 */
	public static String removeClient(String[] vars) {
		RBTreeNode removedNode = tree.searchByKey(Long.parseLong(vars[1]));
		// Delete by account id
		removedNode = tree.delete(removedNode);
		
		return String.format("Client %s(%d) with account %d was deleted",
				removedNode.getValue().getName(),
				removedNode.getValue().getId(),
				removedNode.getValue().getAccountNumber());
	}
	
	/**
	 * 
	 * @param vars
	 * @return
	 */
	public static String changeBalance(String[] vars) {
		// Find node
		RBTreeNode node = tree.searchByKey(Long.parseLong(vars[2]));
		
		// Change the balance
		node.getValue().addToBalance(Long.parseLong(vars[3]));
		
		return String.format("Client %s(%d), with account %d, has changed its balance to %d",
				node.getValue().getName(),
				node.getValue().getId(),
				node.getValue().getAccountNumber(),
				node.getValue().getBalance());
	}
	
	/**
	 * 
	 * @param vars
	 * @return
	 */
	public static String query(String[] vars) {
		String retMsg = "";
		
		switch (vars[1].toUpperCase()) {
			case "MINUS":
				//TODO return all negatives
				retMsg = "Not implemented yet - minus";
				break;
				
			case "MAX":
				retMsg = "Maximum balance of clients is " + tree.getRoot().getMax().getValue().getBalance();
				break;
				
			default:
				RBTreeNode node = tree.searchByKey(Long.parseLong(vars[2]));
				retMsg = String.format("Client %s(%d), with account %d, has a balance of %d",
						node.getValue().getName(),
						node.getValue().getId(),
						node.getValue().getAccountNumber(),
						node.getValue().getBalance());
				break;
		}
		
		return retMsg;
	}
}
