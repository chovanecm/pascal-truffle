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
