package manpergut.us.crashapp3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by manuel on 23/03/18.
 */

public class SecondActivity extends AppCompatActivity{

    private Button c1;
    private Button c2;

    private TextView t;

    private int n;

    public void onCreate(Bundle o){
        super.onCreate(o);
        setContentView(R.layout.second_activity);

        c1 = findViewById(R.id.button11);
        c2 = findViewById(R.id.button12);

        t = findViewById(R.id.textov2);

        c1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                t.setText("20000 ciclos");
                for(int i = 0; i<20000;i++) {
                    n++;
                }
            }
        });

        c2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                t.setText("200000 ciclos");
                for(int i = 0; i<120000; i++){
                    n++;
                }
            }
        });

    }

}
