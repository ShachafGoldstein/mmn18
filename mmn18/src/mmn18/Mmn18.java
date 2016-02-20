/**
 * 
 */
package mmn18;

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
		
		for (String s : input.split("\n")) {
			System.out.println(executeAction(s));
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
		tree.insert(new RBTreeNode(null,null,null,newClient,RBTreeNodeColor.RED));
		
		return String.format("New client %s(%n) was added with account %n and a balance of %n",
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
		// Delete by account id
		RBTreeNode removedNode = tree.delete(new RBTreeNode(null, null, null, new Client(null, 0, Long.parseLong(vars[1]), 0), RBTreeNodeColor.RED));;
		
		return String.format("Client %s(%n) with account %n was deleted",
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
		
		return String.format("Client %s(%n), with account %n, has changed its balance to %n",
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
				retMsg = String.format("Client %s(%n), with account %n, has a balance of %n",
						node.getValue().getName(),
						node.getValue().getId(),
						node.getValue().getAccountNumber(),
						node.getValue().getBalance());
				break;
		}
		
		return retMsg;
	}
}
