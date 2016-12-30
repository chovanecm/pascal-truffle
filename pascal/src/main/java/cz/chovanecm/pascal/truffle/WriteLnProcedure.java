/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.chovanecm.pascal.truffle;

import com.oracle.truffle.api.nodes.Node;
import cz.chovanecm.pascal.ast.ProcedureInterface;
import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.NotEnoughtParametersException;
import cz.rank.pj.pascal.UnknowExpressionTypeException;
import cz.rank.pj.pascal.operator.NotUsableOperatorException;

import java.util.List;

/**
 *
 * @author martin
 */
public class WriteLnProcedure extends Node implements ProcedureInterface{


    @Override
    public ProcedureInterface clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void execute() throws UnknowExpressionTypeException, NotUsableOperatorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setParameters(List<Expression> parameters) throws NotEnoughtParametersException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
