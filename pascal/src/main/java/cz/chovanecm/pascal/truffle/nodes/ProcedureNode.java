package cz.chovanecm.pascal.truffle.nodes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by martin on 1/5/17.
 */
public abstract class ProcedureNode extends StatementNode {
    @Children
    final ExpressionNode[] parameters;

    public ProcedureNode(ExpressionNode[] parameters) {
        this.parameters = parameters;
    }

    public ExpressionNode[] getParameters() {
        return parameters;
    }

    /**
     * Create a new instance of the same type
     *
     * @param parameters
     * @return
     */
    public ProcedureNode newInstance(ExpressionNode[] parameters) {
        try {
            Constructor<? extends ProcedureNode> constructor = getClass().getConstructor(new Class[]{ExpressionNode[].class});
            return constructor.newInstance(new Object[]{parameters});
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.err.println("Really weird!!!!");
            e.printStackTrace();
        }
        return null;
    }
}
