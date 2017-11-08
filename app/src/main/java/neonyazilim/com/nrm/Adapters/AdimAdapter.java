package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import neonyazilim.com.nrm.Models.Adim;
import neonyazilim.com.nrm.R;

/**
 * Created by cihan on 3.11.2017.
 */

public class AdimAdapter extends BaseAdapter{

Activity activity;
    List<Adim> adimList;
    LayoutInflater layoutInflater;

    public AdimAdapter(Activity activity, List adimList) {
        this.activity = activity;
        this.adimList = adimList;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return adimList.size();
    }

    @Override
    public Object getItem(int position) {
        return adimList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.adim_item_view,parent,false);

        TextView baslik =(TextView)view.findViewById(R.id.adim_baslik);
        CheckBox bitti =(CheckBox) view.findViewById(R.id.adim_bitti);

        Adim adim = adimList.get(position);

        bitti.setChecked(adim.isBitti());

        if (position %2==0){
            view.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }

        baslik.setText(adim.getBaslik());
        bitti.setChecked(adim.isBitti());

        return view;
    }
}
