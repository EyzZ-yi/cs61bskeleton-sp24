public class UnionFind {
    // TODO: Instance variables
    //实例变量
    int[] tree;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    /* 创建一个包含 N 个元素的并查集数据结构。初始状态下，所有元素都属于不同的集合。*/
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
    tree=new int[N];
    for(int i=0;i<N;i++) tree[i]=-1;
    }

    /* Returns the size of the set V belongs to. */
    /* 返回元素 V 所属集合的大小。*/
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        return -tree[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    /* 返回节点 V 的父节点。如果 V 是树的根节点，则返回以 V 为根节点的树的负大小。*/
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if(find(v)==v) return -tree[v];
        else return tree[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    /* 如果节点/顶点 V1 和 V2 连接，则返回 true。*/
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        return find(v1)==find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    /* 返回元素 V 所属集合的根节点。此函数采用路径压缩技术，
从而实现快速查找。如果传入无效参数，则抛出 IllegalArgumentException 异常。*/
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if(v>=tree.length) throw  new IllegalArgumentException("Some comment to describe the reason for throwing.");
        int x=v;
        int[] cnt=new int[100];
        int i=0;
        while(tree[x]>=0){
            cnt[i]=x;
            i++;
            x=tree[x];
        }
        i--;
        while (i>=0){
            tree[cnt[i]]=x;
            i--;
        }
        return x;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    /* 通过连接两个元素 V1 和 V2 所属的集合，将它们连接起来。V1 和 V2 可以是任何元素，
并使用按大小合并的启发式方法。如果集合大小相等，则通过将 V1 的根节点连接到 V2 的根节点来打破平局。
将一个元素与其自身合并，或将已连接的元素进行合并，不应改变数据结构。*/
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(v1!=v2) {
        int treev1=find(v1);
        int treev2=find(v2);
        if(sizeOf(treev1)>sizeOf(treev2)) {
            tree[treev2]=treev1;
            tree[treev1]--;}
        else {tree[treev1]=treev2;
              tree[treev2]--;}}
    }

}
