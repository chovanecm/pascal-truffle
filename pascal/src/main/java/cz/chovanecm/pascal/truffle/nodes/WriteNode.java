package cz.chovanecm.pascal.truffle.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

/**
 * Created by martin on 1/17/17.
 */
public abstract class WriteNode extends BuiltinProcedureNode {

    public WriteNode(ExpressionNode[] parameters) {
        super(parameters);
    }

    @Override
    @ExplodeLoop
    public void executeStatement(VirtualFrame frame) {
        for (ExpressionNode parameter : parameters) {
            print(parameter.executeGeneric(frame));
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void print(Object value) {
        getContext().getOut().print(value);
    }
}
