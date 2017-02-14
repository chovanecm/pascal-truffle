package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.NodeFields;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.VirtualFrame;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;

/**
 * Created by martin on 1/23/17.
 */
@NodeFields({@NodeField(name = "variableName", type = String.class),
        @NodeField(name = "frameSlot", type = FrameSlot.class)
})
public abstract class ReadVariableNode extends ExpressionNode {

    public abstract String getVariableName();

    public abstract FrameSlot getFrameSlot();

    @Specialization(guards = "isDouble(frame)")
    public double readDouble(VirtualFrame frame) {
        FrameSlot slot = getFrameSlot();
        return FrameUtil.getDoubleSafe(frame, slot);
    }

    @Specialization(guards = "isLong(frame)")
    public long readLong(VirtualFrame frame) {
        FrameSlot slot = getFrameSlot();
        return FrameUtil.getLongSafe(frame, slot);
    }

    @Specialization(guards = "isBoolean(frame)")
    public boolean readBoolean(VirtualFrame frame) {
        FrameSlot slot = getFrameSlot();
        return FrameUtil.getBooleanSafe(frame, slot);
    }

    @Specialization(guards = "isString(frame)")
    public String readString(VirtualFrame frame) {
        FrameSlot slot = getFrameSlot();
        return (String) FrameUtil.getObjectSafe(frame, slot);
    }

    public boolean isBoolean(VirtualFrame frame) {
        FrameSlot slot = getFrameSlot();
        return slot.getKind() == FrameSlotKind.Boolean;
    }

    public boolean isLong(VirtualFrame frame) {
        FrameSlot slot = getFrameSlot();
        return slot.getKind() == FrameSlotKind.Long;
    }

    public boolean isDouble(VirtualFrame frame) {
        FrameSlot slot = getFrameSlot();
        return slot.getKind() == FrameSlotKind.Double;
    }

    public boolean isString(VirtualFrame frame) {
        FrameSlot slot = getFrameSlot();
        return slot.getKind() == FrameSlotKind.Object;
    }
}
