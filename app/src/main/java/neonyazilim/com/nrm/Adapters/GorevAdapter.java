package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import neonyazilim.com.nrm.Activitys.GorevDetay;
import neonyazilim.com.nrm.Activitys.ProjeDetay;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.gorev_item_view, parent, false);

        LinearLayout linearLayout = view.findViewById(R.id.linear_root);

        TextView baslik = view.findViewById(R.id.baslik);
        TextView durum = view.findViewById(R.id.durum);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GorevDetay.class);
                Gson gson = new Gson();
                String gorevString = gson.toJson(gorevList.get(position));
                intent.putExtra("gorev", gorevString);
                activity.startActivity(intent);
            }
        });


        baslik.setText("" + gorevList.get(position).getBaslik());
        if (gorevList.get(position).getProgress() > 99) {
            durum.setText("Tamamlandı");
            durum.setTextColor(Color.GREEN);
        }else {
            durum.setText("Devam Ediyor");
            durum.setTextColor(Color.RED);
        }

        return view;
    }
}
