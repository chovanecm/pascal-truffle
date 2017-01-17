package cz.chovanecm.pascal.truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Created by martin on 1/17/17.
 */
public class ReadVariableNode extends ExpressionNode {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return null;
    }
}
