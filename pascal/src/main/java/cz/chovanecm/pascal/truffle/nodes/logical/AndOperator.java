package cz.chovanecm.pascal.truffle.nodes.logical;

import com.oracle.truffle.api.dsl.Specialization;
import cz.chovanecm.pascal.truffle.nodes.expression.BinaryNode;

/**
 * Created by martin on 1/24/17.
 */
public abstract class AndOperator extends BinaryNode {
    @Specialization
    public boolean execute(boolean left, boolean right) {
        return left && right;
    }
}
