package br.com.isaquebrb.productsrestconsume.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.isaquebrb.productsrestconsume.R;
import br.com.isaquebrb.productsrestconsume.config.RetrofitConfig;
import br.com.isaquebrb.productsrestconsume.entities.Login;
import br.com.isaquebrb.productsrestconsume.entities.Token;
import br.com.isaquebrb.productsrestconsume.service.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private TextView email, password;
    private Retrofit retrofit;
    private static final String TAG = "MainActivity";
    public static final String USER_PREFERENCE = "user_preference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View objects from layout
        email = findViewById(R.id.userEmailText);
        password = findViewById(R.id.passwordText);

        //Retrofit library to turn a rest API into a java interface
        retrofit = RetrofitConfig.getRetrofitClient();
    }

    /**
     * Method responsible for login the given user/pass in login button
     *
     * @param view button that is being clicked
     */
    public void login(View view) {

        //Get values from text views and instantiate Login object
        Login login = new Login(email.getText().toString(), password.getText().toString());

        //Service representing /login endpoints
        LoginService service = retrofit.create(LoginService.class);

        //Call (Retrofit method that sends a request to a web server) for method login, the response must be a 'Token' object (json)
        Call<Token> call = service.login(login);

        //Call async execution
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if (response.isSuccessful()) {

                    String token = response.body().getToken();

                    saveToken(token);

                    Log.d(TAG, token);
                    Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();

                    //Go to other activity
                    startActivity(new Intent(MainActivity.this, ProductsActivity.class));

                } else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void register(View view){
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

    /**
     * Method responsible for save the token with shared preference
     *
     * @param token obtained in login response request
     */
    private void saveToken(String token) {
        SharedPreferences preferences = getSharedPreferences(USER_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
}
