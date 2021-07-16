package retrofit;

import com.example.root.build_model.bean.resultBean;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface Api {
    @GET("search/get/?")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加
    Call<resultBean> contributorBySimpleGetCall(@QueryMap Map<String, String> map);

//    @FormUrlEncoded
    @GET("{policies}/{privacy}")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加
    Call<ResponseBody> contributorBySimpleGetCall1(@Path(value = "policies",encoded = true) String policies,
                                                  @Path(value = "privacy",encoded = true) String privacy);
}
