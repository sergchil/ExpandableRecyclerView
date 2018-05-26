package com.chilingaryan.expandablerecyclerview.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by sergey on 12/24/17.
 */

public interface RetrofitService {
    @GET
    Call<DummyResponse> getDummyJson(@Url String url);

}
