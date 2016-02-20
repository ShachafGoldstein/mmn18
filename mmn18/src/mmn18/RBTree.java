/**
 * 
 */
package mmn18;

import java.util.ArrayList;

/**
 * @author shachaf
 *
 */
public class RBTree {
	
	private static RBTreeNode nil = new RBTreeNode(null, null, null, null, RBTreeNodeColor.BLACK);
	
	private RBTreeNode root;
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public RBTreeNode delete(RBTreeNode node) {
		RBTreeNode x, y;
		if (node.getLeft() == nil || node.getRight() == nil) {
			y = node;
		}
		else {
			y = successor(node);
		}
		if (y.getLeft() != nil) {
			x = y.getLeft();
		}
		else {
			x = y.getRight();
		}
		x.setParent(y.getParent());
		if (y.getParent() == nil) {
			root = x;
		}
		else if (y == y.getParent().getLeft()) {
			y.getParent().setLeft(x);
		}
		else {
			y.getParent().setRight(x);
		}
		if (y != node) {
			node.setValue(y.getValue());
		}
		if (y.getColor() == RBTreeNodeColor.BLACK) {
			deleteFixup(node);
		}
		return y;
	}
	
	/**
	 * 
	 * @param node
	 */
	private void deleteFixup(RBTreeNode node) {
		RBTreeNode x = node;
		while (x != root && x.getColor() == RBTreeNodeColor.BLACK) {
			RBTreeNode w;
			if (x == x.getParent().getLeft()) {
				w = x.getParent().getRight();
				if (w.getColor() == RBTreeNodeColor.RED) {
					x.getParent().setColor(RBTreeNodeColor.RED);
					leftRotate(x.getParent());
					w = x.getParent().getRight();
				}
				if (w.getLeft().getColor() == RBTreeNodeColor.BLACK &&
					w.getRight().getColor() == RBTreeNodeColor.BLACK ) {
					w.setColor(RBTreeNodeColor.RED);
					x = x.getParent();
				}
				else if (w.getRight().getColor() == RBTreeNodeColor.BLACK) {
					w.getLeft().setColor(RBTreeNodeColor.BLACK);
					rightRotate(w);
					w = x.getParent().getRight();
				}
				w.setColor(x.getParent().getColor());
				x.getParent().setColor(RBTreeNodeColor.BLACK);
				w.getRight().setColor(RBTreeNodeColor.BLACK);
				leftRotate(x.getParent());
				x = root;
			}
			else {
				w = x.getParent().getLeft();
				if (w.getColor() == RBTreeNodeColor.RED) {
					x.getParent().setColor(RBTreeNodeColor.RED);
					rightRotate(x.getParent());
					w = x.getParent().getLeft();
				}
				if (w.getRight().getColor() == RBTreeNodeColor.BLACK &&
					w.getLeft().getColor() == RBTreeNodeColor.BLACK ) {
					w.setColor(RBTreeNodeColor.RED);
					x = x.getParent();
				}
				else if (w.getLeft().getColor() == RBTreeNodeColor.BLACK) {
					w.getRight().setColor(RBTreeNodeColor.BLACK);
					leftRotate(w);
					w = x.getParent().getLeft();
				}
				w.setColor(x.getParent().getColor());
				x.getParent().setColor(RBTreeNodeColor.BLACK);
				w.getLeft().setColor(RBTreeNodeColor.BLACK);
				rightRotate(x.getParent());
				x = root;
			}
		}
		x.setColor(RBTreeNodeColor.BLACK);
	}

	/**
	 * @return the root
	 */
	public RBTreeNode getRoot() {
		return root;
	}
	
	/**
	 * 
	 * @param node
	 */
	public void insert(RBTreeNode node) {
		RBTreeNode y = nil, x = root;
		while (x != nil) {
			y = x;
			if (node.getKey() < x.getKey()) {
				x = x.getLeft();
			}
			else {
				x = x.getRight();
			}
		}
		node.setParent(y);
		if (y == nil) {
			setRoot(node);
		}
		else if (node.getKey() < x.getKey()) {
			y.setLeft(node);
		}
		else {
			y.setRight(node);
		}
		node.setLeft(nil);
		node.setRight(nil);
		node.setColor(RBTreeNodeColor.RED);
		insertFixup(node);
	}
	
