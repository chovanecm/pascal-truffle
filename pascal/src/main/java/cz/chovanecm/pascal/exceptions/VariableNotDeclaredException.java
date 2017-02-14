package cz.chovanecm.pascal.exceptions;

/**
 * Created by chovamar on 2/14/17.
 */
public class VariableNotDeclaredException extends Exception {
    public VariableNotDeclaredException(String name) {
        super("Variable '" + name + "' not declared.");
    }
}
