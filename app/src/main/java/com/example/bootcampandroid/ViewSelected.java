package com.example.bootcampandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSelected extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<Movie> list;
    private static final String APIKEY = "2696829a81b1b5827d515ff121700838";//needed for getting data about movie/s

    Button btnBack;

    //Saving passed data from first activity
    String mvName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected);

        //Creating bundle for accessing data in second activ
        Bundle bundle = getIntent().getExtras();
        mvName = bundle.getString("MVName");

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent();
                backIntent.putExtra("data", mvName);//passing back name
                setResult(RESULT_OK);//sending result
                ViewSelected.this.finish();//closing intent
            }
        });

        initViews();//call function to set our data on screen
    }

   // }

    private void initViews(){
        recyclerView = (RecyclerView) findViewById(R.id.listmovies);
        recyclerView.setHasFixedSize(true);//to set all item to be same size for performance
        list = new ArrayList<>();
        myAdapter = new MovieAdapter(this, list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        loadMovies(mvName);
    }

    private void loadMovies(String name){
        String nameOfMovie = name;
        String key = APIKEY;
        try{
            if (key.isEmpty()){
                Toast.makeText(this, "No api key provided!!!", Toast.LENGTH_SHORT).show();
                return;
            }

            ClientApi client = new ClientApi();
            JsonPlaceHolderApi service = client.getJSON().create(JsonPlaceHolderApi.class);

            Call<MoviesJson> call = service.getSearched(key, nameOfMovie);

            call.enqueue(new Callback<MoviesJson>() {
                @Override
                public void onResponse(Call<MoviesJson> call, Response<MoviesJson> response) {
                    List<Movie> list = response.body().getResult();
                    if(response.body().getTotalResults() == 0){
                        Toast.makeText(ViewSelected.this, "No data for selected movie!!!", Toast.LENGTH_SHORT).show();//small popup for user to know no movie came back
                        Intent returnIntent = new Intent();
                        setResult(RESULT_CANCELED,returnIntent);//returning to main activity a respons that search was bad
                        ViewSelected.this.finish();//finishing with this activity
                        //return to main_activity code for bad search so that we dont save search name
                    }
                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), list));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<MoviesJson> call, Throwable t) {

                    Log.d("Error", t.getMessage());

                    Toast.makeText(ViewSelected.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}