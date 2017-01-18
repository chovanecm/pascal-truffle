package cz.chovanecm.pascal.truffle.nodes.expression;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;

/**
 * Created by martin on 1/18/17.
 */

/**
 * Expression in parentheses,e g. 2 + >>>(3-1)<<<
 */
@NodeInfo(shortName = "parentheses")
@NodeChildren({@NodeChild("expression")})
public abstract class ParenthesisExpressionNode extends ExpressionNode {

    @Specialization
    public long execute(long expression) {
        return expression;
    }

    @Specialization
    public double execute(double expression) {
        return expression;
    }

}
