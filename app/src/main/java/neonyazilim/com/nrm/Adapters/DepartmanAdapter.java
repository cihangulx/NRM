package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.R;

/**
 * Created by tuzlabim on 30.10.2017.
 */

public class DepartmanAdapter extends BaseAdapter {

    Activity activity;
    List<Departman> departmanList;
    LayoutInflater layoutInflater;

    public DepartmanAdapter(Activity activity, List<Departman> departmanList) {
        this.activity = activity;
        this.departmanList = departmanList;
        layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return departmanList.size();
    }

    @Override
    public Object getItem(int position) {
        return departmanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view =layoutInflater.inflate(R.layout.departman_item_view,parent,false);

        TextView baslik = view.findViewById(R.id.baslik);
        baslik.setText(departmanList.get(position).getBaslik());

        return view;
    }
}
