package cz.chovanecm.pascal.truffle.nodes;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import cz.chovanecm.pascal.truffle.PascalTypes;

/**
 * Created by martin on 1/17/17.
 */
@TypeSystemReference(PascalTypes.class)
public abstract class ExpressionNode extends Node {
    public abstract Object execute(VirtualFrame frame);
}
