package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import cz.chovanecm.TruffleRunner;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.truffle.TruffleAstFactory;
import cz.chovanecm.pascal.truffle.nodes.BlockNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by martin on 1/24/17.
 */

/**
 * Test variable declaration.
 *
 * Possible variable name collisions should be solved by the parser.
 */
public class DeclareLongVariableTest {

    @Test
    public void testDeclareVariables() throws Exception {
        String var1 = "variable1";
        String var2 = "variable2";
        AstFactoryInterface astFactory = new TruffleAstFactory();
        BlockNode mainBlock = astFactory.createMainBlock(
                new StatementNode[]{
                        astFactory.createIntegerVariable(var1),
                        astFactory.createIntegerVariable(var2),
                }
        );

        FrameDescriptor frameDescriptor = TruffleRunner.runAndReturnFrame(mainBlock).getFrameDescriptor();

        assert frameDescriptor.findFrameSlot(var1) != null;
        FrameSlot slot = frameDescriptor.findFrameSlot(var1);
        Assert.assertEquals(FrameSlotKind.Long, slot.getKind());
        Assert.assertNotEquals(null, frameDescriptor.findFrameSlot(var2));
        FrameSlot slot2 = frameDescriptor.findFrameSlot(var2);
        Assert.assertEquals(FrameSlotKind.Long, slot2.getKind());
        Assert.assertFalse(slot.equals(slot2));

    }

}