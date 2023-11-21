import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Point;
import java.util.StringTokenizer;

public class Day6 {

    /*
     * --- Day 6: Probably a Fire Hazard ---
     * 
     * Because your neighbors keep defeating you in the holiday house decorating
     * contest year after year, you've decided to deploy one million lights in a
     * 1000x1000 grid.
     * 
     * Furthermore, because you've been especially nice this year, Santa has mailed
     * you instructions on how to display the ideal lighting configuration.
     * 
     * Lights in your grid are numbered from 0 to 999 in each direction; the lights
     * at each corner are at 0,0, 0,999, 999,999, and 999,0. The instructions
     * include whether to turn on, turn off, or toggle various inclusive ranges
     * given as coordinate pairs. Each coordinate pair represents opposite corners
     * of a rectangle, inclusive; a coordinate pair like 0,0 through 2,2 therefore
     * refers to 9 lights in a 3x3 square. The lights all start turned off.
     * 
     * To defeat your neighbors this year, all you have to do is set up your lights
     * by doing the instructions Santa sent you in order.
     * 
     * For example:
     * 
     * turn on 0,0 through 999,999 would turn on (or leave on) every light.
     * 
     * toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning
     * off the ones that were on, and turning on the ones that were off.
     * 
     * turn off 499,499 through 500,500 would turn off (or leave off) the middle
     * four lights.
     * 
     * After following the instructions, how many lights are lit?
     */

    public static void main(String args[]) {
        prob1();
        prob2();
    }

    public static void prob1() {

        File file = new File("day6_input.txt");
        int lights[][] = new int[1000][1000]; // automatically populated w/ 0s

        try {
            Scanner directions = new Scanner(file);
            String toDo;
            while (directions.hasNextLine()) {
                toDo = directions.nextLine();
                followDirections(lights, toDo);
            }
            directions.close();
            System.out.printf("There are %d lights on after following all of Santa's directions.\n",
                    countLightsOn(lights));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void followDirections(int[][] arr, String direction) {
        // determine what you have to do & do it

        Point start = new Point(), end = new Point();
        getCoordinates(direction, start, end);

        if (direction.startsWith("turn on")) {
            turnOn(arr, start, end);
            ;
        } else if (direction.startsWith("turn off")) {
            turnOff(arr, start, end);
        } else {
            toggle(arr, start, end);
        }
    }

    public static void getCoordinates(String direction, Point start, Point end) {
        // extract starting coordinate and ending coordinate
        StringTokenizer st = new StringTokenizer(direction, " ");
        String coords[] = new String[2];
        String word;
        int i = 0;
        while (st.hasMoreTokens()) {
            word = st.nextToken();
            if (word.matches("\\d+.*")) {
                // if the word has numbers, save it in coords
                coords[i++] = word;
            }
        }

        // now have coordinates separated by commas

        // extract start coords
        st = new StringTokenizer(coords[0], ",");
        word = st.nextToken();
        start.x = Integer.valueOf(word);
        word = st.nextToken();
        start.y = Integer.valueOf(word);

        // extract end coords
        st = new StringTokenizer(coords[1], ",");
        word = st.nextToken();
        end.x = Integer.valueOf(word);
        word = st.nextToken();
        end.y = Integer.valueOf(word);

    }

    public static void turnOn(int[][] arr, Point start, Point end) {
        // turn on lights
        for (int i = start.x; i <= end.x; i++) {
            for (int j = start.y; j <= end.y; j++) {
                arr[i][j] = 1;
            }
        }
    }

    public static void turnOff(int[][] arr, Point start, Point end) {
        // turn off lights
        for (int i = start.x; i <= end.x; i++) {
            for (int j = start.y; j <= end.y; j++) {
                arr[i][j] = 0;
            }
        }
    }

    public static void toggle(int[][] arr, Point start, Point end) {
        // make light opposite of what it was before
        for (int i = start.x; i <= end.x; i++) {
            for (int j = start.y; j <= end.y; j++) {
                arr[i][j] = (arr[i][j] + 1) % 2; // flip 0 to 1 and 1 to 0
            }
        }
    }

    public static int countLightsOn(int[][] arr) {
        int on = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                on += arr[i][j];
            }
        }
        return on;
    }

    /*
     * 
     * You just finish implementing your winning light pattern when you realize you
     * mistranslated Santa's message from Ancient Nordic Elvish.
     * 
     * The light grid you bought actually has individual brightness controls; each
     * light can have a brightness of zero or more. The lights all start at zero.
     * 
     * The phrase turn on actually means that you should increase the brightness of
     * those lights by 1.
     * 
     * The phrase turn off actually means that you should decrease the brightness of
     * those lights by 1, to a minimum of zero.
     * 
     * The phrase toggle actually means that you should increase the brightness of
     * those lights by 2.
     * 
     * What is the total brightness of all lights combined after following Santa's
     * instructions?
     * 
     * For example:
     * 
     * turn on 0,0 through 0,0 would increase the total brightness by 1.
     * toggle 0,0 through 999,999 would increase the total brightness by 2000000.
     */

    public static void prob2() {
        File file = new File("day6_input.txt");
        int lights[][] = new int[1000][1000]; // automatically populated w/ 0s

        try {
            Scanner directions = new Scanner(file);
            String toDo;
            while (directions.hasNextLine()) {
                toDo = directions.nextLine();
                followNewDirections(lights, toDo);
            }
            directions.close();
            System.out.printf(
                    "The lights have a brightness of %d after following all of Santa's (translated) directions.\n",
                    countLightsOn(lights));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void followNewDirections(int[][] arr, String direction) {
        // determine what you have to do & do it

        Point start = new Point(), end = new Point();
        getCoordinates(direction, start, end);

        if (direction.startsWith("turn on")) {
            // turn on actually means increase brightness by 1
            changeBrightness(arr, start, end, 1);
        } else if (direction.startsWith("turn off")) {
            // turn off actually means decrease brightness by 1
            changeBrightness(arr, start, end, -1);
        } else {
            // toggle means increase brightness by 2
            changeBrightness(arr, start, end, 2);

        }
    }

    public static void changeBrightness(int[][] arr, Point start, Point end, int change) {
        // change brightness of the lights by 'change'
        for (int i = start.x; i <= end.x; i++) {
            for (int j = start.y; j <= end.y; j++) {
                arr[i][j] += change;
                // dont let it be less than 0 tho
                if (arr[i][j] < 0)
                    arr[i][j] = 0;
            }
        }
    }

}
