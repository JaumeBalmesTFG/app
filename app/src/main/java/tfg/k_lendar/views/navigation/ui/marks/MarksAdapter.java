package tfg.k_lendar.views.navigation.ui.marks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.k_lendar.databinding.ItemSubjectRowView2Binding;
import tfg.k_lendar.databinding.MarksFragmentBinding;
import tfg.k_lendar.http.models.marks.AllModules;
import tfg.k_lendar.http.models.marks.MarksModules;
import tfg.k_lendar.views.navigation.ui.subjects.UfsAdapter;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.ViewHolder>{

    private final AllModules subjects;

    public MarksAdapter(AllModules subjects) {
        this.subjects = subjects;
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
    public MarksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarksAdapter.ViewHolder(MarksFragmentBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MarksAdapter.ViewHolder holder, int position) {
        //TODO BIND OTHER DATA HERE
        MarksAdapter adapter = new MarksAdapter(subjects);
        holder.binding.recyclerSubjects2.setAdapter(adapter);


        holder.binding.setText(subjects.get(position).getName());
        holder.binding.buttonPoints.setOnClickListener(close -> listener.onItemClick(subjects.get(position)));

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.binding.recyclerSubjects2.setLayoutManager(layoutManager);
        UfsAdapter adapter = new UfsAdapter(subjects.get(position).getUfs());
        holder.binding.recyclerSubjects2.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

}
