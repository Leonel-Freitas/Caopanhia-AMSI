package com.example.caopanhia.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caopanhia.R;
import com.example.caopanhia.modelo.Cao;

import java.util.ArrayList;

public class ListaCaesAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Cao> caes;

    public ListaCaesAdapter(Context context, ArrayList<Cao> caes) {
        this.context = context;
        this.caes = caes;
    }

    @Override
    public int getCount() {
        return caes.size();
    }

    @Override
    public Object getItem(int i) {
        return caes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return caes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null){
            view = inflater.inflate(R.layout.item_lista_caes, null);
        }


        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null){
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(caes.get(i));

        return view;
    }

    private class ViewHolderLista{
        private TextView tvNome, tvAnoNascimento;

        public ViewHolderLista(View view){
            tvNome=view.findViewById(R.id.tvNomeCao);
            tvAnoNascimento=view.findViewById(R.id.tvAnoNascimento);
        }

        public void update(Cao cao){
            tvNome.setText(cao.getNome());
            tvAnoNascimento.setText(cao.getAnoNascimento()+"");
        }
    }
}
