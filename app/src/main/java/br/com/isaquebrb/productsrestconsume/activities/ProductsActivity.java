package br.com.isaquebrb.productsrestconsume.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.isaquebrb.productsrestconsume.R;
import br.com.isaquebrb.productsrestconsume.adapters.ProductAdapter;
import br.com.isaquebrb.productsrestconsume.config.RetrofitConfig;
import br.com.isaquebrb.productsrestconsume.entities.Product;
import br.com.isaquebrb.productsrestconsume.entities.Page;
import br.com.isaquebrb.productsrestconsume.service.ProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductsActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    private Retrofit retrofit;
    private List<Product> productList;
    private static final String TAG = "ProductsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        //View objects from layout
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProducts);

        //Retrofit library to turn a rest API into a java interface
        retrofit = RetrofitConfig.getRetrofitClient();

        productList = new ArrayList<>();
        getProducts();

        //Create an adapter for recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Method responsible for doing a GET request to get products and add them into a list
     */
    private void getProducts() {

        //Service representing /products endpoints
        ProductService service = retrofit.create(ProductService.class);

        //Call (Retrofit method that sends a request to a web server) for method findAll, the response must be a 'Page' object (json)
        Call<Page> call = service.findAll();

        //Call async execution
        call.enqueue(new Callback<Page>() {

            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {

                if (response.isSuccessful()) {

                    setAdapterProductsList(response.body().getContent());

                    Log.d(TAG, "onResponse: Products list loaded successfully. Size: "+productList.size());
                } else {
                    Toast.makeText(ProductsActivity.this, "Products loading failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method responsible for converting the objects into a products and save them into adapters product list
     * @param content
     */
    private void setAdapterProductsList(List<Object> content) {
        try{
            //Java library that convert java objects into JSON representation
            Gson gson = new Gson();

            //Convert object to string
            String json = gson.toJson(content);

            //Create a generic type List<Product>
            Type type = new TypeToken<List<Product>>() {
            }.getType();

            //Create a list of products given the JSON
            productList = gson.fromJson(json, type);

            if(productList.isEmpty())
                Toast.makeText(ProductsActivity.this, "No products found", Toast.LENGTH_SHORT).show();

            //Update the adapter list
            adapter.setProductList(productList);

            //Save the adapter
            adapter.notifyDataSetChanged();

        }catch(Exception e){
            Log.e(TAG, e.getMessage());
            Toast.makeText(ProductsActivity.this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * Method responsible to start a new activity to create a new product
     * @param view
     */
    public void insertProduct(View view){
        startActivity(new Intent(this, NewProductActivity.class));
    }

}
