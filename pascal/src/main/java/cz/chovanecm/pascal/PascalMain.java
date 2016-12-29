package cz.chovanecm.pascal;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author martin
 */
public class PascalMain {

    private static Reader inputReader;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Truffle Pascal");
        System.out.println(System.getProperty("graal.TruffleMinInvokeThreshold"));
        parseArgs(args);
    }

    private static void parseArgs(String[] args) {
        if (args.length > 0) {
            String fileName = args[0];
            try {
                inputReader = Files.newBufferedReader(Paths.get(fileName));
            } catch (IOException ex) {
                System.err.println("Couldn't open file " + fileName);
            }

        } else {
            inputReader = new InputStreamReader(System.in);
        }
    }

}
