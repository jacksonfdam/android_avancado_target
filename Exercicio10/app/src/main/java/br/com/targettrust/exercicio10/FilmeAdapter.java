package br.com.targettrust.exercicio10;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FilmeAdapter extends CursorAdapter {
    private LayoutInflater inflater;

    private    static  class   ViewHolder  {
        int    nomeIndex;
        int    fotoIndex;
        TextView nome;
        ImageView foto;
    }

    public FilmeAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder  =   (ViewHolder)    view.getTag();
        holder.nome.setText(cursor.getString(holder.nomeIndex));

        Picasso.with(context).load(cursor.getString(holder.fotoIndex)).into(holder.foto);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =   inflater.inflate(R.layout.linha_filme, null);
        ViewHolder holder  =   new ViewHolder();
        holder.nome    =   (TextView)  view.findViewById(R.id.titulo);
        holder.foto    =   (ImageView)  view.findViewById(R.id.capa);
        holder.nomeIndex   =   cursor.getColumnIndexOrThrow(FilmeProvider.TITULO);
        holder.fotoIndex   =   cursor.getColumnIndexOrThrow(FilmeProvider.CAPA);
        view.setTag(holder);
        return view;
    }
}