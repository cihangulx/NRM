package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import neonyazilim.com.nrm.Models.Gorev;
import neonyazilim.com.nrm.R;

/**
 * Created by tuzlabim on 8.11.2017.
 */

public class GorevAdapter extends BaseAdapter {

    Activity activity;
    List<Gorev> gorevList;
    LayoutInflater layoutInflater;

    public GorevAdapter(Activity activity, List<Gorev> gorevList) {
        this.activity = activity;
        this.gorevList = gorevList;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return gorevList.size();
    }

    @Override
    public Object getItem(int position) {
        return gorevList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.gorev_item_view, parent, false);

        TextView baslik = view.findViewById(R.id.baslik);


        baslik.setText("" + gorevList.get(position).getBaslik());

        return view;
    }
}
