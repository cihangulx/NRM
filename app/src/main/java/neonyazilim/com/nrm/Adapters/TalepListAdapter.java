package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import neonyazilim.com.nrm.Activitys.TalepDetay;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.R;

/**
 * Created by cihan on 10.11.2017.
 */

public class TalepListAdapter extends BaseAdapter {

    Activity activity;
    List<Talep> talepList;
    LayoutInflater layoutInflater;


    public TalepListAdapter(Activity activity, List<Talep> talepList) {
        this.activity = activity;
        this.talepList = talepList;
        layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return talepList.size();
    }

    @Override
    public Object getItem(int position) {
        return talepList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.talep_list_item,parent,false);

        TextView baslik = view.findViewById(R.id.baslik);
        TextView durum = view.findViewById(R.id.durum);
        TextView tarih =view.findViewById(R.id.tarih);
        TextView gonderen =view.findViewById(R.id.gonderen);
        baslik.setText(talepList.get(position).getBaslik());
        durum.setText(talepList.get(position).getDurum());

        final Talep talep = talepList.get(position);
        if (talep.getDurum().equals("İşlemde")){
            durum.setTextColor(Color.parseColor("#fc8c44"));

        }else if (talep.getDurum().equals("Sonuçlandı")){
            durum.setTextColor(Color.GREEN);
        }else if (talep.getDurum().equals("Reddedildi")){
            durum.setTextColor(Color.RED);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,TalepDetay.class);
                intent.putExtra("id",talep.getId());
                intent.putExtra("baslik",talep.getBaslik());
                intent.putExtra("aciklama",talep.getAciklama());
                intent.putExtra("gonderen",talep.getGonderen());
                intent.putExtra("alici",talep.getAlici());
                intent.putExtra("tarih",talep.getTarih());
                activity.startActivity(intent);
            }
        });


        //gonderen.setText(talepList.get(position).getGonderen());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(talepList.get(position).getTarih());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tarih.setText("" + day + "/" + (month) + "/" + year);


        return view;
    }
}
