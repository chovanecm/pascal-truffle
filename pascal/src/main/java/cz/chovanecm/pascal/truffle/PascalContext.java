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
package cz.chovanecm.pascal.truffle;

import com.oracle.truffle.api.ExecutionContext;
import com.oracle.truffle.api.TruffleLanguage;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 *
 * @author martin
 */
public class PascalContext extends ExecutionContext {

    private final TruffleLanguage.Env env;
    private final BufferedReader in;
    private final PrintWriter out;

    public PascalContext(TruffleLanguage.Env env, BufferedReader in, PrintWriter out) {
        this.env = env;
        this.in = in;
        this.out = out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }
}
