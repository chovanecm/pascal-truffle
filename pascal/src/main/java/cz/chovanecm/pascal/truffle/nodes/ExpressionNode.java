package cz.chovanecm.pascal.truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

/**
 * Created by martin on 1/17/17.
 */
public abstract class ExpressionNode extends Node {
    public abstract Object executeGeneric(VirtualFrame frame);
}
