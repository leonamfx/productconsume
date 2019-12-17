package br.com.isaquebrb.productsrestconsume.service;

import br.com.isaquebrb.productsrestconsume.entities.Page;
import br.com.isaquebrb.productsrestconsume.entities.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ProductService {

    @GET("products")
    Call<Page> findAll();

    @POST("products")
    Call<Product> insert(@Body Product product, @Header("Authorization") String token);
}
