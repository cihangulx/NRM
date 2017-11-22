package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import neonyazilim.com.nrm.Activitys.IzinEkle;
import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.R;

/**
 * Created by tuzlabim on 1.11.2017.
 */

public class KullaniciAdapter extends BaseAdapter {

    Activity activity;
    List<Kullanici> kullaniciList;
    LayoutInflater layoutInflater;

    public KullaniciAdapter(Activity activity, List<Kullanici> kullaniciList) {
        this.activity = activity;
        this.kullaniciList = kullaniciList;
        layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return kullaniciList.size();
    }

    @Override
    public Object getItem(int position) {
        return kullaniciList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =layoutInflater.inflate(R.layout.departman_item_view,parent,false);

        LinearLayout linear_root = view.findViewById(R.id.linear_root);


        TextView baslik = view.findViewById(R.id.baslik);
        TextView unvan =view.findViewById(R.id.unvan);
        baslik.setText(kullaniciList.get(position).getIsim()+" "+kullaniciList.get(position).getSoyIsim());
        if (activity instanceof IzinEkle){
           // unvan.setText(kullaniciList.get(position).getUnvan());
        }else {
            unvan.setText(kullaniciList.get(position).getUnvan());
        }




        return view;
    }
}
