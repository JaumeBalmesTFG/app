package tfg.k_lendar.views.navigation.ui.subjects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.k_lendar.R;
import tfg.k_lendar.http.models.modules.ufs.BaseBody;

/**
 * Created by Dumadu on 26-Oct-17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    List<BaseBody> modules;
    hospitalClickListener hospitalClickListener;

    public RecyclerViewAdapter(List<BaseBody> modules, hospitalClickListener hospitalClickListener) {
        this.modules = modules;
        this.hospitalClickListener = hospitalClickListener;
    }

    interface hospitalClickListener {
        void getItem(int position);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows_modules_parent, parent, false);
        return new RecyclerViewHolder(view, hospitalClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        /*holder.textViewName.setText(hospitalses.get(position).id + ". " + hospitalses.get(position).hospitalName);
        holder.textViewAddress.setText(hospitalses.get(position).address);*/
        holder.subjectTitle.setText(modules.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView subjectTitle;
        //hospitalClickListener hospitalClickListener;

        public RecyclerViewHolder(View itemView, hospitalClickListener hospitalClickListener) {
            super(itemView);
            subjectTitle = itemView.findViewById(R.id.textSubject);
            /*this.hospitalClickListener = hospitalClickListener;
            itemView.setOnClickListener(this);*/
        }

        @Override
        public void onClick(View v) {
            //ospitalClickListener.getItem(getAdapterPosition());
        }
    }
}