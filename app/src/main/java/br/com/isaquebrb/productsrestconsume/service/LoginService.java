package br.com.isaquebrb.productsrestconsume.service;

import br.com.isaquebrb.productsrestconsume.entities.Login;
import br.com.isaquebrb.productsrestconsume.entities.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("auth/login")
    Call<Token> login(@Body Login login);
}