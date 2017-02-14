package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.frame.FrameSlot;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;

/**
 * Created by martin on 1/17/17.
 */

public abstract class DeclareVariableNode extends StatementNode {

    private final String name;
    private final FrameSlot frameSlot;

    public DeclareVariableNode(String name, FrameSlot slot) {
        this.name = name;
        this.frameSlot = slot;
    }

    public String getName() {
        return name;
    }

    public FrameSlot getFrameSlot() {
        return frameSlot;
    }

}
