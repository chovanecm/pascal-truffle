package cz.chovanecm.pascal;

import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.Parser;
import cz.rank.pj.pascal.NotEnoughtParametersException;
import cz.rank.pj.pascal.UnknowExpressionTypeException;
import cz.rank.pj.pascal.UnknowProcedureNameException;
import cz.rank.pj.pascal.lexan.LexicalException;
import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.parser.ParseException;
import cz.rank.pj.pascal.parser.UnknowVariableNameException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

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

    @org.kohsuke.args4j.Argument(usage = "Source file. If not specified, standard input will be used.")
    private String sourceFile;
    private Reader inputReader;

    @Option(name = "-no-truffle", usage = "Do not use Truffle, use a simple interpreter instead.")
    private boolean doNotUseTruffle = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, IOException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException, UnknowExpressionTypeException, NotUsableOperatorException {
        System.out.println("Truffle Pascal");
        System.out.println(System.getProperty("graal.TruffleMinInvokeThreshold"));
        new PascalMain().runProgram(args);
    }

    public void runProgram(String[] args) throws ParseException, IOException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException, UnknowExpressionTypeException, NotUsableOperatorException {
        parseArgs(args);
        Parser parser = new Parser(inputReader);

        //Karel's behaviour:
        if (doNotUseTruffle) {
            parser.parse();
            parser.run();
        } else {
            System.out.println("Not implemented yet. Disable truffle support.");
            //Truffle.getRuntime().createCallTarget(rootNode);
        }

    }

    private void parseArgs(String[] args) {
        CmdLineParser cmdParser = new CmdLineParser(this);
        try {
            cmdParser.parseArgument(args);
        } catch (CmdLineException ex) {
            System.err.println(ex.getLocalizedMessage());
            cmdParser.printUsage(System.err);
            System.exit(0);
        }

        if (sourceFile != null) {
            try {
                inputReader = Files.newBufferedReader(Paths.get(sourceFile));
            } catch (IOException ex) {
                System.err.println("Couldn't open file " + sourceFile);
            }

        } else {
            inputReader = new InputStreamReader(System.in);
        }

    }

}
