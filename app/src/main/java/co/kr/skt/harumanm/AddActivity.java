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
    ArrayAdapter<CharSequence> monthspin;
    ArrayAdapter<CharSequence> dayspin;
    float backup;

    Button btnInsert;
    CardView cardInsert;

    Spinner spinnerTown, spinnerBorough, spinnerTime, spinnerType, spinnerMonth, spinnerDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add);

        btnInsert = (Button) findViewById(R.id.btn_insert);
        cardInsert = (CardView) findViewById(R.id.card_insert);
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

        townspin = ArrayAdapter.createFromResource(this, R.array.spinner_town, R.layout.support_simple_spinner_dropdown_item);
        townspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        timespin = ArrayAdapter.createFromResource(this, R.array.spinner_time, R.layout.support_simple_spinner_dropdown_item);
        timespin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typespin = ArrayAdapter.createFromResource(this, R.array.spinner_type, R.layout.support_simple_spinner_dropdown_item);
        typespin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerTown.setAdapter(townspin);
        spinnerTime.setAdapter(timespin);
        spinnerType.setAdapter(typespin);

        spinnerMonth = (Spinner) findViewById(R.id.spinner_Month);
        spinnerDay = (Spinner) findViewById(R.id.spinner_day);
        monthspin = ArrayAdapter.createFromResource(this, R.array.month, R.layout.support_simple_spinner_dropdown_item);
        monthspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthspin);

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mInitSpinner == false) {
                    mInitSpinner = true;
                }
                String month = (String) monthspin.getItem(position);
                if (month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8") || month.equals("10") || month.equals("12")) {
                    dayspin = ArrayAdapter.createFromResource(AddActivity.this, R.array.day_31, R.layout.support_simple_spinner_dropdown_item);
                    dayspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);

                    spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (mInitSpinner == false) {
                                mInitSpinner = true;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (month.equals("2")) {
                    dayspin = ArrayAdapter.createFromResource(AddActivity.this, R.array.day_29, R.layout.support_simple_spinner_dropdown_item);
                    dayspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);

                    spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (mInitSpinner == false) {
                                mInitSpinner = true;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    dayspin = ArrayAdapter.createFromResource(AddActivity.this, R.array.day_30, R.layout.support_simple_spinner_dropdown_item);
                    dayspin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayspin);

                    spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (mInitSpinner == false) {
                                mInitSpinner = true;
                            }
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

                if (townspin.getItem(position).equals("서울시")) {
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
