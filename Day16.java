import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day16 {
    /*
     * --- Day 16: Aunt Sue ---
     * 
     * Your Aunt Sue has given you a wonderful gift, and you'd like to send her a
     * thank you card. However, there's a small problem: she signed it
     * "From, Aunt Sue".
     * 
     * You have 500 Aunts named "Sue".
     * 
     * So, to avoid sending the card to the wrong person, you need to figure out
     * which Aunt Sue (which you conveniently number 1 to 500, for sanity) gave you
     * the gift. You open the present and, as luck would have it, good ol' Aunt Sue
     * got you a My First Crime Scene Analysis Machine! Just what you wanted. Or
     * needed, as the case may be.
     * 
     * The My First Crime Scene Analysis Machine (MFCSAM for short) can detect a few
     * specific compounds in a given sample, as well as how many distinct kinds of
     * those compounds there are. According to the instructions, these are what the
     * MFCSAM can detect:
     * 
     * children, by human DNA age analysis.
     * cats. It doesn't differentiate individual breeds.
     * Several seemingly random breeds of dog: samoyeds, pomeranians, akitas, and
     * vizslas.
     * goldfish. No other kinds of fish.
     * trees, all in one group.
     * cars, presumably by exhaust or gasoline or something.
     * perfumes, which is handy, since many of your Aunts Sue wear a few kinds.
     * In fact, many of your Aunts Sue have many of these. You put the wrapping from
     * the gift into the MFCSAM. It beeps inquisitively at you a few times and then
     * prints out a message on ticker tape:
     * 
     * children: 3
     * cats: 7
     * samoyeds: 2
     * pomeranians: 3
     * akitas: 0
     * vizslas: 0
     * goldfish: 5
     * trees: 3
     * cars: 2
     * perfumes: 1
     * 
     * You make a list of the things you can remember about each Aunt Sue. Things
     * missing from your list aren't zero - you simply don't remember the value.
     * 
     * What is the number of the Sue that got you the gift?
     */

    public static void main(String args[]) {
        prob1();
        prob2();
    }

    public static void prob1() {
        File file = new File("day16_input.txt");
        Sue gifter = getGiftGiverSue();

        try {
            Scanner input = new Scanner(file);
            Sue curr;
            while (input.hasNextLine()) {
                curr = new Sue(input.nextLine());
                if (curr.matches(gifter)) {
                    System.out.printf("Gift-giving Sue is #%d\n", curr.id);
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Sue getGiftGiverSue() {
        Sue g = new Sue(0);

        g.children = 3;
        g.cats = 7;
        g.samoyeds = 2;
        g.pomeranians = 3;
        g.akitas = 0;
        g.vizslas = 0;
        g.goldfish = 5;
        g.trees = 3;
        g.cars = 2;
        g.perfumes = 1;

        return g;
    }

    /*
     * --- Part Two ---
     * 
     * As you're about to send the thank you note, something in the MFCSAM's
     * instructions catches your eye. Apparently, it has an outdated
     * retroencabulator, and so the output from the machine isn't exact values -
     * some of them indicate ranges.
     * 
     * In particular, the cats and trees readings indicates that there are greater
     * than that many (due to the unpredictable nuclear decay of cat dander and tree
     * pollen), while the pomeranians and goldfish readings indicate that there are
     * fewer than that many (due to the modial interaction of magnetoreluctance).
     * 
     * What is the number of the real Aunt Sue?
     */

    // make new 'matches' method in class Sue, then run the same thing with that new
    // method

    // make sure to compare gifter.improvedMatches(newSue), not other way around

    public static void prob2() {
        File file = new File("day16_input.txt");
        Sue gifter = getGiftGiverSue();

        try {
            Scanner input = new Scanner(file);
            Sue curr;
            while (input.hasNextLine()) {
                curr = new Sue(input.nextLine());
                if (gifter.improvedMatches(curr)) {
                    System.out.printf("Gift-giving Sue is #%d\n", curr.id);
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}