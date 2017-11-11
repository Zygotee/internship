package com.example.difka.internship;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GetJson extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ListView listView;
    private static String url = "http://qiscusinterview.herokuapp.com/products";

    ArrayList<HashMap<String, String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_json);

        arrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);
        new GetJSON().execute();
    }

    private class GetJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(GetJson.this);
            progressDialog.setMessage("Tunggu");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();
            String data = httpHandler.Request(url);

            if (data != null) {
                try {

                    JSONArray coba = new JSONArray(data);
                    for (int i = 0; i < coba.length(); i++){
                        JSONObject vian = coba.getJSONObject(i);
                        String id = vian.getString("id");
                        String name = vian.getString("name");
                        String price = vian.getString("price");
                        String desc = vian.getString("description");

                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put("id", id);
                        hashMap.put("name", name);
                        hashMap.put("price", "Harga : "+price);
                        hashMap.put("desc", "Deskripsi : "+desc);

                        arrayList.add(hashMap);
                    }

                } catch (final JSONException e) {
                    Log.e(String.valueOf(GetJson.class), "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(String.valueOf(GetJson.class), "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (progressDialog.isShowing())
                progressDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    GetJson.this, arrayList,
                    R.layout.list_item, new String[]{"name", "price",
                    "desc"}, new int[]{R.id.name,
                    R.id.price, R.id.desc});

            listView.setAdapter(adapter);
        }

    }
}
