package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (getSharedPreferences("user", MODE_PRIVATE).getString("userId",null)!=null){
            S.userId=getSharedPreferences("user", MODE_PRIVATE).getString("userId",null);
            S.userToken=getSharedPreferences("user", MODE_PRIVATE).getString("token",null);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }, 3000);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }, 3000);
        }




    }
}
