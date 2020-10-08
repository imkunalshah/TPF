package com.tpf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_headlines;

    List<HeadlineModel> list;
    HeadlineAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_headlines = findViewById(R.id.rv_headlines);

        rv_headlines.setLayoutManager(new LinearLayoutManager(this));

        list =new ArrayList<>();

        rv_headlines.addItemDecoration(new DividerItemDecoration(rv_headlines.getContext(), DividerItemDecoration.VERTICAL));


        loadHeadlines();
    }

    public void loadHeadlines(){
        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=1733c86866864bf3b611ed72f4525745";

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("ok")){
                        progressDialog.dismiss();

                        JSONArray appointments = jsonObject.getJSONArray("articles");

                        for (int i = 1;i <= appointments.length();i++){
                            JSONObject obj = appointments.getJSONObject(i-1);

                            JSONObject source = obj.optJSONObject("source");
                            HeadlineModel model = new HeadlineModel();

                            model.setName(source.getString("name"));
                            model.setAuthor(obj.getString("author"));
                            model.setContent(obj.getString("content"));
                            model.setDescription(obj.getString("description"));
                            model.setPublishedAt(obj.getString("publishedAt"));
                            model.setTitle(obj.getString("title"));
                            model.setUrlToImage(obj.getString("urlToImage"));
                            model.setUrl(obj.getString("url"));

                            list.add(model);
                        }
                        adapter = new HeadlineAdapter(MainActivity.this,list);
                        rv_headlines.setAdapter(adapter);


                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"Error ! Could not fetch data",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.i("error",e.getMessage());
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonArrayRequest);
    }
}