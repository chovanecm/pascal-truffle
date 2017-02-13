package cz.chovanecm.pascal.truffle;

import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.truffle.nodes.*;
import cz.chovanecm.pascal.truffle.nodes.controlflow.ForNodeFactory;
import cz.chovanecm.pascal.truffle.nodes.controlflow.IfNode;
import cz.chovanecm.pascal.truffle.nodes.controlflow.WhileNode;
import cz.chovanecm.pascal.truffle.nodes.expression.*;
import cz.chovanecm.pascal.truffle.nodes.logical.OrOperator;
import cz.chovanecm.pascal.truffle.nodes.relational.*;
import cz.chovanecm.pascal.truffle.nodes.variables.*;

import java.util.List;

/**
 * Created by martin on 1/17/17.
 */
public class TruffleAstFactory implements AstFactoryInterface {
    private ForNodeFactory forNodeFactory;

    public TruffleAstFactory() {
        this.forNodeFactory = new ForNodeFactory(this);
    }

    @Override
    public ProcedureNode createWriteLnProcedure() {
        return new WritelnNode();
    }

    @Override
    public ProcedureNode createWriteProcedure() {
        return new WriteNode();
    }

    @Override
    public StatementNode createBlock(StatementNode[] statements) {
        return new BlockNode(statements);
    }

    @Override
    public StatementNode createBlock(List<StatementNode> statements) {
        return new BlockNode(statements);
    }

    @Override
    public WriteVariableNode createGlobalAssignment(String variable, ExpressionNode expression) {
        return WriteVariableNodeGen.create(expression, variable);
    }

    @Override
    public StatementNode createWhile(ExpressionNode condition, StatementNode loopBody) {
        return new WhileNode(condition, loopBody);
    }

    @Override
    public StatementNode createIf(ExpressionNode expression, StatementNode statementTrue, StatementNode statementFalse) {
        return new IfNode(expression, statementTrue, statementFalse);
    }

    @Override
    public StatementNode createForDownTo(WriteVariableNode assignmentStatement, ExpressionNode finalExpression, StatementNode executeStatement) {
        return forNodeFactory.generateForDownToNode(assignmentStatement, finalExpression, executeStatement);
    }

    @Override
    public StatementNode createForTo(WriteVariableNode assignmentStatement, ExpressionNode finalExpression, StatementNode executeStatement) {
        return forNodeFactory.generateForToNode(assignmentStatement, finalExpression, executeStatement);
    }

    @Override
    public ExpressionNode createConstant(Long longValue) {
        return ConstantNodeGen.create(longValue);
    }

    @Override
    public ExpressionNode createConstant(Double doubleValue) {
        return ConstantNodeGen.create(doubleValue);
    }

    @Override
    public ExpressionNode createConstant(String stringValue) {
        return ConstantNodeGen.create(stringValue);
    }

    @Override
    public ExpressionNode createConstant(Boolean booleanValue) {
        return ConstantNodeGen.create(booleanValue);
    }

    @Override
    public ExpressionNode createUnaryMinus(ExpressionNode primaryExpression) {
        return UnaryMinusNodeGen.create(primaryExpression);
    }

    @Override
    public ExpressionNode createParenthesis(ExpressionNode expression) {
        return ParenthesisExpressionNodeGen.create(expression);
    }

    @Override
    public DeclareVariableNode createIntegerVariable(String id) {
        return new DeclareLongVariable(id);
    }

    @Override
    public DeclareVariableNode createStringVariable(String id) {
        return new DeclareStringVariable(id);
    }

    @Override
    public DeclareVariableNode createRealVariable(String id) {
        return new DeclareRealVariable(id);
    }

    @Override
    public DeclareVariableNode createBooleanVariable(String id) {
        return new DeclareBooleanVariable(id);
    }

    @Override
    public ExpressionNode createPlusOperator(ExpressionNode left, ExpressionNode right) {
        return AddNodeGen.create(left, right);
    }

