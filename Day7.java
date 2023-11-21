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
        prob2();
    }

    public static void prob1() {
        // unsigned 16 bit int is char
        // A gate provides no signal until all of its inputs have a signal.
        // SO if either of the inputs are undefined, add back to queue. done when queue
        // is finished

        Queue<String> q = new LinkedList<String>(); // use offer for appending to end, poll to get front one
        Hashtable<String, Short> wires = new Hashtable<String, Short>();
        String line, key;
        String parts[];
        short val = 0;

        createQueue(q);

        while (!q.isEmpty()) {
            line = q.poll();
            parts = line.split(" "); // split string by space
            // evaluate left side, find value (IF IT EXISTS YET) - if it doesn't, re-add
            // line to q
            // if it does, add value to right side wire
            if (canEvaluate(parts, wires)) {
                val = evaluate(parts, wires);
                key = parts[parts.length - 1];
                wires.put(key, val);
            } else {
                q.offer(line);
            }
        }

        System.out.printf("a: %d\n", wires.get("a"));

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

    public static boolean canEvaluate(String arr[], Hashtable<String, Short> hash) {
        // case 1 - 2 operands on left , (AND, OR, LSHIFT, RSHIFT)
        // case 2 - 1 operand on left , wire negated (NOT)
        // case 3 - 1 operand on left , all alone

        if (arr.length == 5) {
            // meaning there's two operands on the left side - evaluate arr0 and 2
            if (!(isValid(arr[0], hash) && isValid(arr[2], hash)))
                return false;
        } else if (arr.length == 4) {
            // must be "NOT" - w can be a number or a wire - evaluate arr1
            if (!isValid(arr[1], hash))
                return false;
        } else {
            // length == 3 so left side must be a single value - evaluate arr0
            if (!isValid(arr[0], hash))
                return false;
        }

        return true;
    }

    public static short evaluate(String arr[], Hashtable<String, Short> hash) {
        // given arr of instructions in either form
        // wire1, operation, wire2, ->, wire3
        // or
        // wire1, ->, wire2
        // or
        // NOT wire1 -> wire2
        // evaluate left side (if it exists) and put it in 'val'
        // if left side cant be evaluated, return false

        // case 1 - 2 operands on left , (AND, OR, LSHIFT, RSHIFT)
        // case 2 - 1 operand on left , wire negated (NOT)
        // case 3 - 1 operand on left , all alone
        short val;

        if (arr.length == 5) {
            // meaning there's two operands on the left side, evaluate
            val = evalTwoOperands(arr, hash);
        } else if (arr.length == 4) {
            // must be "NOT" - w can be a number or a wire
            short w = getValue(arr[1], hash);
            val = (short) (~w);
        } else {
            // length == 3 so left side must be a single value
            val = getValue(arr[0], hash);
        }

        // if passed, must be evaluatable
        return val;
    }

    public static short evalTwoOperands(String arr[], Hashtable<String, Short> hash) {
        // returns false if can't be evaluated,
        // if can be evaluated, returns true and sets val as evaluation

        short w1, w2, val;

        // if shift
        if (arr[1].equals("LSHIFT") || arr[1].equals("RSHIFT")) {
            w1 = getValue(arr[0], hash);
            w2 = getValue(arr[2], hash);

            if (arr[1].equals("LSHIFT")) {
                val = (short) (w1 << w2);
            } else {
                // must be RSHIFT
                val = (short) (w1 >> w2);
            }
        } else { // must be AND or OR
            w1 = getValue(arr[0], hash);
            w2 = getValue(arr[2], hash);

            if (arr[1].equals("AND")) {
                val = (short) (w1 & w2);
            } else {
                // must be OR
                val = (short) (w1 | w2);
            }
        }

        return val;
    }

    public static boolean isDigit(String strNum) {
        if (strNum == null)
            return false;
        try {
            Double.parseDouble(strNum);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static short getValue(String str, Hashtable<String, Short> hash) {
        if (isDigit(str))
            return Short.valueOf(str);
        else
            return hash.get(str);
    }

    public static boolean isValid(String str, Hashtable<String, Short> hash) {
        return (isDigit(str) || hash.get(str) != null);
    }

    /*
     * Now, take the signal you got on wire a, override wire b to that signal, and
     * reset the other wires (including wire a). What new signal is ultimately
     * provided to wire a?
     * 
     */

    public static void prob2() {
        Hashtable<String, Short> wires = new Hashtable<String, Short>();
        wires.put("b", (short) 16076);

        Queue<String> q = new LinkedList<String>(); // use offer for appending to end, poll to get front one
        String line, key;
        String parts[];
        short val = 0;

        createQueue(q);

        while (!q.isEmpty()) {
            line = q.poll();
            parts = line.split(" "); // split string by space
            // evaluate left side, find value (IF IT EXISTS YET) - if it doesn't, re-add
            // line to q
            // if it does, add value to right side wire
            if (canEvaluate(parts, wires)) {
                val = evaluate(parts, wires);
                key = parts[parts.length - 1];
                // dont reset b!!!
                if (!(key.equals("b")))
                    wires.put(key, val);
            } else {
                q.offer(line);
            }
        }

        System.out.printf("a: %d\n", wires.get("a"));

    }
}
