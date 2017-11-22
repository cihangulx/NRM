package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import neonyazilim.com.nrm.Models.Proje;
import neonyazilim.com.nrm.R;

/**
 * Created by tuzlabim on 22.11.2017.
 */

public class ProjeListAdapter extends BaseAdapter {


    Activity activity;
    List<Proje> projeList;
    LayoutInflater layoutInflater;


    public ProjeListAdapter(Activity activity, List<Proje> projeList) {
        this.activity = activity;
        this.projeList = projeList;
        layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return projeList.size();
    }

    @Override
    public Object getItem(int position) {
        return projeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getProjeId(int position ){
        return projeList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.departman_item_view,parent,false);

        TextView baslik = view.findViewById(R.id.baslik);


        baslik.setText(projeList.get(position).getBaslik());



        return view;
    }
}
