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

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import cz.chovanecm.contrib.cz.rank.pj.pascal.parser.Parser;
import cz.chovanecm.pascal.truffle.nodes.BlockNode;
import cz.chovanecm.pascal.truffle.nodes.PascalRootNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *
 * @author martin
 */
@TruffleLanguage.Registration(mimeType = PascalLanguage.MIME_TYPE, name = "Pascal", version = "0.1")
public class PascalLanguage extends TruffleLanguage<PascalContext> {
    public static final String MIME_TYPE = "text/pascal";

    public static final PascalLanguage INSTANCE = new PascalLanguage();
    
    // Ensure singleton
    private PascalLanguage() {
        
    }
    @Override
    protected PascalContext createContext(Env env) {
        BufferedReader in = new BufferedReader(new InputStreamReader(env.in()));
        PrintWriter out = new PrintWriter(env.out());
        return new PascalContext(env, in, out);
    }

    @Override
    protected CallTarget parse(Source code, Node context, String... argumentNames) throws Exception {
        Parser parser = new Parser(code.getReader());


        parser.parse();
        BlockNode entryPoint = parser.getEntryPoint();

        // NOTE: unavailable section is just "to make it work"
        return Truffle.getRuntime().createCallTarget(new PascalRootNode(code.createUnavailableSection(),
                entryPoint.getFrameDescriptor(), entryPoint));
    }

    @Override
    protected Object findExportedSymbol(PascalContext context, String globalName, boolean onlyExplicit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Object getLanguageGlobal(PascalContext context) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean isObjectOfLanguage(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Object evalInContext(Source source, Node node, MaterializedFrame mFrame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
