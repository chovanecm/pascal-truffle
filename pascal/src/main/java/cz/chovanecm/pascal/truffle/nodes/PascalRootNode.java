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
import cz.chovanecm.pascal.ast.BlockInterface;
import cz.chovanecm.pascal.truffle.PascalLanguage;
import cz.rank.pj.pascal.UnknowExpressionTypeException;
import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.statement.Statement;

/**
 *
 * @author martin
 */
@NodeInfo(language = "Pascal", description = "Root node of Pascal programmes")
public class PascalRootNode extends RootNode implements BlockInterface{

    public PascalRootNode(SourceSection sourceSection, FrameDescriptor frameDescriptor) {
        super(PascalLanguage.class, sourceSection, frameDescriptor);
    }



    @Override
    public Object execute(VirtualFrame frame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void add(Statement st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void execute() throws UnknowExpressionTypeException, NotUsableOperatorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
