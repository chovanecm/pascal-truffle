package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.NodeFields;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.VirtualFrame;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;

/**
 * Created by martin on 1/23/17.
 */
@NodeFields({@NodeField(name = "variableName", type = String.class)})
public abstract class ReadVariableNode extends ExpressionNode {

    public abstract String getVariableName();

    @Specialization
    public double readReal(VirtualFrame frame) {
        FrameSlot slot = frame.getFrameDescriptor().findFrameSlot(getVariableName());
        return FrameUtil.getDoubleSafe(frame, slot);
    }
}
