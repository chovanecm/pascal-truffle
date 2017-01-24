package cz.chovanecm.pascal.truffle.nodes.variables;

import cz.chovanecm.pascal.truffle.nodes.StatementNode;

/**
 * Created by martin on 1/17/17.
 */
public abstract class DeclareVariableNode extends StatementNode {

    private String name;

    public DeclareVariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
