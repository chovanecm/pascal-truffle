/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.chovanecm.contrib.cz.rank.pj.pascal.parser;

import cz.chovanecm.pascal.ast.ProcedureInterface;

/**
 *
 * @author martin
 */
public interface AstFactoryInterface {

    ProcedureInterface createWriteLnProcedure();

    ProcedureInterface createWriteProcedure();
    
}
