package cz.chovanecm.pascal.truffle.nodes;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.NodeField;
import cz.chovanecm.pascal.truffle.PascalContext;

/**
 * Created by martin on 1/17/17.
 */
@NodeField(name = "context", type = PascalContext.class)
@GenerateNodeFactory
public abstract class BuiltinProcedureNode extends ProcedureNode {
    public BuiltinProcedureNode(ExpressionNode[] parameters) {
        super(parameters);
    }

    /**
     * Get current {@link PascalContext}
     */
    public abstract PascalContext getContext();
}
