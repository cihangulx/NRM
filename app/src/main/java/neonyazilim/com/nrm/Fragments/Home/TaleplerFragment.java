package neonyazilim.com.nrm.Fragments.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import neonyazilim.com.nrm.Adapters.TalepAdapter;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cihan on 26.10.2017.
 */

public class TaleplerFragment extends Fragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.talepler_fragment, container, false);
        Log.e("talep","onCreateView");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e("talep","onViewCreated");
      //  setTalep();
        getTalep();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("talep","onCreate");
    }

    private void getTalep() {

        RequestBody requestBody = new RequestBody(S.userId,S.userToken);
        Call<List<Talep>> call = Db.getConnect().getTalep(requestBody);
        call.enqueue(new Callback<List<Talep>>() {
            @Override
            public void onResponse(Call<List<Talep>> call, Response<List<Talep>> response) {

                Log.d("TF.onResponse()",""+response.body().size()+"item listed.");
                if (response.code() == 200) {
                    Log.d("TF.onResponse()",""+response.body().size()+"item listed.");
                    // Veri alımı Başarılı

                    Gson gson = new Gson();

                 //  Log.e("talep",gson.toJson(response.body().get(0)));
                    Log.e("date",""+new Date().toString());

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                    TalepAdapter talepAdapter = new TalepAdapter(response.body(), getActivity());
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(talepAdapter);

                } else {
                    // veri alımı başarısız sayfa yok veya server kapalı
                }

            }

            @Override
            public void onFailure(Call<List<Talep>> call, Throwable t) {
                //Parse hatası veya liste boş.
                t.printStackTrace();
            }
        });
    }
}
