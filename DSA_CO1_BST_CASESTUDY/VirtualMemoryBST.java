class BSTNode {
    int address;
    BSTNode left, right;
    BSTNode(int addr) { this.address = addr; }
}

class VirtualMemoryBST {
    BSTNode root;

    BSTNode insert(BSTNode node, int addr) {
        if (node == null) return new BSTNode(addr);
        if (addr < node.address) node.left = insert(node.left, addr);
        else if (addr > node.address) node.right = insert(node.right, addr);
        return node;
    }

    void inorder(BSTNode node) {
        if (node == null) return;
        inorder(node.left);
        System.out.print(node.address + " ");
        inorder(node.right);
    }

    boolean search(BSTNode node, int addr) {
        if (node == null) return false;
        if (addr == node.address) return true;
        return addr < node.address ? search(node.left, addr) : search(node.right, addr);
    }

    public static void main(String[] args) {
        VirtualMemoryBST vmBST = new VirtualMemoryBST();
        int[] segments = {5000, 2000, 8000, 1000, 3000, 7000, 9000, 4000};
        for (int s : segments) vmBST.root = vmBST.insert(vmBST.root, s);

        System.out.println("Memory Segments (Sorted):");
        vmBST.inorder(vmBST.root);

        System.out.println("\nSearch 3000: " + vmBST.search(vmBST.root, 3000));
        System.out.println("Search 6000: " + vmBST.search(vmBST.root, 6000));
    }
}
