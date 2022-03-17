package tfg.k_lendar.views.navigation.ui.marks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MarksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MarksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}