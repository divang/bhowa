package societyhelp.app.util;

/**
 * Created by divang.sharma on 1/3/2017.
 */
import java.util.Random;

public class RandomString {

    private char[] alphaSymbols;
    private char[] numericSymbols;
    private char[] specialSymbols;

    private void init() {
        StringBuilder tmp = new StringBuilder();

        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        for (char ch = 'A'; ch <= 'Z'; ++ch)
            tmp.append(ch);
        alphaSymbols = tmp.toString().toCharArray();

        tmp.delete(0, tmp.length());
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        numericSymbols = tmp.toString().toCharArray();

        tmp.delete(0, tmp.length());
        tmp.append('$');
        tmp.append('&');
        tmp.append('!');
        tmp.append('@');
        tmp.append('#');
        tmp.append('(');
        tmp.append(')');
        specialSymbols = tmp.toString().toCharArray();
    }

    private final Random random = new Random();

    private char[] buf;

    public RandomString(int length) {
        if (length < 1)
            throw new IllegalArgumentException("length should be greater then 1.");
        init();
        buf = new char[length];
    }

    public String nextString() {
        int charArrayIndex =0;
        for (int idx = 0; idx < buf.length; ++idx)
        {
            charArrayIndex = random.nextInt(3);
            if(charArrayIndex == 0)
            {
                buf[idx] = alphaSymbols[random.nextInt(alphaSymbols.length)];
            }
            else if(charArrayIndex == 1)
            {
                buf[idx] = specialSymbols[random.nextInt(specialSymbols.length)];
            }
            else if(charArrayIndex == 2)
            {
                buf[idx] = numericSymbols[random.nextInt(numericSymbols.length)];
            }
        }
        return new String(buf);
    }
}