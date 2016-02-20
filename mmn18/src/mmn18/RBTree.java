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
	
	private RBTreeNode root;
	
	/**
	 * 
	 */
	public RBTree() {
		root = RBTreeNode.nil;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public RBTreeNode delete(RBTreeNode node) {
		RBTreeNode x, y;
		
		if (node.getLeft() == RBTreeNode.nil || node.getRight() == RBTreeNode.nil) {
			y = node;
		}
		else {
			y = successor(node);
		}
		
		if (y.getLeft() != RBTreeNode.nil) {
			x = y.getLeft();
		}
		else {
			x = y.getRight();
		}
		
		x.setParent(y.getParent());
		
		if (y.getParent() == RBTreeNode.nil) {
			root = x;
		}
		else if (y.isLeftSon()) {
			y.getParent().setLeft(x);
		}
		else {
			y.getParent().setRight(x);
		}
		
		if (y != node) {
			Client temp = node.getValue();
			node.setValue(y.getValue());
			y.setValue(temp);
		}
		
		if (y.getColor() == RBTreeNodeColor.BLACK) {
			deleteFixup(x);
		}
		
		RBTreeNode.nil.setParent(RBTreeNode.nil);
		
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
			
			if (x.isLeftSon()) {
				w = x.getParent().getRight();
				
				if (w.getColor() == RBTreeNodeColor.RED) {
					w.setColor(RBTreeNodeColor.BLACK);
					x.getParent().setColor(RBTreeNodeColor.RED);
					leftRotate(x.getParent());
					w = x.getParent().getRight();
				}
				
				if (w.getLeft().getColor() == RBTreeNodeColor.BLACK &&
					w.getRight().getColor() == RBTreeNodeColor.BLACK ) {
					w.setColor(RBTreeNodeColor.RED);
					x = x.getParent();
				}
				else {
					if (w.getRight().getColor() == RBTreeNodeColor.BLACK) {
						w.getLeft().setColor(RBTreeNodeColor.BLACK);
						w.setColor(RBTreeNodeColor.RED);
						rightRotate(w);
						w = x.getParent().getRight();
					}
			
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(RBTreeNodeColor.BLACK);
					w.getRight().setColor(RBTreeNodeColor.BLACK);
					leftRotate(x.getParent());
					x = root;
				}
			}
			else {
				w = x.getParent().getLeft();
				
				if (w.getColor() == RBTreeNodeColor.RED) {
					w.setColor(RBTreeNodeColor.BLACK);
					x.getParent().setColor(RBTreeNodeColor.RED);
					rightRotate(x.getParent());
					w = x.getParent().getLeft();
				}
				
				if (w.getRight().getColor() == RBTreeNodeColor.BLACK &&
					w.getLeft().getColor() == RBTreeNodeColor.BLACK ) {
					w.setColor(RBTreeNodeColor.RED);
					x = x.getParent();
				}
				else {
					if (w.getLeft().getColor() == RBTreeNodeColor.BLACK) {
						w.getRight().setColor(RBTreeNodeColor.BLACK);
						w.setColor(RBTreeNodeColor.RED);
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
		RBTreeNode y = RBTreeNode.nil, x = root;
		
		while (x != RBTreeNode.nil) {
			y = x;
			
			if (node.getKey() < x.getKey()) {
				x = x.getLeft();
			}
			else {
				x = x.getRight();
			}
		}
		
		node.setParent(y);
		
		if (y == RBTreeNode.nil) {
			setRoot(node);
		}
		else if (node.getKey() < y.getKey()) {
			y.setLeft(node);
		}
		else {
			y.setRight(node);
		}
		
		node.setLeft(RBTreeNode.nil);
		node.setRight(RBTreeNode.nil);
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
			
			if (z.getParent().isLeftSon()) {
				
				RBTreeNode y = z.getGrandpa().getRight();
				
				if (y.getColor() == RBTreeNodeColor.RED) {
					z.getParent().setColor(RBTreeNodeColor.BLACK);
					y.setColor(RBTreeNodeColor.BLACK);
					z.getGrandpa().setColor(RBTreeNodeColor.RED);
					z = z.getGrandpa();
				}
				else if (!z.isLeftSon()) {
					z = z.getParent();
					leftRotate(z);
				}
				
				z.getParent().setColor(RBTreeNodeColor.BLACK);
				z.getGrandpa().setColor(RBTreeNodeColor.RED);
				rightRotate(z.getGrandpa());
			}
			else {
				RBTreeNode y = z.getGrandpa().getLeft();
				
				if (y.getColor() == RBTreeNodeColor.RED) {
					z.getParent().setColor(RBTreeNodeColor.BLACK);
					y.setColor(RBTreeNodeColor.BLACK);
					z.getGrandpa().setColor(RBTreeNodeColor.RED);
					z = z.getGrandpa();
				}
				else if (z.isLeftSon()) {
					z = z.getParent();
					rightRotate(z);
				}
				
				if (z.getParent() != RBTreeNode.nil) {
					z.getParent().setColor(RBTreeNodeColor.BLACK);
					if (z.getGrandpa() != RBTreeNode.nil) {
						z.getGrandpa().setColor(RBTreeNodeColor.RED);
						leftRotate(z.getGrandpa());
					}
				}
			}
		}
		
		root.setColor(RBTreeNodeColor.BLACK);
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public RBTreeNode predecessor(RBTreeNode node) {
		if (node.getLeft() != RBTreeNode.nil) {
			return treeMaximum(node.getLeft());
		}
		
		RBTreeNode predecessor = node.getParent(), x = node;
		
		while (predecessor != RBTreeNode.nil && x == predecessor.getLeft()) {
			x = predecessor;
			predecessor = predecessor.getParent();
		}
		
		return predecessor;
	}
	/**
	 * 
	 * @param axis
	 */
	private void leftRotate(RBTreeNode axis) {
		RBTreeNode oldRight = axis.getRight();
		
		axis.setRight(oldRight.getLeft());
		
		if (oldRight.getLeft() != RBTreeNode.nil) {
			oldRight.getLeft().setParent(axis);
		}
		
		oldRight.setParent(axis.getParent());
		
		if (axis.getParent() == RBTreeNode.nil) {
			setRoot(oldRight);
		}
		else if (axis.isLeftSon()) {
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
	 * @param axis
	 */
	private void rightRotate(RBTreeNode axis) {
		RBTreeNode oldLeft = axis.getLeft();
		
		axis.setLeft(oldLeft.getRight());
		
		if (oldLeft.getRight() != RBTreeNode.nil) {
			oldLeft.getRight().setParent(axis);
		}
		
		oldLeft.setParent(axis.getParent());
		
		if (axis.getParent() == RBTreeNode.nil) {
			setRoot(oldLeft);
		}
		else if (axis.isRightSon()) {
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
		if (node.getRight() != RBTreeNode.nil) {
			return treeMinimum(node.getRight());
		}

		RBTreeNode successor = node.getParent(), x = node;
		
		while (successor != RBTreeNode.nil && x == successor.getRight()) {
			x = successor;
			successor = successor.getParent();
		}
		
		return successor;
	}

	/**
	 * 
	 * @return
	 */
	public String toLevelString() {
		String retval = "";
		ArrayList<RBTreeNode> l = new ArrayList<RBTreeNode>();
		l.add(root);
		boolean b = true;
		while (b) {
			boolean allNil = true;
			ArrayList<RBTreeNode> children = new ArrayList<>(l.size()*2);
			for (RBTreeNode node : l) {
				if (node == RBTreeNode.nil) {
					retval += "nil ";
					children.add(RBTreeNode.nil);
					children.add(RBTreeNode.nil);
				}
				else {
					allNil = false;
					retval += ("" + node.getKey() + node.getColor().name().charAt(0) + " ");
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
		RBTreeNode max = node;
		
		while (max.getRight() != RBTreeNode.nil) {
			max = max.getRight();
		}
		
		return max;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private RBTreeNode treeMinimum(RBTreeNode node) {
		RBTreeNode min = node;
		
		while (min.getLeft() != RBTreeNode.nil) {
			min = min.getLeft();
		}
		
		return min;
	}
	
	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	public RBTreeNode searchByKey(long accountNumber) {
		RBTreeNode node = root;
		
		while (node != RBTreeNode.nil && node.getKey() != accountNumber)
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
	
	public String negativeBalances(RBTreeNode node) {
		
		if (node == RBTreeNode.nil) {
			return ""; 
		}
		
		return (node.getValue().getBalance() < 0 ? node.toString() : "") + negativeBalances(node.getLeft()) + negativeBalances(node.getRight());
	}
}