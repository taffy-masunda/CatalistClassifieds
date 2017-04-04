package taffy.co.za.catalistclassifieds;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

import taffy.co.za.catalistclassifieds.constant_shared_methods.SharedMethods;
import taffy.co.za.catalistclassifieds.models.Advert;

public class MainActivity extends AppCompatActivity {

    final String latestApiUrl = "http://api.catalist.co.za/get_latest.php";
    ListView lvAdverts;
    Toolbar toolbar;
    Dialog openBrowserDialog;
    Button btnYesDialog, btnCancelDialog;
    TextView tvToolBarTitle, tvLoading;
    ProgressDialog progress;

    // swipe to refresh
    SwipeRefreshLayout swipeList;
    boolean progressFromRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        tvToolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
        tvToolBarTitle.setText(getString(R.string.latest_ads_title));
        setSupportActionBar(toolbar);

        // text view for loading
        tvLoading = (TextView) findViewById(R.id.tvLoading);

        // list view
        lvAdverts = (ListView) findViewById(R.id.lvAdverts);
        lvAdverts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // getting text from visible controls
                TextView title = (TextView) view.findViewById(R.id.tvTitle);
                TextView description = (TextView) view.findViewById(R.id.tvDescription);
                TextView price = (TextView) view.findViewById(R.id.tvPrice);
                TextView institution = (TextView) view.findViewById(R.id.tvInstitution);
                TextView category = (TextView) view.findViewById(R.id.tvCategory);
                TextView placed_date = (TextView) view.findViewById(R.id.tvPlacedDate);

                // getting Invisible data
                TextView ad_id, campus, name, email, phome, whatsapp, imageName;
                ad_id = (TextView) view.findViewById(R.id.tvID);
                campus = (TextView) view.findViewById(R.id.tvCampus);
                name = (TextView) view.findViewById(R.id.tvName);
                email = (TextView) view.findViewById(R.id.tvEmail);
                phome = (TextView) view.findViewById(R.id.tvPhone);
                whatsapp = (TextView) view.findViewById(R.id.tvWhatsapp);
                imageName = (TextView) view.findViewById(R.id.tvImageName);

                Intent openAdvertDetails = new Intent(MainActivity.this, AdvertDetailsActivity.class);
                // parsing visible data
                openAdvertDetails.putExtra("title", title.getText().toString());
                openAdvertDetails.putExtra("description", description.getText().toString());
                openAdvertDetails.putExtra("price", price.getText().toString());
                openAdvertDetails.putExtra("institution", institution.getText().toString());
                openAdvertDetails.putExtra("category", category.getText().toString());
                openAdvertDetails.putExtra("placed_date", placed_date.getText().toString());

                // parsing invisible data
                openAdvertDetails.putExtra("id", ad_id.getText().toString());
                openAdvertDetails.putExtra("campus", campus.getText().toString());
                openAdvertDetails.putExtra("name", name.getText().toString());
                openAdvertDetails.putExtra("email", email.getText().toString());
                openAdvertDetails.putExtra("phone", phome.getText().toString());
                openAdvertDetails.putExtra("whatsapp", whatsapp.getText().toString());
                openAdvertDetails.putExtra("image_name", imageName.getText().toString());

                startActivity(openAdvertDetails);
            }
        });

        // run background to get latest advert list
        new LoadLatestAdverts().execute(latestApiUrl);

        // to refresh list after swipping and flag to remove the progress dialog
        swipeList = (SwipeRefreshLayout) findViewById(R.id.refreshList);
        swipeList.setColorSchemeResources(R.color.splash_bg_color);
        swipeList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressFromRefresh = true;
                lvAdverts.setVisibility(View.INVISIBLE);
                tvLoading.setVisibility(View.VISIBLE);
                new LoadLatestAdverts().execute(latestApiUrl);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_icon:
                SharedMethods.openSearchActivity(this);
                return true;
            case R.id.add_icon:
                SharedMethods.openBrowserDialog(this);
                return true;
            case R.id.menu_about:
                SharedMethods.openAboutActivity(this);
                return true;
            case R.id.menu_tips:
                SharedMethods.openTipsActivity(this);
                return true;
            case R.id.menu_exit:
                SharedMethods.closeAppDialog(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /*
    *   Load Latest results async task  */
    private class LoadLatestAdverts extends AsyncTask<String, Integer, List<Advert>> {

        @Override
        protected void onPreExecute() {
            tvLoading.setVisibility(View.VISIBLE);
        }


        @Override
        protected List<Advert> doInBackground(String... params) {

            List<Advert> latestAdvertList = null;
            try {
                latestAdvertList = SharedMethods.getResults(params[0]);
                //publishProgress(1);
                return latestAdvertList;
            } catch (Exception e) {
                e.printStackTrace();
                return latestAdvertList;
            }
        }

        /*
        *  Populate the listview and dismis the progress bars  */
        @Override
        protected void onPostExecute(List<Advert> result) {
            super.onPostExecute(result);
            AdvertAdapter adapter = new AdvertAdapter(getApplicationContext(), R.layout.advert_for_results, result);
            lvAdverts.setAdapter(adapter);
            swipeList.setRefreshing(false);

            lvAdverts.setVisibility(View.VISIBLE);
            tvLoading.setVisibility(View.GONE);
        }
    }
}



