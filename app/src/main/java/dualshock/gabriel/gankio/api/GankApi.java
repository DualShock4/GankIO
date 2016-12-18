package dualshock.gabriel.gankio.api;

import dualshock.gabriel.gankio.bean.Gank;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface GankApi {
    @GET("data/{type}/{count}/{page}")
    Call<Gank> getDataByType(@Path("type") String type, @Path("count") int count, @Path("page") int page);

//    @GET("data/{type}/{number}/{page}")
//    Observable<Gank> getGankDatas(@Path("type") String type, @Path("number") int number, @Path("page") int page);

}
