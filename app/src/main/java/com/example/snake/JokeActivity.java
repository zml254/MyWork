package com.example.snake;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JokeActivity extends AppCompatActivity {

    private List<Item> itemList = new ArrayList<>();
    RecyclerView recyclerView;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.joke_toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ItemAdapter adapter = new ItemAdapter(itemList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        sendRequest();
    }

    private void sendRequest() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://api.apiopen.top/getJoke?page=2&count=20&type=video").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parse(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parse(String jsonData) {
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Item item = new Item(object.getString("name"),object.getString("text"),R.drawable.user);
                itemList.add(item);
            }
            Message message = new Message();
            handler.sendMessage(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
