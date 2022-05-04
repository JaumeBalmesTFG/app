package tfg.k_lendar.views.navigation.ui.subjects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tfg.k_lendar.R;

public class UfAdapter extends RecyclerView.Adapter<UfAdapter.UfViewHolder> {


    @NonNull
    @Override
    public UfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new UfViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rows_modules_child, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final UfViewHolder holder, final int position) {

        Movie movie = movieList.get(position);

        holder.textViewTitle.setText(movie.getName());
        holder.textViewGenre.setText(movie.getGenre());

    }

    @Override
    public int getItemCount() {

        return movieList.size();

    }


    public class UfViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewGenre;
        private ImageView imageViewMovie;


        public UfViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_title);
            textViewGenre = itemView.findViewById(R.id.tv_genre);
            imageViewMovie = itemView.findViewById(R.id.image_view_movie);

        }


    }