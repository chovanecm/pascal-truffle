/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.chovanecm.contrib.cz.rank.pj.pascal.parser;


import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.ProcedureNode;
import cz.chovanecm.pascal.truffle.nodes.ReadVariableNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;

/**
 *
 * @author martin
 */
public interface AstFactoryInterface {

    ProcedureNode createWriteLnProcedure();

    ProcedureNode createWriteProcedure();

    default StatementNode createMainBlock(StatementNode[] statements) {
        return createBlock(statements);
    }

    StatementNode createBlock(StatementNode[] statements);

    public StatementNode createAssignment(String variable, ExpressionNode expression);

    public StatementNode createWhile(ExpressionNode expression, StatementNode statement);

    public StatementNode createIf(ExpressionNode expression, StatementNode statementTrue, StatementNode statementFalse);

    public StatementNode createForDownTo(StatementNode assignmentStatement, ExpressionNode finalExpression, StatementNode executeStatement);

    public StatementNode createForTo(StatementNode assignmentStatement, ExpressionNode finalExpression, StatementNode executeStatement);

    public ExpressionNode createConstant(Integer integerValue);

    public ExpressionNode createConstant(Double doubleValue);

    public ExpressionNode createConstant(String stringValue);

    public ExpressionNode createUnaryMinus(ExpressionNode primaryExpression);

    public ExpressionNode createParenthesis(ExpressionNode parseExpression);

    public ReadVariableNode createIntegerVariable(String id);

    public ReadVariableNode createStringVariable(String id);

    public ReadVariableNode createRealVariable(String id);

    public ExpressionNode createPlusOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createMinusOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createMultiplicationOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createDivisionOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createLessOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createGreaterOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createEqualOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createLessEqualOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createGreaterEqualOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createNotEqualOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createNotOperator(ExpressionNode expression);

    public ExpressionNode createAndOperator(ExpressionNode left, ExpressionNode right);

    public ExpressionNode createOrOperator(ExpressionNode left, ExpressionNode right);

}
