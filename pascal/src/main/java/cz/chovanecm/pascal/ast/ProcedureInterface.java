package cz.chovanecm.pascal.ast;

import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.NotEnoughtParametersException;
import cz.rank.pj.pascal.statement.Statement;
import java.util.Vector;

/**
 *
 * @author martin
 */
public interface ProcedureInterface extends Statement{

    public void setParameters(Vector<Expression> parseProcedureParameters) throws NotEnoughtParametersException;

    public ProcedureInterface clone();
    
}
