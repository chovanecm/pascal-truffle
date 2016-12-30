/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.chovanecm.contrib.cz.rank.pj.pascal.parser;

import cz.chovanecm.pascal.ast.BlockInterface;
import cz.chovanecm.pascal.ast.ProcedureInterface;
import cz.chovanecm.pascal.ast.VariableInterface;
import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.statement.Statement;

/**
 *
 * @author martin
 */
public interface AstFactoryInterface {

    ProcedureInterface createWriteLnProcedure();

    ProcedureInterface createWriteProcedure();

    BlockInterface createBlock();

    public Statement createAssignment(VariableInterface variable, Expression expression);

    public Statement createWhile(Expression expression, Statement statement);

    public Statement createIf(Expression expression, Statement statementTrue, Statement statementFalse);

    public Statement createForDownTo(Statement assignmentStatement, Expression finalExpression, Statement executeStatement);

    public Statement createForTo(Statement assignmentStatement, Expression finalExpression, Statement executeStatement);

    public Expression createConstant(Integer integerValue);

    public Expression createConstant(Double doubleValue);

    public Expression createConstant(String stringValue);

    public Expression createUnaryMinus(Expression primaryExpression);

    public Expression createParenthesis(Expression parseExpression);

    public VariableInterface createIntegerVariable(String id);

    public VariableInterface createStringVariable(String id);

    public VariableInterface createRealVariable(String id);

    public Expression createPlusOperator(Expression left, Expression right);

    public Expression createMinusOperator(Expression left, Expression right);

    public Expression createMultiplicationOperator(Expression left, Expression right);

    public Expression createDivisionOperator(Expression left, Expression right);

    public Expression createLessOperator(Expression left, Expression right);

    public Expression createGreaterOperator(Expression left, Expression right);

    public Expression createEqualOperator(Expression left, Expression right);

    public Expression createLessEqualOperator(Expression left, Expression right);

    public Expression createGreaterEqualOperator(Expression left, Expression right);

    public Expression createNotEqualOperator(Expression left, Expression right);

    public Expression createNotOperator(Expression expression);

    public Expression createAndOperator(Expression left, Expression right);

    public Expression createOrOperator(Expression left, Expression right);

}
