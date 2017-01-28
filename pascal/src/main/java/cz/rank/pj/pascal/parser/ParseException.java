/*
 * MIT License
 *
 * Copyright (c) 2016 Karel Rank
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cz.rank.pj.pascal.parser;

import cz.rank.pj.pascal.TokenType;

/**
 * User: karl
 * Date: Jan 31, 2006
 * Time: 2:41:19 PM
 */
public class ParseException extends Exception {
    protected int lineNumber;

    public ParseException() {
        super();

        lineNumber = 0;
    }

    public ParseException(String string) {
        super(string);

        lineNumber = 0;
    }

    public ParseException(String string, int line) {
        super(string);
        this.lineNumber = line;
    }

    public ParseException(char c) {
        super("Expected '" + c + "'");
    }

    public ParseException(char c, int line) {
        super("Expected '" + c + "'");
        this.lineNumber = line;
    }

    public ParseException(TokenType type, int line) {
        super("Expected '" + type + "'");
        this.lineNumber = line;
    }

    public String getMessage() {
        return new StringBuilder().append("lineNumber:").append(lineNumber).append(" ").append(super.getMessage()).toString();
    }
}
