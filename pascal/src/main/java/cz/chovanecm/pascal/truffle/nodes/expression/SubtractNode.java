package cz.chovanecm.pascal.truffle.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Created by martin on 1/18/17.
 */
@NodeInfo(shortName = "minus")
public abstract class SubtractNode extends BinaryNode {

    @Specialization
    public long minus(long left, long right) {
        return left - right;
    }

    @Specialization
    public double minus(double left, double right) {
        return left - right;
    }

    @Specialization
    public double minus(double left, long right) {
        return left - right;
    }

    @Specialization
    public double minus(long left, double right) {
        return left - right;
    }

    @Specialization
    public Object minus(Object left, Object right) {
        throw new IllegalArgumentException("Invalid arguments");
    }

}
