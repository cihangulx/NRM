package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import neonyazilim.com.nrm.Models.Islem;
import neonyazilim.com.nrm.R;

/**
 * Created by tuzlabim on 13.11.2017.
 */

public class IslemAdapter extends BaseAdapter {

    Activity activity;
    List<Islem> islemList;
    LayoutInflater layoutInflater;


    public IslemAdapter(Activity activity, List<Islem> islemList) {
        this.activity = activity;
        this.islemList = islemList;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return islemList.size();
    }

    @Override
    public Object getItem(int position) {
        return islemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.islem_item_view, parent, false);

        TextView islem_oncesi = view.findViewById(R.id.islem_oncesi);
        TextView islem_sonrasi = (TextView) view.findViewById(R.id.islem_sonrasi);
        ImageView icon = view.findViewById(R.id.icon);
        TextView tarih = view.findViewById(R.id.tarih);

        final Islem islem = islemList.get(position);

        islem_oncesi.setText(islem.getIslemOncesiDurum());
        islem_sonrasi.setText(islem.getIslem());

        if (islem.getIslem().equals("Reddedildi")) {

            icon.setImageResource(R.drawable.ic_arrow_forward_red_24dp);
            islem_sonrasi.setTextColor(Color.RED);
        } else if (islem.getIslem().equals("İşlemde")) {
            icon.setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        } else if (islem.getIslem().equals("Sonuçlandı")) {
            icon.setImageResource(R.drawable.ic_arrow_forward_green_24dp);
            islem_sonrasi.setTextColor(Color.parseColor("#4CAF50"));
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(islem.getIslemTarihi());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour =calendar.get(Calendar.HOUR);
        int minute =calendar.get(Calendar.MINUTE);


        Date date = new Date();
        Calendar calendar1 = new GregorianCalendar();
        calendar.setTime(date);

        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);


        if (day1!=day){
            tarih.setText("" + day + "/" + (month) + "/" + year);
        }else {
            tarih.setText(hour+":"+minute);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(islem.getIslemAciklamasi());
            }
        });


        return view;
    }

    private void showDialog(String aciklama){
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("İşlem Açıklaması");
        dialog.setMessage(aciklama);
        dialog.setCancelable(true);
        dialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.create().show();

    }

}
