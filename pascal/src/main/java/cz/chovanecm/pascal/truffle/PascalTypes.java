package cz.chovanecm.pascal.truffle;

import com.oracle.truffle.api.dsl.TypeSystem;
import com.oracle.truffle.api.dsl.internal.DSLOptions;

/**
 * Created by martin on 1/18/17.
 */
//Not sure what is this actually good for
@TypeSystem({long.class, String.class, double.class, boolean.class})
@DSLOptions
public class PascalTypes {

}
