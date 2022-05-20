package tfg.k_lendar.views.navigation.ui.marks;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import tfg.k_lendar.databinding.ItemTasksRowView2Binding;
import tfg.k_lendar.http.models.marks.TasksMarks;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private final List<TasksMarks> tasks;

    public TasksAdapter(List<TasksMarks> tasksList) {
        this.tasks = tasksList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTasksRowView2Binding binding;

        public ViewHolder(ItemTasksRowView2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTasksRowView2Binding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.taskName.setText(tasks.get(position).getName());
        holder.binding.taskMark.setText(tasks.get(position).getGrade());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
