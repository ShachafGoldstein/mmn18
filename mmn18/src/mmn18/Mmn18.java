package mmn18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Shachaf Goldstein & Guy Sahar
 *
 *	Main class to manage the bank
 */
public class Mmn18 {
	
	/*
	 * Single reference to the Red-Black tree
	 */
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
			+	"+ JOHN DOE 023456789 2 2500\n"
			+	"+ JOHN DOE 023456789 10 10000\n"
			+	"+ JOHN DOE 023456789 6 6000\n"
			+	"+ JOHN DOE 023456789 4 400\n"
			+	"+ JOHN DOE 023456789 16 1600\n"
			+	"+ JOHN DOE 023456789 8 -5000\n"
			+	"+ JOHN DOE 023456789 25 -600\n"
			+	"+ JOHN DOE 023456789 3 1080\n"
			+	"+ JOHN DOE 023456789 20 1500\n"
			+	"- 16\n"
			+	"- 8\n"
			+	"- 10\n"
			+ 	"JOHN DOE 1 -1000\n"
			+	"- 6\n"
			+	"? 25\n"
			+	"? MINUS\n"
			+	"? MAX\n";
		
		for (String s : input.split("\n")) {
			System.out.println(executeAction(s));
		}

		try {
			tree.getRoot().printTree(System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		// Get input line to parse
		try {
			System.out.println("Please write an action and then press ENTER");
			input = reader.readLine().toUpperCase();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		while (!"EXIT".equals(input.toUpperCase()))
		{
			System.out.println(executeAction(input));

			try {
				tree.getRoot().printTree(System.out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Get input line to parse
			try {
				System.out.println("Please write an action and then press ENTER");
				input = reader.readLine().toUpperCase();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}

		/*try {
			tree.getRoot().printTree(System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		//System.out.println(tree.toLevelString());
	}
	
	/**
	 * Parse an action string and execute the relevant action
	 * 
	 * @param action - Line to parse
	 * @return - The string result
	 */
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
	 * Create a new client and add to tree
	 * 
	 * @param vars - Parameters to create from
	 * @return - The string result
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
	 * Remove a client
	 * 
	 * @param vars - The parameters to delete by
	 * @return - The string result
	 */
	public static String removeClient(String[] vars) {
		
		// Search and delete node
		RBTreeNode removedNode = tree.delete(tree.searchByKey(Long.parseLong(vars[1])));
		
		return String.format("Client %s(%d) with account %d was deleted",
				removedNode.getValue().getName(),
				removedNode.getValue().getId(),
				removedNode.getValue().getAccountNumber());
	}
	
	/**
	 * Change the balance of a client by given value
	 * 
	 * @param vars - The parameters to change by
	 * @return - The string result
	 */
	public static String changeBalance(String[] vars) {
		// Find client
		RBTreeNode node = tree.searchByKey(Long.parseLong(vars[2]));
		
		// Change client balance
		node.getValue().addToBalance(Long.parseLong(vars[3]));
		
		return String.format("Client %s(%d), with account %d, has changed its balance to %d",
				node.getValue().getName(),
				node.getValue().getId(),
				node.getValue().getAccountNumber(),
				node.getValue().getBalance());
	}
	
	/**
	 * Query a client balance, all negative balance clients or maximum balance client
	 * 
	 * @param vars - The parameters of the query
	 * @return - The string result
	 */
	public static String query(String[] vars) {
		String retMsg = "";
		
		switch (vars[1].toUpperCase()) {
			// Get all negative balanced clients
			case "MINUS":
				retMsg = RBTree.negativeBalances(tree.getRoot());
				break;
			// Get maximum balanced client
			case "MAX":
				retMsg = "Maximum balance of clients is " + tree.getRoot().getMax();
				break;
			// Get balance of a specified client
			default:
				RBTreeNode node = tree.searchByKey(Long.parseLong(vars[1]));
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