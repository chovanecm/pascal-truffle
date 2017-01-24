package cz.chovanecm.pascal.truffle.nodes.logical;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;

/**
 * Created by martin on 1/24/17.
 */
@NodeChildren({@NodeChild("expression")})
@NodeInfo(shortName = "not")
public abstract class NotOperator extends ExpressionNode {
    @Specialization
    public boolean execute(boolean expression) {
        return !expression;
    }
}
