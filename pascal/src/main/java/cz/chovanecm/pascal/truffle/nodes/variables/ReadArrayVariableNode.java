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

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import cz.chovanecm.pascal.truffle.PascalTypesGen;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;

/**
 * Created by martin on 2/12/17.
 */
@NodeChild(value = "index", type = ExpressionNode.class)
public abstract class ReadArrayVariableNode extends ReadVariableNode {
    public abstract ExpressionNode getIndex();

    @Specialization
    public Object executeRead(VirtualFrame frame, long index) {
        FrameSlot slot = frame.getFrameDescriptor().findFrameSlot(getVariableName());
        ArrayStructure arrayStructure = PascalTypesGen.asArrayStructure(frame.getValue(slot));
        return arrayStructure.read(Math.toIntExact(index));
    }

}
