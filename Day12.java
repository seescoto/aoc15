import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//need json parser
/* 
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
*/

public class Day12 {
    /*
     * --- Day 12: JSAbacusFramework.io ---
     * 
     * Santa's Accounting-Elves need help balancing the books after a recent order.
     * Unfortunately, their accounting software uses a peculiar storage format.
     * That's where you come in.
     * 
     * They have a JSON document which contains a variety of things: arrays
     * ([1,2,3]), objects ({"a":1, "b":2}), numbers, and strings. Your first job is
     * to simply find all of the numbers throughout the document and add them
     * together.
     * 
     * For example:
     * 
     * [1,2,3] and {"a":2,"b":4} both have a sum of 6.
     * [[[3]]] and {"a":{"b":4},"c":-1} both have a sum of 3.
     * {"a":[-1,1]} and [-1,{"a":1}] both have a sum of 0.
     * [] and {} both have a sum of 0.
     * You will not encounter any strings containing numbers.
     * 
     * What is the sum of all numbers in the document?
     * 
     */

    public static void main(String args[]) {
        System.out.printf("The sum of all numbers is %d\n", prob1());
    }

    public static int prob1() {
        File file = new File("day12_input.txt");
        String input = getInput(file);
        int total = 0;

        // search w/ regex for one or more numbers, go through input and sum them all up
        Pattern pattern = Pattern.compile("-?\\d+"); // search for one or more numbers
        Matcher match = pattern.matcher(input);
        while (match.find()) {
            total += Integer.valueOf(match.group());
        }
        return total;
    }

    public static String getInput(File file) {
        String ret = "";
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                ret += scan.nextLine();
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /*
     * --- Part Two ---
     * 
     * Uh oh - the Accounting-Elves have realized that they double-counted
     * everything red.
     * 
     * Ignore any object (and all of its children) which has any property with the
     * value "red". Do this only for objects ({...}), not arrays ([...]).
     * 
     * [1,2,3] still has a sum of 6.
     * 
     * [1,{"c":"red","b":2},3] now has a sum of 4, because the middle object is
     * ignored.
     * 
     * {"d":"red","e":[1,2,3,4],"f":5} now has a sum of 0, because the entire
     * structure is ignored.
     * 
     * [1,"red",5] has a sum of 6, because "red" in an array has no effect.
     */

    public static int prob2() {
        File file = new File("day12_input.txt");
        String input = getInput(file);
        int total = sumNonRedNums(input);

        return total;
    }

    // in a json element, sum differently based on if it's a json array, object, or
    // a primitive element,
    // if its an object that has 'red' in it, ignore it all
    public static int sumNonRedNums(String input) {
        return 0;
    }
}
