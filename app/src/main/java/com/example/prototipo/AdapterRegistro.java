package com.example.prototipo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterRegistro extends BaseAdapter {
    private final ArrayList<Registro> listaDeDados;

    public AdapterRegistro(ArrayList<Registro> listaDeDados) {
        this.listaDeDados = listaDeDados;
    }

    @Override
    public int getCount() {
        return listaDeDados.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDeDados.get(listaDeDados.size() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.registro, parent, false);
        }

        Registro item = (Registro) getItem(position);

        TextView itemTitle = convertView.findViewById(R.id.itemTitle);
        TextView itemDescription1 = convertView.findViewById(R.id.itemDescription1);
        TextView itemDescription2 = convertView.findViewById(R.id.itemDescription2);
        TextView itemDescription3 = convertView.findViewById(R.id.itemDescription3);

        String texto1 = "Estado > "+item.getPorta();
        String texto2 = "DATA : "+item.getData();
        String texto3 = "User :"+item.getUser_name();
        String texto4 = "ID User : "+item.getUser_id();
        itemTitle.setText(texto1);
        itemDescription1.setText(texto2);
        itemDescription2.setText(texto3);
        itemDescription3.setText(texto4);

        return convertView;
    }
}
