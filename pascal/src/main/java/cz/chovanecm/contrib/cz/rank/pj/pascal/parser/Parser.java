package cz.chovanecm.contrib.cz.rank.pj.pascal.parser;

import cz.chovanecm.pascal.ast.BlockInterface;
import cz.chovanecm.pascal.ast.ProcedureInterface;
import cz.chovanecm.pascal.ast.RankAstFactory;
import cz.chovanecm.pascal.ast.VariableInterface;
import cz.rank.pj.pascal.*;
import cz.rank.pj.pascal.lexan.LexicalAnalyzator;
import cz.rank.pj.pascal.lexan.LexicalException;
import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.parser.ParseException;
import cz.rank.pj.pascal.parser.UnknowVariableNameException;
import cz.rank.pj.pascal.statement.Assignment;
import cz.rank.pj.pascal.statement.Statement;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * User: karl Date: Jan 31, 2006 Time: 2:18:09 PM
 */
public class Parser {

    private static Logger logger;

    static {
        logger = Logger.getLogger(Parser.class);
    }

    LexicalAnalyzator lexan;
    Token currentToken;
    LinkedHashMap<String, VariableInterface> globalVariables;
    LinkedHashMap<String, ProcedureInterface> globalProcedures;
    private Statement entryPoint;
    private boolean tokenPushed;
    private AstFactoryInterface astFactory = new RankAstFactory();

    public Parser(Reader reader) {
        this.lexan = new LexicalAnalyzator(reader);

        initGlobals();
    }

    public Parser(InputStream in) {
        this.lexan = new LexicalAnalyzator(in);

        initGlobals();
    }

    static VariableInterface getVariablesCollision(LinkedHashMap<String, VariableInterface> vars1, LinkedHashMap<String, VariableInterface> vars2) {
        VariableInterface variable = null;

        for (String currentKey : vars1.keySet()) {
            if (vars2.containsKey(currentKey)) {
                variable = vars2.get(currentKey);
                break;
            }
        }

        return variable;
    }

    public AstFactoryInterface getAstFactory() {
        return astFactory;
    }

    public void setAstFactory(AstFactoryInterface astFactory) {
        this.astFactory = astFactory;
    }

    private void initGlobals() {
        globalVariables = new LinkedHashMap<>();
        globalProcedures = new LinkedHashMap<>();
        setTokenPushed(false);

        initStaticMethods();
    }

    private void initStaticMethods() {
        globalProcedures.put("writeln", getAstFactory().createWriteLnProcedure());
        globalProcedures.put("write", getAstFactory().createWriteProcedure());
    }

    void parseProgram() throws ParseException, IOException, LexicalException {
        if (currentToken.isProgram()) {
            if (readToken().isId()) {
                if (!readToken().isSemicolon()) { // isSemicolon
                    throw new ParseException("; expected", lexan.getLineNumber());
                }
            } else { // isId
                throw new ParseException("identificator expected", lexan.getLineNumber());
            }
        }
    }

    public VariableInterface getGlobalVariable(String name) {
        return globalVariables.get(name);
    }

    public ProcedureInterface getGlobalProcedure(String name) {
        return globalProcedures.get(name);
    }

