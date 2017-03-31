package app1.user.com.app1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button button,button_status;
    TextView textView;
    SharedPreferences prefs;
    String data="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.textview);
        button=(Button)findViewById(R.id.button);
        prefs = getSharedPreferences("calling_pref",
                Context.MODE_WORLD_READABLE);

        button_status=(Button)findViewById(R.id.call_status);



        //Toast.makeText(getApplicationContext(),prefs.getString("calling","").toString(),Toast.LENGTH_SHORT).show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text=textView.getText().toString();


                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("calling", text);
                editor.commit();

                Toast.makeText(getApplicationContext(),"Call initiated!",Toast.LENGTH_LONG).show();

            }
        });


        button_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(prefs.getString("calling","").length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please make a call before checking status!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(call_status().length()==0)
                    Toast.makeText(getApplicationContext(),"Wait for reply from another app!",Toast.LENGTH_LONG).show();
                else if(call_status().equals("error"))
                    Toast.makeText(getApplicationContext(),"No such package exists!please install the app",Toast.LENGTH_LONG).show();
                    else
                    Toast.makeText(getApplicationContext(),"Call Status: "+call_status(),Toast.LENGTH_LONG).show();


            }
        });




    }

    public String call_status()
    {
        try {
            Context context = createPackageContext("app.user.com.app2", 0);//first app package name is "com.sharedpref1"
            SharedPreferences sharedPreferences_status = context.getSharedPreferences(
                    "calling_status", Context.MODE_PRIVATE);
            data = sharedPreferences_status.getString("calling_string", "").toString();


        }
        catch (PackageManager.NameNotFoundException e) {
            Log.e("Package Error", e.toString());

            data="error";

        }

        return data;
    }
}
