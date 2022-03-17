package tfg.k_lendar.views.navigation.ui.subjects;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubjectsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SubjectsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}