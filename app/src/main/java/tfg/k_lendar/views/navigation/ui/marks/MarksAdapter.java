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

import tfg.k_lendar.databinding.ItemSubjectRowView2Binding;
import tfg.k_lendar.http.models.marks.MarksModules;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.ViewHolder>{

    private final List<MarksModules> subjects;

    public MarksAdapter(List<MarksModules> subjects) {

        this.subjects = subjects;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSubjectRowView2Binding binding;

        public ViewHolder(ItemSubjectRowView2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public MarksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSubjectRowView2Binding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MarksAdapter.ViewHolder holder, int position) {
        holder.binding.subjectName.setText(subjects.get(position).getName());
        holder.binding.arrowButtonSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.binding.subjectCard.getVisibility() == View.VISIBLE){
                    //TransitionManager.beginDelayedTransition(holder.binding.subjectCard, new AutoTransition());
                    holder.binding.ufsCard.setVisibility(View.INVISIBLE);
                    //holder.binding.taskCard.setVisibility(View.INVISIBLE);
                    holder.binding.arrowButtonSubject.setRotation(270);
                }
                else {
                    TransitionManager.beginDelayedTransition(holder.binding.subjectCard, new AutoTransition());
                    holder.binding.ufsCard.setVisibility(View.VISIBLE);
                    holder.binding.arrowButtonSubject.setRotation(-270);
                }
            }
        });

       LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
       holder.binding.recyclerUfs2.setLayoutManager(layoutManager);
       UfsAdapter2 adapter = new UfsAdapter2(subjects.get(position).getUfs());
       holder.binding.recyclerUfs2.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

}
