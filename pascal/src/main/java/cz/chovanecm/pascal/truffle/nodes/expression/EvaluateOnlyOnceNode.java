package cz.chovanecm.pascal.truffle.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;

/**
 * Created by martin on 1/28/17.
 */
@NodeInfo(shortName = "evalOnce", description = "Evaluate expression only once and remember the value.")
public class EvaluateOnlyOnceNode extends ExpressionNode {

    Object storedValue;
    @Child
    private ExpressionNode expressionNode;

    public EvaluateOnlyOnceNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
        storedValue = null;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        if (storedValue == null) {
            storedValue = expressionNode.execute(frame);
            return storedValue;
        } else {
            return storedValue;
        }
    }
}
