package neonyazilim.com.nrm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.List;

import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Network.Db;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    TextView code,message;
    DonutProgress donut_progress;
    int progress=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        message=findViewById(R.id.message);
        code=findViewById(R.id.code);
        donut_progress=(DonutProgress)findViewById(R.id.donut_progress);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               progress+=10;
                donut_progress.setProgress(progress);
            }
        });
    }

    private void getDepartman(){
        Call<List<Departman>> call = Db.getConnect().getDepartman();
        call.enqueue(new Callback<List<Departman>>() {
            @Override
            public void onResponse(Call<List<Departman>> call, Response<List<Departman>> response) {
                code.setText("Kod:"+response.code());
                if (response.code()==200){
                    for (int i =0;i<response.body().size();i++){
                        message.append(response.body().get(i).getId()+"\n"+
                                response.body().get(i).getBaslik()+"\n"+
                                response.body().get(i).getAciklama()+"\n"
                        );
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Departman>> call, Throwable t) {
                message.setText(t.getMessage());
                code.setText("Kod:404");
            }
        });
    }

    private void getOneDepartman(){
        
    }
}
