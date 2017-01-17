package cz.chovanecm.contrib.cz.rank.pj.pascal.parser;


import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.ProcedureNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import cz.chovanecm.pascal.truffle.nodes.VariableNode;
import cz.rank.pj.pascal.*;
import cz.rank.pj.pascal.lexan.LexicalAnalyzator;
import cz.rank.pj.pascal.lexan.LexicalException;
import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.parser.ParseException;
import cz.rank.pj.pascal.parser.UnknownVariableNameException;
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
    LinkedHashMap<String, VariableNode> globalVariableNodes;
    LinkedHashMap<String, ProcedureNode> globalProcedureNodes;
    private StatementNode entryPoint;
    private boolean tokenPushed;
    private AstFactoryInterface astFactory = null;
    public Parser(Reader reader) {
        this.lexan = new LexicalAnalyzator(reader);

        initGlobals();
    }

    public Parser(InputStream in) {
        this.lexan = new LexicalAnalyzator(in);

        initGlobals();
    }

    static VariableNode getVariableNodesCollision(LinkedHashMap<String, VariableNode> vars1, LinkedHashMap<String, VariableNode> vars2) {
        VariableNode VariableNode = null;

        for (String currentKey : vars1.keySet()) {
            if (vars2.containsKey(currentKey)) {
                VariableNode = vars2.get(currentKey);
                break;
            }
        }

        return VariableNode;
    }

    public AstFactoryInterface getAstFactory() {
        return astFactory;
    }

    public void setAstFactory(AstFactoryInterface astFactory) {
        this.astFactory = astFactory;
    }

    private void initGlobals() {
        globalVariableNodes = new LinkedHashMap<>();
        globalProcedureNodes = new LinkedHashMap<>();
        setTokenPushed(false);

        initStaticMethods();
    }

    private void initStaticMethods() {
        globalProcedureNodes.put("writeln", getAstFactory().createWriteLnProcedure());
        globalProcedureNodes.put("write", getAstFactory().createWriteProcedure());
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

    public VariableNode getGlobalVariableNode(String name) {
        return globalVariableNodes.get(name);
    }

    public ProcedureNode getGlobalProcedureNode(String name) {
        return globalProcedureNodes.get(name);
    }

    LinkedHashMap<String, VariableNode> parseVar() throws ParseException, IOException, LexicalException {
        List<Token> variableNames;
        LinkedHashMap<String, VariableNode> variables;

        variableNames = new ArrayList<>();
        variables = new LinkedHashMap<>();

        boolean VariableNodesParsed = false;

        logger.debug("testing '" + currentToken.getType() + "' if is VAR");
        if (currentToken.isVar()) {
            logger.debug("in VAR");
            while (!VariableNodesParsed) {
                do {
                    readToken();
                    switch (currentToken.getType()) {
                        case ID:
                            variableNames.add(currentToken);
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

                Iterator VariableNodesNamesIterator = variableNames.iterator();

                switch (readToken().getType()) {
                    case INTEGER:

                        logger.debug("INTEGER VariableNode");

                        while (VariableNodesNamesIterator.hasNext()) {
                            Token VariableNode = (Token) VariableNodesNamesIterator.next();

                            logger.debug(VariableNode);

                            if (!variables.containsKey(VariableNode.getName())) {
                                variables.put(VariableNode.getName(), astFactory.createIntegerVariable(VariableNode.getName()));
                            } else {
                                throw new ParseException("VariableNode '" + VariableNode.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }

                        variableNames.clear();
                        break;
                    case STRING:

                        logger.debug("STRING VariableNode");

                        while (VariableNodesNamesIterator.hasNext()) {
                            Token VariableNode = (Token) VariableNodesNamesIterator.next();

                            logger.debug(VariableNode);

                            if (!variables.containsKey(VariableNode.getName())) {
                                variables.put(VariableNode.getName(), astFactory.createStringVariable(VariableNode.getName()));
                            } else {
                                throw new ParseException("VariableNode '" + VariableNode.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }

                        variableNames.clear();
                        break;
                    case REAL:

                        logger.debug("REAL VariableNode");

                        while (VariableNodesNamesIterator.hasNext()) {
                            Token VariableNode = (Token) VariableNodesNamesIterator.next();

                            logger.debug(VariableNode);

                            if (!variables.containsKey(VariableNode.getName())) {
                                variables.put(VariableNode.getName(), astFactory.createRealVariable(VariableNode.getName()));
                            } else {
                                throw new ParseException("VariableNode '" + VariableNode.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }

                        variableNames.clear();
                        break;
                    default:
                        throw new ParseException("type expected", lexan.getLineNumber());
                }

                if (!readToken().isSemicolon()) {
                    throw new ParseException("';' expected", lexan.getLineNumber());
                }

                switch (readToken().getType()) {
                    case BEGIN:
                        VariableNodesParsed = true;
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

    public StatementNode mainBegin() throws IOException, ParseException, LexicalException, NotEnoughtParametersException {

        List<StatementNode> statements = new ArrayList<>();
        while (!readToken().isEnd()) {

            StatementNode st = parseStatement();

            logger.debug(currentToken);

            /*
            if (st == null) {
				throw new ParseException("Unexpected token '" + currentToken + "'");
			}
             */
            if (st != null) {
                statements.add(st);
            }
            if (!readToken().isSemicolon()) {
                logger.error("expected semicolon");
                throw new ParseException("Expected ';'. Have '" + currentToken + "'", lexan.getLineNumber());
            }
        }

        if (!readToken().isDot()) {
            throw new ParseException('.', lexan.getLineNumber());
        }
        StatementNode[] statementArray = new StatementNode[statements.size()];
        statementArray = statements.toArray(statementArray);
        StatementNode block = getAstFactory().createMainBlock(statementArray);
        return block;
    }

    public StatementNode parseBegin() throws IOException, ParseException, LexicalException, NotEnoughtParametersException {
        List<StatementNode> statements = new ArrayList<>();
        while (!readToken().isEnd()) {

            StatementNode st = parseStatement();

            logger.debug(currentToken);
            /*
			if (st == null) {
				throw new ParseException("Unexpected token '" + currentToken + "'");
			}
             */

            if (st != null) {
                statements.add(st);
            }

            if (!readToken().isSemicolon()) {
                throw new ParseException("Expected ';'. Have '" + currentToken + "'", lexan.getLineNumber());
            }

        }
        StatementNode[] statementArray = new StatementNode[statements.size()];
        statementArray = statements.toArray(statementArray);
        StatementNode block = astFactory.createBlock(statementArray);
        return block;
    }

    private List<ExpressionNode> parseProcedureNodeParameters() throws IOException, LexicalException, ParseException, UnknownVariableNameException {
        if (readToken().isRightParentie()) {
            return null;
        }

        List<ExpressionNode> parameters = new ArrayList<>();

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

    private StatementNode parseStatement() throws IOException, LexicalException, ParseException, NotEnoughtParametersException, UnknownVariableNameException, UnknownProcedureNameException {
        logger.debug(currentToken);

        switch (currentToken.getType()) {
            case ID: {
                String name = currentToken.getName();

                switch (readToken().getType()) {
                    case ASSIGMENT:
                        return parseAssigment(checkAndReturnVariableNode(name));
                    // procedure
                    case LPAREN:
                        ProcedureNode procedure = checkAndReturnProcedureNode(name);
                        // TODO
                        procedure.setParameters(parseProcedureNodeParameters());

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

    private StatementNode parseWhile() throws IOException, ParseException, LexicalException, NotEnoughtParametersException,
            UnknownVariableNameException, UnknownProcedureNameException {
        ExpressionNode ex = parseExpression();

        if (!readToken().isDo()) {
            throw new ParseException("Expected 'do'", lexan.getLineNumber());
        }

        readToken();
        StatementNode st = parseStatement();

        return astFactory.createWhile(ex, st);
        // return new While(ex, st);
    }

    private StatementNode parseIf() throws IOException, ParseException, LexicalException, NotEnoughtParametersException, UnknownVariableNameException, UnknownProcedureNameException {
        ExpressionNode ex = parseExpression();

        if (!readToken().isThen()) {
            throw new ParseException("Expected 'then'", lexan.getLineNumber());
        }

        readToken();
        StatementNode st1 = parseStatement();
        StatementNode st2 = null;

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

    private StatementNode parseFor() throws IOException, ParseException, LexicalException, NotEnoughtParametersException,
            UnknownVariableNameException, UnknownProcedureNameException {
        readToken();

        StatementNode assignmentStatement = parseStatement();

        //TODO: Find a better way of checking it.
        /*if (!(assignmentStatement instanceof Assignment)) {
            throw new ParseException("Expected assignment statement!", lexan.getLineNumber());
        }*/

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

        ExpressionNode finalExpression = parseExpression();

        if (!currentToken.isDo()) {
            throw new ParseException("Expected 'do'", lexan.getLineNumber());
        }

        setTokenPushed(false);
        readToken();
        StatementNode executeStatement = parseStatement();

        logger.debug(currentToken);

        if (downto) {
            return astFactory.createForDownTo(assignmentStatement, finalExpression, executeStatement);
            //return new ForDownto((Assignment) assignmentStatement, finalExpression, executeStatement);
        } else {
            return astFactory.createForTo(assignmentStatement, finalExpression, executeStatement);
            //return new ForTo((Assignment) assignmentStatement, finalExpression, executeStatement);
        }
    }

    private VariableNode checkAndReturnVariableNode(String name) throws UnknownVariableNameException {
        VariableNode VariableNode = getGlobalVariableNode(name);

        if (VariableNode == null) {
            throw new UnknownVariableNameException(name + ":" + lexan.getLineNumber());
        }

        return VariableNode;
    }

    private ProcedureNode checkAndReturnProcedureNode(String name) throws UnknownProcedureNameException {
        ProcedureNode procedure = getGlobalProcedureNode(name);

        if (procedure == null) {
            throw new UnknownProcedureNameException(name + ":" + lexan.getLineNumber());
        }

        procedure = procedure.clone();
        /*try {
            procedure = (ProcedureNode) procedure.clone();
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

    private ExpressionNode primaryExpression() throws IOException, LexicalException, UnknownVariableNameException, ParseException {
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
                return checkAndReturnVariableNode(currentToken.getName());

            // Unary minus
            case MINUS:
                logger.debug("parseExpression:token " + currentToken);
                return astFactory.createUnaryMinus(primaryExpression());
            //return new UnaryMinus(primaryExpression());

            case LPAREN:
                logger.debug("parseExpression:lparen");
                ExpressionNode ex = astFactory.createParenthesis(parseExpression());
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

    private ExpressionNode parseOperatorExpression() throws IOException, ParseException, LexicalException, UnknownVariableNameException {
        ExpressionNode ex = primaryExpression();

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

    private ExpressionNode parseCompareExpression() throws IOException, LexicalException, UnknownVariableNameException, ParseException {
        ExpressionNode ex = parseOperatorExpression();

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

    private ExpressionNode parseExpression() throws IOException, LexicalException, UnknownVariableNameException, ParseException {
        ExpressionNode ex;
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

    private StatementNode parseAssigment(VariableNode VariableNode) throws IOException, LexicalException, ParseException, UnknownVariableNameException {
        StatementNode st = astFactory.createAssignment(VariableNode, parseExpression()); //new Assignment(VariableNode, parseExpression());

        logger.debug("parseAssigment token:" + currentToken);
        /*
		if (!readToken().isSemicolon()) {
			throw new ParseException("Expected ';'", lexan.getLineNumber());
		}
         */
        return st;
    }

    public StatementNode parse() throws ParseException, IOException, LexicalException, UnknownVariableNameException, UnknownProcedureNameException, NotEnoughtParametersException {
        boolean parsedAll = false;

        while (!parsedAll) {
            switch (readToken().getType()) {
                case PROGRAM:
                    parseProgram();
                    break;
                case VAR:
                    LinkedHashMap<String, VariableNode> localVariableNodes;
                    localVariableNodes = parseVar();

                    if (globalVariableNodes.isEmpty()) {
                        globalVariableNodes = localVariableNodes;
                    } else {
                        VariableNode collisionVariableNode = getVariableNodesCollision(globalVariableNodes, localVariableNodes);

                        if (collisionVariableNode != null) {
                            throw new ParseException("VariableNode'" + collisionVariableNode.getName() + "' already defined");
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

    public void run() throws UnknownExpressionTypeException, NotUsableOperatorException {
        logger.debug("Executing entrypoint...");
//		logger.debug(entryPoint);
        ((Statement) entryPoint).execute();
    }
}
