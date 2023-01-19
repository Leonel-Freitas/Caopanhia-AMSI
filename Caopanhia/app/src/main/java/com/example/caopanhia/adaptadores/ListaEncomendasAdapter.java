package com.example.caopanhia.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caopanhia.R;
import com.example.caopanhia.modelo.Encomenda;
import com.example.caopanhia.modelo.MarcacaoVeterinaria;

import java.util.ArrayList;

public class ListaEncomendasAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater inflater;
    private final ArrayList<Encomenda> encomendas;

    public ListaEncomendasAdapter(Context context, ArrayList<Encomenda> encomendas) {
        this.context = context;
        this.encomendas = encomendas;
    }

    @Override
    public int getCount() {
        return encomendas.size();
    }

    @Override
    public Object getItem(int i) {
        return encomendas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return encomendas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null){
            view = inflater.inflate(R.layout.item_lista_encomendas, null);
        }


        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null){
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(encomendas.get(i));

        return view;
    }

    //TODO UPDATE

    private class ViewHolderLista{
        private final TextView tvNumeroEncomenda;
        private final TextView tvData;

        public ViewHolderLista(View view){
            tvNumeroEncomenda=view.findViewById(R.id.tvNumeroEncomenda);
            tvData=view.findViewById(R.id.tvData);
        }

        public void update(Encomenda encomenda){
            tvNumeroEncomenda.setText("#"+encomenda.getId());
            tvData.setText(encomenda.getData());
        }
    }
}