    LinkedHashMap<String, VariableInterface> parseVar() throws ParseException, IOException, LexicalException {
        List<Token> variablesNames;
        LinkedHashMap<String, VariableInterface> variables;

        variablesNames = new ArrayList<>();
        variables = new LinkedHashMap<>();

        boolean variablesParsed = false;

        logger.debug("testing '" + currentToken.getType() + "' if is VAR");
        if (currentToken.isVar()) {
            logger.debug("in VAR");
            while (!variablesParsed) {
                do {
                    readToken();
                    switch (currentToken.getType()) {
                        case ID:
                            variablesNames.add(currentToken);
                            switch (readToken().getType()) {
                                case COLON:
                                    break;
                                case COMMA:
                                    break;
                                default:
                                    throw new ParseException("',' or ':' expected", lexan.getLineNumber());
                            }

                            break;
                        default:
                            throw new ParseException("identificator expected", lexan.getLineNumber());
                    }
                } while (!currentToken.isColon());

                Iterator variablesNamesIterator = variablesNames.iterator();

                switch (readToken().getType()) {
                    case INTEGER:

                        logger.debug("INTEGER variable");

                        while (variablesNamesIterator.hasNext()) {
                            Token variable = (Token) variablesNamesIterator.next();

                            logger.debug(variable);

                            if (!variables.containsKey(variable.getName())) {
                                variables.put(variable.getName(), astFactory.createIntegerVariable(variable.getName()));
                            } else {
                                throw new ParseException("variable '" + variable.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }

                        variablesNames.clear();
                        break;
                    case STRING:

                        logger.debug("STRING variable");

                        while (variablesNamesIterator.hasNext()) {
                            Token variable = (Token) variablesNamesIterator.next();

                            logger.debug(variable);

                            if (!variables.containsKey(variable.getName())) {
                                variables.put(variable.getName(), astFactory.createStringVariable(variable.getName()));
                            } else {
                                throw new ParseException("variable '" + variable.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }

                        variablesNames.clear();
                        break;
                    case REAL:

                        logger.debug("REAL variable");

                        while (variablesNamesIterator.hasNext()) {
                            Token variable = (Token) variablesNamesIterator.next();

                            logger.debug(variable);

                            if (!variables.containsKey(variable.getName())) {
                                variables.put(variable.getName(), astFactory.createRealVariable(variable.getName()));
                            } else {
                                throw new ParseException("variable '" + variable.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }

                        variablesNames.clear();
                        break;
                    default:
                        throw new ParseException("type expected", lexan.getLineNumber());
                }

                if (!readToken().isSemicolon()) {
                    throw new ParseException("';' expected", lexan.getLineNumber());
                }

                switch (readToken().getType()) {
                    case BEGIN:
                        variablesParsed = true;
                    case ID:
                        setTokenPushed(true);
                    case VAR:
                        break;
                    default:
                        logger.debug("unexpected token " + currentToken);
                        throw new ParseException("unexpected currentToken '" + currentToken.getType() + "'", lexan.getLineNumber());
                }
            }
        }

        return variables;
    }

    public void procedure() {

    }

    public void function() {

    }

    public Statement mainBegin() throws IOException, ParseException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
        BlockInterface block = getAstFactory().createBlock();

        while (!readToken().isEnd()) {

            Statement st = parseStatement();

            logger.debug(currentToken);

            /*
            if (st == null) {
				throw new ParseException("Unexpected token '" + currentToken + "'");
			}
             */
            if (st != null) {
                block.add(st);
            }

            if (!readToken().isSemicolon()) {
                logger.error("expected semicolon");
                throw new ParseException("Expected ';'. Have '" + currentToken + "'", lexan.getLineNumber());
            }
        }

        if (!readToken().isDot()) {
            throw new ParseException('.', lexan.getLineNumber());
        }

        return block;
    }

    public Statement parseBegin() throws IOException, ParseException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
        BlockInterface block = astFactory.createBlock();
        while (!readToken().isEnd()) {

            Statement st = parseStatement();

            logger.debug(currentToken);
            /*
			if (st == null) {
				throw new ParseException("Unexpected token '" + currentToken + "'");
			}
             */

            if (st != null) {
                block.add(st);
            }

            if (!readToken().isSemicolon()) {
                throw new ParseException("Expected ';'. Have '" + currentToken + "'", lexan.getLineNumber());
            }

        }

        return block;
    }

    private List<Expression> parseProcedureParameters() throws IOException, LexicalException, ParseException, UnknowVariableNameException {
        if (readToken().isRightParentie()) {
            return null;
        }

        List<Expression> parameters = new ArrayList<>();

        boolean hasMoreParameters = true;

        setTokenPushed(true);
        do {
            parameters.add(parseExpression());
            if (!readToken().isComma()) {
                hasMoreParameters = false;
            }
        } while (hasMoreParameters);

        return parameters;
    }

    private Statement parseStatement() throws IOException, LexicalException, UnknowVariableNameException, ParseException, UnknowProcedureNameException, NotEnoughtParametersException {
        logger.debug(currentToken);

        switch (currentToken.getType()) {
            case ID: {
                String name = currentToken.getName();

                switch (readToken().getType()) {
                    case ASSIGMENT:
                        return parseAssigment(checkAndReturnVariable(name));
                    // procedure
                    case LPAREN:
                        ProcedureInterface procedure = checkAndReturnProcedure(name);

                        procedure.setParameters(parseProcedureParameters());

                        if (!currentToken.isRightParentie()) {
                            logger.error(currentToken);
                            throw new ParseException("Expected ')'", lexan.getLineNumber());
                        }

//						readToken();
                        return procedure;
                    default:
                        throw new ParseException("Unexpected token '" + currentToken + "'", lexan.getLineNumber());
                }

//				throw new ParseException("Unexpected token '" + currentToken + "'", lexan.getLineNumber());
//					break;
            }
            case BEGIN: {
                return parseBegin();
            }

            case WHILE: {
                return parseWhile();
            }
            case IF: {
                return parseIf();
            }

            case FOR: {
                return parseFor();
            }

            default:
                throw new ParseException("Unexpected token '" + currentToken + "'", lexan.getLineNumber());
        }
    }

    private Statement parseWhile() throws IOException, ParseException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
        Expression ex = parseExpression();

        if (!readToken().isDo()) {
            throw new ParseException("Expected 'do'", lexan.getLineNumber());
        }

        readToken();
        Statement st = parseStatement();

        return astFactory.createWhile(ex, st);
        // return new While(ex, st);
    }

    private Statement parseIf() throws IOException, ParseException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
        Expression ex = parseExpression();

        if (!readToken().isThen()) {
            throw new ParseException("Expected 'then'", lexan.getLineNumber());
        }

        readToken();
        Statement st1 = parseStatement();
        Statement st2 = null;

        logger.debug(currentToken);

        if (!currentToken.isSemicolon()) {
            if (readToken().isElse()) {
                readToken();
                st2 = parseStatement();
            } else {
                setTokenPushed(true);
            }
        }

        return astFactory.createIf(ex, st1, st2);
        //return new If(ex, st1, st2);
    }

    private Statement parseFor() throws IOException, ParseException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
        readToken();

        Statement assignmentStatement = parseStatement();

        if (!(assignmentStatement instanceof Assignment)) {
            throw new ParseException("Expected assignment statement!", lexan.getLineNumber());
        }

        boolean downto = false;

        switch (currentToken.getType()) {
            case TO:
                break;
            case DOWNTO:
                downto = true;
                break;
            default: {
                throw new ParseException("Expected 'to' or 'downto'!", lexan.getLineNumber());
            }
        }

        readToken();

        Expression finalExpression = parseExpression();

        if (!currentToken.isDo()) {
            throw new ParseException("Expected 'do'", lexan.getLineNumber());
        }

        setTokenPushed(false);
        readToken();
        Statement executeStatement = parseStatement();

        logger.debug(currentToken);

        if (downto) {
            return astFactory.createForDownTo(assignmentStatement, finalExpression, executeStatement);
            //return new ForDownto((Assignment) assignmentStatement, finalExpression, executeStatement);
        } else {
            return astFactory.createForTo(assignmentStatement, finalExpression, executeStatement);
            //return new ForTo((Assignment) assignmentStatement, finalExpression, executeStatement);
        }
    }

    private VariableInterface checkAndReturnVariable(String name) throws UnknowVariableNameException {
        VariableInterface variable = getGlobalVariable(name);

        if (variable == null) {
            throw new UnknowVariableNameException(name + ":" + lexan.getLineNumber());
        }

        return variable;
    }

    private ProcedureInterface checkAndReturnProcedure(String name) throws UnknowProcedureNameException {
        ProcedureInterface procedure = getGlobalProcedure(name);

        if (procedure == null) {
            throw new UnknowProcedureNameException(name + ":" + lexan.getLineNumber());
        }

        procedure = procedure.clone();
        /*try {
            procedure = (ProcedureInterface) procedure.clone();
        } catch (CloneNotSupportedException e) {
            // empty, cloning is SUPPORTED ALWAYS
        }*/
        return procedure;
    }

    private void acceptIt() throws IOException, LexicalException {
        currentToken = lexan.getNextToken();
    }

    private void accept(TokenType token) throws IOException, LexicalException, ParseException {
        if (currentToken.getType() == token) {
            currentToken = lexan.getNextToken();
        } else {
            throw new ParseException(token, lexan.getLineNumber());
        }
    }

    private Expression primaryExpression() throws IOException, LexicalException, UnknowVariableNameException, ParseException {
        switch (readToken().getType()) {
            case VAL_INTEGER:
                logger.debug("parseExpression:interger value " + currentToken.getIntegerValue());
                return astFactory.createConstant(currentToken.getIntegerValue());
            //return new Constant(currentToken.getIntegerValue());

            case VAL_DOUBLE:
                logger.debug("parseExpression:double value " + currentToken.getDoubleValue());
                return astFactory.createConstant(currentToken.getDoubleValue());
            //return new Constant(currentToken.getDoubleValue());

            case VAL_STRING:
                logger.debug("parseExpression:string value " + currentToken.getStringValue());
                return astFactory.createConstant(currentToken.getStringValue());
            //return new Constant(currentToken.getStringValue());

            case ID:
                logger.debug("parseExpression:id name " + currentToken);
                return checkAndReturnVariable(currentToken.getName());

            // Unary minus
            case MINUS:
                logger.debug("parseExpression:token " + currentToken);
                return astFactory.createUnaryMinus(primaryExpression());
            //return new UnaryMinus(primaryExpression());

            case LPAREN:
                logger.debug("parseExpression:lparen");
                Expression ex = astFactory.createParenthesis(parseExpression());
                //Expression ex = new Parenties(parseExpression());

                if (!readToken().isRightParentie()) {
                    throw new ParseException(')', lexan.getLineNumber());
                }

                return ex;
        }

        setTokenPushed(true);
        logger.debug(currentToken);

        throw new ParseException("value expected!", lexan.getLineNumber());
    }

    private Expression parseOperatorExpression() throws IOException, ParseException, LexicalException, UnknowVariableNameException {
        Expression ex = primaryExpression();

        logger.debug(ex);

        boolean operatorFound;

        do {
            readToken();

            logger.debug("token:" + currentToken);

            switch (currentToken.getType()) {
                case PLUS:
                    ex = astFactory.createPlusOperator(ex, parseOperatorExpression());
                    //ex = new PlusOperator(ex, parseOperatorExpression());
                    operatorFound = true;
                    break;
                case MINUS:
                    ex = astFactory.createMinusOperator(ex, parseOperatorExpression());
                    //ex = new MinusOperator(ex, parseOperatorExpression());
                    operatorFound = true;
                    break;
                case MULT:
                    ex = astFactory.createMultiplicationOperator(ex, primaryExpression());
                    //ex = new MultipleOperator(ex, primaryExpression());
                    operatorFound = true;
                    break;
                case REAL_DIV:
                    ex = astFactory.createDivisionOperator(ex, primaryExpression());
                    //ex = new DivideOperator(ex, primaryExpression());
                    operatorFound = true;
                    break;
                case DIV:
                    ex = astFactory.createDivisionOperator(ex, primaryExpression());
                    //ex = new DivideOperator(ex, primaryExpression());
                    operatorFound = true;
                    break;
                default:
                    operatorFound = false;
            }
        } while (operatorFound);

        setTokenPushed(true);
        return ex;
    }

    private Expression parseCompareExpression() throws IOException, LexicalException, UnknowVariableNameException, ParseException {
        Expression ex = parseOperatorExpression();

        logger.debug(ex);

        boolean operatorFound;

        do {
            readToken();

            logger.debug("token:" + currentToken);

            switch (currentToken.getType()) {
                case LESS:
                    ex = astFactory.createLessOperator(ex, parseOperatorExpression());
                    //ex = new LessOperator(ex, parseOperatorExpression());
                    operatorFound = true;
                    break;
                case MORE:
                    ex = astFactory.createGreaterOperator(ex, parseOperatorExpression());
                    //ex = new MoreOperator(ex, parseOperatorExpression());
                    operatorFound = true;
                    break;
                case EQUAL:
                    ex = astFactory.createEqualOperator(ex, parseOperatorExpression());
                    //ex = new EqualOperator(ex, parseOperatorExpression());
                    operatorFound = true;
                    break;
                case LESS_EQUAL:
                    ex = astFactory.createLessEqualOperator(ex, parseOperatorExpression());
                    //ex = new LessEqualOperator(ex, parseOperatorExpression());
                    operatorFound = true;
                    break;
                case MORE_EQUAL:
                    ex = astFactory.createGreaterEqualOperator(ex, parseOperatorExpression());
                    //ex = new MoreEqualOperator(ex, parseOperatorExpression());
                    operatorFound = true;
                    break;
                case NOTEQUAL:
                    ex = astFactory.createNotEqualOperator(ex, parseOperatorExpression());
                    //ex = new NotEqualOperator(ex, parseOperatorExpression());
                    operatorFound = true;
                    break;
                default:
                    operatorFound = false;
            }
        } while (operatorFound);

        setTokenPushed(true);
        return ex;
    }

    private Expression parseExpression() throws IOException, LexicalException, UnknowVariableNameException, ParseException {
        Expression ex;
        if (readToken().isNot()) {
            ex = astFactory.createNotOperator(parseCompareExpression());
            //ex = new NotOperator(parseCompareExpression());
        } else {
            setTokenPushed(true);
            ex = parseCompareExpression();
        }

        logger.debug(ex);

        boolean operatorFound;

        do {
            readToken();

            logger.debug("token:" + currentToken);

            switch (currentToken.getType()) {
                case AND:
                    ex = astFactory.createAndOperator(ex, parseExpression());
                    //ex = new AndOperator(ex, parseExpression());
//					ex = new LessOperator(ex, primaryExpression());
                    operatorFound = true;
                    break;
                case OR:
                    ex = astFactory.createOrOperator(ex, parseExpression());
                    //ex = new OrOperator(ex, parseExpression());
                    operatorFound = true;
                    break;
                /*
				case NOT:
					ex = new NotOperator(parseExpression());
					operatorFound = true;
					break;
                 */
                default:
                    operatorFound = false;
            }
        } while (operatorFound);

        setTokenPushed(true);
        return ex;
    }

    private Statement parseAssigment(VariableInterface variable) throws IOException, LexicalException, ParseException, UnknowVariableNameException {
        Statement st = astFactory.createAssignment(variable, parseExpression()); //new Assignment(variable, parseExpression());

        logger.debug("parseAssigment token:" + currentToken);
        /*
		if (!readToken().isSemicolon()) {
			throw new ParseException("Expected ';'", lexan.getLineNumber());
		}
         */
        return st;
    }

    public Statement parse() throws ParseException, IOException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
        boolean parsedAll = false;

        while (!parsedAll) {
            switch (readToken().getType()) {
                case PROGRAM:
                    parseProgram();
                    break;
                case VAR:
                    LinkedHashMap<String, VariableInterface> localVariables;
                    localVariables = parseVar();

                    if (globalVariables.isEmpty()) {
                        globalVariables = localVariables;
                    } else {
                        VariableInterface collisionVariable = getVariablesCollision(globalVariables, localVariables);

                        if (collisionVariable != null) {
                            throw new ParseException("variable'" + collisionVariable.getName() + "' already defined");
                        }
                    }
                    break;
                case PROCEDURE:
                    procedure();
                    break;
                case FUNCTION:
                    function();
                    break;
                case BEGIN:
                    entryPoint = mainBegin();
                    parsedAll = true;
                    break;
                default:
//					parsedAll = true;
                    logger.debug("unexpected token " + currentToken);
                    throw new ParseException("unexcpected currentToken '" + currentToken + "'", lexan.getLineNumber());
//					break;
            }
        }
        return entryPoint;
    }

    private Token readToken() throws IOException, LexicalException {
        if (isTokenPushed()) {
            setTokenPushed(false);
            return currentToken;
        } else {
            return currentToken = lexan.getNextToken();
        }
    }

    public boolean isTokenPushed() {
        return tokenPushed;
    }

    public void setTokenPushed(boolean tokenPushed) {
        this.tokenPushed = tokenPushed;
    }

    public void run() throws UnknowExpressionTypeException, NotUsableOperatorException {
        logger.debug("Executing entrypoint...");
//		logger.debug(entryPoint);
        entryPoint.execute();
    }
}
