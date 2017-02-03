package cz.chovanecm.contrib.cz.rank.pj.pascal.parser;

import com.oracle.truffle.api.nodes.NodeUtil;
import cz.chovanecm.pascal.truffle.nodes.BlockNode;
import cz.chovanecm.pascal.truffle.nodes.ConstantNode;
import cz.chovanecm.pascal.truffle.nodes.variables.*;
import cz.rank.pj.pascal.NotEnoughtParametersException;
import cz.rank.pj.pascal.UnknownProcedureNameException;
import cz.rank.pj.pascal.lexan.LexicalException;
import cz.rank.pj.pascal.parser.ParseException;
import cz.rank.pj.pascal.parser.UnknownVariableNameException;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: karl Date: Jan 31, 2006 Time: 2:33:08 PM
 */

// TODO Fix tests

public class ParserTest {

    private Parser parser;


    public static void main(String[] args) {
        PropertyConfigurator.configureAndWatch("log4j.properties");
    }

    public void testProgram() {
        parser = new Parser(new StringReader("\nprogram TEST; begin end."));

        try {
            parser.parse();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = ParseException.class)
    public void testWrongProgram() throws Exception {
        parser = new Parser(new StringReader("\nprogram ; begin end."));
        parser.parse();
    }

    public void testVar() {
        parser = new Parser(new StringReader("var a,b : integer; begin end."));

        try {
            parser.parse();
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = ParseException.class)
    public void testVarMissingId() throws Exception {
        parser = new Parser(new StringReader("var a, : integer; begin end."));
        parser.parse();
    }

    @Test(expected = ParseException.class)
    public void testVarDoubleColon() throws Exception {
        parser = new Parser(new StringReader("var a, b:: integer;  begin end."));
        parser.parse();
    }

    @Test(expected = ParseException.class)
    public void testVarDoubleComma() throws ParseException, UnknownVariableNameException, UnknownProcedureNameException, NotEnoughtParametersException, LexicalException, IOException {
        parser = new Parser(new StringReader("var a,, b: integer;  begin end."));
        parser.parse();
    }

    @Test(expected = ParseException.class)
    public void testVarDoubleId() throws Exception {
        parser = new Parser(new StringReader("var a b, : integer;  begin end."));
        parser.parse();
    }

    @Test(expected = ParseException.class)
    public void testVarMissingSemicolon() throws Exception {
        parser = new Parser(new StringReader("var a , b : integer begin end."));
        parser.parse();
    }

    @Test
    public void testVars() {
        parser = new Parser(
                new StringReader("var a  : integer; \n b : string; \nvar c : real;\n var d: Boolean; \nbegin end."));

        try {
            parser.parse();
            DeclareVariableNode node = parser.getGlobalVariable("a");
            assertEquals("a", node.getName());
            assertTrue(node instanceof DeclareLongVariable);

            node = parser.getGlobalVariable("b");
            assertEquals("b", node.getName());
            assertTrue(node instanceof DeclareStringVariable);

            node = parser.getGlobalVariable("c");
            assertEquals("c", node.getName());
            assertTrue(node instanceof DeclareRealVariable);

            node = parser.getGlobalVariable("d");
            assertEquals("d", node.getName());
            assertTrue(node instanceof DeclareBooleanVariable);


        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void helperTestConstantAssignment(WriteVariableNode assignment, Object value) {
        List<ConstantNode> children = NodeUtil.findAllNodeInstances(assignment, ConstantNode.class);
        assertEquals(1, children.size());
        ConstantNode node = children.get(0);
        assertEquals(value, node.getValue());
    }

    @Test
    public void testAsssigment() {
        parser = new Parser(new StringReader("var a  : integer;\nbegin a:=3; a:=1; \nend."));

        try {
            parser.parse();
            List<WriteVariableNode> assignments =
                    NodeUtil.findAllNodeInstances(parser.getEntryPoint(), WriteVariableNode.class);
            assertEquals(2, assignments.size());
            assertEquals("a", assignments.get(0).getVariableName());
            assertEquals("a", assignments.get(1).getVariableName());
            helperTestConstantAssignment(assignments.get(0), 3L);
            helperTestConstantAssignment(assignments.get(1), 1L);

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testBegin() {
        parser = new Parser(new StringReader("var a  : integer;\nbegin a:=(1); begin \na :=(a);end;  \nend."));

        try {
            parser.parse();
            DeclareVariableNode declare = parser.getGlobalVariable("a");
            assertEquals("a", declare.getName());
            assertTrue(declare instanceof DeclareLongVariable);
            List<BlockNode> blockNodes = NodeUtil.findAllNodeInstances(parser.getEntryPoint(), BlockNode.class);
            assertEquals(2, blockNodes.size());


        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testArray() throws ParseException, UnknownProcedureNameException,
            IOException, LexicalException,
            UnknownVariableNameException, NotEnoughtParametersException {
        parser = new Parser(new StringReader("var a  : array [1..10] of integer;\nbegin\n a[1]:=1;\n a[3]:=42;  \nend."));
        parser.parse();
    }
/*
    public void testParenties() {
        parser = new Parser(new StringReader("var a  : integer;\nbegin a:=(1); \na :=(a); \nend."));

        try {
            parser.parse();
            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);

            parser.run();

            assertEquals(1, (int) DeclareVariableNode.getInteger());
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testPlus() {
        parser = new Parser(new StringReader("var a,b,c  : integer;\nbegin a:= 1; \nb := a + a;\nc:= a + (a + b);\na:= a + 50;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(51, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(2, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("c");
            assertEquals("c", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(4, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testMinus() {
        parser = new Parser(new StringReader("var a,b,c  : integer;\nbegin a:= 1; \nb := a - a;\nc:= a - (a -a);\na:= a - 50;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(-49, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(0, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("c");
            assertEquals("c", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(1, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testMultiple() {
        parser = new Parser(new StringReader("var a,b,c  : integer;\nbegin a:= 1; \nb := a * 10;\nc:= b * b;\na:= a * a;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(1, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(10, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("c");
            assertEquals("c", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(100, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testPlusAndMultiple() {
        parser = new Parser(new StringReader("var a,b,c : integer; \nbegin a:= 5 + 5 * 5; b:= (5+5)*5;c:= 5 * 5 + 5;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(30, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(50, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("c");
            assertEquals("c", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(30, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testUnaryMinus() {
        parser = new Parser(new StringReader("var a,b,c  : integer;\nbegin a:= -1; \nb := -(a + a);\nc:= 0 + (- b);\na:= -a;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(1, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(2, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("c");
            assertEquals("c", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(-2, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testLess() {
        parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0; b:=2;\nwhile a < 10 do begin a:= a + 1; b:= b * 2;end;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(10, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(2048, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testLessEqual() {
        parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0; b:=2;\nwhile a <= 10 do begin a:= a + 1; b:= b * 2;end;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(11, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(4096, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testMore() {
        parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 10; b:=2048;\nwhile a > 0 do begin a:= a - 1; b:= b / 2;end;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(0, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(2, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testMoreEqual() {
        parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 11; b:=4096;\nwhile a >= 0 do begin a:= a - 1; b:= b / 2;end;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(-1, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(1, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testNotEqual() {
        parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 11; b:=-100;\nwhile a <> b do begin b:= b + 1;end;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(11, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(11, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testLessWithLeftExpression() {
        parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0;\nwhile a + 1 -1 < 10 + 1 do begin a:= a + 1;end;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(11, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testAnd() {
        parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0;\nwhile (a < 10) and (b < 10) do begin a:= a + 1; b:=b+1; end;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(10, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(10, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testOr() {
        parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0;\nwhile (a < 10) or (b < 10) do begin a:= a + 2; b:=b+1; end;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(20, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(10, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testNot() {
        parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0;\nwhile not a = 10 do begin a:= a + 2; b:=b+1; end;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(10, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(5, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testIf() {
        parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0;\nwhile not a = 10 do begin a:= a + 2; if a = 4 then b:=b+1; end;\nend."));

        try {
            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(10, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(1, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testIfElse() {
        try {
            parser = new Parser(getReaderFromClasspath("ifelse.test"));

            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(10, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(18, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private InputStreamReader getReaderFromClasspath(String fileName) {
        return new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    public void testForTo() {
        try {
            parser = new Parser(getReaderFromClasspath("forto.test"));

            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(21, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(21, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testForDownto() {
        try {
            parser = new Parser(getReaderFromClasspath("fordownto.test"));

            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(-1, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(21, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testBigFile() {
        try {
            parser = new Parser(getReaderFromClasspath("data.test"));

            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(-151, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(0, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testBigFile2() {
        try {
            parser = new Parser(getReaderFromClasspath("data2.test"));

            parser.parse();
            parser.run();

            DeclareVariableNode DeclareVariableNode = parser.getGlobalVariable("a");
            assertEquals("a", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(15, (int) DeclareVariableNode.getInteger());

            DeclareVariableNode = parser.getGlobalVariable("b");
            assertEquals("b", DeclareVariableNode.getName());
            assertTrue(DeclareVariableNode instanceof IntegerVariable);
            assertEquals(0, (int) DeclareVariableNode.getInteger());

        } catch (IOException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (LexicalException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    */
}
