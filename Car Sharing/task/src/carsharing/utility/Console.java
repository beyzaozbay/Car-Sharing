package carsharing.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static String readLine() throws IOException {
        return br.readLine();
    }

    public static void writeLine(String line) {
        System.out.println(line);
    }

    public static int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }
}
