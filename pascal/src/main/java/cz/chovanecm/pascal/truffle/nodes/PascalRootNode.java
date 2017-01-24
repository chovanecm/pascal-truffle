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
package cz.chovanecm.pascal.truffle.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import cz.chovanecm.pascal.truffle.PascalLanguage;


/**
 *
 * @author martin
 */
@NodeInfo(language = "Pascal", description = "Root node of Pascal programs")
public class PascalRootNode extends RootNode {

    @Child
    StatementNode node;

    public PascalRootNode(SourceSection sourceSection, FrameDescriptor frameDescriptor, StatementNode entryPoint) {
        super(PascalLanguage.class, sourceSection, frameDescriptor);
        this.node = entryPoint;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        node.execute(frame);
        return frame;
    }

}
