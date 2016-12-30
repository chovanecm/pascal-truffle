/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.chovanecm.pascal.ast;

import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.rank.pj.pascal.*;
import cz.rank.pj.pascal.operator.*;
import cz.rank.pj.pascal.statement.*;

/**
 * @author martin
 */
public class RankAstFactory implements AstFactoryInterface {

    @Override
    public Statement createAssignment(VariableInterface variable, Expression expression) {
        return new Assignment((Variable) variable, expression);
    }

    @Override
    public Statement createWhile(Expression expression, Statement statement) {
        return new While(expression, statement);
    }

    @Override
    public Statement createIf(Expression expression, Statement statementTrue, Statement statementFalse) {
        return new If(expression, statementTrue, statementFalse);
    }

    @Override
    public Statement createForDownTo(Statement assignmentStatement, Expression finalExpression, Statement executeStatement) {
        return new ForDownto((Assignment) assignmentStatement, finalExpression, executeStatement);
    }

    @Override
    public Statement createForTo(Statement assignmentStatement, Expression finalExpression, Statement executeStatement) {
        return new ForTo((Assignment) assignmentStatement, finalExpression, executeStatement);
    }

    @Override
    public Expression createConstant(Integer integerValue) {
        return new Constant(integerValue);
    }

    @Override
    public Expression createConstant(Double doubleValue) {
        return new Constant(doubleValue);
    }

    @Override
    public Expression createConstant(String stringValue) {
        return new Constant(stringValue);
    }

    @Override
    public Expression createUnaryMinus(Expression primaryExpression) {
        return new UnaryMinus(primaryExpression);
    }

    @Override
    public Expression createParenthesis(Expression parseExpression) {
        return new Parenties(parseExpression);
    }

    @Override
    public VariableInterface createIntegerVariable(String id) {
        return new IntegerVariableMixin(id);
    }

    @Override
    public VariableInterface createStringVariable(String id) {
        return new StringVariableMixin(id);
    }

    @Override
    public VariableInterface createRealVariable(String id) {
        return new RealVariableMixing(id);
    }

    @Override
    public Expression createPlusOperator(Expression left, Expression right) {
        return new PlusOperator(left, right);
    }

    @Override
    public Expression createMinusOperator(Expression left, Expression right) {
        return new MinusOperator(left, right);
    }

    @Override
    public Expression createMultiplicationOperator(Expression left, Expression right) {
        return new MultipleOperator(left, right);
    }

    @Override
    public Expression createDivisionOperator(Expression left, Expression right) {
        return new DivideOperator(left, right);
    }

    @Override
    public Expression createLessOperator(Expression left, Expression right) {
        return new LessOperator(left, right);
    }

    @Override
    public Expression createGreaterOperator(Expression left, Expression right) {
        return new MoreOperator(left, right);
    }

    @Override
    public Expression createEqualOperator(Expression left, Expression right) {
        return new EqualOperator(left, right);
    }

    @Override
    public Expression createLessEqualOperator(Expression left, Expression right) {
        return new LessEqualOperator(left, right);
    }

    @Override
    public Expression createGreaterEqualOperator(Expression left, Expression right) {
        return new MoreEqualOperator(left, right);
    }

    @Override
    public Expression createNotEqualOperator(Expression left, Expression right) {
        return new NotEqualOperator(left, right);
    }

    @Override
    public Expression createNotOperator(Expression expression) {
        return new NotOperator(expression);
    }

    @Override
    public Expression createAndOperator(Expression left, Expression right) {
        return new AndOperator(left, right);
    }

    @Override
    public Expression createOrOperator(Expression left, Expression right) {
        return new OrOperator(left, right);
    }

    @Override
    public ProcedureInterface createWriteLnProcedure() {
        return new WriteLnProcedureMixin();
    }

    @Override
    public ProcedureInterface createWriteProcedure() {
        return new WriteProcedureMixin();
    }

    @Override
    public BlockInterface createBlock() {
        return new BlockDelegator();
    }

    class IntegerVariableMixin extends IntegerVariable implements VariableInterface {

        public IntegerVariableMixin() {
        }

        public IntegerVariableMixin(String name) {
            super(name);
        }

        public IntegerVariableMixin(String name, Integer value) {
            super(name, value);
        }

    }

    class StringVariableMixin extends StringVariable implements VariableInterface {

        public StringVariableMixin() {
        }

        public StringVariableMixin(String name) {
            super(name);
        }

        public StringVariableMixin(String name, String value) {
            super(name, value);
        }

    }

    class RealVariableMixing extends RealVariable implements VariableInterface {

        public RealVariableMixing() {
        }

        public RealVariableMixing(String name) {
            super(name);
        }

        public RealVariableMixing(String name, Double value) {
            super(name, value);
        }

    }

    class WriteLnProcedureMixin extends WriteLnProcedure implements ProcedureInterface {

        @Override
        public ProcedureInterface clone() {
            return new WriteLnProcedureMixin();
        }

    }

    class WriteProcedureMixin extends WriteProcedure implements ProcedureInterface {


        @Override
        public ProcedureInterface clone() {
            return new WriteProcedureMixin();
        }

    }

    class BlockDelegator extends Block implements BlockInterface {

    }

}
