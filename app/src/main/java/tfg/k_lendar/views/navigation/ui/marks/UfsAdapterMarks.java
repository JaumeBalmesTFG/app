package tfg.k_lendar.views.navigation.ui.marks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.k_lendar.databinding.ItemUfsRowView2Binding;
import tfg.k_lendar.databinding.ItemUfsRowViewBinding;
import tfg.k_lendar.http.models.taskTruency.Uf;
import tfg.k_lendar.views.navigation.ui.subjects.UfsAdapter;

public class UfsAdapterMarks extends RecyclerView.Adapter<UfsAdapterMarks.ViewHolder> {

    private final List<Uf> ufs;

    public UfsAdapterMarks(List<Uf> ufsList) {
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
    public UfsAdapterMarks.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemUfsRowView2Binding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(UfsAdapterMarks.ViewHolder holder, final int position) {
        holder.binding.ufTitle.setText(ufs.get(position).getName());
        holder.binding.ufGrade.setText(ufs.get(position).getTruancy_percentage());
    }

    @Override
    public int getItemCount() {
        return ufs.size();
    }
}
