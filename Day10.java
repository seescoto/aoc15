public class Day10 {
    /*
     * --- Day 10: Elves Look, Elves Say ---
     * 
     * Today, the Elves are playing a game called look-and-say. They take turns
     * making sequences by reading aloud the previous sequence and using that
     * reading as the next sequence. For example, 211 is read as
     * "one two, two ones", which becomes 1221 (1 2, 2 1s).
     * 
     * Look-and-say sequences are generated iteratively, using the previous value as
     * input for the next step. For each step, take the previous value, and replace
     * each run of digits (like 111) with the number of digits (3) followed by the
     * digit itself (1).
     * 
     * For example:
     * 
     * 1 becomes 11 (1 copy of digit 1).
     * 11 becomes 21 (2 copies of digit 1).
     * 21 becomes 1211 (one 2 followed by one 1).
     * 1211 becomes 111221 (one 1, one 2, and two 1s).
     * 111221 becomes 312211 (three 1s, two 2s, and one 1).
     * Starting with the digits in your puzzle input, apply this process 40 times.
     * What is the length of the result?
     */

    public static void main(String args[]) {
        prob1("1113222113");
        prob2("1113222113");
    }

    public static void prob1(String input) {
        for (int i = 0; i < 40; i++) {
            input = lookAndSay(input);
        }

        System.out.printf("The length of the input after 40 iterations is %d\n", input.length());

    }

    public static String lookAndSay(String str) {
        // given a string of numbers
        String newStr = "";
        char curr;
        int reps;
        int i = 0, j;

        while (i < str.length()) {
            curr = str.charAt(i);
            reps = 1;
            j = i + 1;
            while (j < str.length() && curr == str.charAt(j)) {
                j++;
                reps++;
            }
            // now we have reps number of curr , add that to string
            // (so if we had 111 then rep = 3, curr = 1, add 31 to string)
            newStr += String.valueOf(reps) + curr;
            // now go on
            i = j;
        }

        return newStr;
    }

    /*
     * --- Part Two ---
     * 
     * Neat, right? You might also enjoy hearing John Conway talking about this
     * sequence (that's Conway of Conway's Game of Life fame).
     * 
     * Now, starting again with the digits in your puzzle input, apply this process
     * 50 times. What is the length of the new result?
     */

    public static void prob2(String input) {
        for (int i = 0; i < 50; i++) {
            input = lookAndSay(input);
        }

        System.out.printf("The length of the input after 50 iterations is %d\n", input.length());

    }
}