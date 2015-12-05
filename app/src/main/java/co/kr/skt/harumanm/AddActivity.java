package co.kr.skt.harumanm;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by T on 2015-12-04.
 */
public class AddActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    boolean mInitSpinner;
    ArrayAdapter<CharSequence> townspin;
    ArrayAdapter<CharSequence> boroughspin;
    ArrayAdapter<CharSequence> timespin;
    ArrayAdapter<CharSequence> typespin;
    float backup;

    Button btnInsert, btnMonthUp, btnMonthDown, btnDayUp, btnDayDown;
    CardView cardInsert, cardMonthUp, cardMonthDown, cardDayUp, cardDayDown;

    Spinner spinnerTown, spinnerBorough, spinnerTime, spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add);

        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnMonthUp = (Button) findViewById(R.id.btn_monthup);
        btnMonthDown = (Button) findViewById(R.id.btn_monthdown);
        btnDayUp = (Button) findViewById(R.id.btn_dayup);
        btnDayDown = (Button) findViewById(R.id.btn_daydown);

        cardInsert = (CardView) findViewById(R.id.card_insert);
        cardMonthUp = (CardView) findViewById(R.id.card_monthup);
        cardMonthDown = (CardView) findViewById(R.id.card_monthdown);
        cardDayUp = (CardView) findViewById(R.id.card_dayup);
        cardDayDown = (CardView) findViewById(R.id.card_daydown);

        spinnerTown = (Spinner) findViewById(R.id.spinner_town);
        spinnerBorough = (Spinner) findViewById(R.id.spinner_borough);
        spinnerTime = (Spinner)findViewById(R.id.spinner_time);
        spinnerType = (Spinner)findViewById(R.id.spinner_type);

        btnInsert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                if (action == event.ACTION_DOWN) {
                    backup = cardInsert.getCardElevation();
                    cardInsert.setCardElevation(0.0f);
                } else if (action == event.ACTION_UP) {
                    cardInsert.setCardElevation(backup);
                }
                return true;
            }
        });

        btnMonthUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                if (action == event.ACTION_DOWN) {
                    backup = cardMonthUp.getCardElevation();
                    cardMonthUp.setCardElevation(0.0f);
                } else if (action == event.ACTION_UP) {
                    cardMonthUp.setCardElevation(backup);
                }
                return true;
            }
        });

        btnMonthDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                if (action == event.ACTION_DOWN) {
                    backup = cardMonthDown.getCardElevation();
                    cardMonthDown.setCardElevation(0.0f);
                } else if (action == event.ACTION_UP) {
                    cardMonthDown.setCardElevation(backup);
                }
                return true;
            }
        });

        btnDayUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                if (action == event.ACTION_DOWN) {
                    backup = cardDayUp.getCardElevation();
                    cardDayUp.setCardElevation(0.0f);
                } else if (action == event.ACTION_UP) {
                    cardDayUp.setCardElevation(backup);
                }
                return true;
            }
        });

        btnDayDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                if (action == event.ACTION_DOWN) {
                    backup = cardDayDown.getCardElevation();
                    cardDayDown.setCardElevation(0.0f);
                } else if (action == event.ACTION_UP) {
                    cardDayDown.setCardElevation(backup);
                }
                return true;
            }
        });
        townspin = ArrayAdapter.createFromResource(this, R.array.spinner_town, R.layout.support_simple_spinner_dropdown_item);
        townspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        timespin = ArrayAdapter.createFromResource(this, R.array.spinner_time, R.layout.support_simple_spinner_dropdown_item);
        timespin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typespin = ArrayAdapter.createFromResource(this, R.array.spinner_type, R.layout.support_simple_spinner_dropdown_item);
        typespin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerTown.setAdapter(townspin);
        spinnerTime.setAdapter(timespin);
        spinnerType.setAdapter(typespin);

        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mInitSpinner == false){
                    mInitSpinner = true;
                    return;
                }
                Toast.makeText(AddActivity.this, timespin.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mInitSpinner == false){
                    mInitSpinner = true;
                    return;
                }
                Toast.makeText(AddActivity.this, typespin.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mInitSpinner == false) {
                    mInitSpinner = true;
                    return;
                }
                Toast.makeText(AddActivity.this, townspin.getItem(position), Toast.LENGTH_SHORT).show();

                if (townspin.getItem(position).equals("서울")) {
                    boroughspin = ArrayAdapter.createFromResource(AddActivity.this, R.array.spinner_borough_seoul, R.layout.support_simple_spinner_dropdown_item);
                    spinnerBorough.setAdapter(boroughspin);

                    spinnerBorough.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (mInitSpinner == false) {
                                mInitSpinner = true;
                                return;
                            }
                            Toast.makeText(AddActivity.this, boroughspin.getItem(position), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (townspin.getItem(position).equals("경기도")) {
                    boroughspin = ArrayAdapter.createFromResource(AddActivity.this, R.array.spinner_borough_gyungki, R.layout.support_simple_spinner_dropdown_item);
                    spinnerBorough.setAdapter(boroughspin);

                    spinnerBorough.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (mInitSpinner == false) {
                                mInitSpinner = true;
                                return;
                            }
                            Toast.makeText(AddActivity.this, boroughspin.getItem(position), Toast.LENGTH_SHORT).show();
                        }



                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
