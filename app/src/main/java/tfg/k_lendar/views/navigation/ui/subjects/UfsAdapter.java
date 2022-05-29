package tfg.k_lendar.views.navigation.ui.subjects;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.databinding.ItemUfsRowViewBinding;
import tfg.k_lendar.http.api.services.module.ModulePlaceHolderApi;
import tfg.k_lendar.http.api.services.uf.UfPlaceHolderApi;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.Uf;
import tfg.k_lendar.views.uf.NewUfActivity;

public class UfsAdapter extends RecyclerView.Adapter<UfsAdapter.ViewHolder> {

    private final List<Uf> ufs;
    private Context context;

    public UfsAdapter(List<Uf> ufsList, Context context) {
        ufs = ufsList;
        this.context = context;
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
            Intent intent = new Intent(context, NewUfActivity.class);
            intent.putExtra("moduleId", ufs.get(position).getModuleId());
            intent.putExtra("ufId", ufs.get(position).getId());
            intent.putExtra("name", ufs.get(position).getName());
            intent.putExtra("hours", String.valueOf(ufs.get(position).getHours()));
            intent.putExtra("truancyPercentage", String.valueOf(ufs.get(position).getTruancy_percentage()));
            intent.putExtra("action", "update");
           context.startActivity(intent);

        });
        holder.binding.buttonDelete.setOnClickListener(onDelete -> {
            archiveUf(context, ufs.get(position));
        });

    }

    public void archiveUf(Context context, Uf uf){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UfPlaceHolderApi UfPlaceHolderApi = retrofit.create(UfPlaceHolderApi.class);
        Call<JsonObject> call = UfPlaceHolderApi.deleteUf(AuthBearerToken.getAuthBearerToken(context), uf.getId());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ToastError.execute(context,"Uf " + uf.getName() + " deleted successfully");


                    } else {
                        ToastError.execute(context, "To delete this uf, first, delete all attached tasks and truancies");
                    }
                } else {
                    ToastError.execute(context, "To delete this uf, first, delete all attached tasks and truancies");
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ToastError.execute(context, "An error ocurred, try again later");
            }
        });
    }

    @Override
    public int getItemCount() {
        return ufs.size();
    }
}
