package tfg.k_lendar.views.navigation.ui.subjects.archived;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.helpers.ToastSuccess;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.services.module.ModulePlaceHolderApi;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.views.module.ArchivedModulesActivity;
import tfg.k_lendar.views.module.NewEditModuleActivity;
import tfg.k_lendar.views.navigation.NavigationActivity;
import tfg.k_lendar.views.navigation.ui.subjects.SubjectsAdapter;
import tfg.k_lendar.views.navigation.ui.subjects.SubjectsFragment;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArchivedModulesActivity archivedModulesActivity;
    private List<Modules> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter (List<Modules> itemList, Context context, ArchivedModulesActivity archivedModulesActivity){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.archivedModulesActivity = archivedModulesActivity;
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
        holder.archiveSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unarchiveSubject(mData.get(position));
            }
        });
    }

    public void setItems(List<Modules> items) { mData = items; }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleSubject;
        Button archiveSubjectButton;

        public ViewHolder(View itemView){
            super (itemView);
            titleSubject = itemView.findViewById(R.id.subjectTitle);
            archiveSubjectButton = itemView.findViewById(R.id.archiveSubjectButton);
        }
    }

    public void unarchiveSubject(Modules module){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModulePlaceHolderApi ModulePlaceHolderApi = retrofit.create(ModulePlaceHolderApi.class);
        Call<JsonObject> call = ModulePlaceHolderApi.archiveUnarchiveModule(AuthBearerToken.getAuthBearerToken(context), module.getId());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ToastError.execute(context, "Module " + module.getName() + " unarchived successfully");
                        //archivedModulesActivity.getAllArchivedModules(archivedModulesActivity, context);
                    } else {
                        ToastError.execute(context, "An error ocurred, try again later");
                    }
                } else {
                    ToastError.execute(context, "An error ocurred, try again later");
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ToastError.execute(context, "An error ocurred, try again later");
            }
        });
    }


}
