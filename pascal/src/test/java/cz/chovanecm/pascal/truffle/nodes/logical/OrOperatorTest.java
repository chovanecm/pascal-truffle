package cz.chovanecm.pascal.truffle.nodes.logical;

import com.oracle.truffle.api.frame.VirtualFrame;
import cz.chovanecm.TruffleRunner;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.truffle.TruffleAstFactory;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import cz.chovanecm.pascal.truffle.nodes.variables.DebugExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.variables.DebugExpressionNodeGen;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by martin on 1/24/17.
 */
public class OrOperatorTest {
    List<StatementNode> statements;
    AstFactoryInterface astFactory;
    boolean leftExecuted = false;
    boolean rightExecuted = false;

    @Before
    public void setUp() {
        statements = new ArrayList<>();
        astFactory = new TruffleAstFactory();
        boolean leftExecuted = false;
        boolean rightExecuted = false;

        statements.add(astFactory.createBooleanVariable("result"));
    }

    @Test
    public void trueOrFalse() throws Exception {

        statements.add(
                astFactory.createGlobalAssignment("result",
                        astFactory.createOrOperator(
                                new ExpressionNode() {
                                    @Override
                                    public Object execute(VirtualFrame frame) {
                                        leftExecuted = true;
                                        return true;
                                    }
                                },
                                new ExpressionNode() {
                                    @Override
                                    public Object execute(VirtualFrame frame) {
                                        rightExecuted = true;
                                        return false;
                                    }
                                })));
        DebugExpressionNode resultValue = DebugExpressionNodeGen.create(astFactory.createReadVariable("result"));
        statements.add(resultValue);
        TruffleRunner.runAndReturnFrame(astFactory.createMainBlock(statements));
        assertEquals(true, resultValue.getBooleanValue());
        assertEquals(true, leftExecuted);
        assertEquals(false, rightExecuted);

    }

    @Test
    public void falseOrTrue() throws Exception {

        statements.add(
                astFactory.createGlobalAssignment("result",
                        astFactory.createOrOperator(
                                new ExpressionNode() {
                                    @Override
                                    public Object execute(VirtualFrame frame) {
                                        leftExecuted = true;
                                        return false;
                                    }
                                },
                                new ExpressionNode() {
                                    @Override
                                    public Object execute(VirtualFrame frame) {
                                        rightExecuted = true;
                                        return true;
                                    }
                                })));
        DebugExpressionNode resultValue = DebugExpressionNodeGen.create(astFactory.createReadVariable("result"));
        statements.add(resultValue);
        TruffleRunner.runAndReturnFrame(astFactory.createMainBlock(statements));
        assertEquals(true, resultValue.getBooleanValue());
        assertEquals(true, leftExecuted);
        assertEquals(true, rightExecuted);

    }


    @Test
    public void falseOrfalse() throws Exception {

        statements.add(
                astFactory.createGlobalAssignment("result",
                        astFactory.createOrOperator(
                                new ExpressionNode() {
                                    @Override
                                    public Object execute(VirtualFrame frame) {
                                        leftExecuted = true;
                                        return false;
                                    }
                                },
                                new ExpressionNode() {
                                    @Override
                                    public Object execute(VirtualFrame frame) {
                                        rightExecuted = true;
                                        return false;
                                    }
                                })));
        DebugExpressionNode resultValue = DebugExpressionNodeGen.create(astFactory.createReadVariable("result"));
        statements.add(resultValue);
        TruffleRunner.runAndReturnFrame(astFactory.createMainBlock(statements));
        assertEquals(false, resultValue.getBooleanValue());
        assertEquals(true, leftExecuted);
        assertEquals(true, rightExecuted);

    }

}