package cz.chovanecm.pascal.truffle.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Created by martin on 1/18/17.
 */
@NodeInfo(shortName = "multiply")
public abstract class MultiplyNode extends BinaryNode {

    @Specialization
    public long multiply(long left, long right) {
        return left * right;
    }

    @Specialization
    public double multiply(double left, double right) {
        return left * right;
    }

    @Specialization
    public double multiply(long left, double right) {
        return left * right;
    }

    @Specialization
    public double multiply(double left, long right) {
        return left * right;
    }
}
