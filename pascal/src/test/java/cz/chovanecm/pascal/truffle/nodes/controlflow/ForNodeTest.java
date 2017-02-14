package cz.chovanecm.pascal.truffle.nodes.controlflow;

import cz.chovanecm.TruffleRunner;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.truffle.TruffleAstFactory;
import cz.chovanecm.pascal.truffle.nodes.BlockNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import cz.chovanecm.pascal.truffle.nodes.variables.TestExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.variables.TestExpressionNodeGen;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by martin on 1/28/17.
 */
public class ForNodeTest {

    List<StatementNode> statements;
    AstFactoryInterface astFactory;
    String controlVariableName = "counter";
    String finalValueVariableName = "finalValue";
    TestExpressionNode trackControlVariableInLoop;
    TestExpressionNode trackFinalValueAfterLoop;

    @Before
    public void setUp() {
        statements = new ArrayList<>();
        astFactory = new TruffleAstFactory();
        statements.add(astFactory.createIntegerVariable(controlVariableName));
        statements.add(astFactory.createIntegerVariable(finalValueVariableName));
        trackControlVariableInLoop = TestExpressionNodeGen.create(astFactory.createReadVariable(controlVariableName));
        trackFinalValueAfterLoop = TestExpressionNodeGen.create(astFactory.createReadVariable(finalValueVariableName));

    }


    /**
     * Generate a for loop for i:= start to end, respectively
     * for i: = start downto and change the "end" variable in the loop.
     * Because changing the end variable shouldn't have an effect in Pascal loop,
     * the loop should stop as defined at the beginning.
     * <p>
     * The control variable, the initial value, and the final value must
     * be of the same scalar type (or subrange thereof), and must not be
     * altered by the repeated statement.
     * Note: The final value of the control variable is left undefined.
     *
     * @param direction
     * @return
     */
    public BlockNode generateCode(long start, long end, ForNodeFactory.ForDirection direction) {
        statements.add(
                astFactory.createGlobalAssignment(finalValueVariableName,
                        astFactory.createConstant(end)));

        StatementNode loopBody = astFactory.createBlock(
                new StatementNode[]{
                        trackControlVariableInLoop,
                        astFactory.createGlobalAssignment(finalValueVariableName,
                                astFactory.createConstant(end * 2 + 1))
                }
        );
        if (direction == ForNodeFactory.ForDirection.UP) {
            statements.add(astFactory.createForTo(
                    astFactory.createGlobalAssignment(controlVariableName, astFactory.createConstant(start)),
                    astFactory.createReadVariable(finalValueVariableName),
                    loopBody));
        } else if (direction == ForNodeFactory.ForDirection.DOWN) {
            statements.add(astFactory.createForDownTo(
                    astFactory.createGlobalAssignment(controlVariableName, astFactory.createConstant(start)),
                    astFactory.createReadVariable(finalValueVariableName),
                    loopBody));
        }
        statements.add(trackFinalValueAfterLoop);
        return astFactory.createBlock(statements);
    }

    @Test
    public void testANeverRunningLoop() {
        TruffleRunner.runAndReturnFrame(generateCode(0, -1, ForNodeFactory.ForDirection.UP));
        assertNull("The body loop should NOT be called.", trackControlVariableInLoop.getLongValue());
    }

    @Test
    public void testOnePassLoopDirectionUp() {
        TruffleRunner.runAndReturnFrame(generateCode(1, 1, ForNodeFactory.ForDirection.UP));
        assertEquals("The control variable should be 1, because the loop body should be entered exactly once.",
                new Long(1), trackControlVariableInLoop.getLongValue());

    }

    @Test
    public void testOnePassLoopDirectionDown() {
        TruffleRunner.runAndReturnFrame(generateCode(1, 1, ForNodeFactory.ForDirection.DOWN));
        assertEquals("The control variable should be 1, because the loop body should be entered exactly once.",
                new Long(1), trackControlVariableInLoop.getLongValue());
    }

    @Test
    public void testTenPassesLoopDirectionDown() {
        TruffleRunner.runAndReturnFrame(generateCode(50, 41, ForNodeFactory.ForDirection.DOWN));
        assertEquals("The control variable should be 41, because we started at 50 and went down to 41.",
                new Long(41), trackControlVariableInLoop.getLongValue());
    }

    @Test
    public void testTenPassesLoopDirectionUp() {
        TruffleRunner.runAndReturnFrame(generateCode(-19, -10, ForNodeFactory.ForDirection.UP));
        assertEquals("The control variable should be 10, because we started at -19 and went to -10.",
                new Long(-10), trackControlVariableInLoop.getLongValue());
    }

    @Test
    public void testManyPassesLoopDirectionUp() {
        TruffleRunner.runAndReturnFrame(generateCode(0, 1000000, ForNodeFactory.ForDirection.UP));
        assertEquals("The control variable should be 10, because we started at -19 and went to -10.",
                new Long(1000000), trackControlVariableInLoop.getLongValue());
    }
}