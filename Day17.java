import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day17 {
    /*
     * --- Day 17: No Such Thing as Too Much ---
     * 
     * The elves bought too much eggnog again - 150 liters this time. To fit it all
     * into your refrigerator, you'll need to move it into smaller containers. You
     * take an inventory of the capacities of the available containers.
     * 
     * For example, suppose you have containers of size 20, 15, 10, 5, and 5 liters.
     * If you need to store 25 liters, there are four ways to do it:
     * 
     * 15 and 10
     * 20 and 5 (the first 5)
     * 20 and 5 (the second 5)
     * 15, 5, and 5
     * 
     * Filling all containers entirely, how many different combinations of
     * containers can exactly fit all 150 liters of eggnog?
     */

    final public static int EGGNOG_AMOUNT = 150;

    public static void main(String args[]) {
        prob1();
        prob2();
    }

    public static void prob1() {
        ArrayList<Integer> containers = getContainers("day17_input.txt");
        ArrayList<Integer> combo = new ArrayList<>();
        ArrayList<Integer> empty = new ArrayList<>();
        ArrayList<ArrayList<Integer>> combos = new ArrayList<>();
        combos.add(empty);

        // get all possible combinations of containers, count how many can hold exactly
        // EGGNOG_AMOUNT
        getCombos(combos, combo, containers);

        int count = 0;
        for (ArrayList<Integer> arr : combos) {
            if (sumList(arr) == EGGNOG_AMOUNT)
                count++;
        }
        System.out.printf("There are %d possible combinations of containers that can hold %d liters of eggnog\n", count,
                EGGNOG_AMOUNT);

    }

    public static ArrayList<Integer> getContainers(String fileName) {
        // text file, each line has a number - size of the container
        ArrayList<Integer> arr = new ArrayList<>();
        File file = new File(fileName);
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                arr.add(Integer.valueOf(input.nextLine()));
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public static void getCombos(ArrayList<ArrayList<Integer>> combos, ArrayList<Integer> currentCombo,
            ArrayList<Integer> containers) {

        ArrayList<ArrayList<Integer>> combosCopy = new ArrayList<>(combos);

        // start with list combos which only contains an empty array, then go thru all
        // the objects in containers
        // for each container, for each array in combos, make a new list with combo +
        // container and then add it back to combos
        for (Integer c : containers) {
            for (ArrayList<Integer> combo : combosCopy) {
                ArrayList<Integer> newArr = new ArrayList<>(combo);
                newArr.add(c);
                combos.add(newArr);
            }
            combosCopy = new ArrayList<ArrayList<Integer>>(combos);
        }

    }

    public static int sumList(ArrayList<Integer> arr) {
        int total = 0;
        for (Integer i : arr) {
            total += i;
        }

        return total;
    }

    /*
     * --- Part Two ---
     * 
     * While playing with all the containers in the kitchen, another load of eggnog
     * arrives! The shipping and receiving department is requesting as many
     * containers as you can spare.
     * 
     * Find the minimum number of containers that can exactly fit all 150 liters of
     * eggnog. How many different ways can you fill that number of containers and
     * still hold exactly 150 litres?
     * 
     * In the example above, the minimum number of containers was two. There were
     * three ways to use that many containers, and so the answer there would be 3.
     */

    public static void prob2() {
        // could make a new combo method that stops when each array is at length k OR i
        // can just loop through all the combos and pick and choose from them
        ArrayList<Integer> containers = getContainers("day17_input.txt");
        ArrayList<Integer> combo = new ArrayList<>();
        ArrayList<Integer> empty = new ArrayList<>();
        ArrayList<ArrayList<Integer>> combos = new ArrayList<>();
        combos.add(empty);

        // get all possible combinations of containers, count how many can hold exactly
        // EGGNOG_AMOUNT
        getCombos(combos, combo, containers);

        // find minimum # containers that it takes to hold the egnog amount
        int minContainers = Integer.MAX_VALUE;
        for (ArrayList<Integer> arr : combos) {
            if (sumList(arr) == EGGNOG_AMOUNT)
                if (arr.size() < minContainers)
                    minContainers = arr.size();
        }
        System.out.printf("It takes at least %d containers to store %d liters of eggnog.\n", minContainers,
                EGGNOG_AMOUNT);

        // find how many containers have minsize and store exactly the right amount
        int count = 0;
        for (ArrayList<Integer> arr : combos) {
            if (sumList(arr) == EGGNOG_AMOUNT && arr.size() == minContainers)
                count++;
        }
        System.out.printf("There are %d ways to combine %d containers that store the eggnog.\n", count, minContainers);
    }
}
