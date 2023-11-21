import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4 {
    /*
     * Santa needs help mining some AdventCoins (very similar to bitcoins) to use as
     * gifts for all the economically forward-thinking little girls and boys.
     * 
     * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at
     * least five zeroes. The input to the MD5 hash is some secret key (your puzzle
     * input, given below) followed by a number in decimal. To mine AdventCoins, you
     * must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...)
     * that produces such a hash.
     * 
     * For example:
     * 
     * If your secret key is abcdef, the answer is 609043, because the MD5 hash of
     * abcdef609043 starts with five zeroes (000001dbbfa...), and it is the lowest
     * such number to do so.
     * If your secret key is pqrstuv, the lowest number it combines with to make an
     * MD5 hash starting with five zeroes is 1048970; that is, the MD5 hash of
     * pqrstuv1048970 looks like 000006136ef....
     */

    public static String input = "iwrupvqb";

    public static void main(String args[]) {
        prob1(0, Integer.MAX_VALUE);
        prob2(0, Integer.MAX_VALUE);

    }

    public static void prob1(int start, int end) {
        // start trying to find the secret key + answer that gives a hash that starts
        // with 5 0s
        // begins with int 'start' and ends with int 'end'

        String hashInput, hashOutput;

        for (int i = start; i <= end; i++) {
            hashInput = input + i;
            hashOutput = getOutput(hashInput);
            // if length of output is <= 27, first 5 chars in hexadecimal must be 0s
            if (hashOutput.length() <= (32 - 5)) {
                System.out.printf("Valid number is %d\n", i);
                return;
            }

        }

        System.out.printf("No valid number found between %d and %d\n", start, end);

    }

    public static MessageDigest MD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getOutput(String input) {
        MessageDigest hash = MD5();
        hash.update(input.getBytes(Charset.forName("UTF-8")), 0, input.length());
        return new BigInteger(1, hash.digest()).toString(16); // returns output as a hex string
    }

    /*
     * 
     * Now find one that starts with six zeroes.
     * 
     */

    public static void prob2(int start, int end) {
        // start trying to find the secret key + answer that gives a hash that starts
        // with /SIX/ 0s
        // begins with int 'start' and ends with int 'end'

        String hashInput, hashOutput;

        for (int i = start; i <= end; i++) {
            hashInput = input + i;
            hashOutput = getOutput(hashInput);
            // if length of output is <= (32 - 6), first 6 chars in hexadecimal must be 0s
            if (hashOutput.length() <= (32 - 6)) {
                System.out.printf("Valid number is %d\n", i);
                return;
            }

        }

        System.out.printf("No valid number found between %d and %d\n", start, end);

    }

}