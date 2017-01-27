package cz.chovanecm.pascal.truffle.nodes.controlflow;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import cz.chovanecm.pascal.truffle.PascalTypesGen;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;

/**
 * Created by martin on 1/27/17.
 */

/**
 * If Node
 */
@NodeInfo(shortName = "if")
public class IfNode extends StatementNode {
    @Child
    private ExpressionNode conditionNode;
    @Child
    private StatementNode thenNode;
    @Child
    private StatementNode elseNode;

    public IfNode(ExpressionNode conditionNode, StatementNode thenNode, StatementNode elseNode) {
        this.conditionNode = conditionNode;
        this.thenNode = thenNode;
        this.elseNode = elseNode;
    }

    @Override
    public void execute(VirtualFrame frame) {
        try {
            boolean conditionResult = PascalTypesGen.expectBoolean(conditionNode.execute(frame));
            if (conditionResult) {
                thenNode.execute(frame);
            } else if (elseNode != null) {
                elseNode.execute(frame);
            }
        } catch (UnexpectedResultException e) {
            CompilerDirectives.transferToInterpreter();
            throw new UnsupportedSpecializationException(this, new Node[]{conditionNode}, e.getResult());
        }
    }
}
