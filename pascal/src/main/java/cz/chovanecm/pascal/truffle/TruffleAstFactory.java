package cz.chovanecm.pascal.truffle;

import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.truffle.nodes.*;

/**
 * Created by martin on 1/17/17.
 */
public class TruffleAstFactory implements AstFactoryInterface {
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
    public StatementNode createAssignment(String variable, ExpressionNode expression) {
        return null;
    }

    @Override
    public StatementNode createWhile(ExpressionNode expression, StatementNode statement) {
        return null;
    }

    @Override
    public StatementNode createIf(ExpressionNode expression, StatementNode statementTrue, StatementNode statementFalse) {
        return null;
    }

    @Override
    public StatementNode createForDownTo(StatementNode assignmentStatement, ExpressionNode finalExpression, StatementNode executeStatement) {
        return null;
    }

    @Override
    public StatementNode createForTo(StatementNode assignmentStatement, ExpressionNode finalExpression, StatementNode executeStatement) {
        return null;
    }

    @Override
    public ExpressionNode createConstant(Integer integerValue) {
        return new ConstantNode(integerValue);
    }

    @Override
    public ExpressionNode createConstant(Double doubleValue) {
        return null;
    }

    @Override
    public ExpressionNode createConstant(String stringValue) {
        return null;
    }

    @Override
    public ExpressionNode createUnaryMinus(ExpressionNode primaryExpression) {
        return null;
    }

    @Override
    public ExpressionNode createParenthesis(ExpressionNode parseExpression) {
        return null;
    }

    @Override
    public ReadVariableNode createIntegerVariable(String id) {
        return null;
    }

    @Override
    public ReadVariableNode createStringVariable(String id) {
        return null;
    }

    @Override
    public ReadVariableNode createRealVariable(String id) {
        return null;
    }

    @Override
    public ExpressionNode createPlusOperator(ExpressionNode left, ExpressionNode right) {
        return null;
    }

    @Override
    public ExpressionNode createMinusOperator(ExpressionNode left, ExpressionNode right) {
        return null;
    }

    @Override
    public ExpressionNode createMultiplicationOperator(ExpressionNode left, ExpressionNode right) {
        return null;
    }

    @Override
    public ExpressionNode createDivisionOperator(ExpressionNode left, ExpressionNode right) {
        return null;
    }

    @Override
    public ExpressionNode createLessOperator(ExpressionNode left, ExpressionNode right) {
        return null;
    }

    @Override
    public ExpressionNode createGreaterOperator(ExpressionNode left, ExpressionNode right) {
        return null;
    }

    @Override
    public ExpressionNode createEqualOperator(ExpressionNode left, ExpressionNode right) {
        return null;
    }

    @Override
    public ExpressionNode createLessEqualOperator(ExpressionNode left, ExpressionNode right) {
        return null;
    }

    @Override
    public ExpressionNode createGreaterEqualOperator(ExpressionNode left, ExpressionNode right) {
        return null;
    }

    @Override
    public ExpressionNode createNotEqualOperator(ExpressionNode left, ExpressionNode right) {
        return null;
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
        return null;
    }
}
