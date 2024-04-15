package tree;

import java.util.Comparator;
import pair.Pair;

public class BinarySearchTree<T> {
    private TreeNode<T> root;
    private Comparator<T> comparator;

    // Default constructor without custom comparator
    public BinarySearchTree() {
        root = null;
        comparator = null;
    }
    // Constructor with custom comparator
    public BinarySearchTree(Comparator<T> comparator) {
        root = null;
        this.comparator = comparator;
    }

    // Method comparing two values of type T
    private int compare(T a, T b) {
        if (comparator != null) {
        	// If a custom comparator is provided, use it
            return comparator.compare(a, b);
        } else {
        	// Check if both objects are instances of Comparable
            if (a instanceof Comparable && b instanceof Comparable) {
            	// Safe to cast and compare
            	return ((Comparable<T>)a).compareTo(b);
            } else {
            	
                throw new IllegalArgumentException("Either use a Comparator or store Comparable elements.");
            }
        }
    }
    // Insert value into the BST
    public void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null value");
        }
        // Recursive insert into the BST
        root = insertRec(root, value);
    }

    // Method to insert new value into the BST recursively
    private TreeNode<T> insertRec(TreeNode<T> node, T value) {
        if (node == null) {
            return new TreeNode<>(value);
        }
        int cmp = compare(value, node.value);
        if (cmp < 0) {
        	// Value less than current - left
            node.left = insertRec(node.left, value);
        } else if (cmp > 0) {
        	// Greater than current - right
            node.right = insertRec(node.right, value);
        }
        // If value is equal do not add duplicates
        return node;
    }

    // Recursive method to search for a value in BST
    private boolean searchRec(TreeNode<T> node, T value) {
        if (node == null) {
            return false; // Not found
        }
        int cmp = compare(value, node.value);
        if (cmp < 0) {
            return searchRec(node.left, value);
        } else if (cmp > 0) {
            return searchRec(node.right, value);
        } else {
            return true; // Found
        }
    }

    // Method to start in-order traversal of BST
    public void inorder() {
        inorderRec(root);
    }


    // Recursive helper method for inorder traversal
    private void inorderRec(TreeNode<T> node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.print(node.value + " \n");
            inorderRec(node.right);
        }
    }
    

    // Main method for demonstration and testing
    public static void main(String[] args) {
    	
    	// Example of TreeNode with Integer value
		TreeNode<Integer> tn = new TreeNode<>(5);
    	if (tn instanceof Comparable<?>) {
    		Comparable<?> c = (Comparable<?>)tn;
    	}
    	
    	// Create a binary search tree and insert random integers
    	BinarySearchTree<Integer> bst0 = new BinarySearchTree<>();
    	for (int i=0;i<10;i++) {
    		int val = (int)(Math.random()*100);
    		System.out.println("inserting " + val);
    		bst0.insert(val);
    	}
    	// Print the sorted values inorder traversal
    	bst0.inorder();
    	
    	// Another example inserting random integers into BST and printing
    	BinarySearchTree<Integer> bst = new BinarySearchTree<>();
    	for (int i=0;i<10;i++) {
    		int val = (int)(Math.random()*100);
    		System.out.println("inserting " + val);
    		bst.insert(val);
    	}
    	bst.inorder();
    	
    	/* this will fail during the insert or constructor call
    	 * because Objects are not Comparable,
    	 * unless I pass in a Comparator into the constructor
    	 */
		// commenting because this code is only needed for testing

    	// BinarySearchTree<Object> bst2 = new BinarySearchTree<>();
    	// for (int i=0;i<10;i++) {
    	// 	Object val = new Object();
    	// 	bst2.insert(val);
    	// }
    	// bst2.inorder();
    	
    	/* Assume I have a BST named BinarysearchTree that takes Pair objects 
    	 * where the Pair objects have Key,Value pairs of 
    	 * Integer and String,  and the ordering I have chosen 
    	 * for the binary search tree is to be order by the keys 
    	 * in ascending order (that is to say, Integers). 
    	 * In this case, after creating the BST 
    	 * (code not included here), we this should work:
    	 * 
    	 * Pair<Integer, String> p1 = new Pair(5, "John");
    	 * BinarysearchTree.insert(p1);
    	 * Pair<Integer, String> p2 = new Pair(3, "Bob");
    	 * BinarysearchTree.insert(p2);
    	 * Pair<Integer, String> p3 = new Pair(9, "Alice");
    	 * BinarysearchTree.insert(p3);
    	 * Pair<Integer, String> p4 = new Pair(13, "Mallory");
    	 * BinarysearchTree.insert(p4);
    	 * Pair<Integer, String> p5 = new Pair(7, "Larry");
    	 * BinarysearchTree.insert(p5);
    	 * 
    	 * And the output of:
    	 * BinarysearchTree.inorder();
    	 * 
    	 * will be:
    	 * 
    	 * Pair[key=3, value=Bob] Pair[key=5, value=John] \
    	 *  Pair[key=7, value=Larry] Pair[key=9, value=Alice] \
    	 *  Pair[key=13, value=Mallory]
    	 * 
    	 */

    	// Create new binary search tree that compares Pair objects based on their Integer keys
		BinarySearchTree<Pair<Integer, String>> BinarysearchTree = new BinarySearchTree<>(Comparator.comparing(Pair<Integer, String>::getKey));

		// Insert Pair objects into the BST
		Pair<Integer, String> firstpair = new Pair<>(5, "John");
		BinarysearchTree.insert(firstpair);
		Pair<Integer, String> secondpair = new Pair<>(3, "Bob");
		BinarysearchTree.insert(secondpair);
		Pair<Integer, String> thirdpair = new Pair<>(9, "Alice");
		BinarysearchTree.insert(thirdpair);
		Pair<Integer, String> fourthpair = new Pair<>(13, "Mallory");
		BinarysearchTree.insert(fourthpair);
		Pair<Integer, String> fifthpair = new Pair<>(7, "Larry");
		BinarysearchTree.insert(fifthpair);
		
		// Print the BST ordered by key in ascending order
		System.out.println("\nBST Ordered by key in ascending order");
		BinarysearchTree.inorder();

		// Create new binary search tree for Pair objects ordered by key in descending order
		BinarySearchTree<Pair<Integer, String>> bst4 = new BinarySearchTree<>(Comparator.comparing(Pair<Integer, String>::getKey).reversed());

		// Insert the same pairs into this BST
		bst4.insert(firstpair);
		bst4.insert(secondpair);
		bst4.insert(thirdpair);
		bst4.insert(fourthpair);
		bst4.insert(fifthpair);
		
		// Print BST ordered by key, descending order
		System.out.println("\nBST Ordered by key in descending order");
		bst4.inorder();


		// Create new binary search tree for Pair objects ordered by value, String
		BinarySearchTree<Pair<Integer, String>> bst5 = new BinarySearchTree<>(Comparator.comparing(Pair<Integer, String>::getValue));

		// Insert the same pairs into this BST
		bst5.insert(firstpair);
		bst5.insert(secondpair);
		bst5.insert(thirdpair);
		bst5.insert(fourthpair);
		bst5.insert(fifthpair);
		
		// Print the BST ordered by value
		System.out.println("\nBST Ordered by value");
		bst5.inorder();






    }
}