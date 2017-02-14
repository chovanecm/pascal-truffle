package cz.chovanecm.pascal.truffle.nodes.controlflow;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RepeatingNode;
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
    private LoopNode loop;

    public WhileNode(ExpressionNode conditionNode, StatementNode loopNode) {

        loop = Truffle.getRuntime().createLoopNode(new WhileRepeatingNode(conditionNode, loopNode));
    }

    @Override
    public void execute(VirtualFrame frame) {
        loop.executeLoop(frame);
    }

    public static class WhileRepeatingNode extends Node implements RepeatingNode {
        @Child
        private ExpressionNode conditionNode;
        @Child
        private StatementNode loopNode;

        public WhileRepeatingNode(ExpressionNode conditionNode, StatementNode loopNode) {
            this.conditionNode = conditionNode;
            this.loopNode = loopNode;
        }

        @Override
        public boolean executeRepeating(VirtualFrame frame) {
            if (PascalTypesGen.asBoolean(conditionNode.execute(frame))) {
                loopNode.execute(frame);
                return true;
            } else {
                return false;
            }
        }
    }
}
