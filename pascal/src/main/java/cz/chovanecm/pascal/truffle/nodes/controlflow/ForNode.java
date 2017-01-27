package cz.chovanecm.pascal.truffle.nodes.controlflow;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import cz.chovanecm.pascal.truffle.PascalTypesGen;
import cz.chovanecm.pascal.truffle.nodes.BlockNode;
import cz.chovanecm.pascal.truffle.nodes.ConstantNodeGen;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;
import cz.chovanecm.pascal.truffle.nodes.expression.AddNodeGen;
import cz.chovanecm.pascal.truffle.nodes.relational.GreaterOrEqualOperatorNodeGen;
import cz.chovanecm.pascal.truffle.nodes.relational.LessOrEqualOperatorNodeGen;
import cz.chovanecm.pascal.truffle.nodes.variables.ReadVariableNode;
import cz.chovanecm.pascal.truffle.nodes.variables.ReadVariableNodeGen;
import cz.chovanecm.pascal.truffle.nodes.variables.WriteVariableNode;
import cz.chovanecm.pascal.truffle.nodes.variables.WriteVariableNodeGen;

/**
 * Created by martin on 1/27/17.
 */
public class ForNode extends StatementNode {
    @Child
    StatementNode whileNode;
    @Child
    private WriteVariableNode assignmentNode;
    @Child
    private ExpressionNode finalValueNode;
    @Child
    private StatementNode loopNode;
    private ForDirection forDirection;
    @Child
    private ExpressionNode conditionNode;

    public ForNode(WriteVariableNode assignmentNode, ExpressionNode finalValueNode,
                   StatementNode loopNode, ForDirection forDirection) {

        this.assignmentNode = assignmentNode;
        this.finalValueNode = finalValueNode;
        this.forDirection = forDirection;
        this.loopNode = addIncrements(loopNode);
        this.conditionNode = buildCondition();
    }

    private StatementNode addIncrements(StatementNode loopBody) {
        long step = 0;
        if (forDirection == ForDirection.UP) {
            step = 1;
        } else {
            step = -1;
        }
        ExpressionNode newLoopVariableValue = AddNodeGen.create(
                ReadVariableNodeGen.create(assignmentNode.getVariableName()),
                ConstantNodeGen.create(step)
        );
        WriteVariableNode writeBackNode = WriteVariableNodeGen.create(newLoopVariableValue, assignmentNode.getVariableName());
        return new BlockNode(new StatementNode[]{loopBody, writeBackNode});
    }

    private ExpressionNode buildCondition(/*VirtualFrame frame*/) {
        /* FreePascal (and maybe other implementations
        evaluate the final expression only once
         */
        /*ConstantNode finalValue = ConstantNodeGen.create(finalValueNode.execute(frame));*/
        ReadVariableNode readVariableNode = ReadVariableNodeGen.create(assignmentNode.getVariableName());
        if (forDirection == ForDirection.UP) {
            return LessOrEqualOperatorNodeGen.create(readVariableNode, finalValueNode);
        } else {
            //DOWNTO
            return GreaterOrEqualOperatorNodeGen.create(readVariableNode, finalValueNode);
        }
    }

    @Override
    public void execute(VirtualFrame frame) {
        assignmentNode.execute(frame);
/*        whileNode = new WhileNode(conditionNode, loopNode);
        whileNode.execute(frame);*/
        try {
            while (PascalTypesGen.expectBoolean(conditionNode.execute(frame))) {
                loopNode.execute(frame);
            }
        } catch (UnexpectedResultException e) {
            CompilerDirectives.transferToInterpreter();
            throw new UnsupportedSpecializationException(this, new Node[]{conditionNode}, e.getResult());
        }
    }

    public static enum ForDirection {
        UP,
        DOWN
    }
}
