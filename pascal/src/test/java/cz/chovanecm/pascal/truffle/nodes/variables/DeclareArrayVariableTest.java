package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import cz.chovanecm.TruffleRunner;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.truffle.PascalTypesGen;
import cz.chovanecm.pascal.truffle.TruffleAstFactory;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by chovamar on 2/6/17.
 */
public class DeclareArrayVariableTest {
    @Test
    public void testDeclareRealArray() throws UnexpectedResultException {
        String variableName = "arr";
        int lowerBound = 5;
        int upperBound = 10;
        AstFactoryInterface astFactory = new TruffleAstFactory();
        StatementNode mainBlock = astFactory.createMainBlock(
                new StatementNode[]{
                        astFactory.createDeclareSimpleArray(variableName, lowerBound, upperBound, double.class)
                }
        );
        VirtualFrame virtualFrame = TruffleRunner.runAndReturnFrame(mainBlock);
        FrameDescriptor frameDescriptor = virtualFrame.getFrameDescriptor();
        FrameSlot slot = frameDescriptor.findFrameSlot(variableName);
        assertNotNull(slot);
        ArrayStructure array = PascalTypesGen.expectArrayStructure(FrameUtil.getObjectSafe(virtualFrame, slot));
        assertEquals(lowerBound, array.getLowerBound());
        assertEquals(upperBound, array.getUpperBound());
        assertEquals(upperBound - lowerBound, PascalTypesGen.asDoubleArray(array.getArray()).length);
    }

    @Test
    public void testDeclareStringArray() throws UnexpectedResultException {
        String variableName = "arr";
        int lowerBound = 5;
        int upperBound = 10;
        AstFactoryInterface astFactory = new TruffleAstFactory();
        StatementNode mainBlock = astFactory.createMainBlock(
                new StatementNode[]{
                        astFactory.createDeclareSimpleArray(variableName, lowerBound, upperBound, String.class)
                }
        );
        VirtualFrame virtualFrame = TruffleRunner.runAndReturnFrame(mainBlock);
        FrameDescriptor frameDescriptor = virtualFrame.getFrameDescriptor();
        FrameSlot slot = frameDescriptor.findFrameSlot(variableName);
        assertNotNull(slot);
        ArrayStructure array = PascalTypesGen.expectArrayStructure(FrameUtil.getObjectSafe(virtualFrame, slot));
        assertEquals(lowerBound, array.getLowerBound());
        assertEquals(upperBound, array.getUpperBound());
        assertEquals(upperBound - lowerBound, PascalTypesGen.asStringArray(array.getArray()).length);
    }
}