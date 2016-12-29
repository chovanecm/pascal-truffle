package cz.chovanecm.pascal;

import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.Parser;
import cz.rank.pj.pascal.NotEnoughtParametersException;
import cz.rank.pj.pascal.UnknowExpressionTypeException;
import cz.rank.pj.pascal.UnknowProcedureNameException;
import cz.rank.pj.pascal.lexan.LexicalException;
import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.parser.ParseException;
import cz.rank.pj.pascal.parser.UnknowVariableNameException;
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
    public static void main(String[] args) throws ParseException, IOException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException, UnknowExpressionTypeException, NotUsableOperatorException {
        System.out.println("Truffle Pascal");
        System.out.println(System.getProperty("graal.TruffleMinInvokeThreshold"));
        parseArgs(args);
        Parser parser = new Parser(inputReader);
        
        //Karel's behaviour:
        parser.parse();
        parser.run();
        
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
