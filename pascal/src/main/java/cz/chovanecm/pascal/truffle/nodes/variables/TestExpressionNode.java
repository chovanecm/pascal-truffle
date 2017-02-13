package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import cz.chovanecm.pascal.truffle.nodes.ExpressionNode;
import cz.chovanecm.pascal.truffle.nodes.StatementNode;

/**
 * Created by martin on 1/24/17.
 */

/**
 * Remember the evaluated expression.
 */
@NodeChild(value = "expression", type = ExpressionNode.class)
public abstract class TestExpressionNode extends StatementNode {
    private Double doubleValue;
    private String stringValue;
    private Long longValue;
    private Boolean booleanValue;

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    @Specialization
    public void readDouble(double value) {
        this.doubleValue = value;
    }

    @Specialization
    public void readLong(long value) {
        longValue = value;
    }

    @Specialization
    public void readBoolean(boolean value) {
        booleanValue = value;
    }

    @Specialization
    public void readString(String value) {
        stringValue = value;
    }
}
