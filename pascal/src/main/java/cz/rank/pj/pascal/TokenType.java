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

package cz.rank.pj.pascal;

/**
 * User: karl
 * Date: Jan 26, 2006
 * Time: 4:50:59 PM
 */
public enum TokenType {
    EMPTY,
    STRING,
    INTEGER,
    REAL,
    TYPE,
    VAR,
    FOR,
    TO,
    DOWNTO,
    DO,
    WHILE,
    REPEAT,
    UNTIL,
    IF,
    THEN,
    ELSE,
    LABEL,
    ID,
    LPAREN,
    RPAREN,
    LBRACKET,
    RBRACKET,
    EQUAL,
    NOTEQUAL,
    LESS,
    MORE,
    LESS_EQUAL,
    MORE_EQUAL,
    COLON,
    SEMICOLON,
    COMMA,
    PLUS,
    MINUS,
    MULT,
    DIV,
    REAL_DIV,
    MOD,
    ASSIGMENT,
    PROGRAM,
    PROCEDURE,
    FUNCTION,
    ARRAY,
    BEGIN,
    END,
    AND,
    OR,
    NOT,
    VAL_INTEGER,
    VAL_STRING,
    VAL_DOUBLE,
    VAL_BOOLEAN,
    EOF,
    DOT
}
