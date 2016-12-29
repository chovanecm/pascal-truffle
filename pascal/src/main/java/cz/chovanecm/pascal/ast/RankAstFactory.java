/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.chovanecm.pascal.ast;

import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.rank.pj.pascal.WriteLnProcedure;
import cz.rank.pj.pascal.WriteProcedure;

/**
 *
 * @author martin
 */
public class RankAstFactory implements AstFactoryInterface {

    class WriteLnProcedureDelegator extends WriteLnProcedure implements ProcedureInterface {

        @Override
        public ProcedureInterface clone() {
            return new WriteLnProcedureDelegator();
        }
    }

    class WriteProcedureDelegator extends WriteProcedure implements ProcedureInterface {

        @Override
        public ProcedureInterface clone() {
            return new WriteProcedureDelegator();
        }

    }

    @Override
    public ProcedureInterface createWriteLnProcedure() {
        return new WriteLnProcedureDelegator();
    }

    @Override
    public ProcedureInterface createWriteProcedure() {
        return new WriteProcedureDelegator();
    }

}
