import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Perfect {
    private final int N = 2000;
    private final int p;
    private final int a;
    private final int b;
    private final int aj;
    private final int bj;
    private final int[][] keys;
    private final int[][] values;
    private final int[] m;
    public Perfect(String[] words) {
        int[] counts = new int[N];
        m = new int[N];
        int maxHashCode = 0;
        for (String word : words) {
            int hashCode = word.hashCode();
            if (hashCode > maxHashCode) {
                maxHashCode = hashCode;
            }
        }
        BigInteger bigMaxHashCode = BigInteger.valueOf(maxHashCode);
        p = bigMaxHashCode.nextProbablePrime().intValue();
        Random random = new Random();
        a = random.nextInt(p - 1) + 1;
        b = random.nextInt(p-1)+1;
        aj = random.nextInt(p - 1) + 1;
        bj = random.nextInt(p-1)+1;
        for (String word : words) {
            int h1 = hash1(word);
            counts[h1]++;
        }
        for (int i = 0; i < N; i++) {
            m[i] = counts[i] * counts[i];
        }
        keys = new int[N][];
        values = new int[N][];
        for (int i = 0; i < N; i++) {
            keys[i] = new int[m[i]];
            values[i] = new int[m[i]];
            Arrays.fill(keys[i], -1);
        }
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int h1 = hash1(word);
            int h2 = hash2(word, h1);
            while (keys[h1][h2] != -1) {
                h2 = (h2 + 1) % m[h1];
            }
            keys[h1][h2] = word.hashCode();
            values[h1][h2] = i;
        }

    }
    public int hashCode(String string) {
        int h1 = hash1(string);
        int h2 = hash2(string, h1);
        while (keys[h1][h2] != string.hashCode()) {
            h2 = (h2 + 1) % m[h1];
            if (keys[h1][h2] == -1) {
                return -1;
            }
        }
        return values[h1][h2];
    }
    private int hash1(String word) {
        int hashCode = word.hashCode();
        BigInteger aBigInt = BigInteger.valueOf(a);
        BigInteger hashCodeBigInt = BigInteger.valueOf(hashCode);
        BigInteger bBigInt = BigInteger.valueOf(b);
        BigInteger pBigInt = BigInteger.valueOf(p);
        BigInteger result = aBigInt.multiply(hashCodeBigInt).add(bBigInt).mod(pBigInt).mod(BigInteger.valueOf(N));
        return result.intValue();
    }
    private int hash2(String word, int h1) {
        int hashCode = word.hashCode();
        BigInteger ajBigInt = BigInteger.valueOf(aj);
        BigInteger hashCodeBigInt = BigInteger.valueOf(hashCode);
        BigInteger bjBigInt = BigInteger.valueOf(bj);
        BigInteger pBigInt = BigInteger.valueOf(p);
        BigInteger result = ajBigInt.multiply(hashCodeBigInt).add(bjBigInt).mod(pBigInt).mod(BigInteger.valueOf(m[h1]));
        return result.intValue();
    }
    public static void main(String[] args) throws FileNotFoundException {
        String[] words = new String[2000];
        int n = 0;
        File file = new File("/Users/rishavshrivastava/Downloads/new/Sorting_/src/names-1.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine() && n < 2000) {
            words[n++] = scanner.nextLine();
        }
        Perfect pht = new Perfect(words);
        int[] a = new int[2000];
        for (int i = 0; i < words.length; i++) {
            String key = words[i];
            int h = pht.hashCode(key);
            a[i] = h;
        }
        Arrays.sort(a);
        //System.out.println(Arrays.toString(a));
        for (int i = 0; i < 2000; i++) {
            if (a[i] != i) {
                System.out.println("a[" + i + "] == " + a[i]);
                return;
            }
        }
        System.out.println("All good.");
    }
}