	/**
	 * 
	 * @param node
	 */
	private void insertFixup(RBTreeNode node) {
		RBTreeNode z = node;
		while (z.getParent().getColor() == RBTreeNodeColor.RED) {
			if (z.getParent() == z.getParent().getParent().getLeft()) {
				RBTreeNode y = z.getParent().getParent().getRight();
				if (y.getColor() == RBTreeNodeColor.RED) {
					z.getParent().setColor(RBTreeNodeColor.BLACK);
					y.setColor(RBTreeNodeColor.BLACK);
					z.getParent().getParent().setColor(RBTreeNodeColor.RED);
					z = z.getParent().getParent();
				}
				else if (z == z.getParent().getRight()) {
					z = z.getParent();
					leftRotate(z);
				}
				z.getParent().setColor(RBTreeNodeColor.BLACK);
				z.getParent().getParent().setColor(RBTreeNodeColor.RED);
				rightRotate(z.getParent().getParent());
			}
			else {
				RBTreeNode y = z.getParent().getParent().getLeft();
				if (y.getColor() == RBTreeNodeColor.RED) {
					z.getParent().setColor(RBTreeNodeColor.BLACK);
					y.setColor(RBTreeNodeColor.BLACK);
					z.getParent().getParent().setColor(RBTreeNodeColor.RED);
					z = z.getParent().getParent();
				}
				else if (z == z.getParent().getLeft()) {
					z = z.getParent();
					rightRotate(z);
				}
				z.getParent().setColor(RBTreeNodeColor.BLACK);
				z.getParent().getParent().setColor(RBTreeNodeColor.RED);
				leftRotate(z.getParent().getParent());
			}
		}
		root.setColor(RBTreeNodeColor.BLACK);
	}
	/**
	 * 
	 * @param axis
	 */
	private void leftRotate(RBTreeNode axis) {
		RBTreeNode oldRight = axis.getRight();
		axis.setRight(oldRight.getLeft());
		if (oldRight.getLeft() != nil) {
			oldRight.getLeft().setParent(axis);
		}
		oldRight.setParent(axis.getParent());
		if (axis.getParent() == nil) {
			setRoot(oldRight);
		}
		else if (axis == axis.getParent().getLeft()) {
			axis.getParent().setLeft(oldRight);
		}
		else {
			axis.getParent().setRight(oldRight);
		}
		oldRight.setLeft(axis);
		axis.setParent(oldRight);
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public RBTreeNode predecessor(RBTreeNode node) {
		if (node.getLeft() != nil) {
			return treeMaximum(node.getLeft());
		}
		RBTreeNode predecessor = node.getParent(), x = node;
		while (predecessor != nil && x == predecessor.getLeft()) {
			x = predecessor;
			predecessor = predecessor.getParent();
		}
		return predecessor;
	}
	
	/**
	 * 
	 * @param axis
	 */
	private void rightRotate(RBTreeNode axis) {
		RBTreeNode oldLeft = axis.getLeft();
		axis.setLeft(oldLeft.getRight());
		if (oldLeft.getRight() != nil) {
			oldLeft.getRight().setParent(axis);
		}
		oldLeft.setParent(axis.getParent());
		if (axis.getParent() == nil) {
			setRoot(oldLeft);
		}
		else if (axis == axis.getParent().getRight()) {
			axis.getParent().setRight(oldLeft);
		}
		else {
			axis.getParent().setLeft(oldLeft);
		}
		oldLeft.setRight(axis);
		axis.setParent(oldLeft);
	}
	
	/**
	 * @param root the root to set
	 */
	public void setRoot(RBTreeNode root) {
		this.root = root;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public RBTreeNode successor(RBTreeNode node) {
		if (node.getRight() != nil) {
			return treeMinimum(node.getRight());
		}
		RBTreeNode successor = node.getParent(), x = node;
		while (successor != nil && x == successor.getRight()) {
			x = successor;
			successor = successor.getParent();
		}
		return successor;
	}
	
	public String toLevelString() {
		String retval = "";
		ArrayList<RBTreeNode> l = new ArrayList<RBTreeNode>();
		l.add(root);
		boolean b = true;
		while (b) {
			boolean allNil = true;
			ArrayList<RBTreeNode> children = new ArrayList<>(l.size()*2);
			for (RBTreeNode node : l) {
				if (node == nil) {
					retval += "nil ";
					children.add(nil);
					children.add(nil);
				}
				else {
					allNil = false;
					retval += (node.getKey() + ' ');
					children.add(node.getLeft());
					children.add(node.getRight());
				}
			}
			b = !allNil;
			retval += '\n';
			l = children;
		}
		return retval;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RBTree [root=" + root + "]";
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	private RBTreeNode treeMaximum(RBTreeNode node) {
		RBTreeNode max = nil;
		
		while (node.getRight() != nil) {
			max = node.getRight();
		}
		
		return max;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private RBTreeNode treeMinimum(RBTreeNode node) {
		RBTreeNode min = nil;
		
		while (node.getLeft() != nil) {
			min = node.getLeft();
		}
		
		return min;
	}
	
	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	public RBTreeNode searchByKey(long accountNumber)
	{
		RBTreeNode node = root;
		
		while (node != nil && node.getKey() != accountNumber)
		{
			if(accountNumber < node.getKey())
			{
				node = node.getLeft();
			}
			else
			{
				node = node.getRight();
			}
		}
		
		return node;
	}
}