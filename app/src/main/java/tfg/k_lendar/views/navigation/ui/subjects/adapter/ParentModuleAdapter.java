package tfg.k_lendar.views.navigation.ui.subjects.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.k_lendar.R;
import tfg.k_lendar.http.models.recyclerView.modules.ParentModule;

public class ParentModuleAdapter extends RecyclerView.Adapter<ParentModuleAdapter.ParentViewHolder>{
    // An object of RecyclerView.RecycledViewPool
    // is created to share the Views
    // between the child and
    // the parent RecyclerViews
    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();
    private List<ParentModule> itemList;

    public ParentModuleAdapter(List<ParentModule> itemList)
    {
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.parent_item_subject,
                        parent, false);

        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ParentViewHolder parentViewHolder,
            int position)
    {

        // Create an instance of the ParentModule
        // class for the given position
        ParentModule parentModule = itemList.get(position);
        System.out.println(itemList.get(position));

        // For the created instance,
        // get the title and set it
        // as the text for the TextView
        parentViewHolder.textSubject.setText(parentModule.getName());

        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.

        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                parentViewHolder
                        .ChildRecyclerView
                        .getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager
                .setInitialPrefetchItemCount(
                        parentModule
                                .getUfs()
                                .size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        ChildUfAdapter childItemAdapter
                = new ChildUfAdapter(
                parentModule
                        .getChildUfs());
        parentViewHolder
                .ChildRecyclerView
                .setLayoutManager(layoutManager);
        parentViewHolder
                .ChildRecyclerView
                .setAdapter(childItemAdapter);
        parentViewHolder
                .ChildRecyclerView
                .setRecycledViewPool(viewPool);
    }

    // This method returns the number
    // of items we have added in the
    // ParentModuleList i.e. the number
    // of instances we have created
    // of the ParentModuleList
    @Override
    public int getItemCount()
    {

        return itemList.size();
    }

    // This class is to initialize
    // the Views present in
    // the parent RecyclerView
    class ParentViewHolder
            extends RecyclerView.ViewHolder {

        private TextView textSubject;
        private RecyclerView ChildRecyclerView;

        ParentViewHolder(final View itemView)
        {
            super(itemView);

            textSubject
                    = itemView
                    .findViewById(
                            R.id.textSubject);
            ChildRecyclerView
                    = itemView
                    .findViewById(
                            R.id.childRecyclerView);
        }
    }
}
