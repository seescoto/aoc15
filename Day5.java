import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day5 {

    /*
     * --- Day 5: Doesn't He Have Intern-Elves For This? ---
     * 
     * Santa needs help figuring out which strings in his text file are naughty or
     * nice.
     * 
     * A nice string is one with all of the following properties:
     * 
     * It contains at least three vowels (aeiou only), like aei, xazegov, or
     * aeiouaeiouaeiou.
     * 
     * It contains at least one letter that appears twice in a row, like xx, abcdde
     * (dd), or aabbccdd (aa, bb, cc, or dd).
     * 
     * It does not contain the strings ab, cd, pq, or xy, even if they are part of
     * one of the other requirements.
     * 
     * For example:
     * 
     * ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...),
     * a double letter (...dd...), and none of the disallowed substrings.
     * aaa is nice because it has at least three vowels and a double letter, even
     * though the letters used by different rules overlap.
     * jchzalrnumimnmhp is naughty because it has no double letter.
     * haegwjzuvuyypxyu is naughty because it contains the string xy.
     * dvszwmarrgswjxmb is naughty because it contains only one vowel.
     * How many strings are nice?
     */

    public static void main(String args[]) {
        prob1();
        prob2();
    }

    public static void prob1() {
        File file = new File("day5_input.txt");
        try {
            Scanner words = new Scanner(file);
            String str;
            int niceWords = 0;

            while (words.hasNextLine()) {
                str = words.nextLine();
                if (isNice1(str))
                    niceWords++;
            }

            words.close();

            System.out.printf("There are %d nice words on Santa's list.\n", niceWords);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static boolean isNice1(String str) {
        // check to make sure there are 3+ vowels in the string, it has a double letter,
        // and doesnt have a forbidden substring
        // forbidden substrings = {ab, cd, pq, xy}

        int numVowels = 0;
        boolean hasDouble = false;
        char prev, curr;

        // check if first letter is a vowel since we wont see this in the for loop
        if (isAVowel(str.charAt(0)))
            numVowels++;

        for (int i = 1; i < str.length(); i++) {
            prev = str.charAt(i - 1);
            curr = str.charAt(i);

            if (isAVowel(curr))
                numVowels++;
            if (prev == curr)
                hasDouble = true;
            if (isForbidden(prev, curr))
                return false;
        }

        return (numVowels >= 3 && hasDouble);
    }

    public static boolean isAVowel(char c) {
        switch (c) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return false;
        }
    }

    public static boolean isForbidden(char c1, char c2) {
        // returns true if c1+c2 is ab, cd, pq, or xy
        if (c1 == 'a' && c2 == 'b')
            return true;
        else if (c1 == 'c' && c2 == 'd')
            return true;
        else if (c1 == 'p' && c2 == 'q')
            return true;
        else if (c1 == 'x' && c2 == 'y')
            return true;

        return false;
    }

    /*
     * Realizing the error of his ways, Santa has switched to a better model of
     * determining whether a string is naughty or nice. None of the old rules apply,
     * as they are all clearly ridiculous.
     * 
     * Now, a nice string is one with all of the following properties:
     * 
     * It contains a pair of any two letters that appears at least twice in the
     * string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like
     * aaa (aa, but it overlaps).
     * 
     * It contains at least one letter which repeats with exactly one letter between
     * them, like xyx, abcdefeghi (efe), or even aaa.
     * 
     * For example:
     * 
     * qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a
     * letter that repeats with exactly one letter between them (zxz).
     * 
     * xxyxx is nice because it has a pair that appears twice and a letter that
     * repeats with one between, even though the letters used by each rule overlap.
     * 
     * uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a
     * single letter between them.
     * 
     * ieodomkazucvgmuy is naughty because it has a repeating letter with one
     * between (odo), but no pair that appears twice.
     * 
     * How many strings are nice under these new rules?
     */

    public static void prob2() {
        File file = new File("day5_input.txt");
        try {
            Scanner words = new Scanner(file);
            String str;
            int niceWords = 0;

            while (words.hasNextLine()) {
                str = words.nextLine();
                if (isNice2(str))
                    niceWords++;
            }

            words.close();

            System.out.printf("There are %d nice words on Santa's list.\n", niceWords);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static boolean isNice2(String str) {
        // has a letter that repeats with exactly one char in between (ex. ada or lol)
        // has a pair of any two letters that appears at least twice BUT doesnt overlap

        boolean repeatLetter = false, hasPairTwice = false;
        char old, curr;

        // check if first two (or second two) letters are a pair that appears twice in
        // the string
        if (appearsTwice(str, 0) || appearsTwice(str, 1))
            hasPairTwice = true;

        for (int i = 2; i < str.length(); i++) {
            old = str.charAt(i - 2);
            curr = str.charAt(i);

            if (old == curr)
                repeatLetter = true;

            if (!hasPairTwice && appearsTwice(str, i))
                hasPairTwice = true;

            // break if both are true
            if (repeatLetter && hasPairTwice)
                return true;
        }

        return false;

    }

    public static boolean appearsTwice(String str, int charIndex) {
        // get first two chars starting at charIndex = substring
        // then search through rest of string (starting at charIndex + 2) to see if it
        // comes up again

        // avoid out of bounds index
        if (str.length() <= charIndex + 3)
            return false;

        char c1 = str.charAt(charIndex), c2 = str.charAt(charIndex + 1);

        // if c1 c2 come up again after their original one, then must be true
        for (int i = charIndex + 3; i < str.length(); i++) {
            if (str.charAt(i - 1) == c1 && str.charAt(i) == c2)
                return true;
        }

        // else looped through everything and didn't find them twice, so no
        return false;
    }

}
