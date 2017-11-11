package neonyazilim.com.nrm.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import neonyazilim.com.nrm.Activitys.TalepDetay;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.R;

/**
 * Created by cihan on 26.10.2017.
 */

public class TalepAdapter extends RecyclerView.Adapter<TalepViewHolder> {

    private List<Talep> talepList;
    private Activity activity;

    public TalepAdapter(List<Talep> talepList, Activity activity) {
        this.talepList = talepList;
        this.activity = activity;
    }

    @Override
    public TalepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.talep_card,parent,false);

        TalepViewHolder talepViewHolder =new TalepViewHolder(view);

        talepViewHolder.setIsRecyclable(false);

        return talepViewHolder;
    }

    @Override
    public void onBindViewHolder(TalepViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        final Talep talep = talepList.get(position);

        holder.talepBaslik.setText(talep.getBaslik());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(talep.getTarih());
        int year = calendar.get(Calendar.YEAR);
//Add one to month {0 - 11}
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

       holder.talepTarih.setText(""+day+"/"+month+"/"+year);
        holder.talepGonderenResim.setImageResource(R.drawable.ic_person_black_24dp);
        holder.talep_gonderen.setText(talep.getDepartman());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
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



    }

    @Override
    public int getItemCount() {
        return talepList.size();
    }
}
class TalepViewHolder extends RecyclerView.ViewHolder{

    ImageView talepGonderenResim;
    TextView talepBaslik,talepAciklama,talep_gonderen,talepTarih;

    public TalepViewHolder(View itemView) {
        super(itemView);

        talepBaslik=(TextView)itemView.findViewById(R.id.talep_baslik);
       //talepAciklama=(TextView)itemView.findViewById(R.id.talep_aciklama);
        talep_gonderen=(TextView)itemView.findViewById(R.id.talep_gonderen);
        talepTarih=(TextView)itemView.findViewById(R.id.talep_tarih);
        talepGonderenResim=(ImageView)itemView.findViewById(R.id.talep_resim);
    }
}