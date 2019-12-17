package br.com.isaquebrb.productsrestconsume.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.isaquebrb.productsrestconsume.R;
import br.com.isaquebrb.productsrestconsume.config.RetrofitConfig;
import br.com.isaquebrb.productsrestconsume.entities.User;
import br.com.isaquebrb.productsrestconsume.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private TextView name, password, email, phone;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //View objects from layout
        name = findViewById(R.id.userNameText);
        password = findViewById(R.id.userPassText);
        email = findViewById(R.id.userEmailText);
        phone = findViewById(R.id.userPhoneText);

        //Retrofit library to turn a rest API into a java interface
        retrofit = RetrofitConfig.getRetrofitClient();
    }

    /**
     * Method responsible for create a new user in the rest api with a retrofit request
     * @param view
     */
    public void signUp(View view) {

        //Retrieve the data from views and instantiate a new user
        User user = new User(
                null,
                name.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                phone.getText().toString());

        UserService service = retrofit.create(UserService.class);

        Call<User> call = service.createUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"User created successfully",Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"Fail to add user",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
