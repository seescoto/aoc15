import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

public class Day9 {
    /*
     * --- Day 9: All in a Single Night ---
     * 
     * Every year, Santa manages to deliver all of his presents in a single night.
     * 
     * This year, however, he has some new locations to visit; his elves have
     * provided him the distances between every pair of locations. He can start and
     * end at any two (different) locations he wants, but he must visit each
     * location exactly once. What is the shortest distance he can travel to achieve
     * this?
     * 
     * For example, given the following distances:
     * 
     * London to Dublin = 464
     * London to Belfast = 518
     * Dublin to Belfast = 141
     * The possible routes are therefore:
     * 
     * Dublin -> London -> Belfast = 982
     * London -> Dublin -> Belfast = 605
     * London -> Belfast -> Dublin = 659
     * Dublin -> Belfast -> London = 659
     * Belfast -> Dublin -> London = 605
     * Belfast -> London -> Dublin = 982
     * The shortest of these is London -> Dublin -> Belfast = 605, and so the answer
     * is 605 in this example.
     * 
     * What is the distance of the shortest route?
     * 
     */

    public static void main(String args[]) {
        prob1();
        prob2();
    }

    public static void prob1() {
        File file = new File("day9_input.txt");
        ArrayList<Edge> edges = createEdges(file);
        Set<String> cities = listCities(edges);

        // create permutations of list of cities - meaning different paths
        // store paths with their weight in hash 'permutations'
        // then find the smallest weight and return that

        Hashtable<ArrayList<String>, Integer> permutations = createPermutations(cities, edges);
        ArrayList<String> path = new ArrayList<String>(cities);
        int minPath = permutations.get(path);
        for (ArrayList<String> p : permutations.keySet()) {
            if (permutations.get(p) < minPath) {
                path = p;
                minPath = permutations.get(p);
            }
        }
        System.out.printf("Minimum path has weight : %d\n", minPath);
        for (String c : path) {
            System.out.println(c);
        }

    }

    public static ArrayList<Edge> createEdges(File file) {
        ArrayList<Edge> edges = new ArrayList<>();

        try {
            Scanner input = new Scanner(file);
            String arr[];

            // create hash map of edges and weights
            while (input.hasNextLine()) {
                arr = input.nextLine().split(" ");
                // add (city1, city2) and edge weight to edges
                Edge e = new Edge(arr[0], arr[2], Integer.valueOf(arr[arr.length - 1]));
                edges.add(e);
            }

            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return edges;
    }

    public static HashSet<String> listCities(ArrayList<Edge> edges) {
        // add all cities to set - wont hold duplicates, can just try adding all

        HashSet<String> cities = new HashSet<>();
        for (Edge e : edges) {
            cities.add(e.source);
            cities.add(e.dest);
        }
        return cities;
    }

    public static Hashtable<ArrayList<String>, Integer> createPermutations(Set<String> cities, ArrayList<Edge> edges) {
        Hashtable<ArrayList<String>, Integer> permutations = new Hashtable<>();

        ArrayList<ArrayList<String>> paths = new ArrayList<>();
        ArrayList<String> curr = new ArrayList<>();
        ArrayList<String> toPermute = new ArrayList<>(cities);
        getPerm(curr, toPermute, paths);

        for (ArrayList<String> path : paths) {
            int w = getWeight(path, edges);
            permutations.put(path, w);
        }

        return permutations;
    }

    public static void getPerm(ArrayList<String> curr, ArrayList<String> toPermute,
            ArrayList<ArrayList<String>> perms) {
        ArrayList<String> copyToPermute = new ArrayList<>(toPermute);
        // unique permutation
        if (toPermute.size() == 0) {
            ArrayList<String> path = new ArrayList<>(curr);
            perms.add(path);
        } else {
            // permutation not complete
            for (String city : copyToPermute) {
                toPermute.remove(city);
                curr.add(city);
                getPerm(curr, toPermute, perms);
                curr.remove(city);
                toPermute.add(city);
            }
        }
    }

    public static int getWeight(ArrayList<String> path, ArrayList<Edge> edges) {
        String source, dest;
        Edge e;
        int weight = 0;

        for (int i = 1; i < path.size(); i++) {
            source = path.get(i - 1);
            dest = path.get(i);
            e = findEdge(edges, source, dest);
            if (e == null)
                return -1;
            weight += e.weight;
        }
        // close loop - jk, dont need to close loop
        // weight += findEdge(edges, path.get(path.size() - 1), path.get(0)).weight;

        return weight;
    }

    public static Edge findEdge(ArrayList<Edge> edges, String source, String dest) {
        for (Edge e : edges) {
            if ((e.source.equals(source) && e.dest.equals(dest))
                    || (e.source.equals(dest) && e.dest.equals(source)))
                return e;
        }

        return null;
    }

    /*
     * --- Part Two ---
     * 
     * The next year, just to show off, Santa decides to take the route with the
     * longest distance instead.
     * 
     * He can still start and end at any two (different) locations he wants, and he
     * still must visit each location exactly once.
     * 
     * For example, given the distances above, the longest route would be 982 via
     * (for example) Dublin -> London -> Belfast.
     * 
     * What is the distance of the longest route?
     */

    public static void prob2() {
        File file = new File("day9_input.txt");
        ArrayList<Edge> edges = createEdges(file);
        Set<String> cities = listCities(edges);

        // same as before, only search for max weight
        Hashtable<ArrayList<String>, Integer> permutations = createPermutations(cities, edges);
        ArrayList<String> path = new ArrayList<String>(cities);
        int maxPath = permutations.get(path);
        for (ArrayList<String> p : permutations.keySet()) {
            if (permutations.get(p) > maxPath) {
                path = p;
                maxPath = permutations.get(p);
            }
        }
        System.out.printf("Maximum path is has weight : %d\n", maxPath);
        for (String c : path) {
            System.out.println(c);
        }

    }

}
