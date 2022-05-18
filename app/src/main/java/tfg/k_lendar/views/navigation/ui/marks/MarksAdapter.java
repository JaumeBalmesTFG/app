package tfg.k_lendar.views.navigation.ui.marks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.k_lendar.databinding.MarksFragmentBinding;
import tfg.k_lendar.http.models.module.AllModules;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.ViewHolder> {

    private final List<AllModules> subjects;
    private final OnItemClickListener listener;

    public MarksAdapter(List<AllModules> subjects, MarksAdapter.OnItemClickListener listener) {
        this.subjects = subjects;
        this.listener = listener;
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
        return new ViewHolder(MarksFragmentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MarksAdapter.ViewHolder holder, int position) {
        holder.binding.subjectText.setText(subjects.get(position).getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.binding.ufsRecycler.setLayoutManager(layoutManager);
        UfsAdapterMarks adapter = new UfsAdapterMarks(subjects.get(position).getUfs());
        holder.binding.ufsRecycler.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public interface OnItemClickListener {
        void onItemClick(AllModules item);
    }
}
