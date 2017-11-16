package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import neonyazilim.com.nrm.Activitys.ProjeDetay;
import neonyazilim.com.nrm.Models.Gorev;
import neonyazilim.com.nrm.Models.Proje;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cihan on 26.10.2017.
 */

public class ProjeAdapter extends RecyclerView.Adapter<ProjeViewHolder> {
    Activity activity;
    List<Proje> projeList;

    public ProjeAdapter(Activity activity, List<Proje> projeList) {
        this.activity = activity;
        this.projeList = projeList;
    }

    @Override
    public ProjeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.proje_card, parent, false);
        ProjeViewHolder projeViewHolder = new ProjeViewHolder(view);
        projeViewHolder.setIsRecyclable(false);
        return projeViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProjeViewHolder holder, int position) {
        final Proje proje = projeList.get(position);


        holder.proje_baslik.setText(""+proje.getBaslik());
        holder.proje_aciklama.setText(""+proje.getAciklama());
        String tarih = proje.getTarih().getDay()+"/"+proje.getTarih().getMonth()+"/"+proje.getTarih().getYear();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(proje.getTarih());
        int year = calendar.get(Calendar.YEAR);
//Add one to month {0 - 11}
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        holder.proje_tarih.setText(""+day+"/"+month+"/"+year);


        RequestBody requestBody = new RequestBody(proje.getId(), S.userToken);

        Call<List<Gorev>> call = Db.getConnect().getGorevler(requestBody);
        call.enqueue(new Callback<List<Gorev>>() {
            @Override
            public void onResponse(Call<List<Gorev>> call, Response<List<Gorev>> response) {
               Log.e("code:", "" + response.code());
                if (response.code() == 200) {
                    if (response.body().size() > 0) {
                        Log.e("res", response.body().get(0).getBaslik());

                        double bolum = 100f / response.body().size();
                        double carpan = getTamamlananGorevler(response.body());

                        float sonuc = new Float(bolum * carpan);

                        if (sonuc<30){
                            holder.progress.setTextColor(Color.RED);
                        }else if (sonuc<50){
                            holder.progress.setTextColor(Color.parseColor("#FF9800"));
                        }else if (sonuc<65){
                            holder.progress.setTextColor(Color.parseColor("#8BC34A"));
                        }else if (sonuc<80){
                            holder.progress.setTextColor(Color.parseColor("#7CB342"));
                        }else {
                            holder.progress.setTextColor(Color.parseColor("#4CAF50"));
                        }
                        holder.progress.setText("%"+Math.round(sonuc));

                    } else {
                        holder.progress.setText("%0.0");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Gorev>> call, Throwable t) {
                Log.e("fail", t.getMessage());
              holder.progress.setText("%0.0");
            }
        });







        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String projeString = gson.toJson(proje);
                Intent intent = new Intent(activity, ProjeDetay.class);
                intent.putExtra("proje", projeString);
                activity.startActivity(intent);

            }
        });
        holder.progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String projeString = gson.toJson(proje);
                Intent intent = new Intent(activity, ProjeDetay.class);
                intent.putExtra("proje", projeString);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projeList.size();
    }


    private int getTamamlananGorevler(List<Gorev> gorevList) {
        int tamamlanan = 0;
        for (int i = 0; i < gorevList.size(); i++) {
            if (gorevList.get(i).getProgress() > 99) {
                tamamlanan++;
            }
        }
        return tamamlanan;
    }

}

class ProjeViewHolder extends RecyclerView.ViewHolder {

    public Button progress;
    public TextView proje_baslik, proje_aciklama, proje_tarih;

    public ProjeViewHolder(View itemView) {
        super(itemView);

        progress = itemView.findViewById(R.id.bt_progress);
        proje_baslik = itemView.findViewById(R.id.proje_baslik);
        proje_aciklama = itemView.findViewById(R.id.proje_aciklama);
        proje_tarih = itemView.findViewById(R.id.proje_tarih);


    }
}
