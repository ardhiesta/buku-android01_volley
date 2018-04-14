package io.github.ardhiesta.buku_android01_volley;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Product extends BaseAdapter {

    private Activity activity;
    private ArrayList<Product> arraylist_data = new ArrayList<Product>();

    private static LayoutInflater inflater = null;
    public Adapter_Product(Activity a, ArrayList<Product> d){
        activity = a; arraylist_data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return arraylist_data.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist_data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_product, null);

        TextView textViewnama = (TextView)vi.findViewById(R.id.textViewnama); // title

        Product product = arraylist_data.get(position);
        textViewnama.setText(product.getNama());
        return vi;
    }
}
