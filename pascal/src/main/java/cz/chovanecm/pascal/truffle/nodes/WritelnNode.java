package cz.chovanecm.pascal.truffle.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Created by martin on 1/5/17.
 */
@NodeInfo(shortName = "writeln")
public class WritelnNode extends WriteNode {

    public WritelnNode(ExpressionNode[] parameters) {
        super(parameters);
    }

    public WritelnNode() {
        super(new ExpressionNode[]{});
    }

    @Override
    public void execute(VirtualFrame frame) {
        super.execute(frame);
        println();
    }

    @CompilerDirectives.TruffleBoundary
    public void println() {
        System.out.println();
    }
}
