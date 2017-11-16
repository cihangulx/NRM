package neonyazilim.com.nrm.Fragments.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import neonyazilim.com.nrm.R;

/**
 * Created by tuzlabim on 16.11.2017.
 */

public class GelenTalepler extends Fragment {

    ListView talep_listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gelen_talepler,container,false);


        talep_listview=view.findViewById(R.id.gelen_talepler_listview);

        return  view;

    }
}
