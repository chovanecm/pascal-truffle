/* 
 * Copyright 2017 Martin Chovanec.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.chovanecm.pascal.truffle.ast;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.ast.BlockInterface;
import cz.chovanecm.pascal.ast.ProcedureInterface;
import cz.chovanecm.pascal.ast.VariableInterface;
import cz.chovanecm.pascal.truffle.WriteLnProcedure;
import cz.chovanecm.pascal.truffle.nodes.PascalRootNode;
import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.statement.Statement;

/**
 *
 * @author martin
 */
public class TruffleAstFactory implements AstFactoryInterface {

    private RootNode entryPoint;
    private Source source;
    
    public TruffleAstFactory(Source source) {
        this.source = source;
    }
    
    @Override
    public ProcedureInterface createWriteLnProcedure() {
        return new WriteLnProcedure();
    }

    @Override
    public ProcedureInterface createWriteProcedure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BlockInterface createMainBlock() {
        // TODO: Temporary solution:
        SourceSection section = source.createUnavailableSection();
        PascalRootNode block = new PascalRootNode(section, new FrameDescriptor());
        setEntryPoint(block);
        return block;
    }

    
    @Override
    public BlockInterface createBlock() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createAssignment(VariableInterface variable, Expression expression) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createWhile(Expression ex, Statement statement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createIf(Expression expression, Statement statementTrue, Statement statementFalse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createForDownTo(Statement assignmentStatement, Expression finalExpression, Statement executeStatement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createForTo(Statement assignmentStatement, Expression finalExpression, Statement executeStatement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createConstant(Integer integerValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createConstant(Double integerValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createConstant(String integerValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createUnaryMinus(Expression primaryExpression) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createParenthesis(Expression parseExpression) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VariableInterface createIntegerVariable(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VariableInterface createStringVariable(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VariableInterface createRealVariable(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createPlusOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createMinusOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createMultiplicationOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createDivisionOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createLessOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createGreaterOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createEqualOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createLessEqualOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createGreaterEqualOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createNotEqualOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createNotOperator(Expression expression) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createAndOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression createOrOperator(Expression left, Expression right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public RootNode getEntryPoint() {
        return entryPoint;
    }

    protected void setEntryPoint(RootNode entryPoint) {
        this.entryPoint = entryPoint;
    }

}
