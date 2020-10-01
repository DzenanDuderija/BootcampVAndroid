package com.example.bootcampandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

/*SQLLite better and easier option problem with saving data to array searched*/

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView etName;

    Button btnSearch;

    //boolean isLoaded = false;

    String keyForData = "1";// key for saving data !? if hardcoding it will make safer!!!

    ArrayList<String> searched = new ArrayList<>();
    //previous.getString("MovieNames", ""); //Declaring string that will hold our last 10 searches

    //intent code
    final int VIEW_SELECTED = 1;

    //public static final String MY_PREFS_FILENAME = "com.example.bootcampandroid.Names";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMovieFromData();


        etName = findViewById(R.id.etName);//edited TextvView in purpos of creating autocomplete feature
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    etName.showDropDown();
                }
            }
        });

        //String[] searched = {"batman", "rocky", "pirates", "fast", "chan"};//previous.getString("MovieNames", ""); //Declaring string that will hold our last 10 searches

        ArrayAdapter<String> arAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, searched);

        etName.setThreshold(1);
        etName.setAdapter(arAdapter);

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mvName = etName.getText().toString().trim();


                //pass through data that we want to viewselected activity
                Intent intent = new Intent(MainActivity.this, com.example.bootcampandroid.ViewSelected.class);

                //Creating bundle for accessing data in second activity
                Bundle bundle = new Bundle();
                //Adding data to that bundle
                bundle.putString("MVName", mvName);
                //Adding bundle to intent
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_SELECTED);
            }
        });
    }

    //for handeling respons from viewselected activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String mv;

        if (requestCode == VIEW_SELECTED){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "You have searched for : " + etName.getText().toString().trim() + " successfully", Toast.LENGTH_SHORT).show();//notification
                //addMovieToData(searched, etName.getText().toString() , keyForData);
                mv = etName.getText().toString().trim();
                //ArrayList<String> newAr = newMovie(searched, mv);
                Toast.makeText(this, "You want to add to list: " + mv, Toast.LENGTH_SHORT).show();
                newMovie(searched, mv);
                onRestart();

            }else if(resultCode == RESULT_CANCELED){
                onRestart();
            }
        }
    }

    //calling this function so that we can refresh our screen after search
    @Override
    public void onRestart(){
        super.onRestart();
        Toast.makeText(this, "onRestart called!!", Toast.LENGTH_SHORT).show();
        //addMovieToData();
        //loadMovieFromData();
        MainActivity.this.finish();// finish Mainactivity
        startActivity(getIntent());//start mainactivity again!!!
    }


    public void newMovie(ArrayList<String> listData, String data){
        Toast.makeText(this,"Called newMovie method", Toast.LENGTH_SHORT).show();
        if(listData.size() == 0){
            listData.add(0,data);
            Toast.makeText(this,"List was empty", Toast.LENGTH_SHORT).show();
            return;
        }
        for (String i : listData){
            //Toast.makeText(this," " + i.toString() + " this val", Toast.LENGTH_SHORT).show();
            if(i.equals(data)){
                Toast.makeText(this,"usao u prvi if for loopa", Toast.LENGTH_SHORT).show();
                Toast.makeText(this,i, Toast.LENGTH_SHORT).show();
                listData.remove(i);//if name of the movie already exist remove it from its position
                listData.add(0,data);//add movie to top of list
                Toast.makeText(this,"Movie added to a list!!!", Toast.LENGTH_SHORT).show();

            } else if (!i.equals(data) && listData.size() < 10){
                Toast.makeText(this,"usao u drugi if for loopa", Toast.LENGTH_SHORT).show();
                listData.add(0, data);//if doesn't exist and list isn't over 10 elements add to top
                Toast.makeText(this,"Movie added to a list!!!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this,"zadnji else for loopa", Toast.LENGTH_SHORT).show();
                listData.remove(9);//list is over 10 elements remove last
                listData.add(0, data);//add element to top
                Toast.makeText(this,"Movie added to a list!!!", Toast.LENGTH_SHORT).show();

            }
        }

    }


    //add new list to be saved to data
    public void addMovieToData(){

        SharedPreferences pref = getSharedPreferences("data sp file", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        Gson gson = new Gson();
        //edit.remove(key).apply(); //if the key represent we can try to remove previous array !!!
        String json = gson.toJson(searched);//convert listData to json
        edit.putString("movies list", json);//in editor save json file with correct key val
        Toast.makeText(this,"Movie saved to data!!!", Toast.LENGTH_SHORT).show();
        edit.apply();// apply changes
    }


    //load data to array for usage
    public void loadMovieFromData(){
        SharedPreferences pref = getSharedPreferences("data sp file", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("movies list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        searched = gson.fromJson(json, type);

        if(searched == null){
            searched = new ArrayList<>();
        }
        Toast.makeText(this,"Movie loaded", Toast.LENGTH_SHORT).show();
        //return gson.fromJson(json, type);
    }


    //@Override
    public void onDestroy(){
        super.onDestroy();
        addMovieToData();
        loadMovieFromData();
        Toast.makeText(this,"onDestroy called!!!!!", Toast.LENGTH_SHORT).show();
        //finish();
    }

}