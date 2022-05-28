package tfg.k_lendar.views.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.services.module.ModulePlaceHolderApi;
import tfg.k_lendar.http.models.helper.TaskTruancySimple;

public class TodaySimpleAdapter extends RecyclerView.Adapter<TodaySimpleAdapter.ViewHolder> {

    private TodayTaskTruancyActivity todayTaskTruancyActivity;
    private List<TaskTruancySimple> mData;
    private LayoutInflater mInflater;
    private Context context;

    public TodaySimpleAdapter(List<TaskTruancySimple> itemList, Context context, TodayTaskTruancyActivity todayTaskTruancyActivity){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.todayTaskTruancyActivity = todayTaskTruancyActivity;
    }

    @Override
    public int getItemCount(){return mData.size();}

    @Override
    public TodaySimpleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_subjects_archived, null);
        return new TodaySimpleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodaySimpleAdapter.ViewHolder holder, final int position){
        holder.titleSubject.setText(mData.get(position).getName());
        holder.archiveSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unarchiveSubject(mData.get(position));
            }
        });
    }

    public void setItems(List<TaskTruancySimple> items) { mData = items; }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleSubject;
        Button archiveSubjectButton;

        public ViewHolder(View itemView){
            super (itemView);
            titleSubject = itemView.findViewById(R.id.subjectTitle);
            archiveSubjectButton = itemView.findViewById(R.id.archiveSubjectButton);
        }
    }

    public void unarchiveSubject(TaskTruancySimple module){

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
                        todayTaskTruancyActivity.finish();
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
