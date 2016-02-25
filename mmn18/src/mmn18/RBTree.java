package mmn18;

/**
 * @author Shachaf Goldstein & Guy Sahar
 *
 *	Class to represent and handle the Red-Black tree
 */
public class RBTree {

	/**
	 * Return a string representation of negative balance nodes
	 * @param node - The node to start from
	 * @return - The string representation
	 */
	public static String negativeBalances(RBTreeNode node) {

		// Recursion stop point
		if (node.isNil()) {
			return "";
		}

		return (node.getValue().getBalance() < 0 ? node.toString() : "") + negativeBalances(node.getLeft())
				+ negativeBalances(node.getRight());
	}

	/**
	 * Get the successor node to the given node (by value of the key)
	 *
	 * @param node
	 *            - The node to start from
	 * @return - The node with the successor key to the given node
	 */
	public static RBTreeNode successor(RBTreeNode node) {

		// If node has a right son get the minimum node of the right sub tree
		if (node.hasRightSon()) {
			return treeMinimum(node.getRight());
		}

		RBTreeNode successor = node.getParent(), tempNode = node;

		// Iterate to the right in node parent's sub tree
		while (!successor.isNil() && tempNode.isRightSon()) {
			tempNode = successor;
			successor = successor.getParent();
		}

		return successor;
	}

	/**
	 * Get the minimum node in the Red-Black tree
	 *
	 * @param node
	 *            - The node to start from
	 * @return - The minimum node in the Red-Black tree
	 */
	private static RBTreeNode treeMinimum(RBTreeNode node) {
		RBTreeNode min = node;

		// Iterate to the left until NIL
		while (min.hasLeftSon()) {
			min = min.getLeft();
		}

		return min;
	}

	/*
	 * The root node of the Red-Black tree
	 */
	private RBTreeNode root;

	/**
	 * Initialize Red-Black tree with a NIL node
	 */
	public RBTree() {
		root = RBTreeNode.nil;
	}

	/**
	 * Delete a node from the tree
	 *
	 * @param node
	 *            - The node to delete
	 * @return - The deleted node
	 */
	public RBTreeNode delete(RBTreeNode node) {
		// deletedNode - y, tempNode - x
		RBTreeNode tempNode, deletedNode;

		// Init deletedNode with the node it self it has a nil son or with its
		// successor otherwise
		if (!node.hasLeftSon() || !node.hasRightSon()) {
			deletedNode = node;
		} else {
			deletedNode = successor(node);
		}

		// Init tempNode with the left son if not NIL, otherwise with right son
		// of deletedNode
		if (deletedNode.hasLeftSon()) {
			tempNode = deletedNode.getLeft();
		} else {
			tempNode = deletedNode.getRight();
		}

		// Set deletedNode parent as tempNode parent
		tempNode.setParent(deletedNode.getParent());

		// replace value of node with deletedNode if not the same
		if (deletedNode != node) {
			Client temp = node.getValue();
			node.setValue(deletedNode.getValue());
			//node.setMax(deletedNode.getMax());
			node.setMax();
			deletedNode.setValue(temp);
		}

		// Check if deletedNode was the root
		if (!deletedNode.hasParent()) {
			setRoot(tempNode);
		} else {
			// Replace the reference to deletedNode in the parent
			if (deletedNode.isLeftSon()) {
				deletedNode.getParent().setLeft(tempNode);
			} else {
				deletedNode.getParent().setRight(tempNode);
			}
		}

		// Handle max values
		tempNode.setMax();
		//setMax(tempNode.getParent());

		// Call fixup if deletedNode is black
		if (deletedNode.isBlack()) {
			deleteFixup(tempNode);
		}

		// Make sure NIL stays clean
		RBTreeNode.nil.setParent(RBTreeNode.nil);

		return deletedNode;
	}

	/**
	 * Fix and balance the Red-Black tree after a deletion
	 *
	 * @param deletedNode
	 *            - The node that was deleted
	 */
	private void deleteFixup(RBTreeNode deletedNode) {
		// node - x
		RBTreeNode node = deletedNode;

		// Continue fixup as long as node isn't root and is black
		while ((node != root) && (node.isBlack())) {
			// tempNode - y
			RBTreeNode tempNode;
						
			boolean isLeft = node.isLeftSon();
			
			tempNode = node.getBrother();

			if (tempNode.isRed()) {
				tempNode.setBlack();
				node.getParent().setRed();
				
				if (isLeft) {
					leftRotate(node.getParent());			// Left if left son
					tempNode = node.getParent().getRight(); // Get right son
				} else {
					rightRotate(node.getParent());			// Right if right son
					tempNode = node.getParent().getLeft(); // Get left son
				}
			}

			if (tempNode.getLeft().isBlack() && tempNode.getRight().isBlack()) {
				tempNode.setRed();
				node = node.getParent();
			} else {
				
				if (isLeft) {
					if (tempNode.getRight().isBlack()) {
						tempNode.getLeft().setBlack();
						tempNode.setRed();
						rightRotate(tempNode);				// Right if left son
						tempNode = node.getParent().getRight(); // Get right son
					}
				} else {
					if (tempNode.getLeft().isBlack()) {
						tempNode.getRight().setBlack();
						tempNode.setRed();
						leftRotate(tempNode);				// Left if right son
						tempNode = node.getParent().getLeft(); // Get left son
					}
				}

				tempNode.setColor(node.getParent().getColor());
				node.getParent().setBlack();
				tempNode.getRight().setBlack();
				
				if (isLeft)	leftRotate(node.getParent()); // Right if left son
				else rightRotate(node.getParent());
				node = root;
			}
		}

		node.setBlack();
	}

