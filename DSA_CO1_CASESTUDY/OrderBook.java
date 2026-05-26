import java.util.HashMap;
import java.util.Map;

class OrderNode {
    long orderId;
    double price;
    int vol;
    OrderNode left, right;

    OrderNode(long oid, double price, int vol) {
        this.orderId = oid; this.price = price; this.vol = vol;
    }
}

class OrderBook {
    OrderNode root = null;
    Map<Long, OrderNode> byId = new HashMap<>();

    // INSERT: descending BST (left child > parent) + register in HashMap
    void insertOrder(long oid, double price, int vol) {
        OrderNode newNode = new OrderNode(oid, price, vol);
        byId.put(oid, newNode);           // O(1) HashMap register
        root = insertBST(root, newNode);  // O(log n) BST insert
    }

    private OrderNode insertBST(OrderNode node, OrderNode newNode) {
        if (node == null) return newNode;
        if (newNode.price > node.price)       // descending: higher goes LEFT
            node.left = insertBST(node.left, newNode);
        else
            node.right = insertBST(node.right, newNode);
        return node;
    }

    // CANCEL: O(1) HashMap lookup -> O(log n) BST delete -> O(1) HashMap remove
    boolean cancelById(long oid) {
        if (!byId.containsKey(oid)) return false;
        OrderNode target = byId.get(oid);    // O(1)
        root = deleteBST(root, target.price, oid); // O(log n)
        byId.remove(oid);                    // O(1)
        return true;
    }

    private OrderNode deleteBST(OrderNode node, double price, long oid) {
        if (node == null) return null;
        if (price > node.price) node.left = deleteBST(node.left, price, oid);
        else if (price < node.price) node.right = deleteBST(node.right, price, oid);
        else {
            if (node.orderId != oid) { // same price, different order
                node.right = deleteBST(node.right, price, oid); return node;
            }
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            OrderNode succ = node.right;
            while (succ.left != null) succ = succ.left;
            node.price = succ.price; node.orderId = succ.orderId; node.vol = succ.vol;
            node.right = deleteBST(node.right, succ.price, succ.orderId);
        }
        return node;
    }

    // PEEK: leftmost node = highest price (descending BST)
    OrderNode peekBestBid() {
        if (root == null) return null;
        OrderNode cur = root;
        while (cur.left != null) cur = cur.left; // O(log n)
        return cur;
    }

    public static void main(String[] args) {
        OrderBook ob = new OrderBook();
        // Insert surviving 9 orders (matched ones removed)
        long[][] orders = {{101,2980,100},{102,2965,200},{104,2985,150},
                           {105,2970,300},{107,2978,80},{109,2982,120},
                           {111,2972,90},{113,2986,60},{114,2975,200}};
        for (long[] o : orders) ob.insertOrder(o[0], o[1], (int)o[2]);
        System.out.println("Best Bid: " + ob.peekBestBid().price); // 2986
        ob.cancelById(113L);
        System.out.println("After cancel 113, Best Bid: " + ob.peekBestBid().price); // 2985
    }
}
