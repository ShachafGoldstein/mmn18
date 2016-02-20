/**
 * 
 */
package mmn18;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author shachaf
 *
 */
public class RBTreeNode {
	
	public static final RBTreeNode nil;
	
	static {
		nil = new RBTreeNode(null, null, null, null, RBTreeNodeColor.BLACK);
		
		nil.setLeft(nil);
		nil.setRight(nil);
		nil.setParent(nil);
	}
	
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
	
	/**
	 * 
	 * @return
	 */
	public RBTreeNode getGrandpa() {
		return this.getParent().getParent();
	}
	
	/**
	 * 
	 * @return
	 */
	public RBTreeNode getBrother() {
		if (isLeftSon()) {
			return this.getParent().getRight();
		}
		else if (isRightSon()){
			return this.getParent().getLeft();
		}
		return nil;
	}
	
	/**
	 * 
	 * @return
	 */
	public RBTreeNode getUncle() {
		return this.getParent().getBrother();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isLeftSon() {
		return (this == this.getParent().getLeft());			
	}
	
	public boolean isRightSon() {
		return (this == this.getParent().getRight());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RBTreeNode [left=" + left + ", right=" + right + ", parent=" + parent + ", max=" + max + ", value="
				+ value + ", color=" + color + "]";
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
	 
	    private void printNodeValue(OutputStream out) throws IOException {
	        if (this.equals(nil)) {
	            out.write("NIL".getBytes());
	        } else {
	            out.write((getKey()+""+getColor().name().charAt(0)).getBytes());
	        }
	        out.write('\n');
	    }
	    
	    // use string and not stringbuffer on purpose as we need to change the indent at each recursion
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
}
