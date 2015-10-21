package br.com.targettrust.exercicio05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jackson on 21/10/2015.
 */
public class ListViewAdapter  extends BaseAdapter {

    //Itens de exibição / filtrados
    private List<Map<String,String>> itens_exibicao;
    //Essa lista contem todos os itens.
    private List<Map<String,String>>itens;
    //Utilizado no getView para carregar e construir um item.
    private LayoutInflater layoutInflater;

    public ListViewAdapter(Context context, List<Map<String,String>> itens) {
        this.itens = itens;
        this.itens_exibicao = itens;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens_exibicao.size();
    }

    @Override
    public Map<String,String> getItem(int arg0) {
        return itens_exibicao.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ItemHelper itemHelper = new ItemHelper();
        Map<String,String> objeto = itens_exibicao.get(arg0);

        if (arg1 == null) {
            arg1 = layoutInflater.inflate(R.layout.listview_item, null);
            itemHelper.descricao = (TextView) arg1.findViewById(R.id.textView1);
            itemHelper.valor = (TextView) arg1.findViewById(R.id.textView2);
            arg1.setTag(itemHelper);
        } else {
            itemHelper = (ItemHelper) arg1.getTag();
        }

        itemHelper.descricao.setText(objeto.get("descricao"));
        itemHelper.valor.setText(objeto.get("nome"));

        return arg1;
    }

    private class ItemHelper {

        TextView descricao, valor;
    }

    /** Método responsável pelo filtro. Utilizaremos em um EditText
     *
     * @return
     */
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected android.widget.Filter.FilterResults performFiltering(CharSequence filtro) {
                FilterResults results = new FilterResults();
                //se não foi realizado nenhum filtro insere todos os itens.
                if (filtro == null || filtro.length() == 0) {
                    results.count = itens.size();
                    results.values = itens;
                } else {
                    //cria um array para armazenar os objetos filtrados.
                    List<Map<String,String>> itens_filtrados = new ArrayList<Map<String,String>>();

                    //percorre toda lista verificando se contem a palavra do filtro na descricao do objeto.
                    for (int i = 0; i < itens.size(); i++) {
                        Map<String,String> data = itens.get(i);

                        filtro = filtro.toString().toLowerCase();
                        String condicao = data.get("nome").toLowerCase();

                        if (condicao.contains(filtro)) {
                            //se conter adiciona na lista de itens filtrados.
                            itens_filtrados.add(data);
                        }
                    }
                    // Define o resultado do filtro na variavel FilterResults
                    results.count = itens_filtrados.size();
                    results.values = itens_filtrados;
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                itens_exibicao = (List<Map<String,String>>) results.values; // Valores filtrados.
                notifyDataSetChanged();  // Notifica a lista de alteração
            }

        };
        return filter;
    }
}