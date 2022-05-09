package tfg.k_lendar.views.navigation.ui.subjects.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.k_lendar.R;
import tfg.k_lendar.http.models.recyclerView.modules.ChildUf;

public class ChildUfAdapter extends RecyclerView.Adapter<ChildUfAdapter.ChildViewHolder>{
    private List<ChildUf> ChildUfList;

    // Constructor
    ChildUfAdapter(List<ChildUf> childUfList)
    {
        this.ChildUfList = childUfList;
    }


    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(
            @NonNull ChildViewHolder childViewHolder,
            int position)
    {

        // Create an instance of the ChildUf
        // class for the given position
        ChildUf childUf
                = ChildUfList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        childViewHolder
                .ChildUfTitle
                .setText(childUf.getName());
    }

    @Override
    public int getItemCount()
    {

        // This method returns the number
        // of items we have added
        // in the ChildUfList
        // i.e. the number of instances
        // of the ChildUfList
        // that have been created
        return ChildUfList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class ChildViewHolder
            extends RecyclerView.ViewHolder {

        TextView ChildUfTitle;

        ChildViewHolder(View itemView)
        {
            super(itemView);
            ChildUfTitle
                    = itemView.findViewById(
                    R.id.ufTitle);
        }
    }
}
