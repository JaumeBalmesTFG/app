package tfg.k_lendar.views.navigation.ui.subjects;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.k_lendar.databinding.ItemSubjectRowViewBinding;
import tfg.k_lendar.http.models.taskTruency.Modules;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {

    private final List<Modules> subjects;
    private final OnItemClickListener listener;

    public SubjectsAdapter(List<Modules> modulesList, OnItemClickListener listener) {
        subjects = modulesList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSubjectRowViewBinding binding;

        public ViewHolder(ItemSubjectRowViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSubjectRowViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.subjectText.setText(subjects.get(position).getName());
        holder.binding.buttonPoints.setOnClickListener(close -> listener.onItemClick(subjects.get(position)));

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.binding.recyclerUfs.setLayoutManager(layoutManager);
        UfsAdapter adapter = new UfsAdapter(subjects.get(position).getUfs());
        holder.binding.recyclerUfs.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Modules item);
    }
}
