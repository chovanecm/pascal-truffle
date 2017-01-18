package cz.chovanecm.pascal.truffle.nodes;

/**
 * Created by martin on 1/17/17.
 */
public abstract class ReadVariableNode extends ExpressionNode {
    private String name;

    public String getName() {
        return name;
    }

}
