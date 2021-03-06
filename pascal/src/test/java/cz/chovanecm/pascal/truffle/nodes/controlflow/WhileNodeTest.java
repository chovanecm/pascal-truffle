package cz.chovanecm.pascal.truffle.nodes.controlflow;

import cz.chovanecm.TruffleRunner;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.exceptions.VariableNotDeclaredException;
import cz.chovanecm.pascal.truffle.TruffleAstFactory;
import cz.chovanecm.pascal.truffle.nodes.BlockNode;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
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
public class WhileNodeTest {

    List<StatementNode> statements;
    AstFactoryInterface astFactory;
    String counterVariableName = "counter";
    TestExpressionNode trackCounterValueInLoop;
    TestExpressionNode trackCounterValueAfterLoop;

    @Before
    public void setUp() throws VariableNotDeclaredException {
        statements = new ArrayList<>();
        astFactory = new TruffleAstFactory();
        statements.add(astFactory.createIntegerVariable(counterVariableName));
        statements.add(astFactory.createGlobalAssignment(counterVariableName, astFactory.createConstant(0L)));
        trackCounterValueInLoop = TestExpressionNodeGen.create(astFactory.createReadVariable(counterVariableName));
        trackCounterValueAfterLoop = TestExpressionNodeGen.create(astFactory.createReadVariable(counterVariableName));
    }


    public BlockNode generateCode(long numberOfPasses) throws VariableNotDeclaredException {
        ExpressionNode conditionNode = astFactory.createNotEqualOperator(
                astFactory.createReadVariable(counterVariableName),
                astFactory.createConstant(numberOfPasses)
        );

        StatementNode loopBody = astFactory.createBlock(
                new StatementNode[]{
                        trackCounterValueInLoop,
                        astFactory.createIncrementVariable(astFactory.createReadVariable(counterVariableName))
                }
        );
        statements.add(new WhileNode(conditionNode, loopBody));
        statements.add(trackCounterValueAfterLoop);
        return astFactory.createBlock(statements);
    }

    @Test
    public void testANeverRunningLoop() throws VariableNotDeclaredException {
        TruffleRunner.runAndReturnFrame(generateCode(0));
        assertNull("The body loop should NOT be called.", trackCounterValueInLoop.getLongValue());
        assertEquals("The counter value should be zero, because the loop body has not been called.",
                new Long(0), trackCounterValueAfterLoop.getLongValue());
    }

    @Test
    public void testOnePassLoop() throws VariableNotDeclaredException {
        TruffleRunner.runAndReturnFrame(generateCode(1));
        assertEquals("The last captured value in the loop should be 0.",
                new Long(0), trackCounterValueInLoop.getLongValue());
        assertEquals("The loop should be called once.",
                new Long(1), trackCounterValueAfterLoop.getLongValue());
    }

    @Test
    public void testTennPassesLoop() throws VariableNotDeclaredException {
        TruffleRunner.runAndReturnFrame(generateCode(10));
        assertEquals("The last captured value in the loop should be 9.",
                new Long(9), trackCounterValueInLoop.getLongValue());
        assertEquals("The loop should be called 10 times.",
                new Long(10), trackCounterValueAfterLoop.getLongValue());
    }
}