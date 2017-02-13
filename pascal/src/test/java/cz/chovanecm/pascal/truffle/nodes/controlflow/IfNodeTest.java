package cz.chovanecm.pascal.truffle.nodes.controlflow;

import cz.chovanecm.TruffleRunner;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.truffle.TruffleAstFactory;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import cz.chovanecm.pascal.truffle.nodes.variables.TestExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.variables.TestExpressionNodeGen;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by martin on 1/28/17.
 */
public class IfNodeTest {

    AstFactoryInterface astFactory;
    TestExpressionNode thenBranchUsed;
    TestExpressionNode elseBranchUsed;

    @Before
    public void setUp() {
        astFactory = new TruffleAstFactory();
        thenBranchUsed = TestExpressionNodeGen.create(astFactory.createConstant(true));
        ;
        elseBranchUsed = TestExpressionNodeGen.create(astFactory.createConstant(true));
        ;
    }

    public StatementNode generateIfNode(IfNodeType nodeType, boolean shouldUseThenBranch) {
        ExpressionNode conditionNode = astFactory.createConstant(shouldUseThenBranch);
        if (nodeType == IfNodeType.WITH_ELSE_BRANCH) {
            return astFactory.createIf(conditionNode, thenBranchUsed, elseBranchUsed);
        } else {
            return astFactory.createIf(conditionNode, thenBranchUsed, null);
        }
    }

    @Test
    public void testTrueWithElseBranch() {
        TruffleRunner.runAndReturnFrame(generateIfNode(IfNodeType.WITH_ELSE_BRANCH, true));
        assertEquals("The program should go via the 'then' branch!", true, thenBranchUsed.getBooleanValue());
        assertNull("The program should NOT go via the 'else' branch!", elseBranchUsed.getBooleanValue());
    }

    @Test
    public void testFalseWithElseBranch() {
        TruffleRunner.runAndReturnFrame(generateIfNode(IfNodeType.WITH_ELSE_BRANCH, false));
        assertNull("The program should NOT go via the 'then' branch!", thenBranchUsed.getBooleanValue());
        assertEquals("The program should go via the 'else' branch!", true, elseBranchUsed.getBooleanValue());
    }

    @Test
    public void testTrueWithoutElseBranch() {
        TruffleRunner.runAndReturnFrame(generateIfNode(IfNodeType.WITHOUT_ELSE_BRANCH, true));
        assertEquals("The program should go via the 'then' branch!", true, thenBranchUsed.getBooleanValue());
        assertNull("The program should NOT go via the 'else' branch and there even shouldn't be one!", elseBranchUsed.getBooleanValue());
    }

    @Test
    public void testFalseWithoutElseBranch() {
        TruffleRunner.runAndReturnFrame(generateIfNode(IfNodeType.WITHOUT_ELSE_BRANCH, false));
        assertNull("The program should NOT go via the 'then' branch!", thenBranchUsed.getBooleanValue());
        assertNull("The program should NOT go via the 'else' branch because there is no such branch!", elseBranchUsed.getBooleanValue());
    }

    enum IfNodeType {
        WITHOUT_ELSE_BRANCH,
        WITH_ELSE_BRANCH
    }
}