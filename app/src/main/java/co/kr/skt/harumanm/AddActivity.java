package co.kr.skt.harumanm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by T on 2015-12-04.
 */
public class AddActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    boolean mInitSpinner;
    ArrayAdapter<CharSequence> townspin, boroughspin, timespin, typespin, monthspin, dayspin;
    float backup;

    Button btnInsert;
    CardView cardInsert;

    Spinner spinnerTown, spinnerBorough, spinnerTime, spinnerType, spinnerMonth, spinnerDay;

    EditText editTitle, editPay, editMinAge, editMaxAge, editContent;

    CheckBox checkM, checkF;

    AlbaJsonObject entityObject;

    EditText townSave, boroughSave, timeSave, typeSave, monthSave, daySave, genderSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add);

        if (entityObject == null){
            entityObject = new AlbaJsonObject();
        }

        townSave = (EditText)findViewById(R.id.town_save);
        boroughSave = (EditText)findViewById(R.id.borough_save);
        timeSave = (EditText)findViewById(R.id.time_save);
        typeSave = (EditText)findViewById(R.id.type_save);
        monthSave = (EditText)findViewById(R.id.month_save);
        daySave = (EditText)findViewById(R.id.day_save);
        genderSave = (EditText)findViewById(R.id.gender_save);

        checkM = (CheckBox) findViewById(R.id.check_m_add);
        checkF = (CheckBox) findViewById(R.id.check_f_add);

        checkM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkM.isChecked() == true){
                    if (checkF.isChecked()==false){
                        String gender = "M";
                        genderSave.setText(gender);
                    }else if(checkF.isChecked() == true){
                        String gender = "A";
                        genderSave.setText(gender);
                    }
                }else if(checkM.isChecked() == false){
                    if (checkF.isChecked() == true){
                        String gender = "F";
                        genderSave.setText(gender);
                    }else if (checkF.isChecked() == false){
                        String gender = "";
                        genderSave.setText(gender);
                    }
                }
            }
        });
        checkF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkF.isChecked() == true){
                    if (checkM.isChecked()==false){
                        String gender = "F";
                        genderSave.setText(gender);
                    }else if(checkM.isChecked() == true){
                        String gender = "A";
                        genderSave.setText(gender);
                    }
                }else if(checkF.isChecked() == false){
                    if (checkM.isChecked() == true){
                        String gender = "M";
                        genderSave.setText(gender);
                    }else if (checkM.isChecked() == false){
                        String gender = "";
                        genderSave.setText(gender);
                    }
                }
            }
        });



        editTitle = (EditText) findViewById(R.id.edit_title_add);
        editPay = (EditText) findViewById(R.id.edit_pay_add);
        editMinAge = (EditText) findViewById(R.id.edit_minage_add);
        editMaxAge = (EditText) findViewById(R.id.edit_maxage_add);
        editContent = (EditText) findViewById(R.id.edit_content_add);

        btnInsert = (Button) findViewById(R.id.btn_insert);
        cardInsert = (CardView) findViewById(R.id.card_insert);
        spinnerTown = (Spinner) findViewById(R.id.spinner_town);
        spinnerBorough = (Spinner) findViewById(R.id.spinner_borough);
        spinnerTime = (Spinner) findViewById(R.id.spinner_time);
        spinnerType = (Spinner) findViewById(R.id.spinner_type);


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
                monthSave.setText(monthspin.getItem(position).toString());
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
                            daySave.setText(dayspin.getItem(position).toString());
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
                            daySave.setText(dayspin.getItem(position).toString());
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
                            daySave.setText(dayspin.getItem(position).toString());
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
                if (mInitSpinner == false) {
                    mInitSpinner = true;
                    return;
                }
                if (timespin.getItem(position).toString().equalsIgnoreCase("모두가능")) {
                    timeSave.setText("A");
                } else if (timespin.getItem(position).toString().equalsIgnoreCase("주말")) {
                    timeSave.setText("W");
                } else if (timespin.getItem(position).toString().equalsIgnoreCase("평일")) {
                    timeSave.setText("D");
                } else if (timespin.getItem(position).toString().equalsIgnoreCase("평일야간")) {
                    timeSave.setText("N");
                } else {
                    timeSave.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mInitSpinner == false) {
                    mInitSpinner = true;
                    return;
                }
                if (typespin.getItem(position).toString().equalsIgnoreCase("서빙or주방")) {
                    typeSave.setText("E");
                } else if (typespin.getItem(position).toString().equalsIgnoreCase("매장관리")) {
                    typeSave.setText("M");
                } else if (typespin.getItem(position).toString().equalsIgnoreCase("서비스")) {
                    typeSave.setText("S");
                } else if (typespin.getItem(position).toString().equalsIgnoreCase("생산or기능")) {
                    typeSave.setText("P");
                } else {
                    typeSave.setText("");
                }
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
                townSave.setText(townspin.getItem(position).toString());
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
                            boroughSave.setText(boroughspin.getItem(position).toString());
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
                            boroughSave.setText(boroughspin.getItem(position).toString());
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

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTitle.getText().toString().trim().equalsIgnoreCase("")){
                    Snackbar.make(v.getRootView(), "제목을 입력해주세요.",Snackbar.LENGTH_SHORT).show();
                }else if (townSave.getText().toString().trim().equalsIgnoreCase("")||townSave.getText().toString().equalsIgnoreCase("======")){
                    Snackbar.make(v.getRootView(),"지역을 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                }else if (boroughSave.getText().toString().trim().equalsIgnoreCase("")||boroughSave.getText().toString().equalsIgnoreCase("======")){
                    Snackbar.make(v.getRootView(),"지역을 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                }else if (editPay.getText().toString().trim().equalsIgnoreCase("")){
                    Snackbar.make(v.getRootView(),"급여를 입력해주세요.", Snackbar.LENGTH_SHORT).show();
                }else if (timeSave.getText().toString().trim().equalsIgnoreCase("")){
                    Snackbar.make(v.getRootView(),"시간대를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                }else if (monthSave.getText().toString().trim().equalsIgnoreCase("==")||monthSave.getText().toString().equalsIgnoreCase("")){
                    Snackbar.make(v.getRootView(), "채용마감 날짜를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                }else if (daySave.getText().toString().trim().equalsIgnoreCase("==")||daySave.getText().toString().equalsIgnoreCase("")){
                    Snackbar.make(v.getRootView(), "채용마감 날짜를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                }else if (typeSave.getText().toString().trim().equalsIgnoreCase("")){
                    Snackbar.make(v.getRootView(), "직종을 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                }else if (editMinAge.getText().toString().trim().equalsIgnoreCase("") || editMaxAge.getText().toString().equalsIgnoreCase("")){
                    Snackbar.make(v.getRootView(),"연령을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
                }else if (checkF.isChecked() == false && checkM.isChecked()==false){
                    Snackbar.make(v.getRootView(),"성별을 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                }else if (editContent.getText().toString().trim().equalsIgnoreCase("")){
                    Snackbar.make(v.getRootView(),"내용을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
                } else {
                    Log.e("log", "TITLE>>>" + editTitle.getText().toString() + "\n"
                            + "CONTNET>>>" + editContent.getText().toString() + "\n"
                            + "LOCAL>>>" + townSave.getText().toString() + " " + boroughSave.getText().toString() + "\n"
                            + "PAY>>>" + editPay.getText().toString() + "\n"
                            + "TIME>>>" + timeSave.getText().toString() + "\n"
                            + "FINISH>>>" + "2016-" + monthSave.getText().toString() + "-" + daySave.getText().toString() + "\n"
                            + "TYPE>>>" + typeSave.getText().toString() + "\n"
                            + "MINAGE>>>" + editMinAge.getText().toString() + "\n"
                            + "MAXAGE>>>" + editMaxAge.getText().toString() + "\n"
                            + "GENDER>>>" + genderSave.getText().toString());
                    AlertDialog.Builder ad = new AlertDialog.Builder(AddActivity.this);
                    ad.setMessage("단기알바 게시글을 등록하겠습니까?").setCancelable(false).setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new AlbaInsertTask().execute("id_1020",
                                            editTitle.getText().toString(),
                                            editContent.getText().toString(),
                                            townSave.getText().toString() + " " + boroughSave.getText().toString(),
                                            editPay.getText().toString(),
                                            timeSave.getText().toString(),
                                            "2016-" + monthSave.getText().toString() + "-" + daySave.getText().toString(),
                                            typeSave.getText().toString(),
                                            editMinAge.getText().toString(),
                                            editMaxAge.getText().toString(),
                                            genderSave.getText().toString());
                                    finish();
                                }
                            }).setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    AlertDialog alertDialog = ad.create();
                    alertDialog.show();
                }
            }
        });
    }

    /* 등록 버튼 백그라운드 쓰레드 */
    private class AlbaInsertTask extends AsyncTask<String, Void, ArrayList<AlbaJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<AlbaJsonObject> doInBackground(String... params) {

            ArrayList<AlbaJsonObject> albaVlaues = null;
            HttpURLConnection connection = null;
            String id = params[0];
            String title = params[1];
            String content = params[2];
            String local = params[3];
            String pay = params[4];
            String time = params[5];
            String finish = params[6];
            String type = params[7];
            String min_age = params[8];
            String max_age = params[9];
            String gender = params[10];
            BufferedReader fromServer = null;
            try {
                URL url = new URL(HaumanURLConstant.INSERT_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.write(("ID=" + id
                        + "&AB_TITLE=" + title
                        + "&AB_CONTENT=" + content
                        + "&AB_LOCAL=" + local
                        + "&AB_PAY=" + pay
                        + "&AB_TIME=" + time
                        + "&AB_FINISH=" + finish
                        + "&AB_TYPE=" + type
                        + "&AB_MIN_AGE=" + min_age
                        + "&AB_MAX_AGE=" + max_age
                        + "&AB_GENDER=" + gender).getBytes("UTF-8"));
                toServer.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonBuf = new StringBuilder();
                    String line = "";

                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }

                    JSONObject root = new JSONObject(jsonBuf.toString());
                    String resultValue = root.getString("result");

                    if (resultValue.equalsIgnoreCase("success")) {

                        albaVlaues = new ArrayList<AlbaJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_insert");

                        int jsonObjectSize = albaInfos.length();

                        for (int i = 0; i < jsonObjectSize; i++) {

                            JSONObject jsonObj = albaInfos.getJSONObject(i);
                            AlbaJsonObject vo = new AlbaJsonObject();

                            vo.ID = jsonObj.getString("ID");
                            vo.AB_TITLE = jsonObj.getString("AB_TITLE");
                            vo.AB_CONTENT = jsonObj.getString("AB_CONTENT");
                            vo.AB_LOCAL = jsonObj.getString("AB_LOCAL");
                            vo.AB_PAY = jsonObj.getString("AB_PAY");
                            vo.AB_TIME = jsonObj.getString("AB_TIME");
                            vo.AB_FINISH = jsonObj.getString("AB_FINISH");
                            vo.AB_TYPE = jsonObj.getString("AB_TYPE");
                            vo.AB_MIN_AGE = jsonObj.getString("AB_MIN_AGE");
                            vo.AB_MAX_AGE = jsonObj.getString("AB_MAX_AGE");
                            vo.AB_GENDER = jsonObj.getString("AB_GENDER");
                            vo.AB_DATE = jsonObj.getString("AB_DATE");
                            vo.AB_FINISH_CHECK = jsonObj.getString("AB_FINISH_CHECK");
                            vo.AB_NUMBER = jsonObj.getInt("AB_NUMBER");
                            albaVlaues.add(vo);
                        }
                    }
                } else {
                    //http 에러 처리
                }
            } catch (Exception e) {
                Log.e("AlbaInsert 문제발생", e.toString());

            } finally {
                if (fromServer != null) {
                    try {
                        fromServer.close();

                    } catch (IOException iew) {
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return albaVlaues;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AddActivity.this);
            pd = ProgressDialog.show(AddActivity.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(ArrayList<AlbaJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            if (pd != null) {
                pd.dismiss();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder yy = new AlertDialog.Builder(AddActivity.this);
        yy.setMessage("작성중이던 글은 저장되지 않습니다.\n이 화면에서 벗어나시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        AlertDialog alertDialog = yy.create();
        alertDialog.show();
    }
}
