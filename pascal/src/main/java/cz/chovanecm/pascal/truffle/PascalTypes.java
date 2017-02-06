package cz.chovanecm.pascal.truffle;

import com.oracle.truffle.api.dsl.TypeSystem;
import com.oracle.truffle.api.dsl.internal.DSLOptions;

/**
 * Created by martin on 1/18/17.
 */
//TODO: String[].class seem not to be supported
@TypeSystem({long.class, String.class, double.class, boolean.class, long[].class, double[].class, boolean[].class})
@DSLOptions
public class PascalTypes {

}
