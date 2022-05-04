package tfg.k_lendar.views.navigation.ui.subjects;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;

import tfg.k_lendar.R;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // the custom layout for the first recycler view is inflated here
        View theView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows_modules_parent, parent, false);

        // returns a view holder with the view as a parameter
        return new ModuleViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, final int position) {

        //display genre type
        holder.textViewCategory.setText(data.get(position).getGenre());

        //bind the inner recycler view to an adapter
        horizontalAdapter = new UfAdapter(data.get(position).getList(), context);
        holder.recyclerViewHorizontal.setAdapter(horizontalAdapter);

    }


    @Override
    public int getItemCount() {
        //returns size of the list
        return data.size();

    }
}