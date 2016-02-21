package mmn18;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Shachaf Goldstein & Guy Sahar
 *
 *	Class to represent and handle a node of the Red-Black tree
 */
public class RBTreeNode {

	/*
	 * Single representation of NIL
	 */
	public static final RBTreeNode nil;

	/**
	 * Init NIL node 
	 */
	static {
		nil = new RBTreeNode(null, null, null, null, RBTreeNodeColor.BLACK);

		nil.setLeft(nil);
		nil.setRight(nil);
		nil.setParent(nil);
	}

	/*
	 * The left child node
	 */
	private RBTreeNode left;
	/*
	 * The right child node
	 */
	private RBTreeNode right;
	/*
	 * The parent node
	 */
	private RBTreeNode parent;
	/*
	 * The max of node's sub tree
	 */
	private RBTreeNode max;
	/*
	 * The node's value
	 */
	private Client value;
	/*
	 * The node's color
	 */
	private RBTreeNodeColor color;

	/**
	 * Create a new node with given parameters
	 * 
	 * @param left - The left child node
	 * @param right - The right child node
	 * @param parent - The parent node
	 * @param value - The value of the node
	 * @param color - The color of the node
	 */
	public RBTreeNode(RBTreeNode left, RBTreeNode right, RBTreeNode parent, Client value, RBTreeNodeColor color) {
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.value = value;
		this.color = color;
		
		// Init max to current node at creation
		max = this;
	}

	/**
	 * Get the brother (left or right) of current node
	 * 
	 * @return - The left/right brother of current node
	 */
	public RBTreeNode getBrother() {
		// Return left if right and right if left, otherwise NIL
		if (isLeftSon()) {
			return getParent().getRight();
		} else if (isRightSon()) {
			return getParent().getLeft();
		}
		
		return nil;
	}

	/**
	 * @return the color of current node
	 */
	public RBTreeNodeColor getColor() {
		return color;
	}

	/**
	 * Get the grandfather of current node
	 * 
	 * @return - The grandfather of current node
	 */
	public RBTreeNode getGrandpa() {
		return getParent().getParent();
	}

	/**
	 * Get the key of current node
	 * @return - The key of current node
	 */
	public long getKey() {
		return getValue().getAccountNumber();
	}

	/**
	 * @return the left child of current node
	 */
	public RBTreeNode getLeft() {
		return left;
	}

	/**
	 * @return the max of current node's sub tree
	 */
	public RBTreeNode getMax() {
		return max;
	}

	/**
	 * @return the parent of current node
	 */
	public RBTreeNode getParent() {
		return parent;
	}

	/**
	 * @return the right child of current node
	 */
	public RBTreeNode getRight() {
		return right;
	}

	/**
	 * Get the uncle of current node ( The brother of the parent)
	 * 
	 * @return - The uncle of current node
	 */
	public RBTreeNode getUncle() {
		return getParent().getBrother();
	}

	/**
	 * @return the value of current node
	 */
	public Client getValue() {
		return value;
	}

	/**
	 * Check if current node is the left child of its father
	 * 
	 * @return - True if current node is the left child of its father
	 */
	public boolean isLeftSon() {
		return (this == getParent().getLeft());
	}

	/**
	 * Check if current node is the right child of its father
	 * 
	 * @return - True if current node is the right child of its father
	 */
	public boolean isRightSon() {
		return (this == getParent().getRight());
	}

	private void printNodeValue(OutputStream out) throws IOException {
		if (equals(nil)) {
			out.write("NIL".getBytes());
		} else {
			out.write((getKey() + "" + getColor().name().charAt(0) + "" + max.getKey() + "-"
					+ max.getValue().getBalance()).getBytes());
		}
		out.write('\n');
	}

	public void printTree(OutputStream out) throws IOException {
		if (right != nil) {
			right.printTree(out, true, "");
		}
		printNodeValue(out);
		if (left != nil) {
			left.printTree(out, false, "");
		}
	}

	// use string and not stringbuffer on purpose as we need to change the
	// indent at each recursion
	private void printTree(OutputStream out, boolean isRight, String indent) throws IOException {
		if (right != nil) {
			right.printTree(out, true, indent + (isRight ? "        " : " |      "));
		}
		out.write(indent.getBytes());
		if (isRight) {
			out.write(" /".getBytes());
		} else {
			out.write(" \\".getBytes());
		}
		out.write("----- ".getBytes());
		printNodeValue(out);
		if (left != nil) {
			left.printTree(out, false, indent + (isRight ? " |      " : "        "));
		}
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(RBTreeNodeColor color) {
		this.color = color;
	}

	/**
	 * @param left
	 *            the left child to set
	 */
	public void setLeft(RBTreeNode left) {
		this.left = left;
	}

	/**
	 * @param max
	 *            the max node to set
	 */
	public void setMax(RBTreeNode max) {
		this.max = max;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(RBTreeNode parent) {
		this.parent = parent;
	}

	/**
	 * @param right
	 *            the right child to set
	 */
	public void setRight(RBTreeNode right) {
		this.right = right;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Client value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (this == nil) ? "NIL" : "" + value;
	}
}
