package cz.chovanecm.pascal.truffle.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Created by martin on 1/5/17.
 */
public abstract class WritelnNode extends WriteNode {

    public WritelnNode(ExpressionNode[] parameters) {
        super(parameters);
    }

    @Override
    public void executeStatement(VirtualFrame frame) {
        super.executeStatement(frame);
        println();
    }

    @CompilerDirectives.TruffleBoundary
    public void println() {
        getContext().getOut().println();
    }
}
