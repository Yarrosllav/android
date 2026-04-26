package com.example.simplestore;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StoreApi {

    @GET("products")
    Call<List<Product>> getAllProducts();
}
