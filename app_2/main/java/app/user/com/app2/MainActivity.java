package app.user.com.app2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String data="",status;

    Button button_accept,button_decline;

    SharedPreferences sharedPreferences_status;
    String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        button_accept=(Button)findViewById(R.id.accept_call);
        button_decline=(Button)findViewById(R.id.decline_call);


        //can access to all other apps
        sharedPreferences_status = getSharedPreferences(
                "calling_status", Context.MODE_WORLD_READABLE);

        result=fetch_messgae();

        //Toast.makeText(getApplicationContext(),sharedPreferences_status.getString("calling_string","").toString(),Toast.LENGTH_LONG).show();

        if(fetch_messgae().equals("error"))
            Toast.makeText(getApplicationContext(),"Package error! No requests from the app!",Toast.LENGTH_LONG).show();
        else if(fetch_messgae().length()==0)
            Toast.makeText(getApplicationContext(),"No calls!",Toast.LENGTH_LONG).show();
            else
            Toast.makeText(getApplicationContext(),"Calling from another app\nMessage: "+fetch_messgae(),Toast.LENGTH_LONG).show();


        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fetch_messgae().equals("error")||fetch_messgae().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"No calls",Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferences.Editor editor=sharedPreferences_status.edit();
                editor.putString("calling_string","Call Accepted!\nMessage: "+fetch_messgae());
                editor.commit();

                Toast.makeText(getApplicationContext(),"Call Accepted!",Toast.LENGTH_LONG).show();
            }
        });

        button_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fetch_messgae().equals("error")||fetch_messgae().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"No calls",Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferences.Editor editor=sharedPreferences_status.edit();
                editor.putString("calling_string","Call Declined!\nMessage: "+fetch_messgae());
                editor.commit();
                Toast.makeText(getApplicationContext(),"Call Declined!",Toast.LENGTH_LONG).show();
            }
        });



    }

    public String fetch_messgae()
    {
        try {
            Context con = createPackageContext("app1.user.com.app1", 0);//first app package name is "com.sharedpref1"
            SharedPreferences pref = con.getSharedPreferences(
                    "calling_pref", Context.MODE_PRIVATE);
            data = pref.getString("calling", "No Value");


        }
        catch (PackageManager.NameNotFoundException e) {
            Log.e("Package Error", e.toString());

            data="error";

        }

        return data;
    }


}
