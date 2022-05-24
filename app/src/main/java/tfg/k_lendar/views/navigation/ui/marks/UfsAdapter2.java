package tfg.k_lendar.views.navigation.ui.marks;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
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
        holder.binding.truancysMark.setText(String.valueOf(ufs.get(position).getTotalTruancies()));
        holder.binding.progressBar.setProgress(ufs.get(position).getTruancy_percentage());
        holder.binding.arrowButtonUf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.binding.taskCard.getVisibility() == View.VISIBLE){
                    //TransitionManager.beginDelayedTransition(subjectCard, new AutoTransition());
                    holder.binding.taskCard.setVisibility(View.INVISIBLE);
                    holder.binding.arrowButtonUf.setRotation(270);
                }
                else {
                    TransitionManager.beginDelayedTransition(holder.binding.ufCard, new AutoTransition());
                    holder.binding.taskCard.setVisibility(View.VISIBLE);
                    holder.binding.arrowButtonUf.setRotation(-270);

                }
            }
        });

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
