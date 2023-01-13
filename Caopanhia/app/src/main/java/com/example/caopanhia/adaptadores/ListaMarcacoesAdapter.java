package com.example.caopanhia.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caopanhia.R;
import com.example.caopanhia.modelo.Cao;
import com.example.caopanhia.modelo.MarcacaoVeterinaria;

import java.util.ArrayList;

public class ListaMarcacoesAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MarcacaoVeterinaria> marcacoes;

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
    public long getItemId(int i) {return marcacoes.get(i).getId();}

    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null){
            view = inflater.inflate(R.layout.item_lista_caes, null);
        }


        ListaMarcacoesAdapter.ViewHolderLista viewHolder = (ListaMarcacoesAdapter.ViewHolderLista) view.getTag();
        if(viewHolder == null){
            viewHolder = new ListaMarcacoesAdapter.ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(marcacoes.get(i));

        return view;
    }

    private class ViewHolderLista {
        private TextView tvNome, tvAnoNascimento;

        public ViewHolderLista(View view) {
            tvNome = view.findViewById(R.id.tvNomeCao);
            tvAnoNascimento = view.findViewById(R.id.tvAnoNascimento);
        }

        public void update(MarcacaoVeterinaria marcacaoVeterinaria) {
            tvNome.setText(marcacaoVeterinaria.getData() + " " + marcacaoVeterinaria.getHora());
            tvAnoNascimento.setText(marcacaoVeterinaria.getCao() + "");
        }
    }
}
