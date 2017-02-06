/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.chovanecm.contrib.cz.rank.pj.pascal.parser;


import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.ProcedureNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import cz.chovanecm.pascal.truffle.nodes.variables.DeclareVariableNode;
import cz.chovanecm.pascal.truffle.nodes.variables.ReadVariableNode;
import cz.chovanecm.pascal.truffle.nodes.variables.WriteVariableNode;

import java.util.List;

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

    default StatementNode createMainBlock(List<StatementNode> statements) {
        return createBlock(statements);
    }

    StatementNode createBlock(StatementNode[] statements);

    StatementNode createBlock(List<StatementNode> statements);

    public WriteVariableNode createGlobalAssignment(String variable, ExpressionNode expression);

    public StatementNode createWhile(ExpressionNode condition, StatementNode loopBody);

    public StatementNode createIf(ExpressionNode expression, StatementNode statementTrue, StatementNode statementFalse);

    public StatementNode createForDownTo(WriteVariableNode assignmentStatement, ExpressionNode finalExpression, StatementNode executeStatement);

    public StatementNode createForTo(WriteVariableNode assignmentStatement, ExpressionNode finalExpression, StatementNode executeStatement);

    public ExpressionNode createConstant(Long integerValue);

    public ExpressionNode createConstant(Double doubleValue);

    public ExpressionNode createConstant(String stringValue);

    public ExpressionNode createConstant(Boolean booleanValue);

    public ExpressionNode createUnaryMinus(ExpressionNode primaryExpression);

    public ExpressionNode createParenthesis(ExpressionNode expression);

    public DeclareVariableNode createIntegerVariable(String id);

    public DeclareVariableNode createStringVariable(String id);

    public DeclareVariableNode createRealVariable(String id);

    public DeclareVariableNode createBooleanVariable(String id);

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

    /**
     * Increment the variable by 1
     *
     * @param variableNode Variable to be incremented by 1
     * @return Statement
     */
    StatementNode createIncrementVariable(ReadVariableNode variableNode);

    ReadVariableNode createReadVariable(String id);

    StatementNode createDecrementVariable(ReadVariableNode variableNode);

    DeclareVariableNode createDeclareSimpleArray(String id, int lowerBound, int upperBound, Class<?> type);

    StatementNode createWriteArrayAssignment(String arrayName, ExpressionNode writePosition, ExpressionNode value);
}