	/**
	 * @return the root
	 */
	public RBTreeNode getRoot() {
		return root;
	}

	/**
	 * Insert a new node to the Red-Black tree
	 * @param node - The new node to insert
	 */
	public void insert(RBTreeNode node) {
		// nodeParent - y, tempNode - x
		RBTreeNode nodeParent = RBTreeNode.nil, tempNode = root;

		// Find the right parent for the new node
		while (!tempNode.isNil()) {
			nodeParent = tempNode;

			if (node.getKey() < tempNode.getKey()) {
				tempNode = tempNode.getLeft();
			} else {
				tempNode = tempNode.getRight();
			}
		}

		// Set the parent for the new node
		node.setParent(nodeParent);

		// Link the node as root or as a child to nodeParent
		if (nodeParent.isNil()) {
			setRoot(node);
		} else if (node.getKey() < nodeParent.getKey()) {
			nodeParent.setLeft(node);
		} else {
			nodeParent.setRight(node);
		}

		// Calculate new max for parent
		nodeParent.setMax();

		// Init new node and call fix up
		node.setLeft(RBTreeNode.nil);
		node.setRight(RBTreeNode.nil);
		node.setRed();
		insertFixup(node);
	}

	/**
	 * Fix and balance the Red-Black tree after a new node was added
	 * 
	 * @param node - The node that was added
	 */
	private void insertFixup(RBTreeNode node) {
		// tempNode - z
		RBTreeNode tempNode = node;

		// Fix the nodes so there are no red parents to red children
		while (tempNode.getParent().isRed()) {
			
			boolean isLeft = tempNode.getParent().isLeftSon();
			
			RBTreeNode uncleNode = tempNode.getUncle();

			if (uncleNode.isRed()) {
				tempNode.getParent().setBlack();
				uncleNode.setBlack();
				tempNode.getGrandpa().setRed();
				tempNode = tempNode.getGrandpa();
			} else {
				if (isLeft) {
					if (tempNode.isRightSon()) {
						tempNode = tempNode.getParent();
						leftRotate(tempNode);
					}
				} else {
					if (tempNode.isLeftSon()) {
						tempNode = tempNode.getParent();
						rightRotate(tempNode);
					}
				}
				tempNode.getParent().setBlack();
				tempNode.getGrandpa().setRed();
				if (isLeft) rightRotate(tempNode.getGrandpa());
				else leftRotate(tempNode.getGrandpa());
			}
		}

		// Make sure root stays BLACK
		root.setBlack();
	}

	/**
	 * Do a left rotate on axis node
	 * 
	 * @param axis - The node to rotate around
	 */
	private void leftRotate(RBTreeNode axis) {
		RBTreeNode oldRight = axis.getRight();

		// Handle axis right side
		axis.setRight(oldRight.getLeft());

		if (!oldRight.getLeft().isNil()) {
			oldRight.getLeft().setParent(axis);
		}

		oldRight.setParent(axis.getParent());

		if (axis.getParent().isNil()) {
			setRoot(oldRight);
		} else if (axis.isLeftSon()) {
			axis.getParent().setLeft(oldRight);
		} else {
			axis.getParent().setRight(oldRight);
		}

		// Handle axis left side
		oldRight.setLeft(axis);
		axis.setParent(oldRight);

		// Calculate max values
		axis.setMax();
		//setMax(oldRight);
	}

	/**
	 * Do a right rotate on axis node
	 * 
	 * @param axis - The node to rotate around
	 */
	private void rightRotate(RBTreeNode axis) {
		RBTreeNode oldLeft = axis.getLeft();

		// Handle axis left side		
		axis.setLeft(oldLeft.getRight());

		if (!oldLeft.getRight().isNil()) {
			oldLeft.getRight().setParent(axis);
		}

		oldLeft.setParent(axis.getParent());

		if (axis.getParent().isNil()) {
			setRoot(oldLeft);
		} else if (axis.isRightSon()) {
			axis.getParent().setRight(oldLeft);
		} else {
			axis.getParent().setLeft(oldLeft);
		}

		// Handle axis right side
		oldLeft.setRight(axis);
		axis.setParent(oldLeft);

		// Calculate max values
		axis.setMax();
		//setMax(oldLeft);
	}

	/**
	 * Find the node with given account number
	 *
	 * @param accountNumber
	 *            - The account number to search for
	 * @return - The node with given account number or NIL if not found
	 */
	public RBTreeNode searchByKey(long accountNumber) {
		RBTreeNode node = root;

		// Iterate the tree by key to find the node
		while (!node.isNil() && (node.getKey() != accountNumber)) {
			if (accountNumber < node.getKey()) {
				node = node.getLeft();
			} else {
				node = node.getRight();
			}
		}

		// Make sure the correct node was found
		return (node.getKey() == accountNumber ? node : RBTreeNode.nil);
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(RBTreeNode root) {
		this.root = root;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RBTree [root=" + root + "]";
	}
}