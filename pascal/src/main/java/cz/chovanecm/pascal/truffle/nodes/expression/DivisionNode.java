package cz.chovanecm.pascal.truffle.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Created by martin on 1/18/17.
 */
@NodeInfo(shortName = "divide")
public abstract class DivisionNode extends BinaryNode {

    /**
     * Divide two longs, return double (at least this is what freepascal does)
     *
     * @param left
     * @param right
     * @return
     */
    @Specialization
    public double divide(long left, long right) {
        return (double) left / right;
    }

    @Specialization
    public double divide(long left, double right) {
        return left / right;
    }

    @Specialization
    public double divide(double left, long right) {
        return left / right;
    }

    @Specialization
    public double divide(double left, double right) {
        return left / right;
    }
}
