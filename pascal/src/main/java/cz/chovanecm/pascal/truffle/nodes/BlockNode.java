/*
 * Copyright 2017 martin.
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
package cz.chovanecm.pascal.truffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author martin
 */
@NodeInfo(language = "Pascal")
public final class BlockNode extends StatementNode {
    @Children
    private final StatementNode [] statements;

    public BlockNode(StatementNode[] statements) {
        this.statements = statements;
    }

    public BlockNode(List<StatementNode> statements) {
        StatementNode[] statementArray = new StatementNode[statements.size()];
        statementArray = statements.toArray(statementArray);
        this.statements = statementArray;
    }

    @Override
    @ExplodeLoop
    public void execute(VirtualFrame frame) {
        for (StatementNode statement : statements) {
            statement.execute(frame);
        }
    }

    @Override
    public StatementNode appendStatement(StatementNode statement) {
        StatementNode[] newStatements = Arrays.copyOf(statements, statements.length + 1);
        newStatements[newStatements.length - 1] = statement;
        return new BlockNode(newStatements);
    }
}
