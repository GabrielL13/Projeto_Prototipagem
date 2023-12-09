package com.example.prototipo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private final ArrayList<Fechadura> listaDeDados;

    public Adapter(ArrayList<Fechadura> listaDeDados) {
        this.listaDeDados = listaDeDados;
    }

    @Override
    public int getCount() {
        return listaDeDados.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDeDados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        }

        Fechadura item = (Fechadura) getItem(position);

        TextView itemTitle = convertView.findViewById(R.id.itemTitle);
        TextView itemDescription = convertView.findViewById(R.id.itemDescription);

        itemTitle.setText(item.getNome());
        itemDescription.setText(item.getId());

        return convertView;
    }
}
