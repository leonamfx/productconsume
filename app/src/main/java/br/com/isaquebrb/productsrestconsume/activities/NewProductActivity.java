package br.com.isaquebrb.productsrestconsume.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.isaquebrb.productsrestconsume.R;
import br.com.isaquebrb.productsrestconsume.config.RetrofitConfig;
import br.com.isaquebrb.productsrestconsume.entities.Product;
import br.com.isaquebrb.productsrestconsume.service.ProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static br.com.isaquebrb.productsrestconsume.activities.MainActivity.USER_PREFERENCE;

public class NewProductActivity extends AppCompatActivity {

    private TextView name, description, price, imgUrl;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        //View objects from layout
        name = findViewById(R.id.nameProductText);
        description = findViewById(R.id.descriptionProductText);
        price = findViewById(R.id.priceProductText);
        imgUrl = findViewById(R.id.imgUrlText);

        //Retrofit library to turn a rest API into a java interface
        retrofit = RetrofitConfig.getRetrofitClient();
    }

    /**
     * Method responsible for insert a new product in the rest api using retrofit call
     * @param view
     */
    public void insertProduct(View view) {

        //Create a product from given information in the textviews
        Product product = instantiateProduct();

        //Obtain the token saved in method login (Main Activity)
        SharedPreferences preferences = getSharedPreferences(USER_PREFERENCE, MODE_PRIVATE);
        String token = "Bearer " + preferences.getString("token", null);

        //Service representing /products endpoints
        ProductService service = retrofit.create(ProductService.class);

        //Call (Retrofit method that sends a request to a web server) for method findAll, the response must be a 'Page' object (json)
        Call<Product> call = service.insert(product, token);

        //Call async execution
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                if(response.isSuccessful()){

                    Toast.makeText(NewProductActivity.this, "Product created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NewProductActivity.this, ProductsActivity.class));

                }else{
                    Toast.makeText(NewProductActivity.this, "Error to add a product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(NewProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * Method responsible to get the texts from textviews and instantiate a new user
     * @return
     */
    private Product instantiateProduct() {

        //Convert string price to double object
        String strPrice = price.getText().toString();
        Double priceValue = 0d;
        if (!strPrice.isEmpty()) {
            try {
                priceValue = Double.parseDouble(strPrice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new Product(
                null,
                name.getText().toString(),
                description.getText().toString(),
                priceValue,
                imgUrl.getText().toString());
    }
}
