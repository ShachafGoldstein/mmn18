package mmn18;

/**
 * @author Shachaf Goldstein & Guy Sahar
 *
 *	Class to represent and handle the Red-Black tree
 */
public class RBTree {

	/**
	 * Get the max relative to children
	 * 
	 * @param node
	 *            - current node to check against
	 * @return - The max node
	 */
	private static RBTreeNode getChildMax(RBTreeNode node) {
		RBTreeNode leftMax = node.getLeft().getMax(), rightMax = node.getRight().getMax();

		if ((leftMax != RBTreeNode.nil) && (rightMax != RBTreeNode.nil)) {
			return (leftMax.getValue().getBalance() > rightMax.getValue().getBalance()) ? leftMax : rightMax;
		}

		return (leftMax != RBTreeNode.nil) ? leftMax : (rightMax != RBTreeNode.nil) ? rightMax : RBTreeNode.nil;
	}

	/**
	 * Return a string representation of negative balance nodes
	 * @param node - The node to start from
	 * @return - The string representation
	 */
	public static String negativeBalances(RBTreeNode node) {

		// Recursion stop point
		if (node == RBTreeNode.nil) {
			return "";
		}

		return (node.getValue().getBalance() < 0 ? node.toString() : "") + negativeBalances(node.getLeft())
				+ negativeBalances(node.getRight());
	}

