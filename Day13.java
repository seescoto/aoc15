import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Day13 {

    /*
     * --- Day 13: Knights of the Dinner Table ---
     * 
     * In years past, the holiday feast with your family hasn't gone so well. Not
     * everyone gets along! This year, you resolve, will be different. You're going
     * to find the optimal seating arrangement and avoid all those awkward
     * conversations.
     * 
     * You start by writing up a list of everyone invited and the amount their
     * happiness would increase or decrease if they were to find themselves sitting
     * next to each other person. You have a circular table that will be just big
     * enough to fit everyone comfortably, and so each person will have exactly two
     * neighbors.
     * 
     * For example, suppose you have only four attendees planned, and you calculate
     * their potential happiness as follows:
     * 
     * Alice would gain 54 happiness units by sitting next to Bob.
     * Alice would lose 79 happiness units by sitting next to Carol.
     * Alice would lose 2 happiness units by sitting next to David.
     * Bob would gain 83 happiness units by sitting next to Alice.
     * Bob would lose 7 happiness units by sitting next to Carol.
     * Bob would lose 63 happiness units by sitting next to David.
     * Carol would lose 62 happiness units by sitting next to Alice.
     * Carol would gain 60 happiness units by sitting next to Bob.
     * Carol would gain 55 happiness units by sitting next to David.
     * David would gain 46 happiness units by sitting next to Alice.
     * David would lose 7 happiness units by sitting next to Bob.
     * David would gain 41 happiness units by sitting next to Carol.
     * Then, if you seat Alice next to David, Alice would lose 2 happiness units
     * (because David talks so much), but David would gain 46 happiness units
     * (because Alice is such a good listener), for a total change of 44.
     * 
     * If you continue around the table, you could then seat Bob next to Alice (Bob
     * gains 83, Alice gains 54). Finally, seat Carol, who sits next to Bob (Carol
     * gains 60, Bob loses 7) and David (Carol gains 55, David gains 41).
     * 
     * After trying every other seating arrangement in this hypothetical scenario,
     * you find that this one is the most optimal, with a total change in happiness
     * of 330.
     * 
     * What is the total change in happiness for the optimal seating arrangement of
     * the actual guest list?
     */

    public static void main(String args[]) {
        prob1();
        System.out.println();
        prob2();
    }

    public static void prob1() {
        ArrayList<Edge> edges = createEdges("day13_input.txt");
        ArrayList<String> people = getNames(edges);
        Hashtable<ArrayList<String>, Integer> arrangements = createPermutations(people, edges);

        // find arrangemnt with min total weight
        ArrayList<String> arrangement = people;
        int maxWeight = arrangements.get(people);
        for (ArrayList<String> a : arrangements.keySet()) {
            if (maxWeight < arrangements.get(a)) {
                maxWeight = arrangements.get(a);
                arrangement = a;
            }
        }

        System.out.printf("The ideal arrangment has a change in happiness of %d\n", maxWeight);
        for (String p : arrangement) {
            System.out.println(p);
        }
    }

    public static ArrayList<Edge> createEdges(String fileName) {
        File file = new File(fileName);
        ArrayList<Edge> edges = new ArrayList<>();
        try {
            Scanner input = new Scanner(file);
            String line;
            Edge e;
            while (input.hasNextLine()) {
                line = input.nextLine();
                e = createEdge(line);
                edges.add(e);
            }
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return edges;
    }

    public static Edge createEdge(String str) {
        // given a str in the form :
        // NAME1 would GAIN/LOSE NUM happiness units by sitting next to NAME2.
        // make an edge between (name1, name2) with weight NUM (positive if gain, neg if
        // lose);
        str = str.substring(0, str.length() - 1); // cut off period at the end
        String words[] = str.split(" ");
        String name1, name2;

        // get weight
        int weight = Integer.valueOf(words[3]);
        if (words[2].equals("lose"))
            weight *= -1;

        // get names
        name1 = words[0];
        name2 = words[words.length - 1];

        // return edge
        return (new Edge(name1, name2, weight));
    }

    public static ArrayList<String> getNames(ArrayList<Edge> edges) {
        // add all names from edges (as long as its not already in the list)

        ArrayList<String> names = new ArrayList<>();
        for (Edge e : edges) {
            if (!names.contains(e.source))
                names.add(e.source);
            if (!names.contains(e.dest))
                names.add(e.dest);
        }

        return names;
    }

    public static Hashtable<ArrayList<String>, Integer> createPermutations(ArrayList<String> people,
            ArrayList<Edge> edges) {
        Hashtable<ArrayList<String>, Integer> permutations = new Hashtable<>();

        ArrayList<ArrayList<String>> paths = new ArrayList<>();
        ArrayList<String> curr = new ArrayList<>();
        ArrayList<String> toPermute = new ArrayList<>(people);
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
            ArrayList<String> arrangement = new ArrayList<>(curr);
            perms.add(arrangement);
        } else {
            // permutation not complete
            for (String person : copyToPermute) {
                toPermute.remove(person);
                curr.add(person);
                getPerm(curr, toPermute, perms);
                curr.remove(person);
                toPermute.add(person);
            }
        }
    }

    public static int getWeight(ArrayList<String> path, ArrayList<Edge> edges) {
        // directed! must go both ways, back and forth
        String source, dest;
        Edge e;
        int weight = 0;

        for (int i = 1; i < path.size(); i++) {
            source = path.get(i - 1);
            dest = path.get(i);
            e = findEdgeDirected(edges, source, dest);
            if (e == null)
                return -1;
            weight += e.weight;
            e = findEdgeDirected(edges, dest, source);
            if (e == null)
                return -1;
            weight += e.weight;
        }
        // close loop
        weight += findEdgeDirected(edges, path.get(path.size() - 1), path.get(0)).weight;
        weight += findEdgeDirected(edges, path.get(0), path.get(path.size() - 1)).weight;

        return weight;
    }

    public static Edge findEdgeDirected(ArrayList<Edge> edges, String source, String dest) {
        for (Edge e : edges) {
            if (e.source.equals(source) && e.dest.equals(dest))
                return e;
        }

        return null;
    }

    /*
     * --- Part Two ---
     * 
     * In all the commotion, you realize that you forgot to seat yourself. At this
     * point, you're pretty apathetic toward the whole thing, and your happiness
     * wouldn't really go up or down regardless of who you sit next to. You assume
     * everyone else would be just as ambivalent about sitting next to you, too.
     * 
     * So, add yourself to the list, and give all happiness relationships that
     * involve you a score of 0.
     * 
     * What is the total change in happiness for the optimal seating arrangement
     * that actually includes yourself?
     */

    public static void prob2() {
        ArrayList<Edge> edges = createEdges("day13_input.txt");
        ArrayList<String> people = getNames(edges);
        addEdges(people, edges, "ME!", 0);
        people.add("ME!");
        Hashtable<ArrayList<String>, Integer> arrangements = createPermutations(people, edges);

        // find arrangemnt with min total weight
        ArrayList<String> arrangement = people;
        int maxWeight = arrangements.get(people);
        for (ArrayList<String> a : arrangements.keySet()) {
            if (maxWeight < arrangements.get(a)) {
                maxWeight = arrangements.get(a);
                arrangement = a;
            }
        }

        System.out.printf("The ideal arrangment has a change in happiness of %d\n", maxWeight);
        for (String p : arrangement) {
            System.out.println(p);
        }

    }

    public static void addEdges(ArrayList<String> people, ArrayList<Edge> edges, String newPerson,
            int weight) {
        // for all people in people, add a new edge from newPerson to them AND BACK with
        // weight
        Edge e;
        for (String p : people) {
            e = new Edge(newPerson, p, weight);
            edges.add(e);
            e = new Edge(p, newPerson, weight);
            edges.add(e);
        }
    }
}
