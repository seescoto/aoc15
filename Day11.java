public class Day11 {
    /*
     * --- Day 11: Corporate Policy ---
     * 
     * Santa's previous password expired, and he needs help choosing a new one.
     * 
     * To help him remember his new password after the old one expires, Santa has
     * devised a method of coming up with a password based on the previous one.
     * Corporate policy dictates that passwords must be exactly eight lowercase
     * letters (for security reasons), so he finds his new password by incrementing
     * his old password string repeatedly until it is valid.
     * 
     * Incrementing is just like counting with numbers: xx, xy, xz, ya, yb, and so
     * on. Increase the rightmost letter one step; if it was z, it wraps around to
     * a, and repeat with the next letter to the left until one doesn't wrap around.
     * 
     * Unfortunately for Santa, a new Security-Elf recently started, and he has
     * imposed some additional password requirements:
     * 
     * Passwords must include one increasing straight of at least three letters,
     * like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; abd
     * doesn't count.
     * 
     * Passwords may not contain the letters i, o, or l, as these letters can be
     * mistaken for other characters and are therefore confusing.
     * 
     * Passwords must contain at least two different, non-overlapping pairs of
     * letters, like aa, bb, or zz.
     * 
     * For example:
     * 
     * hijklmmn meets the first requirement (because it contains the straight hij)
     * but fails the second requirement requirement (because it contains i and l).
     * abbceffg meets the third requirement (because it repeats bb and ff) but fails
     * the first requirement.
     * abbcegjk fails the third requirement, because it only has one double letter
     * (bb).
     * 
     * The next password after abcdefgh is abcdffaa.
     * The next password after ghijklmn is ghjaabcc, because you eventually skip all
     * the passwords that start with ghi..., since i is not allowed.
     * Given Santa's current password (your puzzle input), what should his next
     * password be?
     */
    public static String password = "hxbxwxba";

    public static void main(String args[]) {
        String newPassword = prob1(password);
        prob1(newPassword);
    }

    public static String prob1(String str) {
        char[] chars = str.toCharArray();
        do {
            chars = increment(chars);
        } while (!isValidPassword(chars));

        System.out.println(String.valueOf(chars));
        return String.valueOf(chars);

    }

    public static char[] changeInvalidChars(char chars[]) {
        for (int i = 0; i < chars.length; i++) {
            if (!isValidChar(chars[i])) {
                chars = increment(chars, i);
            }
        }
        return chars;
    }

    public static char[] increment(char[] chars) {
        // given a character array, increment +1 from the end of the list until all
        // chars are valid
        return increment(chars, chars.length - 1);
    }

    public static char[] increment(char[] chars, int index) {
        if (index >= 0 && index < chars.length) {
            if (chars[index] != 'z') {
                chars[index]++;
                if (!isValidChar(chars[index]))
                    chars = increment(chars, index); // try again, same index
            } else {
                chars[index] = 'a';
                chars = increment(chars, index - 1);
            }
        }
        return chars;
    }

    public static boolean isValidChar(char c) {
        return (c != 'o' && c != 'i' && c != 'l');
    }

    public static boolean hasThreeInARow(char[] password) {
        for (int i = 0; i < password.length - 2; i++) {
            if (isThreeIncreasing(password, i))
                return true;
        }
        return false;
    }

    public static boolean isThreeIncreasing(char[] password, int index) {
        // returns true if three chars starting at index are increasing chars (like a,
        // b, c)
        boolean first, second;
        first = (password[index] + 1 == password[index + 1]);
        second = (password[index + 1] + 1 == password[index + 2]);
        return (first && second);
    }

    public static boolean hasTwoPairs(char[] password) {
        int numPairs = 0, i = 0;
        while (i < password.length - 1) {
            if (password[i] == password[i + 1]) {
                numPairs++;
                i++; // skip the second char of the current pair so we dont have overlapping pairs
            }
            i++;
        }
        return (numPairs >= 2);
    }

    public static boolean isValidPassword(char[] password) {
        // make sure all chars are valid
        for (char c : password) {
            if (!isValidChar(c))
                return false;
        }
        // else just make sure it's following the other rules
        return (hasTwoPairs(password) && hasThreeInARow(password));
    }

    /*
     * --- Part Two ---
     * 
     * Santa's password expired again. What's the next one?
     */

    // just run new password output back through prob1();
}