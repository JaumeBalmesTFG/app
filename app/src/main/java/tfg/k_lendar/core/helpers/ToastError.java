package tfg.k_lendar.core.helpers;

import android.content.Context;
import android.widget.Toast;

public class ToastError {
    public static void execute(Context context, String error) {
        Toast toast;
        toast = Toast.makeText(context, error, Toast.LENGTH_SHORT);
        toast.show();
    }
}
