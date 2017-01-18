package cz.chovanecm.pascal.truffle.nodes.expression;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;

/**
 * Created by martin on 1/18/17.
 */
@NodeChildren({@NodeChild("left"), @NodeChild("right")})
public abstract class BinaryNode extends ExpressionNode {

}
