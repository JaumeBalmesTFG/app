package tfg.k_lendar.views.shared;

import android.content.Context;
import android.content.Intent;
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
import tfg.k_lendar.views.task.EditTaskActivity;
import tfg.k_lendar.views.truancy.EditTruancyActivity;
import tfg.k_lendar.views.uf.NewUfActivity;

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
        View view = mInflater.inflate(R.layout.item_task_truancy, parent, false);
        return new TodaySimpleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodaySimpleAdapter.ViewHolder holder, final int position){
        holder.taskTruancyName.setText(mData.get(position).getTitle());
        holder.editTaskTruancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (mData.get(position).getElementType().equals("task")) {
                    intent = new Intent(todayTaskTruancyActivity, EditTaskActivity.class);

                    intent.putExtra("taskName", mData.get(position).getTitle());
                    intent.putExtra("taskId", mData.get(position).getElementId());
                    intent.putExtra("ufId", mData.get(position).getUfId());
                    intent.putExtra("moduleId", mData.get(position).getModuleId());
                    intent.putExtra("ruleId", mData.get(position).getRuleId());

                } else {
                    intent = new Intent(todayTaskTruancyActivity, EditTruancyActivity.class);
                }

                todayTaskTruancyActivity.startActivity(intent);
            }
        });
    }

    public void setItems(List<TaskTruancySimple> items) { mData = items; }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView taskTruancyName;
        Button editTaskTruancy;

        public ViewHolder(View itemView){
            super (itemView);
            taskTruancyName = itemView.findViewById(R.id.taskTruancyName);
            editTaskTruancy = itemView.findViewById(R.id.editTaskTruancy);
        }
    }

    public void unarchiveSubject(TaskTruancySimple module){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModulePlaceHolderApi ModulePlaceHolderApi = retrofit.create(ModulePlaceHolderApi.class);
        Call<JsonObject> call = ModulePlaceHolderApi.archiveUnarchiveModule(AuthBearerToken.getAuthBearerToken(context), module.getElementId());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        //todayTaskTruancyActivity.finish();
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
