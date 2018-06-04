package manpergut.us.crashapp3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by manuel on 23/03/18.
 */

public class ThirdActivity extends AppCompatActivity {

    public Button crash;

    public void onCreate(Bundle o){
        super.onCreate(o);
        setContentView(R.layout.third_activity);

        crash = findViewById(R.id.crash);

        crash.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                int k = 3/0;
            }
        });
    }

}