package tfg.k_lendar.views.navigation.ui.marks;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import tfg.k_lendar.databinding.ItemUfsRowView2Binding;
import tfg.k_lendar.http.models.marks.UfMarks;

public class UfsAdapter2 extends RecyclerView.Adapter<UfsAdapter2.ViewHolder> {

    private final List<UfMarks> ufs;

    public UfsAdapter2(List<UfMarks> ufsList) {
        ufs = ufsList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemUfsRowView2Binding binding;

        public ViewHolder(ItemUfsRowView2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemUfsRowView2Binding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.ufName.setText(ufs.get(position).getName());
        holder.binding.ufMark.setText(ufs.get(position).getGlobalUfGrade());
        holder.binding.truancysMark.setText(ufs.get(position).getTotalTruancies());
        holder.binding.progressBar.setProgress(ufs.get(position).getTruancy_percentage());

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.binding.recyclerTasks2.setLayoutManager(layoutManager);
        TasksAdapter adapter = new TasksAdapter(ufs.get(position).getTasks());
        holder.binding.recyclerTasks2.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return ufs.size();
    }
}
