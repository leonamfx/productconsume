package br.com.isaquebrb.productsrestconsume.service;

import br.com.isaquebrb.productsrestconsume.entities.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("users")
    Call<User> createUser(@Body User user);
}
