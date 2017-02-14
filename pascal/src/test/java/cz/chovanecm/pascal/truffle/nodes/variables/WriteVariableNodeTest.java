package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.frame.*;
import cz.chovanecm.TruffleRunner;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.exceptions.TypeException;
import cz.chovanecm.pascal.exceptions.VariableNotDeclaredException;
import cz.chovanecm.pascal.truffle.TruffleAstFactory;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by martin on 1/24/17.
 */
public class WriteVariableNodeTest {
    private List<StatementNode> statements;
    private AstFactoryInterface astFactory;
    private String longVariableName = "variable1";
    private String stringVariableName = "variable2";

    @Before
    public void setUp() throws Exception {
        astFactory = new TruffleAstFactory();
        statements = new ArrayList<>();
        statements.add(astFactory.createIntegerVariable(longVariableName));
        statements.add(astFactory.createStringVariable(stringVariableName));
    }

    @Test
    public void testLongAssignment() throws VariableNotDeclaredException {
        long storedValue = 42;
        statements.add(astFactory.createGlobalAssignment(longVariableName,
                astFactory.createConstant(storedValue)
        ));
        VirtualFrame frame = TruffleRunner.runAndReturnFrame(astFactory.createMainBlock(statements));
        FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
        FrameSlot slot = frameDescriptor.findFrameSlot(longVariableName);
        assert (slot.getKind() == FrameSlotKind.Long);
        assert (FrameUtil.getLongSafe(frame, slot) == storedValue);
    }

    @Test
    public void testStringAssignment() throws VariableNotDeclaredException {
        String storedValue = "Pascal in JVM";
        statements.add(astFactory.createGlobalAssignment(stringVariableName,
                astFactory.createConstant(storedValue)
        ));
        VirtualFrame frame = TruffleRunner.runAndReturnFrame(astFactory.createMainBlock(statements));
        FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
        FrameSlot slot = frameDescriptor.findFrameSlot(stringVariableName);
        assert (slot.getKind() == FrameSlotKind.Object);
        assert (FrameUtil.getObjectSafe(frame, slot).equals(storedValue));
    }

    @Test(expected = TypeException.class)
    public void testDoubleToLongAssignment() throws VariableNotDeclaredException {
        double storedValue = 0.25;
        statements.add(astFactory.createGlobalAssignment(longVariableName,
                astFactory.createConstant(storedValue)
        ));
        VirtualFrame frame = TruffleRunner.runAndReturnFrame(astFactory.createMainBlock(statements));
        FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
        FrameSlot slot = frameDescriptor.findFrameSlot(longVariableName);
        fail("Shouldn't be reached.");
    }

    @Test(expected = TypeException.class)
    public void testDoubleToStringAssignment() throws VariableNotDeclaredException {
        double storedValue = 0.25;
        statements.add(astFactory.createGlobalAssignment(stringVariableName,
                astFactory.createConstant(storedValue)
        ));
        VirtualFrame frame = TruffleRunner.runAndReturnFrame(astFactory.createMainBlock(statements));
        FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
        FrameSlot slot = frameDescriptor.findFrameSlot(stringVariableName);
        fail("Shouldn't be reached.");
    }

    @Test(expected = TypeException.class)
    public void testStringToLongAssignment() throws VariableNotDeclaredException {
        String storedValue = "Pascal in JVM";
        statements.add(astFactory.createGlobalAssignment(longVariableName,
                astFactory.createConstant(storedValue)
        ));
        VirtualFrame frame = TruffleRunner.runAndReturnFrame(astFactory.createMainBlock(statements));
        FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
        FrameSlot slot = frameDescriptor.findFrameSlot(longVariableName);
        fail("Shouldn't be reached.");
    }
}

