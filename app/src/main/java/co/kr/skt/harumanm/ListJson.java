package co.kr.skt.harumanm;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_test);

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

        new AlbaListTask().execute("ID");
    }

    private class AlbaListTask extends AsyncTask<String, Void, ArrayList<AlbaJsonObject>> {
        ProgressDialog pd;

        @Override
        protected ArrayList<AlbaJsonObject> doInBackground(String... params) {

            ArrayList<AlbaJsonObject> albaValues = null;
            HttpURLConnection connection = null;
            String query = params[0];
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

                toServer.write(("alba = " + query).getBytes("UTF-8"));
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
                            vo.AB_NUMBER = jsonObj.getString("AB_NUMBER");

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
            pd.setMessage("데이터가져오는 중");
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.show();
        }

        @Override
        protected void onPostExecute(ArrayList<AlbaJsonObject> albaJsonObjects) {
            super.onPostExecute(albaJsonObjects);
            pd.dismiss();

            if (albaJsonObjects != null && albaJsonObjects.size() > 0) {
                infoTitle.setText(albaJsonObjects.get(0).AB_TITLE);
                infoMinAge.setText(albaJsonObjects.get(0).AB_MIN_AGE);
                infoMaxAge.setText(albaJsonObjects.get(0).AB_MAX_AGE);
                infoPay.setText(albaJsonObjects.get(0).AB_PAY);
                infoContent.setText(albaJsonObjects.get(0).AB_CONTENT);
                infoLocal.setText(albaJsonObjects.get(0).AB_LOCAL);
                if (albaJsonObjects.get(0).AB_TIME.equalsIgnoreCase("A")) {
                    infoTime.setText("모두가능");
                } else if (albaJsonObjects.get(0).AB_TIME.equalsIgnoreCase("w")) {
                    infoTime.setText("주말");
                } else if (albaJsonObjects.get(0).AB_TIME.equalsIgnoreCase("d")) {
                    infoTime.setText("평일");
                } else if (albaJsonObjects.get(0).AB_TIME.equalsIgnoreCase("n")) {
                    infoTime.setText("평일야간");
                }
                if (albaJsonObjects.get(0).AB_GENDER.equalsIgnoreCase("f")) {
                    infoGender.setText("여성");
                } else if (albaJsonObjects.get(0).AB_GENDER.equalsIgnoreCase("m")) {
                    infoGender.setText("남성");
                } else {
                    infoGender.setText("성별무관");
                }
                if (albaJsonObjects.get(0).AB_TYPE.equalsIgnoreCase("E")) {
                    infoType.setText("서빙º주방");
                } else if (albaJsonObjects.get(0).AB_TYPE.equalsIgnoreCase("M")) {
                    infoType.setText("매장관리");
                } else if (albaJsonObjects.get(0).AB_TYPE.equalsIgnoreCase("S")) {
                    infoType.setText("서비스");
                } else if (albaJsonObjects.get(0).AB_TYPE.equalsIgnoreCase("P")) {
                    infoType.setText("생산º기능");
                }
                infoFinish.setText(albaJsonObjects.get(0).AB_FINISH+"");
            }
        }
    }
}
