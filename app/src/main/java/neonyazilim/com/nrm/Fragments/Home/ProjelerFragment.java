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

import java.util.ArrayList;
import java.util.List;

import neonyazilim.com.nrm.Adapters.ProjeAdapter;
import neonyazilim.com.nrm.Adapters.TalepAdapter;
import neonyazilim.com.nrm.Models.Proje;
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

public class ProjelerFragment extends Fragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.projeler_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        getProjeler();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    private void getProjeler() {
        Log.e("responseb","getProjeler();");
        RequestBody requestBody = new RequestBody();
        requestBody.setUserId(S.userId);
        requestBody.setToken(S.userToken);
        Call<List<Proje>> call = Db.getConnect().getProjeler(requestBody);
        call.enqueue(new Callback<List<Proje>>() {
            @Override
            public void onResponse(Call<List<Proje>> call, Response<List<Proje>> response) {
                if (response.code()==200){
                    if (response.body().size() > 0) {
                        //Projeler Alındı

                        List<Proje> projeList = response.body();

                        ProjeAdapter projeAdapter = new ProjeAdapter(getActivity(),response.body());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setAdapter(projeAdapter);

                        Log.e("responseb",response.body().get(0).getBaslik());

                    }
                }
                Log.e("responseb",""+response.code());
            }

            @Override
            public void onFailure(Call<List<Proje>> call, Throwable t) {
                Log.e("responseb",""+t.getMessage());
            }
        });
    }
}
