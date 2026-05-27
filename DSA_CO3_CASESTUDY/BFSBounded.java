import java.util.*;

public class BFSBounded {

    static Set<String> bfsBounded(
            Map<String, List<String>> adj, String source, int maxDepth) {

        Set<String> visited = new HashSet<>();
        visited.add(source);

        Deque<Object[]> queue = new ArrayDeque<>();
        queue.offer(new Object[]{ source, 0 });

        Set<String> reached = new HashSet<>();

        while (!queue.isEmpty()) {
            Object[] cur = queue.poll();

            String u = (String) cur[0];
            int depth = (int) cur[1];

            if (depth == maxDepth) continue;

            List<String> neighbours =
                    adj.getOrDefault(u, new ArrayList<>());

            for (String neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.offer(new Object[]{ neighbour, depth + 1 });

                    reached.add(neighbour);
                }
            }
        }

        return reached;
    }

    public static void main(String[] args) {

        Map<String, List<String>> adj = new HashMap<>();

        adj.put("A", Arrays.asList("B", "C"));
        adj.put("B", Arrays.asList("D", "E"));
        adj.put("C", Arrays.asList("E", "F"));
        adj.put("D", Arrays.asList("G"));
        adj.put("E", Arrays.asList("G", "H"));
        adj.put("F", Arrays.asList("H", "I"));

        Set<String> result = bfsBounded(adj, "A", 3);

        System.out.println("Reached: " + new TreeSet<>(result));
    }
}