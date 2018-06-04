package manpergut.us.crashapp3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button c1;
    private Button c2;
    private Button v1;
    private Button v2;
    private Button crash;

    private TextView texto;

    private int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c1 = findViewById(R.id.button1);
        c2 = findViewById(R.id.button2);
        v1 = findViewById(R.id.button3);
        v2 = findViewById(R.id.button4);
        crash = findViewById(R.id.button5);

        texto = findViewById(R.id.text);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;n<1000;n++){
                    n++;
                }
                texto.setText(n+"");
            }
        });

        c2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                for(int i=0;n<50000;n++){
                    n++;
                }
                texto.setText(n+"");
            }
        });

        v1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent nova = new Intent(MainActivity.this, SecondActivity.class);
                MainActivity.this.startActivity(nova);
            }
        });

        v2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent nova = new Intent(MainActivity.this, SecondActivity2.class);
                MainActivity.this.startActivity(nova);
            }
        });

        crash.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent nova = new Intent(MainActivity.this, ThirdActivity.class);
                MainActivity.this.startActivity(nova);
            }
        });

    }
}
