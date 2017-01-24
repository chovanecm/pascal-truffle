package cz.chovanecm.pascal.truffle.nodes.logical;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import cz.chovanecm.pascal.truffle.PascalTypesGen;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;

/**
 * Created by martin on 1/24/17.
 */
@NodeInfo(shortName = "or")
public class OrOperator extends ExpressionNode {

    @Child
    private ExpressionNode left;
    @Child
    private ExpressionNode right;

    public OrOperator(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return executeBoolean(frame);
    }

    public boolean executeBoolean(VirtualFrame frame) {
        boolean leftValue;
        try {
            leftValue = PascalTypesGen.expectBoolean(getLeft().execute(frame));
        } catch (UnexpectedResultException e) {
            CompilerDirectives.transferToInterpreter();
            throw new UnsupportedSpecializationException(this, new Node[]{getLeft(), getRight()}, new Object[]{e.getResult(), null});
        }
        boolean rightValue = false;
        if (!leftValue) {
            // Left value not evaluated to true, we need to evaluate left:
            try {
                rightValue = PascalTypesGen.expectBoolean(getRight().execute(frame));
            } catch (UnexpectedResultException e) {
                CompilerDirectives.transferToInterpreter();
                throw new UnsupportedSpecializationException(this, new Node[]{getLeft(), getRight()}, new Object[]{leftValue, e.getResult()});
            }
        }
        return execute(frame, leftValue, rightValue);
    }

    public boolean execute(VirtualFrame frame, boolean left, boolean right) {
        // The right command should be evaluated only if left was false!
        return left || right;
    }
}
