package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import neonyazilim.com.nrm.Models.Izin;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.ProjeIzin;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cihan on 21.11.2017.
 */

public class KullaniciIzinAdapter extends BaseAdapter {


    Activity activity;
    List<Kullanici> kullaniciList;
    LayoutInflater layoutInflater;

    public KullaniciIzinAdapter(Activity activity, List<Kullanici> kullaniciList) {
        this.activity = activity;
        this.kullaniciList = kullaniciList;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public String getKullaniciId(int position) {
        return kullaniciList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.kullanici_izin_view, parent, false);


        TextView isim = view.findViewById(R.id.isim);
        TextView unvan = view.findViewById(R.id.unvan);
        final TextView izin = view.findViewById(R.id.izin);

        final Kullanici kullanici = kullaniciList.get(position);


        RequestBody requestBody = new RequestBody();
        ProjeIzin projeIzin = new ProjeIzin();
        projeIzin.setUserId(kullanici.getId());
        requestBody.setProjeIzin(projeIzin);

        Call<List<ProjeIzin>> call = Db.getConnect().getProjeIzin(requestBody);
        call.enqueue(new Callback<List<ProjeIzin>>() {
            @Override
            public void onResponse(Call<List<ProjeIzin>> call, Response<List<ProjeIzin>> response) {
                if (response.code() == 200) {
                    izin.setText("" + response.body().size() + " Proje İzini\n");
                }
            }

            @Override
            public void onFailure(Call<List<ProjeIzin>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        isim.setText(kullanici.getIsim() + " " + kullanici.getSoyIsim());
        unvan.setText(kullanici.getUnvan());


        return view;
    }
}
