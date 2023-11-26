public class Edge implements Comparable<Edge> {
    public String source, dest;
    public int weight;

    Edge(String source, String dest, int weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge e) {
        return Integer.compare(this.weight, e.weight);
    }

    @Override
    public String toString() {
        return this.source + " -> " + this.dest + " : " + this.weight;
    }

}