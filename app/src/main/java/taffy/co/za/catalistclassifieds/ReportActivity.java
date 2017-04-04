package taffy.co.za.catalistclassifieds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import taffy.co.za.catalistclassifieds.models.Advert;

public class ReportActivity extends AppCompatActivity {

    private String [] arrCategories = {"Select Report Category", "Offensive Content", "Scam", "Pornography", "Hate Speech", "False Advert" };
    //Resources res = getResources();
    //private String [] arrCategories = res.getStringArray(R.array.offense_categories);

    Toolbar toolbar;
    ProgressBar progressBar;
    Button btnReport;
    Spinner spinCategory = null;
    EditText edtReporterName, edtReporterEmail, edtReporterPhone, edtReportDescription;
    String advert_id, reportCategory, reporterName, reporterEmail, reporterPhone, reportDescription;
    String serverMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        // Tool bar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.report_title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // progress bar
        progressBar = (ProgressBar) findViewById(R.id.reportProgressBar);

        // SETTING UP SPINNER VIEW
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrCategories);
        spinCategory = (Spinner) findViewById(R.id.spinFeedbackCategory);
        spinCategory.setAdapter(categoryAdapter);

        // getting user input
        edtReporterName = (EditText) findViewById(R.id.edtReporterName);
        edtReporterEmail = (EditText) findViewById(R.id.edtReporterEmail);
        edtReporterPhone = (EditText) findViewById(R.id.edtReporterPhone);
        edtReportDescription = (EditText) findViewById(R.id.edtReportDescription);


        // Search Button
        btnReport = (Button)findViewById(R.id.btnReport);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // building report url
                advert_id = getIntent().getStringExtra("advert_id");
                reportCategory = spinCategory.getSelectedItem().toString();
                reporterName = edtReporterName.getText().toString();
                reporterEmail = edtReporterEmail.getText().toString();
                reporterPhone = edtReporterPhone.getText().toString();
                reportDescription = edtReportDescription.getText().toString();

                if(reportCategory.equalsIgnoreCase("Select report Category") ||  reporterName.equals("") || reporterEmail.equals("") || reportDescription.equals("") ){
                    Toast.makeText(ReportActivity.this, "Please fill out all required fields", Toast.LENGTH_SHORT).show();
                }else {
                    String url;
                    StringBuilder builder = new StringBuilder("http://api.catalist.co.za/report_ad.php?");
                    builder.append("advert_id=" + advert_id);
                    builder.append("&report_type=" + reportCategory);
                    builder.append("&name=" + reporterName);
                    builder.append("&email=" + reporterEmail);
                    builder.append("&phone=" + reporterPhone);
                    builder.append("&description=" + reportDescription);

                    url = builder.toString();

                    // execute in background
                    new SendReportAsync().execute(url);
                }

            }
        });

    }

    public void clearFields(){
        edtReporterName.setText("");
        edtReporterEmail.setText("");
        edtReporterPhone.setText("");
        edtReportDescription.setText("");
    }



    // Sending the report
    class SendReportAsync extends AsyncTask<String, Integer, String>{

        ProgressDialog prog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            prog = new ProgressDialog(ReportActivity.this);
            prog.setTitle("Processing report");
            prog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            URL url = null;
            String line = "", jsonResult = "";

            try {
                url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();

                BufferedReader bf = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                // read through file
                while ((line = bf.readLine()) != null) {
                    jsonResult += line;
                    publishProgress(1);
                }

                return jsonResult;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return jsonResult;
            } catch (IOException e) {
                e.printStackTrace();
                return jsonResult;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(ReportActivity.this, s, Toast.LENGTH_SHORT).show();

            prog.dismiss();
            // clear fields after sending
            clearFields();
        }
    }
}


