package com.example.caopanhia.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caopanhia.R;
import com.example.caopanhia.modelo.MarcacaoVeterinaria;

import java.util.ArrayList;

public class ListaMarcacoesAdapter extends BaseAdapter {
    private final Context context;
    private LayoutInflater inflater;
    private final ArrayList<MarcacaoVeterinaria> marcacoes;

    public ListaMarcacoesAdapter(Context context, ArrayList<MarcacaoVeterinaria> marcacoes) {
        this.context = context;
        this.marcacoes = marcacoes;
    }

    @Override
    public int getCount() {
        return marcacoes.size();
    }

    @Override
    public Object getItem(int i) {
        return marcacoes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return marcacoes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null){
            view = inflater.inflate(R.layout.item_lista_marcacoes, null);
        }


        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null){
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(marcacoes.get(i));

        return view;
    }


    private class ViewHolderLista{
        private final TextView tvNomeCao;
        private final TextView tvData;
        private final TextView tvHora;

        public ViewHolderLista(View view){
            tvNomeCao=view.findViewById(R.id.tvNomeCao);
            tvData=view.findViewById(R.id.tvData);
            tvHora=view.findViewById(R.id.tvHora);
        }

        public void update(MarcacaoVeterinaria marcacao){
            tvNomeCao.setText(marcacao.getNomeCao());
            tvData.setText(marcacao.getData());
            tvHora.setText(marcacao.getHora());
        }
    }
}
