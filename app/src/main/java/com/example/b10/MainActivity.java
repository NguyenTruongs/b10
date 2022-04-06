package com.example.b10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.b10.adapter.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String JSON_URL_ANIMAL = "http://192.168.43.118:81/Android_LOGIN_REGISTER/Animal_Content.php";

    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Animal> listAnimal;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listAnimal = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewId);
        jsonrequest();
    }

    // Json request : yêu cầu json
    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL_ANIMAL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        Animal animal = new Animal();

                        animal.setCreature_name(jsonObject.getString("creature_name"));
                        animal.setPrice(jsonObject.getString("price"));
                        animal.setContent(jsonObject.getString("content"));
                        animal.setImage(jsonObject.getString("image"));
                        listAnimal.add(animal);
                        Log.d("VVV",jsonObject.getString("creature_name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setupRecyclerview(listAnimal);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    // set up recyclerview : thiết lập chế độ xem lại
    private void setupRecyclerview(List<Animal> listAnimal) {
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,listAnimal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(myadapter);
    }


}