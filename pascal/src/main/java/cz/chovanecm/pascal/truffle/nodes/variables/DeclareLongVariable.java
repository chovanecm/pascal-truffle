package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Created by martin on 1/23/17.
 */
public class DeclareLongVariable extends DeclareVariableNode {
    public DeclareLongVariable(String name, FrameSlot frameSlot) {
        super(name, frameSlot);
    }

    @Override
    public void execute(VirtualFrame frame) {
        //frame.getFrameDescriptor().addFrameSlot(getName(), FrameSlotKind.Long);
    }
}
