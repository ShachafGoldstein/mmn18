/**
 * 
 */
package mmn18;

/**
 * @author shachaf
 *
 */
public class RBTreeNode {
	private RBTreeNode left;
	private RBTreeNode right;
	private RBTreeNode parent;
	private RBTreeNode max;
	private Client value;
	private RBTreeNodeColor color;

	/**
	 * @param left
	 * @param right
	 * @param parent
	 * @param value
	 * @param color
	 */
	public RBTreeNode(RBTreeNode left, RBTreeNode right, RBTreeNode parent, Client value, RBTreeNodeColor color) {
		super();
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.value = value;
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public RBTreeNodeColor getColor() {
		return color;
	}

	/**
	 * 
	 * @return
	 */
	public long getKey()
	{
		return getValue().getAccountNumber();
	}

	/**
	 * @return the left
	 */
	public RBTreeNode getLeft() {
		return left;
	}

	/**
	 * @return the max
	 */
	public RBTreeNode getMax() {
		return max;
	}

	/**
	 * @return the parent
	 */
	public RBTreeNode getParent() {
		return parent;
	}

	/**
	 * @return the right
	 */
	public RBTreeNode getRight() {
		return right;
	}

	/**
	 * @return the value
	 */
	public Client getValue() {
		return value;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(RBTreeNodeColor color) {
		this.color = color;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(RBTreeNode left) {
		this.left = left;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(RBTreeNode max) {
		this.max = max;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(RBTreeNode parent) {
		this.parent = parent;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(RBTreeNode right) {
		this.right = right;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Client value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RBTreeNode [left=" + left + ", right=" + right + ", parent=" + parent + ", max=" + max + ", value="
				+ value + ", color=" + color + "]";
	}
	
}
