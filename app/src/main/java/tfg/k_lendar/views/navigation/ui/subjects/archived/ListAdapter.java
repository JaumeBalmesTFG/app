package tfg.k_lendar.views.navigation.ui.subjects.archived;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.k_lendar.R;
import tfg.k_lendar.http.models.taskTruency.Modules;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Modules> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter (List<Modules> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount(){return mData.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_subjects_archived, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.titleSubject.setText(mData.get(position).getName());
    }

    public void setItems(List<Modules> items) { mData = items; }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleSubject;

        public ViewHolder(View itemView){
            super (itemView);
            titleSubject = itemView.findViewById(R.id.subjectTitle);
        }
    }
}
