package cz.chovanecm.contrib.cz.rank.pj.pascal.parser;


import cz.chovanecm.pascal.exceptions.VariableNotDeclaredException;
import cz.chovanecm.pascal.truffle.TruffleAstFactory;
import cz.chovanecm.pascal.truffle.nodes.BlockNode;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.ProcedureNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import cz.chovanecm.pascal.truffle.nodes.variables.DeclareVariableNode;
import cz.chovanecm.pascal.truffle.nodes.variables.WriteVariableNode;
import cz.rank.pj.pascal.*;
import cz.rank.pj.pascal.lexan.LexicalAnalyzator;
import cz.rank.pj.pascal.lexan.LexicalException;
import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.parser.ParseException;
import cz.rank.pj.pascal.parser.UnknownVariableNameException;
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
    LinkedHashMap<String, DeclareVariableNode> globalVariables;
    LinkedHashMap<String, ProcedureNode> globalProcedures;
    private BlockNode entryPoint;
    private boolean tokenPushed;
    private AstFactoryInterface astFactory = new TruffleAstFactory();

    public Parser(Reader reader) {
        this.lexan = new LexicalAnalyzator(reader);

        initGlobals();
    }

    public Parser(InputStream in) {
        this.lexan = new LexicalAnalyzator(in);

        initGlobals();
    }

    static DeclareVariableNode getVariableNodesCollision(LinkedHashMap<String, DeclareVariableNode> vars1, LinkedHashMap<String, DeclareVariableNode> vars2) {
        DeclareVariableNode variable = null;

        for (String currentKey : vars1.keySet()) {
            if (vars2.containsKey(currentKey)) {
                variable = vars2.get(currentKey);
                break;
            }
        }

        return variable;
    }

    public BlockNode getEntryPoint() {
        return entryPoint;
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
        globalProcedures.put("read", getAstFactory().createReadProcedure());
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


    public DeclareVariableNode getGlobalVariable(String name) {
        return globalVariables.get(name);
    }

    public ProcedureNode getGlobalProcedure(String name) {
        return globalProcedures.get(name);
    }

    LinkedHashMap<String, DeclareVariableNode> parseVar() throws ParseException, IOException, LexicalException {
        List<Token> variableNames;
        LinkedHashMap<String, DeclareVariableNode> variables;

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

                        logger.debug("INTEGER Variable");

                        while (VariableNodesNamesIterator.hasNext()) {
                            Token variableToken = (Token) VariableNodesNamesIterator.next();

                            logger.debug(variableToken);

                            if (!variables.containsKey(variableToken.getName())) {
                                variables.put(variableToken.getName(), astFactory.createIntegerVariable(variableToken.getName()));
                            } else {
                                throw new ParseException("DeclareVariableNode '" + variableToken.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }

                        variableNames.clear();
                        break;
                    case STRING:

                        logger.debug("STRING DeclareVariableNode");

                        while (VariableNodesNamesIterator.hasNext()) {
                            Token variableToken = (Token) VariableNodesNamesIterator.next();

                            logger.debug(variableToken);

                            if (!variables.containsKey(variableToken.getName())) {
                                variables.put(variableToken.getName(), astFactory.createStringVariable(variableToken.getName()));
                            } else {
                                throw new ParseException("DeclareVariableNode '" + variableToken.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }

                        variableNames.clear();
                        break;
                    case REAL:

                        logger.debug("REAL DeclareVariableNode");

                        while (VariableNodesNamesIterator.hasNext()) {
                            Token variableToken = (Token) VariableNodesNamesIterator.next();

                            logger.debug(variableToken);

                            if (!variables.containsKey(variableToken.getName())) {
                                variables.put(variableToken.getName(), astFactory.createRealVariable(variableToken.getName()));
                            } else {
                                throw new ParseException("DeclareVariableNode '" + variableToken.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }

                        variableNames.clear();
                        break;
                    case BOOLEAN:

                        logger.debug("BOOLEAN DeclareVariableNode");

                        while (VariableNodesNamesIterator.hasNext()) {
                            Token variableToken = (Token) VariableNodesNamesIterator.next();

                            logger.debug(variableToken);

                            if (!variables.containsKey(variableToken.getName())) {
                                variables.put(variableToken.getName(), astFactory.createBooleanVariable(variableToken.getName()));
                            } else {
                                throw new ParseException("DeclareVariableNode '" + variableToken.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }

                        variableNames.clear();
                        break;

                    case ARRAY:
                        logger.debug("ARRAY DeclareVariableNode");
                        String arrayName = null;
                        while (VariableNodesNamesIterator.hasNext()) {
                            Token variableToken = (Token) VariableNodesNamesIterator.next();

                            logger.debug(variableToken);

                            if (!variables.containsKey(variableToken.getName())) {
                                arrayName = variableToken.getName();
                            } else {
                                throw new ParseException("DeclareVariableNode '" + variableToken.getName() + "'is defined 2times!", lexan.getLineNumber());
                            }
                        }
                        variableNames.clear();
                        expectToken(TokenType.LBRACKET);
                        expectToken(TokenType.VAL_INTEGER);

                        // NOTE: In fact, this must be not necessarily a range
                        // it can be for example Boolean, but for simplicity,
                        // we consider only 1-D arrays indexed by integers
                        int lowerBound = currentToken.getIntegerValue();
                        expectToken(TokenType.DOTDOT);
                        expectToken(TokenType.VAL_INTEGER);
                        int upperBound = currentToken.getIntegerValue();


                        expectToken(TokenType.RBRACKET);
                        expectToken(TokenType.OF);
                        readToken();
                        switch (currentToken.getType()) {
                            case INTEGER:
                                variables.put(arrayName, astFactory.createDeclareSimpleArray(
                                        arrayName, lowerBound, upperBound, long.class
                                ));
                                break;
                            case REAL:
                                variables.put(arrayName, astFactory.createDeclareSimpleArray(
                                        arrayName, lowerBound, upperBound, double.class
                                ));
                                break;
                            case BOOLEAN:
                                variables.put(arrayName, astFactory.createDeclareSimpleArray(
                                        arrayName, lowerBound, upperBound, boolean.class
                                ));
                                break;
                            case STRING:
                                variables.put(arrayName, astFactory.createDeclareSimpleArray(
                                        arrayName, lowerBound, upperBound, String.class
                                ));
                                break;
                            default:
                                throw new ParseException("Only integer, real, boolean and string arrays are supported.", lexan.getLineNumber());
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

    public BlockNode mainBegin() throws IOException, ParseException, LexicalException, NotEnoughtParametersException, UnknownProcedureNameException, UnknownVariableNameException {

        // Declare variables and insert variable declarations
        List<StatementNode> statements = new ArrayList<>(globalVariables.values());
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

        BlockNode block = getAstFactory().createMainBlock(statements);
        return block;
    }

    public StatementNode parseBegin() throws IOException, ParseException, LexicalException, NotEnoughtParametersException,
            UnknownProcedureNameException, UnknownVariableNameException {
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
        StatementNode block = astFactory.createBlock(statements);
        return block;
    }

    private ExpressionNode[] parseProcedureNodeParameters() throws IOException, LexicalException, ParseException, UnknownVariableNameException {
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

        ExpressionNode[] parameterArray = new ExpressionNode[parameters.size()];
        parameterArray = parameters.toArray(parameterArray);
        return parameterArray;
    }

    private StatementNode parseStatement() throws IOException, LexicalException, ParseException, NotEnoughtParametersException, UnknownVariableNameException, UnknownProcedureNameException {
        logger.debug(currentToken);

        switch (currentToken.getType()) {
            case ID: {
                String name = currentToken.getName();

                switch (readToken().getType()) {
                    case ASSIGNMENT:
                        return parseAssignment(checkAndReturnVariableNode(name).getName());
                    case LBRACKET:
                        return parseArrayAssignment(checkAndReturnVariableNode(name).getName());
                    // procedure
                    case LPAREN:
                        ProcedureNode procedure = checkAndReturnProcedureNode(name, parseProcedureNodeParameters());


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
        if (!(assignmentStatement instanceof WriteVariableNode)) {
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

        ExpressionNode finalExpression = parseExpression();

        if (!currentToken.isDo()) {
            throw new ParseException("Expected 'do'", lexan.getLineNumber());
        }

        setTokenPushed(false);
        readToken();
        StatementNode executeStatement = parseStatement();

        logger.debug(currentToken);

        if (downto) {
            try {
                return astFactory.createForDownTo((WriteVariableNode) assignmentStatement, finalExpression, executeStatement);
            } catch (VariableNotDeclaredException e) {
                throw new UnknownVariableNameException(e.getMessage());
            }
            //return new ForDownto((Assignment) assignmentStatement, finalExpression, executeStatement);
        } else {
            try {
                return astFactory.createForTo((WriteVariableNode) assignmentStatement, finalExpression, executeStatement);
            } catch (VariableNotDeclaredException e) {
                throw new UnknownVariableNameException(e.getMessage());
            }
            //return new ForTo((Assignment) assignmentStatement, finalExpression, executeStatement);
        }
    }

    private DeclareVariableNode checkAndReturnVariableNode(String name) throws UnknownVariableNameException {
        DeclareVariableNode variable = getGlobalVariable(name);

        if (variable == null) {
            throw new UnknownVariableNameException(name + ":" + lexan.getLineNumber());
        }

        return variable;
    }

    private ProcedureNode checkAndReturnProcedureNode(String name, ExpressionNode[] parameters) throws UnknownProcedureNameException {
        ProcedureNode procedure = getGlobalProcedure(name);

        if (procedure == null) {
            throw new UnknownProcedureNameException(name + ":" + lexan.getLineNumber());
        }

        procedure = procedure.newInstance(parameters);
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
                return astFactory.createConstant(currentToken.getIntegerValue().longValue());
            //return new Constant(currentToken.getLongValue());

            case VAL_DOUBLE:
                logger.debug("parseExpression:double value " + currentToken.getDoubleValue());
                return astFactory.createConstant(currentToken.getDoubleValue());
            //return new Constant(currentToken.getDoubleValue());

            case VAL_STRING:
                logger.debug("parseExpression:string value " + currentToken.getStringValue());
                return astFactory.createConstant(currentToken.getStringValue());
            //return new Constant(currentToken.getStringValue());
            case VAL_BOOLEAN:
                return astFactory.createConstant(currentToken.getBooleanValue());

            case ID:
                logger.debug("parseExpression:id name " + currentToken);

                return readVariable(currentToken.getName());
            //return astFactory.createReadVariable(currentToken.getName());

            // Unary minus
            case MINUS:
                logger.debug("parseExpression:token " + currentToken);
                return astFactory.createUnaryMinus(primaryExpression());
            //return new UnaryMinusNode(primaryExpression());

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

    private ExpressionNode readVariable(String variableName) throws IOException, LexicalException, ParseException,
            UnknownVariableNameException {
        if (readToken().getType() == TokenType.LBRACKET) {
            ExpressionNode readArray = null;
            try {
                readArray = astFactory.createReadArrayVariable(variableName, parseExpression());
            } catch (VariableNotDeclaredException e) {
                throw new UnknownVariableNameException(e.getMessage());
            }
            expectToken(TokenType.RBRACKET);
            return readArray;
        } else {
            // Don't consume the token now
            setTokenPushed(true);
            try {
                return astFactory.createReadVariable(variableName);
            } catch (VariableNotDeclaredException e) {
                throw new UnknownVariableNameException(e.getMessage());
            }
        }
    }

    private ExpressionNode parseOperatorExpression() throws IOException,
            ParseException, LexicalException, UnknownVariableNameException {
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
                    ex = astFactory.createMinusOperator(ex, primaryExpression());
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
                    ex = astFactory.createIntegerDivisionOperator(ex, primaryExpression());
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

    private StatementNode parseAssignment(String variable) throws IOException, LexicalException, ParseException, UnknownVariableNameException {
        StatementNode st = null; //new Assignment(DeclareVariableNode, parseExpression());
        try {
            st = astFactory.createGlobalAssignment(variable, parseExpression());
        } catch (VariableNotDeclaredException e) {
            throw new UnknownVariableNameException(e.getMessage());
        }

        logger.debug("parseAssignment token:" + currentToken);
        /*
		if (!readToken().isSemicolon()) {
			throw new ParseException("Expected ';'", lexan.getLineNumber());
		}
         */
        return st;
    }

    private StatementNode parseArrayAssignment(String variableName) throws UnknownVariableNameException, ParseException, LexicalException, IOException {
        ExpressionNode index = parseExpression();
        expectToken(TokenType.RBRACKET);
        expectToken(TokenType.ASSIGNMENT);
        ExpressionNode value = parseExpression();
        try {
            return astFactory.createWriteArrayAssignment(variableName, index, value);
        } catch (VariableNotDeclaredException e) {
            throw new UnknownVariableNameException(e.getMessage());
        }
    }

    public StatementNode parse() throws ParseException, IOException, LexicalException, UnknownVariableNameException, UnknownProcedureNameException, NotEnoughtParametersException {
        boolean parsedAll = false;

        while (!parsedAll) {
            switch (readToken().getType()) {
                case PROGRAM:
                    parseProgram();
                    break;
                case VAR:
                    LinkedHashMap<String, DeclareVariableNode> localVariableNodes;
                    localVariableNodes = parseVar();

                    if (globalVariables.isEmpty()) {
                        globalVariables = localVariableNodes;
                    } else {
                        DeclareVariableNode collisionVariable = getVariableNodesCollision(globalVariables, localVariableNodes);

                        if (collisionVariable != null) {
                            throw new ParseException("DeclareVariableNode'" + collisionVariable.getName() + "' already defined");
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
                    this.entryPoint = mainBegin();
                    parsedAll = true;
                    break;
                default:
//					parsedAll = true;
                    logger.debug("unexpected token " + currentToken);
                    throw new ParseException("unexcpected currentToken '" + currentToken + "'", lexan.getLineNumber());
//					break;
            }
        }
        return this.entryPoint;
    }

    private Token readToken() throws IOException, LexicalException {
        if (isTokenPushed()) {
            setTokenPushed(false);
            return currentToken;
        } else {
            return currentToken = lexan.getNextToken();
        }
    }

    private void expectToken(TokenType type) throws ParseException, IOException, LexicalException {
        TokenType gotType = readToken().getType();
        if (gotType != type) {
            throw new ParseException("Token " + type.toString() + " expected, got " + gotType.toString() + " instead!", lexan.getLineNumber());
        }
    }

    public boolean isTokenPushed() {
        return tokenPushed;
    }

    public void setTokenPushed(boolean tokenPushed) {
        this.tokenPushed = tokenPushed;
    }

    public void run() throws UnknownExpressionTypeException, NotUsableOperatorException {
        throw new UnsupportedOperationException("A relict from the original interpreter.");
        //      logger.debug("Executing entrypoint...");
        //		logger.debug(entryPoint);
        //        ((Statement) entryPoint).execute();
    }
}
