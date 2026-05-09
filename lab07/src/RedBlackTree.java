public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        /**
         * 创建一个红黑树节点，其值为 ITEM，颜色取决于 ISBLACK 的值。
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        /**
         * 创建一个红黑树节点，包含元素 ITEM，颜色取决于 ISBLACK 的值，左子节点为 LEFT，右子节点为 RIGHT。
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    /**
     * 翻转节点及其子节点的颜色。假设该节点同时拥有左子节点和右子节点。
     */
    void flipColors(RBTreeNode<T> node) {

            node.right.isBlack=true;
            node.left.isBlack=true;
            node.isBlack=false;
        // TODO: YOUR CODE HERE
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    /**
     * 将给定节点向右旋转。返回此子树的新根节点。
     * 在此实现中，请务必交换新根节点和旧根节点的颜色！
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> p=node;//
        if(node.left==null) return node;
            boolean m=node.isBlack;
            node.isBlack=node.left.isBlack;
            node.left.isBlack=m;
        RBTreeNode<T> j=node.left.right;//左字节点的右节点
        node=node.left;
        node.right=p;
        node.right.left=j;
        return node;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    /**
     * 将给定节点向左旋转。返回此子树的新根节点。
     * 在此实现中，请务必交换新根节点和旧根节点的颜色！
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE

        RBTreeNode<T> p=node;//
        if(node.right==null) return node;
            boolean m=node.isBlack;
            node.isBlack=node.right.isBlack;
            node.right.isBlack=m;
        RBTreeNode<T> j=node.right.left;
        node=node.right;
        node.left=p;
        node.left.right=j;
        return node;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    /**
     * 辅助方法，用于判断给定节点是否为红色。空节点（子节点或叶子节点）默认被视为黑色。
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    /**
     * 将元素插入红黑树。并将树的根节点染成黑色。
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    /**
     * 将给定节点插入到此红黑树中。已提供注释以帮助分解问题。对于每种情况，请考虑执行这些操作所需的场景。
     * 此外，请务必查看此类中的其他方法！
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.

        // TODO: Handle normal binary search tree insertion.

        // TODO: Rotate left operation

        // TODO: Rotate right operation

        // TODO: Color flip

        // TODO: 插入（返回）新的红色叶子节点。
        if(node==null) {
            return new RBTreeNode<>(false,item,null,null);}
// TODO: 处理普通二叉搜索树的插入操作。
        int cmp=item.compareTo(node.item);
        if(cmp<0){
            node.left=insert(node.left,item);}
        else if(cmp>0){
            node.right=insert(node.right,item);}
// TODO: 左旋操作
        if(isRed(node.right) && !isRed(node.left))
        node=rotateLeft(node);
// TODO: 右旋操作
        if(isRed(node.left) && isRed(node.left.left))
        node=rotateRight(node);
// TODO: 颜色翻转
        if(isRed(node.left)&&isRed(node.right))
        flipColors(node);
        return node; //fix this return statement修复此返回语句
    }
}
