package tfg.k_lendar.views.navigation.ui.marks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.k_lendar.databinding.MarksFragmentBinding;
import tfg.k_lendar.http.models.marks.MarksModules;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder>{

    private final List<MarksModules> marksModules;

    public SubjectAdapter(List<MarksModules> marksModules) {
        this.marksModules = marksModules;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MarksFragmentBinding binding;

        public ViewHolder(MarksFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubjectAdapter.ViewHolder(MarksFragmentBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ViewHolder holder, int position) {
        //LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        //holder.binding.recyclerSubjects2.setLayoutManager(layoutManager);
        //MarksAdapter adapter = new MarksAdapter(marksModules);
        //holder.binding.recyclerSubjects2.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return marksModules.size();
    }

}
