package co.kr.skt.harumanm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    float backup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CardView cardView1 = (CardView) findViewById(R.id.cardview1);
        final CardView cardView2 = (CardView) findViewById(R.id.cardview2);

        final Button btnAdd = (Button) findViewById(R.id.btn_add);
        final Button btnMylist = (Button) findViewById(R.id.btn_mylist);


        btnAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                if (action == event.ACTION_DOWN) {
                    backup = cardView1.getCardElevation();
                    cardView1.setCardElevation(0.0f);
                } else if (action == event.ACTION_UP) {
                    cardView1.setCardElevation(backup);
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        btnMylist.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                if (action == event.ACTION_DOWN) {
                    backup = cardView2.getCardElevation();
                    cardView2.setCardElevation(0.0f);
                } else if(action == event.ACTION_UP){
                    cardView2.setCardElevation(backup);
                }
                return true;
            }
        });
    }
}