	/**
	 * Set the max of the given node
	 *
	 * @param node
	 *            - The node to set max of
	 */
	private static void setMax(RBTreeNode node) {
		RBTreeNode childMax = getChildMax(node);

		// Check root's max against children's max
		if (childMax == RBTreeNode.nil) {
			node.setMax(node);
		} else {
			node.setMax((node.getValue().getBalance() > childMax.getValue().getBalance()) ? node : childMax);
		}
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
		if (node.getRight() != RBTreeNode.nil) {
			return treeMinimum(node.getRight());
		}

		RBTreeNode successor = node.getParent(), tempNode = node;

		// Iterate to the right in node parent's sub tree
		while ((successor != RBTreeNode.nil) && (tempNode == successor.getRight())) {
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
		while (min.getLeft() != RBTreeNode.nil) {
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
		if ((node.getLeft() == RBTreeNode.nil) || (node.getRight() == RBTreeNode.nil)) {
			deletedNode = node;
		} else {
			deletedNode = successor(node);
		}

		// Init tempNode with the left son if not NIL, otherwise with right son
		// of deletedNode
		if (deletedNode.getLeft() != RBTreeNode.nil) {
			tempNode = deletedNode.getLeft();
		} else {
			tempNode = deletedNode.getRight();
		}

		// Set deletedNode parent as tempNode parent
		tempNode.setParent(deletedNode.getParent());

		// replace value of node with deletedNode if not the same
		if (deletedNode != node) {
			// Client temp = node.getValue();
			node.setValue(deletedNode.getValue());
			// y.setValue(temp);
			node.setMax(deletedNode.getMax());
		}

		// Check if deletedNode was the root
		if (deletedNode.getParent() == RBTreeNode.nil) {
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
		setMax(tempNode);
		setMax(tempNode.getParent());

		// Call fixup if deletedNode is black
		if (deletedNode.getColor() == RBTreeNodeColor.BLACK) {
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

		// Continue fixup as long as node isn't root and isn't black
		while ((node != root) && (node.getColor() == RBTreeNodeColor.BLACK)) {
			// tempNode - y
			RBTreeNode tempNode;

			if (node.isLeftSon()) {
				tempNode = node.getParent().getRight();

				if (tempNode.getColor() == RBTreeNodeColor.RED) {
					tempNode.setColor(RBTreeNodeColor.BLACK);
					node.getParent().setColor(RBTreeNodeColor.RED);
					leftRotate(node.getParent());
					tempNode = node.getParent().getRight();
				}

				if ((tempNode.getLeft().getColor() == RBTreeNodeColor.BLACK)
						&& (tempNode.getRight().getColor() == RBTreeNodeColor.BLACK)) {
					tempNode.setColor(RBTreeNodeColor.RED);
					node = node.getParent();
				} else {
					if (tempNode.getRight().getColor() == RBTreeNodeColor.BLACK) {
						tempNode.getLeft().setColor(RBTreeNodeColor.BLACK);
						tempNode.setColor(RBTreeNodeColor.RED);
						rightRotate(tempNode);
						tempNode = node.getParent().getRight();
					}

					tempNode.setColor(node.getParent().getColor());
					node.getParent().setColor(RBTreeNodeColor.BLACK);
					tempNode.getRight().setColor(RBTreeNodeColor.BLACK);
					leftRotate(node.getParent());
					node = root;
				}
			} else {
				tempNode = node.getParent().getLeft();

				if (tempNode.getColor() == RBTreeNodeColor.RED) {
					tempNode.setColor(RBTreeNodeColor.BLACK);
					node.getParent().setColor(RBTreeNodeColor.RED);
					rightRotate(node.getParent());
					tempNode = node.getParent().getLeft();
				}

				if ((tempNode.getRight().getColor() == RBTreeNodeColor.BLACK)
						&& (tempNode.getLeft().getColor() == RBTreeNodeColor.BLACK)) {
					tempNode.setColor(RBTreeNodeColor.RED);
					node = node.getParent();
				} else {
					if (tempNode.getLeft().getColor() == RBTreeNodeColor.BLACK) {
						tempNode.getRight().setColor(RBTreeNodeColor.BLACK);
						tempNode.setColor(RBTreeNodeColor.RED);
						leftRotate(tempNode);
						tempNode = node.getParent().getLeft();
					}

					tempNode.setColor(node.getParent().getColor());
					node.getParent().setColor(RBTreeNodeColor.BLACK);
					tempNode.getLeft().setColor(RBTreeNodeColor.BLACK);
					rightRotate(node.getParent());
					node = root;
				}
			}
		}

		node.setColor(RBTreeNodeColor.BLACK);
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
		while (tempNode != RBTreeNode.nil) {
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
		if (nodeParent == RBTreeNode.nil) {
			setRoot(node);
		} else if (node.getKey() < nodeParent.getKey()) {
			nodeParent.setLeft(node);
		} else {
			nodeParent.setRight(node);
		}

		// Calculate new max for parent
		setMax(nodeParent);

		// Init new node and call fix up
		node.setLeft(RBTreeNode.nil);
		node.setRight(RBTreeNode.nil);
		node.setColor(RBTreeNodeColor.RED);
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
		while (tempNode.getParent().getColor() == RBTreeNodeColor.RED) {

			// Handle for left child, then right child
			if (tempNode.getParent().isLeftSon()) {
				// rightUncleNode - y	
				RBTreeNode rightUncleNode = tempNode.getGrandpa().getRight();

				if (rightUncleNode.getColor() == RBTreeNodeColor.RED) {
					tempNode.getParent().setColor(RBTreeNodeColor.BLACK);
					rightUncleNode.setColor(RBTreeNodeColor.BLACK);
					tempNode.getGrandpa().setColor(RBTreeNodeColor.RED);
					tempNode = tempNode.getGrandpa();
				} else {
					if (tempNode.isRightSon()) {
						tempNode = tempNode.getParent();
						leftRotate(tempNode);
					}

					tempNode.getParent().setColor(RBTreeNodeColor.BLACK);
					tempNode.getGrandpa().setColor(RBTreeNodeColor.RED);
					rightRotate(tempNode.getGrandpa());
				}
			} else {
				// leftUncleNode - y	
				RBTreeNode leftUncleNode = tempNode.getGrandpa().getLeft();

				if (leftUncleNode.getColor() == RBTreeNodeColor.RED) {
					tempNode.getParent().setColor(RBTreeNodeColor.BLACK);
					leftUncleNode.setColor(RBTreeNodeColor.BLACK);
					tempNode.getGrandpa().setColor(RBTreeNodeColor.RED);
					tempNode = tempNode.getGrandpa();
				} else {
					if (tempNode.isLeftSon()) {
						tempNode = tempNode.getParent();
						rightRotate(tempNode);
					}

					tempNode.getParent().setColor(RBTreeNodeColor.BLACK);
					tempNode.getGrandpa().setColor(RBTreeNodeColor.RED);
					leftRotate(tempNode.getGrandpa());
				}
			}
		}

		// Make sure root stays BLACK
		root.setColor(RBTreeNodeColor.BLACK);
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

		if (oldRight.getLeft() != RBTreeNode.nil) {
			oldRight.getLeft().setParent(axis);
		}

		oldRight.setParent(axis.getParent());

		if (axis.getParent() == RBTreeNode.nil) {
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
		setMax(axis);
		setMax(oldRight);
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

		if (oldLeft.getRight() != RBTreeNode.nil) {
			oldLeft.getRight().setParent(axis);
		}

		oldLeft.setParent(axis.getParent());

		if (axis.getParent() == RBTreeNode.nil) {
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
		setMax(axis);
		setMax(oldLeft);
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
		while ((node != RBTreeNode.nil) && (node.getKey() != accountNumber)) {
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