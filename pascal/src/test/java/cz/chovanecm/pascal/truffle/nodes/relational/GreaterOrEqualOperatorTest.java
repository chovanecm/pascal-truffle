package cz.chovanecm.pascal.truffle.nodes.relational;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by martin on 1/24/17.
 */
public class GreaterOrEqualOperatorTest {
    @Test
    public void execute() throws Exception {
        GreaterOrEqualOperator op = GreaterOrEqualOperatorNodeGen.create(null, null);
        assertEquals("false >= false should be true", true, op.execute(null, false, false));
        assertEquals("false >= true should be false", false, op.execute(null, false, true));
        assertEquals("true >= false should be true", true, op.execute(null, true, false));
        assertEquals("true >= true should be true", true, op.execute(null, true, true));
    }

}