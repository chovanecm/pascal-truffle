package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import cz.chovanecm.pascal.exceptions.TypeException;
import cz.chovanecm.pascal.truffle.PascalTypesGen;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;

import java.util.function.Consumer;

/**
 * Created by chovamar on 2/6/17.
 */
@NodeChild(value = "index", type = ExpressionNode.class)
public abstract class WriteArrayVariableNode extends WriteVariableNode {

    public abstract ExpressionNode getIndex();

    private void execute(VirtualFrame frame, Consumer<ArrayStructure> function) {
        FrameSlot slot = frame.getFrameDescriptor().findFrameSlot(getVariableName());
        try {
            ArrayStructure array = PascalTypesGen.expectArrayStructure(FrameUtil.getObjectSafe(frame, slot));
            function.accept(array);
        } catch (UnexpectedResultException e) {
            CompilerDirectives.transferToInterpreter();
            throw new TypeException(e);
        }
    }

    @Specialization
    public void executeLong(VirtualFrame frame, long value, long index) {
        execute(frame, (arrayStructure -> {
            arrayStructure.writeLong(Math.toIntExact((int) index), value);
        }));
    }

    @Specialization
    public void executeString(VirtualFrame frame, String value, long index) {
        execute(frame, (arrayStructure -> {
            arrayStructure.writeString(Math.toIntExact((int) index), value);
        }));
    }

    @Specialization
    public void executeDouble(VirtualFrame frame, double value, long index) {
        execute(frame, (arrayStructure -> {
            arrayStructure.writeDouble(Math.toIntExact((int) index), value);
        }));
    }

    @Specialization
    public void executeBoolean(VirtualFrame frame, boolean value, long index) {
        execute(frame, (arrayStructure -> {
            arrayStructure.writeBoolean(Math.toIntExact((int) index), value);
        }));
    }
}
