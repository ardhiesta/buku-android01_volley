package io.github.ardhiesta.buku_android01_volley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import io.github.ardhiesta.buku_android01_volley.config.Config;
import io.github.ardhiesta.buku_android01_volley.controller.AppController;

public class ViewData extends AppCompatActivity {
    private String TAG = ViewData.class.getSimpleName();
    ListView listview;
    ArrayList<Product> arraylist_data = new ArrayList<Product>();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdata);

        listview = (ListView) findViewById(R.id.listViewdata);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        getData();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewData.this, AddData.class);
                intent.putExtra("id", arraylist_data.get(position).getId());
                intent.putExtra("nama", arraylist_data.get(position).getNama());
                intent.putExtra("harga", arraylist_data.get(position).getHarga());
                intent.putExtra("deskripsi", arraylist_data.get(position).getDeskripsi());
                startActivity(intent);
                finish();
            }
        });
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void getData() {
//        showProgressDialog();
        StringRequest strReq = new StringRequest(Request.Method.GET, Config.getdata, new Response.Listener<String>() {
            @Override
            public void onResponse(String responses) {
//                hideProgressDialog();
                System.out.println("");
                try {
                    JSONObject response = new JSONObject(responses);
                    JSONObject header = response.getJSONObject("data");
                    Iterator<String> iterator = header.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        JSONObject item = (JSONObject) header.get(key);

                        Product product = new Product();
                        product.setId(item.getString("id"));
                        product.setNama(item.getString("nama"));
                        product.setDeskripsi(item.getString("deskripsi"));
                        product.setHarga(item.getString("harga"));
                        product.setCreated_at(item.getString("created_at"));
                        product.setUpdated_at(item.getString("updated_at"));
                        arraylist_data.add(product);
//                                Log.e(TAG, item.toString());
//                                Log.e(TAG, item.getString("nama"));
                    }
                    listview.setAdapter(new Adapter_Product(ViewData.this, arraylist_data));
//                            System.out.println(arraylist_data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("");
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance(getApplicationContext()).addToRequestQueue(strReq);
    }
}
