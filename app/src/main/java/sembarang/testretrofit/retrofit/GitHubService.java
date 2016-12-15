package sembarang.testretrofit.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sembarang.testretrofit.model.Repo;

/**
 * @author hendrawd on 12/15/16
 */

public interface GitHubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepo(@Path("user") String user);
}