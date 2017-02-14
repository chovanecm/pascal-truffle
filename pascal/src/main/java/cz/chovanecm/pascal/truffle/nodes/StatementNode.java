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

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;


/**
 *
 * @author martin
 */

public abstract class StatementNode extends Node {

    public abstract void execute(VirtualFrame frame);

    /**
     * Append a statement to an existing statement and return it.
     * If possible, the node returns itself, otherwise a new copy is created and returned.
     *
     * @param statement
     * @return
     */
    public BlockNode appendStatement(StatementNode statement, FrameDescriptor frameDescriptor) {
        return new BlockNode(new StatementNode[]{this, statement}, frameDescriptor);
    }
}
