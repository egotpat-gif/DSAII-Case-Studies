class FenwickTree {
    int[] tree;
    int n;

    FenwickTree(int n) {
        this.n = n;
        tree = new int[n + 1];
    }

    void update(int i, int delta) {
        for (; i <= n; i += i & (-i))
            tree[i] += delta;
    }

    int prefixSum(int i) {
        int sum = 0;
        for (; i > 0; i -= i & (-i))
            sum += tree[i];
        return sum;
    }

    int rangeQuery(int l, int r) {
        return prefixSum(r) - prefixSum(l - 1);
    }

    public static void main(String[] args) {
        int[] scores = {0, 120, 85, 200, 60, 175, 90, 140, 110};
        FenwickTree ft = new FenwickTree(8);
        for (int i = 1; i <= 8; i++) ft.update(i, scores[i]);

        System.out.println("Total score (all players): " + ft.prefixSum(8));
        System.out.println("Score (players 3-6): " + ft.rangeQuery(3, 6));

        ft.update(4, 50); // Player 4 gains 50 points
        System.out.println("After update, total: " + ft.prefixSum(8));
    }
}
