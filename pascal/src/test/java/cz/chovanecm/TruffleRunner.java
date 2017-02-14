package cz.chovanecm;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import cz.chovanecm.pascal.truffle.nodes.BlockNode;
import cz.chovanecm.pascal.truffle.nodes.PascalRootNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;

/**
 * Run statements instantly
 */
public class TruffleRunner {
    public static VirtualFrame runAndReturnFrame(BlockNode statement) {
        return runAndReturnFrame(statement, statement.getFrameDescriptor());
    }

    public static VirtualFrame runAndReturnFrame(StatementNode statement, FrameDescriptor frameDescriptor) {
        CallTarget target = Truffle.getRuntime().createCallTarget(new PascalRootNode(null, frameDescriptor, statement));
        return (VirtualFrame) target.call();
    }
}
