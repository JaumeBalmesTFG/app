package tfg.k_lendar.http.api.services.auth;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.http.api.requests.auth.AuthRequest;
import tfg.k_lendar.http.models.auth.Auth;

public class AuthService {

    public static void execute(String email){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthPlaceHolderApi authPlaceHolderApi = retrofit.create(AuthPlaceHolderApi.class);

        AuthRequest authRequest = new AuthRequest(email);

        Call<Auth> call = authPlaceHolderApi.createPost(authRequest);

        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
               /* if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }*/

                Auth auth = response.body();

                //return auth.getMessage();
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
            }
        });
    }
}
