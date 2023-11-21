import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Day7 {
    /*
     * --- Day 7: Some Assembly Required ---
     * 
     * This year, Santa brought little Bobby Tables a set of wires and bitwise logic
     * gates! Unfortunately, little Bobby is a little under the recommended age
     * range, and he needs help assembling the circuit.
     * 
     * Each wire has an identifier (some lowercase letters) and can carry a 16-bit
     * signal (a number from 0 to 65535). A signal is provided to each wire by a
     * gate, another wire, or some specific value. Each wire can only get a signal
     * from one source, but can provide its signal to multiple destinations. A gate
     * provides no signal until all of its inputs have a signal.
     * 
     * The included instructions booklet describes how to connect the parts
     * together: x AND y -> z means to connect wires x and y to an AND gate, and
     * then connect its output to wire z.
     * 
     * For example:
     * 
     * 123 -> x means that the signal 123 is provided to wire x.
     * x AND y -> z means that the bitwise AND of wire x and wire y is provided to
     * wire z.
     * p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2 and
     * then provided to wire q.
     * NOT e -> f means that the bitwise complement of the value from wire e is
     * provided to wire f.
     * Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). If,
     * for some reason, you'd like to emulate the circuit instead, almost all
     * programming languages (for example, C, JavaScript, or Python) provide
     * operators for these gates.
     * 
     * For example, here is a simple circuit:
     * 
     * 123 -> x
     * 456 -> y
     * x AND y -> d
     * x OR y -> e
     * x LSHIFT 2 -> f
     * y RSHIFT 2 -> g
     * NOT x -> h
     * NOT y -> i
     * After it is run, these are the signals on the wires:
     * 
     * d: 72
     * e: 507
     * f: 492
     * g: 114
     * h: 65412
     * i: 65079
     * x: 123
     * y: 456
     * 
     * In little Bobby's kit's instructions booklet (provided as your puzzle input),
     * what signal is ultimately provided to wire a?
     */

    public static void main(String args[]) {
        prob1();
    }

    public static void prob1() {
        // unsigned 16 bit int is char
        // A gate provides no signal until all of its inputs have a signal.
        // SO if either of the inputs are undefined, add back to queue. done when queue
        // is finished

        Queue<String> q = new LinkedList<String>(); // use offer for appending to end, poll to get front one
        Hashtable<String, Character> wires = new Hashtable<String, Character>();
        String line;
        String parts[];
        char val;

        createQueue(q);

        while (!q.isEmpty()) {
            line = q.poll();
            parts = line.split(" "); // split string by space
            // evaluate left side, find value (IF IT EXISTS YET) - if it doesn't, re-add
            // line to q
            val = evaluate(parts, wires);

        }

    }

    public static void createQueue(Queue<String> q) {
        // add all lines from input into queue so we can use them
        File file = new File("day7_input.txt");
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                q.offer(input.nextLine());
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static char evaluate(String arr[], Hashtable<String, Character> hash) {
        // given arr of instructions in either form
        // wire1, operation, wire2, ->, wire3
        // or
        // wire1, ->, wire2
        // or
        // NOT wire1 -> wire2
        // evaluate left side (if it exists) and return it if so
        char left;

        // case 1 - 2 operands on left , both are wires (so AND and OR)
        // case 2 - 2 operands on left , first is wire next is int (LSHIFT and RSHIFT)
        // case 3 - 1 operand on left , wire negated (NOT)
        // case 4 - 1 operand on left , all alone

        if (arr.length == 5) {
            // meaning there's two operands on the left side, evaluate
            // and, rshift, lshift, or, not
            if (hash.get(arr[0]) == null || hash.get(arr[2]) == null)
                return '\0';
            left = eval(arr, hash);

        }

        return '0';
    }

    public static char eval(String arr[], Hashtable<String, Character> hash) {
        int ret = 0;
        int w1Val = hash.get(arr[0]), w2Val = hash.get(arr[2]);
        short w1 = (short) w1Val, w2 = (short) w2Val;

        switch (arr[1]) {
            case "AND":
                ret = w1 & w2;
                break;
            case "OR":
                ret = w1 | w2;
                break;
            case "LSHIFT":
                break;

        }

        return (char) ret;
    }
}
