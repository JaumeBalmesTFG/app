package tfg.k_lendar.core.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import tfg.k_lendar.R;

public class ToastSuccess {
    public static void execute(Context context, String message, Class nextActivity) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        //view.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        toast.show();
        Intent intent = new Intent(context, nextActivity);
        context.startActivity(intent);
    }
}