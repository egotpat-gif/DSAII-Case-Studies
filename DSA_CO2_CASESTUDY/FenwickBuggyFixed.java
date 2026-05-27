public class FenwickBuggyFixed {
    int[] bit;          // 1-indexed; bit[0] unused
    int n;

    FenwickBuggyFixed(int n) { this.n = n; this.bit = new int[n + 1]; }

    /** Return spend[1] + spend[2] + ... + spend[i]. */
    long prefixSum(int i) {
        long s = 0;
        while (i > 0) {
            s += bit[i];
            i -= (i & -i);
        }
        return s;
    }

    /** Add `delta` to the element at index i. */
    void pointUpdate(int i, int delta) {
        while (i <= n) {
            bit[i] += delta;
            i += i & -i;
        }
    }

    /** Simple demonstration / test harness so the class can be run. */
    public static void main(String[] args) {
        FenwickBuggyFixed ft = new FenwickBuggyFixed(8);
        // 1-indexed updates
        ft.pointUpdate(1, 5);
        ft.pointUpdate(2, 3);
        ft.pointUpdate(4, 7);

        System.out.println("prefixSum(1) = " + ft.prefixSum(1)); // 5
        System.out.println("prefixSum(2) = " + ft.prefixSum(2)); // 8
        System.out.println("prefixSum(3) = " + ft.prefixSum(3)); // 8
        System.out.println("prefixSum(4) = " + ft.prefixSum(4)); // 15
    }
}