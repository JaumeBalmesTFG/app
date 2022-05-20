package tfg.k_lendar.views.navigation.ui.marks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.k_lendar.databinding.MarksFragmentBinding;
import tfg.k_lendar.http.models.marks.AllModules;

public class SubjectRecyclerAdapter extends RecyclerView.Adapter<SubjectRecyclerAdapter.ViewHolder>{

    private final AllModules all;

    public SubjectRecyclerAdapter(AllModules all) {
        this.all = all;
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
    public SubjectRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubjectRecyclerAdapter.ViewHolder(MarksFragmentBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectRecyclerAdapter.ViewHolder holder, int position) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.binding.recyclerSubjects2.setLayoutManager(layoutManager);
        MarksAdapter adapter = new MarksAdapter(all.getBody());
        holder.binding.recyclerSubjects2.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}
