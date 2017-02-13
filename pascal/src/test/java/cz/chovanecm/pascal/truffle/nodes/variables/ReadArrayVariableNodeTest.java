/*
 *  Copyright 2017 Martin Chovanec, chovamar@fit.cvut.cz
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package cz.chovanecm.pascal.truffle.nodes.variables;

import cz.chovanecm.TruffleRunner;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.AstFactoryInterface;
import cz.chovanecm.pascal.truffle.TruffleAstFactory;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by martin on 2/12/17.
 */
public class ReadArrayVariableNodeTest {
    private AstFactoryInterface astFactory;
    private String arrayName;
    private DebugExpressionNode[] expressionNodes;

    @Before
    public void setup() {
        astFactory = new TruffleAstFactory();
        arrayName = "array";
        expressionNodes = new DebugExpressionNode[]{
                DebugExpressionNodeGen.create(astFactory.createReadArrayVariable(arrayName,
                        astFactory.createConstant(0L))),
                DebugExpressionNodeGen.create(astFactory.createReadArrayVariable(arrayName,
                        astFactory.createConstant(1L))),
                DebugExpressionNodeGen.create(astFactory.createReadArrayVariable(arrayName,
                        astFactory.createConstant(2L))),
                DebugExpressionNodeGen.create(astFactory.createReadArrayVariable(arrayName,
                        astFactory.createConstant(3L)))
        };
    }

    @Test
    public void testReadIntegerArrayVariable() {

        StatementNode statement = astFactory.createMainBlock(new StatementNode[]{
                astFactory.createDeclareSimpleArray(arrayName, 0, 3, long.class),
                astFactory.createWriteArrayAssignment(arrayName, astFactory.createConstant(0L), astFactory.createConstant(0L)),
                astFactory.createWriteArrayAssignment(arrayName, astFactory.createConstant(1L), astFactory.createConstant(10L)),
                astFactory.createWriteArrayAssignment(arrayName, astFactory.createConstant(2L), astFactory.createConstant(20L)),
                astFactory.createWriteArrayAssignment(arrayName, astFactory.createConstant(3L), astFactory.createConstant(30L)),
        });
        statement = statement.appendStatement(astFactory.createBlock(expressionNodes));
        TruffleRunner.runAndReturnFrame(statement);
        assertEquals(new Long(0), expressionNodes[0].getLongValue());
        assertEquals(new Long(10), expressionNodes[1].getLongValue());
        assertEquals(new Long(20), expressionNodes[2].getLongValue());
        assertEquals(new Long(30), expressionNodes[3].getLongValue());
    }

    @Test
    public void testReadStringArrayVariable() {

        StatementNode statement = astFactory.createMainBlock(new StatementNode[]{
                astFactory.createDeclareSimpleArray(arrayName, 0, 3, String.class),
                astFactory.createWriteArrayAssignment(arrayName, astFactory.createConstant(0L), astFactory.createConstant("Zero")),
                astFactory.createWriteArrayAssignment(arrayName, astFactory.createConstant(1L), astFactory.createConstant("One")),
                astFactory.createWriteArrayAssignment(arrayName, astFactory.createConstant(2L), astFactory.createConstant("Two")),
                astFactory.createWriteArrayAssignment(arrayName, astFactory.createConstant(3L), astFactory.createConstant("Three")),
        });
        statement = statement.appendStatement(astFactory.createBlock(expressionNodes));
        TruffleRunner.runAndReturnFrame(statement);
        assertEquals("Zero", expressionNodes[0].getStringValue());
        assertEquals("One", expressionNodes[1].getStringValue());
        assertEquals("Two", expressionNodes[2].getStringValue());
        assertEquals("Three", expressionNodes[3].getStringValue());
    }
}