/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.chovanecm.pascal.truffle;

import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.ast.ProcedureInterface;

/**
 *
 * @author martin
 */
public class TruffleAstFactory implements AstFactoryInterface{

    @Override
    public ProcedureInterface createWriteLnProcedure() {
        return new WriteLnProcedure();
    }

    @Override
    public ProcedureInterface createWriteProcedure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
