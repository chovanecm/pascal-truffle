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
 * While node without any optimisation
 * (without Truffle.getRuntime().createLoopNode())
 */
@NodeInfo(shortName = "while")
public class WhileNode extends StatementNode {
    @Child
    private ExpressionNode conditionNode;
    @Child
    private StatementNode loopNode;

    public WhileNode(ExpressionNode conditionNode, StatementNode loopNode) {
        this.conditionNode = conditionNode;
        this.loopNode = loopNode;
    }

    @Override
    public void execute(VirtualFrame frame) {
        try {
            while (PascalTypesGen.expectBoolean(conditionNode.execute(frame))) {
                loopNode.execute(frame);
            }
        } catch (UnexpectedResultException e) {
            CompilerDirectives.transferToInterpreter();
            throw new UnsupportedSpecializationException(this, new Node[]{conditionNode}, e.getResult());
        }
    }
}
