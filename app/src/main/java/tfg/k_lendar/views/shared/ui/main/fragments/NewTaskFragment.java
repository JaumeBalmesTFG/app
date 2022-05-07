package tfg.k_lendar.views.shared.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

import tfg.k_lendar.R;

public class NewTaskFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AutoCompleteTextView subject = view.findViewById(R.id.subjectsDropdown);
        String[] items = {"item 1", "item 2", "item 3"};
        ArrayAdapter adapter = new ArrayAdapter<>(this.getContext(), R.layout.dropdown_item, items);
        subject.setAdapter(adapter);
    }


}
