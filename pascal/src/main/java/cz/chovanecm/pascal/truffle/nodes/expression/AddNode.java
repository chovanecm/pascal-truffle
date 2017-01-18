package cz.chovanecm.pascal.truffle.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Created by martin on 1/18/17.
 */
@NodeInfo(shortName = "add")
public abstract class AddNode extends BinaryNode {

    @Specialization
    public long add(long left, long right) {
        return left + right;
    }

    @Specialization
    public double add(double left, double right) {
        return left + right;
    }

    @Specialization
    public double add(double left, long right) {
        return left + right;
    }

    @Specialization
    public double add(long left, double right) {
        return left + new Double(right);
    }

    @Specialization
    public String add(String left, String right) {
        return left + right;
    }

}
