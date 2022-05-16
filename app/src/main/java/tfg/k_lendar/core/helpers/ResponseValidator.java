package tfg.k_lendar.core.helpers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ResponseValidator {

    static final Set<Integer> success = new HashSet<>(Arrays.asList(200,201,202,203,204));

    public static boolean validate(int code){return success.contains(code);}

}
