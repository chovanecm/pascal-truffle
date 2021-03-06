package cz.chovanecm.pascal.truffle.nodes.expression;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;
import com.oracle.truffle.api.dsl.Specialization;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;

/**
 * Created by martin on 1/18/17.
 */
@NodeChildren({@NodeChild("expression")})
public abstract class UnaryMinusNode extends ExpressionNode {
    @Child
    ExpressionNode expression;

    @Specialization
    public long minus(long expression) {
        return -expression;
    }

    @Specialization
    public double minus(double expression) {
        return -expression;
    }

}
