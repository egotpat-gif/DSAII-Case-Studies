import java.util.*;

class FamilyTreeDFS {
    Map<String, List<String>> tree = new HashMap<>();

    void addChild(String parent, String child) {
        tree.computeIfAbsent(parent, k -> new ArrayList<>()).add(child);
    }

    void dfs(String person, int depth) {
        System.out.println("  ".repeat(depth) + person);
        List<String> children = tree.getOrDefault(person, new ArrayList<>());
        for (String child : children)
            dfs(child, depth + 1);
    }

    boolean findPath(String current, String target, List<String> path) {
        path.add(current);
        if (current.equals(target)) return true;
        for (String child : tree.getOrDefault(current, new ArrayList<>())) {
            if (findPath(child, target, path)) return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {
        FamilyTreeDFS ft = new FamilyTreeDFS();
        ft.addChild("Great-Great-Grandfather", "Great-Grandfather");
        ft.addChild("Great-Grandfather", "Grandfather");
        ft.addChild("Great-Grandfather", "Grand-Uncle");
        ft.addChild("Grandfather", "Father");
        ft.addChild("Grandfather", "Uncle");
        ft.addChild("Father", "You");
        ft.addChild("Father", "Sibling");

        System.out.println("Full Family Tree (DFS):");
        ft.dfs("Great-Great-Grandfather", 0);

        List<String> path = new ArrayList<>();
        ft.findPath("Great-Great-Grandfather", "You", path);
        System.out.println("\nLineage to 'You': " + String.join(" --> ", path));
    }
}
