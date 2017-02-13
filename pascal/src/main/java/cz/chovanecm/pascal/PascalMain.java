/* 
 * Copyright 2017 Martin Chovanec, chovamar@fit.cvut.cz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.chovanecm.pascal;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.vm.PolyglotEngine;
import cz.chovanecm.pascal.truffle.PascalLanguage;
import cz.rank.pj.pascal.NotEnoughtParametersException;
import cz.rank.pj.pascal.UnknownExpressionTypeException;
import cz.rank.pj.pascal.UnknownProcedureNameException;
import cz.rank.pj.pascal.lexan.LexicalException;
import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.parser.ParseException;
import cz.rank.pj.pascal.parser.UnknownVariableNameException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

/**
 * @author martin
 */
public class PascalMain {

    @org.kohsuke.args4j.Argument(usage = "Source file. If not specified, standard input will be used.")
    private String sourceFile;
    private Reader inputReader;

    @Option(name = "-time", usage = "Print execution time.")
    private boolean measureTime = false;

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Truffle Pascal");
        System.out.println(System.getProperty("graal.TruffleMinInvokeThreshold"));
        new PascalMain().runProgram(args);
    }

    public void runProgram(String[] args) throws ParseException, IOException, LexicalException, UnknownVariableNameException, UnknownProcedureNameException, NotEnoughtParametersException, UnknownExpressionTypeException, NotUsableOperatorException, Exception {
        parseArgs(args);

        long userStart = getUserTime();
        long cpuStart = getCpuTime();
        long wallStart = getTotalTime();

        runProgramWithTruffle();

        if (measureTime) {
            System.out.println();
            System.out.println("real " + (getCpuTime() - cpuStart) / 1E9 + "s");
            System.out.println("user " + (getUserTime() - userStart) / 1E9 + "s");
        }

        inputReader.close();
    }

    public void runProgramWithTruffle() throws Exception {
        Source.Builder builder = Source.newBuilder(inputReader);
        if (sourceFile != null) {
            builder = builder.name(sourceFile).mimeType(PascalLanguage.MIME_TYPE);
        } else {
            builder = builder.name("<interactive session>").interactive().mimeType(PascalLanguage.MIME_TYPE);
        }
        System.err.println("Building source");
        Source source = builder.build();
        PolyglotEngine engine = PolyglotEngine.newBuilder().setIn(System.in).setOut(System.out).build();
        System.err.println("Executing");
        PolyglotEngine.Value result = engine.eval(source);
        engine.dispose();
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

    /**
     * Get CPU time in nanoseconds.
     */
    public long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }

    /**
     * Get user time in nanoseconds.
     */
    public long getUserTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadUserTime() : 0L;
    }

    /**
     * Get system time in nanoseconds.
     */
    public long getTotalTime() {
        return Instant.now().toEpochMilli();
    }

}
