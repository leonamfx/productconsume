package br.com.isaquebrb.productsrestconsume.config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8080/"; //localhost


    public static Retrofit getRetrofitClient(){

        if(retrofit == null){

            //An OkHttp interceptor which logs HTTP request and response data.
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            //Logs request and response lines and their respective headers and bodies (if present).
            interceptor.level(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;

    }
}
