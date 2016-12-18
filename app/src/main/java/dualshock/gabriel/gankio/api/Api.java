package dualshock.gabriel.gankio.api;


import dualshock.gabriel.gankio.bean.Gank;
import dualshock.gabriel.gankio.util.ConstantValue;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static Call<Gank> getApi(String type, int count, int page) {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(ConstantValue.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GankApi api = retrofit.create(GankApi.class);
        return api.getDataByType(type, count, page);
    }
}
