/**
 * 
 */
package mmn18;

/**
 * @author Guy
 *
 */
public class RedBlackTree {
	private TreeNode root;

	/**
	 * @param root
	 */
	public RedBlackTree(TreeNode root) {
		super();
		this.root = root;
	}

	/**
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}
}