    @Override
    public ExpressionNode createMinusOperator(ExpressionNode left, ExpressionNode right) {
        return SubtractNodeGen.create(left, right);
    }

    @Override
    public ExpressionNode createMultiplicationOperator(ExpressionNode left, ExpressionNode right) {
        return MultiplyNodeGen.create(left, right);
    }

    @Override
    public ExpressionNode createDivisionOperator(ExpressionNode left, ExpressionNode right) {
        return DivisionNodeGen.create(left, right);
    }

    @Override
    public ExpressionNode createLessOperator(ExpressionNode left, ExpressionNode right) {
        return LessThanOperatorNodeGen.create(left, right);
    }

    @Override
    public ExpressionNode createGreaterOperator(ExpressionNode left, ExpressionNode right) {
        return GreaterThanOperatorNodeGen.create(left, right);
    }

    @Override
    public ExpressionNode createEqualOperator(ExpressionNode left, ExpressionNode right) {
        return EqualsOperatorNodeGen.create(left, right);
    }

    @Override
    public ExpressionNode createLessEqualOperator(ExpressionNode left, ExpressionNode right) {
        return LessOrEqualOperatorNodeGen.create(left, right);
    }

    @Override
    public ExpressionNode createGreaterEqualOperator(ExpressionNode left, ExpressionNode right) {
        return GreaterOrEqualOperatorNodeGen.create(left, right);
    }

    @Override
    public ExpressionNode createNotEqualOperator(ExpressionNode left, ExpressionNode right) {
        return NotEqualsOperatorNodeGen.create(left, right);
    }

    @Override
    public ExpressionNode createNotOperator(ExpressionNode expression) {
        return null;
    }

    @Override
    public ExpressionNode createAndOperator(ExpressionNode left, ExpressionNode right) {
        return null;
    }

    @Override
    public ExpressionNode createOrOperator(ExpressionNode left, ExpressionNode right) {
        return new OrOperator(left, right);
    }

    @Override
    public ReadVariableNode createReadVariable(String id) {
        return ReadVariableNodeGen.create(id);
    }

    @Override
    public StatementNode createIncrementVariable(ReadVariableNode variableNode) {
        ExpressionNode newLoopVariableValue = createPlusOperator(
                ReadVariableNodeGen.create(variableNode.getVariableName()),
                ConstantNodeGen.create(1L)
        );
        WriteVariableNode writeBackNode = WriteVariableNodeGen.create(newLoopVariableValue, variableNode.getVariableName());
        return writeBackNode;
    }

    @Override
    public StatementNode createDecrementVariable(ReadVariableNode variableNode) {
        ExpressionNode newLoopVariableValue = createMinusOperator(
                ReadVariableNodeGen.create(variableNode.getVariableName()),
                ConstantNodeGen.create(1L)
        );
        WriteVariableNode writeBackNode = WriteVariableNodeGen.create(newLoopVariableValue, variableNode.getVariableName());
        return writeBackNode;
    }

    @Override
    public DeclareVariableNode createDeclareSimpleArray(String id, int lowerBound, int upperBound, Class<?> type) {
        return new DeclareArrayVariable(id, lowerBound, upperBound, type);
    }

    @Override
    public StatementNode createWriteArrayAssignment(String arrayName, ExpressionNode writePosition, ExpressionNode value) {
        return WriteArrayVariableNodeGen.create(value, writePosition, arrayName);
    }

    @Override
    public ExpressionNode createReadArrayVariable(String arrayName, ExpressionNode readPosition) {
        return ReadArrayVariableNodeGen.create(readPosition, arrayName);
    }

    @Override
    public ProcedureNode createReadProcedure() {
        return null;
    }

    @Override
    public ExpressionNode createIntegerDivisionOperator(ExpressionNode left, ExpressionNode right) {
        return IntegerDivisionNodeGen.create(left, right);
    }
}
