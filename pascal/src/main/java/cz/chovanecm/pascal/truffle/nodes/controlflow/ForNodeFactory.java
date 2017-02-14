package cz.chovanecm.pascal.truffle.nodes.controlflow;

import com.oracle.truffle.api.frame.FrameDescriptor;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.exceptions.VariableNotDeclaredException;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import cz.chovanecm.pascal.truffle.nodes.expression.EvaluateOnlyOnceNode;
import cz.chovanecm.pascal.truffle.nodes.variables.WriteVariableNode;

/**
 * Created by martin on 1/28/17.
 */
public class ForNodeFactory {
    private AstFactoryInterface astFactory;
    private FrameDescriptor frameDescriptor;

    public ForNodeFactory(AstFactoryInterface astFactory, FrameDescriptor frameDescriptor) {
        this.astFactory = astFactory;
        this.frameDescriptor = frameDescriptor;
    }

    public StatementNode generateForToNode(WriteVariableNode assignment, ExpressionNode finalExpression, StatementNode loopBody) throws VariableNotDeclaredException {
        loopBody = wrapBodyWithControlVariableStep(assignment, loopBody, ForDirection.UP);
        ExpressionNode conditionNode = buildConditionNode(assignment, finalExpression, ForDirection.UP);
        return assignment.appendStatement(astFactory.createWhile(conditionNode, loopBody), getFrameDescriptor());
    }

    public StatementNode generateForDownToNode(WriteVariableNode assignment, ExpressionNode finalExpression, StatementNode loopBody) throws VariableNotDeclaredException {
        loopBody = wrapBodyWithControlVariableStep(assignment, loopBody, ForDirection.DOWN);
        ExpressionNode conditionNode = buildConditionNode(assignment, finalExpression, ForDirection.DOWN);
        return assignment.appendStatement(astFactory.createWhile(conditionNode, loopBody), getFrameDescriptor());
    }

    /**
     * Append either incrementation or decrementation at the end of the body loop.
     *
     * @param assignment The control variable assignment, This is used to determine the variable that will be modified.
     * @param loopBody
     * @param direction
     * @return
     */
    private StatementNode wrapBodyWithControlVariableStep(WriteVariableNode assignment, StatementNode loopBody, ForDirection direction) throws VariableNotDeclaredException {
        if (direction == ForDirection.UP) {
            return loopBody.appendStatement(
                    astFactory.createIncrementVariable(
                            astFactory.createReadVariable(assignment.getVariableName())), getFrameDescriptor());

        } else if (direction == ForDirection.DOWN) {
            return loopBody.appendStatement(
                    astFactory.createDecrementVariable(astFactory.createReadVariable(assignment.getVariableName())),
                    getFrameDescriptor());
        } else {
            throw new RuntimeException("Unexpected loop direction " + direction.toString());
        }
    }

    private ExpressionNode buildConditionNode(WriteVariableNode assignment, ExpressionNode finalValue, ForDirection direction) throws VariableNotDeclaredException {
        if (direction == ForDirection.UP) {
            return astFactory.createLessEqualOperator(
                    astFactory.createReadVariable(assignment.getVariableName()),
                    new EvaluateOnlyOnceNode(finalValue)
            );
        } else if (direction == ForDirection.DOWN) {
            return astFactory.createGreaterEqualOperator(
                    astFactory.createReadVariable(assignment.getVariableName()),
                    new EvaluateOnlyOnceNode(finalValue)
            );
        } else {
            throw new RuntimeException("Unexpected loop direction " + direction.toString());
        }
    }

    public FrameDescriptor getFrameDescriptor() {
        return frameDescriptor;
    }

    public enum ForDirection {
        UP,
        DOWN
    }
}
