package tfg.k_lendar.core.helpers;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class RemoveErrorTextWatcher implements TextWatcher {
    private TextInputLayout textInputLayout;

    public RemoveErrorTextWatcher(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(textInputLayout.isErrorEnabled()){
            textInputLayout.setErrorEnabled(false);
        }
    }
}
