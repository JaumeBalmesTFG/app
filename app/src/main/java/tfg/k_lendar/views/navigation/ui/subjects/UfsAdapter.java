package tfg.k_lendar.views.navigation.ui.subjects;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import tfg.k_lendar.databinding.ItemUfsRowViewBinding;
import tfg.k_lendar.http.models.taskTruency.Uf;

public class UfsAdapter extends RecyclerView.Adapter<UfsAdapter.ViewHolder> {

    private final List<Uf> ufs;

    public UfsAdapter(List<Uf> ufsList) {
        ufs = ufsList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemUfsRowViewBinding binding;

        public ViewHolder(ItemUfsRowViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemUfsRowViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.ufText.setText(ufs.get(position).getName());
        holder.binding.buttonEdit.setOnClickListener(onEdit -> {
            //TODO starIntentToEdit()
            /* Intent intent = new Intent(this, UfEditor.class);
               intent.putExtra(EXTRA_MESSAGE, "message");
               startActivity(intent); */

        });
        holder.binding.buttonDelete.setOnClickListener(onDelete -> {
            //TODO retrofitCallToDelete()
        });

    }

    @Override
    public int getItemCount() {
        return ufs.size();
    }
}
