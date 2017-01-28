package cz.chovanecm.pascal.truffle.nodes.controlflow;

import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import cz.chovanecm.pascal.truffle.nodes.expression.EvaluateOnlyOnceNode;
import cz.chovanecm.pascal.truffle.nodes.variables.WriteVariableNode;

/**
 * Created by martin on 1/28/17.
 */
public class ForNodeFactory {
    private AstFactoryInterface astFactory;

    public ForNodeFactory(AstFactoryInterface astFactory) {
        this.astFactory = astFactory;
    }

    public StatementNode generateForToNode(WriteVariableNode assignment, ExpressionNode finalExpression, StatementNode loopBody) {
        loopBody = wrapBodyWithControlVariableStep(assignment, loopBody, ForDirection.UP);
        ExpressionNode conditionNode = buildConditionNode(assignment, finalExpression, ForDirection.UP);
        return assignment.appendStatement(astFactory.createWhile(conditionNode, loopBody));
    }

    public StatementNode generateForDownToNode(WriteVariableNode assignment, ExpressionNode finalExpression, StatementNode loopBody) {
        loopBody = wrapBodyWithControlVariableStep(assignment, loopBody, ForDirection.DOWN);
        ExpressionNode conditionNode = buildConditionNode(assignment, finalExpression, ForDirection.DOWN);
        return assignment.appendStatement(astFactory.createWhile(conditionNode, loopBody));
    }

    /**
     * Append either incrementation or decrementation at the end of the body loop.
     *
     * @param assignment The control variable assignment, This is used to determine the variable that will be modified.
     * @param loopBody
     * @param direction
     * @return
     */
    private StatementNode wrapBodyWithControlVariableStep(WriteVariableNode assignment, StatementNode loopBody, ForDirection direction) {
        if (direction == ForDirection.UP) {
            return loopBody.appendStatement(
                    astFactory.createIncrementVariable(
                            astFactory.createReadVariable(assignment.getVariableName())));

        } else if (direction == ForDirection.DOWN) {
            return loopBody.appendStatement(
                    astFactory.createDecrementVariable(astFactory.createReadVariable(assignment.getVariableName())));
        } else {
            throw new RuntimeException("Unexpected loop direction " + direction.toString());
        }
    }

    private ExpressionNode buildConditionNode(WriteVariableNode assignment, ExpressionNode finalValue, ForDirection direction) {
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

    public enum ForDirection {
        UP,
        DOWN
    }
}
