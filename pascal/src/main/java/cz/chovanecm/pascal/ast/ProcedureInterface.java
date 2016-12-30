package cz.chovanecm.pascal.ast;

import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.NotEnoughtParametersException;
import cz.rank.pj.pascal.statement.Statement;

import java.util.List;

/**
 *
 * @author martin
 */
public interface ProcedureInterface extends Statement {

    void setParameters(List<Expression> parameters) throws NotEnoughtParametersException;

    ProcedureInterface clone();

}
