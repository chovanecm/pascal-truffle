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
