package cz.chovanecm.pascal.truffle.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Created by martin on 1/17/17.
 */
@NodeInfo(shortName = "write")
public class WriteNode extends BuiltinProcedureNode {

    public WriteNode(ExpressionNode[] parameters) {
        super(parameters);
    }

    public WriteNode() {
        super(new ExpressionNode[]{});
    }

    @Override
    @ExplodeLoop
    public void executeStatement(VirtualFrame frame) {
        boolean firstPass = true;
        for (ExpressionNode parameter : parameters) {
            // NOTE: Should we use ConditionProfile?
            if (!firstPass) {
                // Pritn separating space between argumetns.
                print(" ");
            } else {
                firstPass = false;
            }
            print(parameter.executeGeneric(frame));
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void print(Object value) {
        System.out.print(value);
    }
}
