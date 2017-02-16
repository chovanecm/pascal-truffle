package cz.chovanecm.pascal.truffle.nodes.variables;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import cz.chovanecm.pascal.exceptions.TypeException;

/**
 * Created by chovamar on 2/6/17.
 */
public class DeclareArrayVariable extends DeclareVariableNode {
    private final Class<?> clazz;
    private int lowerBound;
    private int upperBound;

    public DeclareArrayVariable(String name, FrameSlot frameSlot, int lowerBound, int upperBound, Class<?> clazz) {
        super(name, frameSlot);
        if (lowerBound > upperBound) {
            CompilerDirectives.transferToInterpreter();
            throw new TypeException(String.format("Array lower bound (%d) should not be higher as the upper bound (%d)", lowerBound, upperBound));
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.clazz = clazz;
    }

    @Override
    public void execute(VirtualFrame frame) {
        frame.setObject(getFrameSlot(), new ArrayStructure(lowerBound, upperBound, clazz));
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
}
