package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import neonyazilim.com.nrm.Activitys.TalepDetay;
import neonyazilim.com.nrm.Activitys.TalepYonetimi;
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
      //  TextView gonderen =view.findViewById(R.id.gonderen);
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
                Gson gson = new Gson();
                String projeString = gson.toJson(talep);
                Intent intent = new Intent(activity, TalepDetay.class);
                intent.putExtra("talep", projeString);
                activity.startActivity(intent);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(activity,v);
                popupMenu.getMenuInflater().inflate(R.menu.talep_detay_bottom_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final TalepYonetimi talepYonetimi = (TalepYonetimi) activity;

                        switch (item.getItemId()) {
                            case R.id.action_sonuclandir:
                                talepYonetimi.showDialog(talep,"Sonuçlandır", "Sonuçlandı");
                                break;
                            case R.id.action_reddet:
                                talepYonetimi.showDialog(talep,"Reddet", "Reddedildi");
                                break;
                            case R.id.action_restore:
                                talepYonetimi.showDialog(talep,"Düzeltme İste", "Düzeltme İstendi");
                                break;
                            case R.id.action_isleme_al:
                                talepYonetimi.showDialog(talep,"İşleme Geri Al", "İşlemde");
                                break;
                            case R.id.action_delete:

                                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                                dialog.setMessage("Talebi Silmek İstediğinize Emin misiniz ?\nBu işlem geri alınamaz.");
                                dialog.setCancelable(false);
                                dialog.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        talepYonetimi.talebiSil(talep);
                                    }
                                });
                                dialog.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                dialog.create().show();
                                break;

                        }

                        return true;
                    }
                });

                popupMenu.show();
                return true;
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
