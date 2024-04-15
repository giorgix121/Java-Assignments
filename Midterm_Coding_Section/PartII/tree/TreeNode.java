package tree;

public class TreeNode<P> {
    P value;
    TreeNode<P> left;
    TreeNode<P> right;

    TreeNode(P value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
