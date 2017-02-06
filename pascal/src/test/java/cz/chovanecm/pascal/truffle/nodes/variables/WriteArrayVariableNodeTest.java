package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.frame.*;
import cz.chovanecm.TruffleRunner;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.truffle.PascalTypesGen;
import cz.chovanecm.pascal.truffle.TruffleAstFactory;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by chovamar on 2/6/17.
 */
public class WriteArrayVariableNodeTest {

    @Test
    public void testWriteToArray() {
        AstFactoryInterface astFactory = new TruffleAstFactory();
        long value = 42;
        int index = 15;
        int lowerBound = 10;
        int upperBound = 20;
        StatementNode block = astFactory.createMainBlock(new StatementNode[]{
                astFactory.createDeclareSimpleArray("arr", lowerBound, upperBound, long.class),
                astFactory.createWriteArrayAssignment("arr", astFactory.createConstant((long) index),
                        astFactory.createConstant(value)),
                astFactory.createWriteArrayAssignment("arr", astFactory.createConstant(index + 1L),
                        astFactory.createConstant(value * 2))
        });

        VirtualFrame frame = TruffleRunner.runAndReturnFrame(block);
        FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
        FrameSlot slot = frameDescriptor.findFrameSlot("arr");
        assert (slot.getKind() == FrameSlotKind.Object);
        ArrayStructure array = PascalTypesGen.asArrayStructure(FrameUtil.getObjectSafe(frame, slot));
        assertEquals(value, PascalTypesGen.asLongArray(array.getArray())[index - lowerBound]);
        assertEquals(value * 2, PascalTypesGen.asLongArray(array.getArray())[index + 1 - lowerBound]);
    }
}