package io.github.ardhiesta.buku_android01_volley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import io.github.ardhiesta.buku_android01_volley.config.Config;
import io.github.ardhiesta.buku_android01_volley.controller.AppController;

public class AddData extends AppCompatActivity {
    String TAG = AddData.class.getSimpleName();
    String id = "";
    String action = ""; //add, edit, delete

    EditText editTextNama;
    EditText editTextHarga;
    EditText editTextDeskripsi;

    Button buttonAdd;
    Button buttonEdit;
    Button buttonDelete;

    Intent intent;
    Bundle bundle;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddata);

        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextHarga = (EditText) findViewById(R.id.editTextHarga);
        editTextDeskripsi = (EditText) findViewById(R.id.editTextDeskripsi);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        intent = getIntent();
        bundle = intent.getExtras();

        if(bundle != null){
            id = bundle.getString("id");
            editTextNama.setText(bundle.getString("nama"));
            editTextHarga.setText(bundle.getString("harga"));
            editTextDeskripsi.setText(bundle.getString("deskripsi"));
        }else{
            buttonEdit.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.GONE);
        }

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "add";
                kirimdata();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "edit";
                kirimdata();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "delete";
                kirimdata();
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

    private void kirimdata(){
        showProgressDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, Config.senddata, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();
                Toast.makeText(AddData.this, response, Toast.LENGTH_SHORT).show();
                editTextNama.setText(null);
                editTextHarga.setText(null);
                editTextDeskripsi.setText(null);
                if(action=="delete"){
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("nama", String.valueOf(editTextNama.getText()));
                params.put("harga", String.valueOf(editTextHarga.getText()));
                params.put("deskripsi", String.valueOf(editTextDeskripsi.getText()));
                params.put("action", action);

                return params;
            }
        };
        AppController.getInstance(getApplicationContext()).addToRequestQueue(strReq);
    }
}
