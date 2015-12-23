package co.kr.skt.harumanm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
 * Created by T on 2015-12-18.
 */
public class ListJson extends AppCompatActivity {

    TextView infoTitle, infoPay, infoMinAge, infoMaxAge, infoContent, infoLocal, infoTime, infoType, infoGender, infoFinish;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_info);

        btnFinish = (Button) findViewById(R.id.btn_finish);

        infoTitle = (TextView) findViewById(R.id.info_title);
        infoPay = (TextView) findViewById(R.id.info_pay);
        infoMinAge = (TextView) findViewById(R.id.info_min_age);
        infoMaxAge = (TextView) findViewById(R.id.info_max_age);
        infoContent = (TextView) findViewById(R.id.info_content);
        infoLocal = (TextView) findViewById(R.id.info_town);
        infoFinish = (TextView) findViewById(R.id.info_finish);
        infoGender = (TextView) findViewById(R.id.info_gender);
        infoTime = (TextView) findViewById(R.id.info_time);
        infoType = (TextView) findViewById(R.id.info_type);

        new AlbaListTask().execute();

    }

    /* 게시글 상세내용 백그라운드 쓰레드 */
    private class AlbaListTask extends AsyncTask<String, Void, ArrayList<AlbaJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<AlbaJsonObject> doInBackground(String... params) {

            ArrayList<AlbaJsonObject> albaValues = null;
            HttpURLConnection connection = null;
            BufferedReader fromServer = null;

            try {
                URL url = new URL(HaumanURLConstant.SEARCH_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.close();

                int responseCod = connection.getResponseCode();

                if (responseCod == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonBuf = new StringBuilder();
                    String line = "";

                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }
                    JSONObject root = new JSONObject(jsonBuf.toString());
                    String resultValue = root.getString("result");

                    if (resultValue.equalsIgnoreCase("success")) {
                        albaValues = new ArrayList<AlbaJsonObject>();
                        JSONArray albaInfos = root.getJSONArray("result_list");

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

                            albaValues.add(vo);
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                Log.e("AlbaTask 문제발생", e.toString());
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
            return albaValues;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ListJson.this);
            pd = ProgressDialog.show(ListJson.this, "", "", true, true, null);
        }

        @Override
        protected void onPostExecute(final ArrayList<AlbaJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);

            Intent intent = getIntent();
            final int ABno = intent.getExtras().getInt("ListPosition") - 1;

            if (albaJsonObjects != null && albaJsonObjects.size() > 0) {

                infoTitle.setText(albaJsonObjects.get(ABno).AB_TITLE);
                infoMinAge.setText(albaJsonObjects.get(ABno).AB_MIN_AGE);
                infoMaxAge.setText(albaJsonObjects.get(ABno).AB_MAX_AGE);
                infoPay.setText(albaJsonObjects.get(ABno).AB_PAY);
                infoContent.setText(albaJsonObjects.get(ABno).AB_CONTENT);
                infoLocal.setText(albaJsonObjects.get(ABno).AB_LOCAL);
                if (albaJsonObjects.get(ABno).AB_TIME.equalsIgnoreCase("A")) {
                    infoTime.setText("모두가능");
                } else if (albaJsonObjects.get(ABno).AB_TIME.equalsIgnoreCase("w")) {
                    infoTime.setText("주말");
                } else if (albaJsonObjects.get(ABno).AB_TIME.equalsIgnoreCase("d")) {
                    infoTime.setText("평일");
                } else if (albaJsonObjects.get(ABno).AB_TIME.equalsIgnoreCase("n")) {
                    infoTime.setText("평일야간");
                }
                if (albaJsonObjects.get(ABno).AB_GENDER.equalsIgnoreCase("f")) {
                    infoGender.setText("여성");
                } else if (albaJsonObjects.get(ABno).AB_GENDER.equalsIgnoreCase("m")) {
                    infoGender.setText("남성");
                } else {
                    infoGender.setText("성별무관");
                }
                if (albaJsonObjects.get(ABno).AB_TYPE.equalsIgnoreCase("E")) {
                    infoType.setText("서빙○주방");
                } else if (albaJsonObjects.get(ABno).AB_TYPE.equalsIgnoreCase("M")) {
                    infoType.setText("매장관리");
                } else if (albaJsonObjects.get(ABno).AB_TYPE.equalsIgnoreCase("S")) {
                    infoType.setText("서비스");
                } else if (albaJsonObjects.get(ABno).AB_TYPE.equalsIgnoreCase("P")) {
                    infoType.setText("생산○기능");
                }
                infoFinish.setText(albaJsonObjects.get(ABno).AB_FINISH + "");
            }
            btnFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder ad = new AlertDialog.Builder(ListJson.this);
                    ad.setMessage("이 게시글의 채용을 마감하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String a = String.valueOf(albaJsonObjects.get(ABno).AB_NUMBER);
                                    Log.e("aaaaaa>>>", a);
                                    new AlbaFinishTask().execute(a);
                                    finish();
                                    Intent intent = new Intent(ListJson.this, ListActivity.class);
                                    startActivity(intent);
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
            });
            if (pd != null) {
                pd.dismiss();
            }
        }
    }

    /* 마감 버튼 백그라운드 쓰레드 */
    private class AlbaFinishTask extends AsyncTask<String, Void, ArrayList<AlbaJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<AlbaJsonObject> doInBackground(String... params) {

            ArrayList<AlbaJsonObject> albaVlaues = null;
            HttpURLConnection connection = null;
            String query = params[0];
            BufferedReader fromServer = null;
            try {
                URL url = new URL(HaumanURLConstant.FINISH_DO);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(15000);
                OutputStream toServer = connection.getOutputStream();

                toServer.write(("AB_NUMBER=" + query).getBytes("UTF-8"));
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
                        JSONArray albaInfos = root.getJSONArray("result_finish");

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
            pd = new ProgressDialog(ListJson.this);
            pd = ProgressDialog.show(ListJson.this, "", "", true, true, null);
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
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(ListJson.this, ListActivity.class);
        startActivity(intent);
    }
}

