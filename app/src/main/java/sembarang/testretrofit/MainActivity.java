package sembarang.testretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sembarang.testretrofit.model.Repo;
import sembarang.testretrofit.retrofit.GitHubService;
import sembarang.testretrofit.volley.VolleySingleton;

public class MainActivity extends AppCompatActivity {

    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTime = System.currentTimeMillis();

        //comment each of it to test one against another
        createGitHubServicesWithVolley("hendrawd");
        //createGitHubServicesWithRetrofit("hendrawd");
    }

    private void createGitHubServicesWithRetrofit(String username) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repo>> call = service.listRepo(username);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repoList = response.body();
                for (Repo repo : repoList) {
                    Log.e("retrofit", repo.name);
                }

                printExecutionTime("retrofit");
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createGitHubServicesWithVolley(String username) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://api.github.com/users/");
        urlBuilder.append(username);
        urlBuilder.append("/repos");

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                urlBuilder.toString(),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Repo> repoList = JSON.parseObject(response, new TypeReference<List<Repo>>() {
                        });
                        for (Repo repo : repoList) {
                            Log.e("volley", repo.name);
                        }

                        printExecutionTime("volley");
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void printExecutionTime(String type) {
        long endTime = System.currentTimeMillis();
        Log.e(type + " execution time", String.valueOf(endTime - startTime));
    }
}
