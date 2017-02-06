package cz.chovanecm.pascal.truffle;

import com.oracle.truffle.api.dsl.TypeSystem;
import com.oracle.truffle.api.dsl.internal.DSLOptions;
import cz.chovanecm.pascal.truffle.nodes.variables.ArrayStructure;

/**
 * Created by martin on 1/18/17.
 */
//TODO: String[].class seem not to be supported
@TypeSystem({long.class, double.class, boolean.class, String[].class,
        long[].class, double[].class, boolean[].class,
        ArrayStructure.class})
@DSLOptions
public class PascalTypes {

}
