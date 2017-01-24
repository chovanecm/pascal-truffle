package cz.chovanecm.pascal.truffle.nodes.relational;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import cz.chovanecm.pascal.truffle.nodes.expression.BinaryNode;

/**
 * Created by martin on 1/24/17.
 */
public abstract class GreaterOrEqualOperator extends BinaryNode {
    @Specialization
    public boolean execute(VirtualFrame frame, boolean left, boolean right) {
        return !(right & !left);
    }

    @Specialization
    public boolean execute(VirtualFrame frame, double left, double right) {
        return left >= right;
    }

    @Specialization
    public boolean execute(VirtualFrame frame, long left, long right) {
        return left >= right;
    }

    @Specialization
    public boolean execute(VirtualFrame frame, Long left, double right) {
        return left.doubleValue() >= right;
    }

    @Specialization
    public boolean execute(VirtualFrame frame, double left, Long right) {
        return left >= right.doubleValue();
    }

    @Specialization
    public boolean execute(VirtualFrame frame, String left, String right) {
        return left.compareTo(right) >= 0;
    }


}
