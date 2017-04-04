package taffy.co.za.catalistclassifieds;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

public class SearchResultsActivity extends AppCompatActivity {

    ListView lvAdverts;
    Toolbar toolbar;
    Dialog openBrowserDialog;
    Button btnYesDialog, btnCancelDialog;
    TextView tvToolBarTitle, tvNoReults;
    ImageView backImage;
    String searchPhrase, category, institution;
    private SwipeRefreshLayout swipeList;
    private boolean progressFromRefresh = false;
    private TextView tvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_activity);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.search_results_title));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //text view loading
        tvLoading = (TextView) findViewById(R.id.tvLoading);

        // No results text view
        tvNoReults = (TextView) findViewById(R.id.tvNoResults);

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
                imageName = (TextView)view.findViewById(R.id.tvImageName);

                Intent openAdvertDetails = new Intent(SearchResultsActivity.this, AdvertDetailsActivity.class);
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

        // get the search criteria variables
        searchPhrase = getIntent().getStringExtra("searchPhrase");
        category = getIntent().getStringExtra("category");
        institution = getIntent().getStringExtra("institution");

       // TextView tvSearch = (TextView)findViewById(R.id.tvSearchCriteria);
        //tvSearch.setText(searchPhrase + "\n" + category + "\n" + institution);

        // run background processes
        new LoadSearchAdverts().execute("http://api.catalist.co.za/search_api.php?" +
                "searchPhrase=" + searchPhrase +
                "&category=" + category +
                "&institution=" + institution);

        // to refresh list after swipping
        swipeList = (SwipeRefreshLayout) findViewById(R.id.refreshList);
        swipeList.setColorSchemeResources(R.color.splash_bg_color);
        swipeList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressFromRefresh = true;
                lvAdverts.setVisibility(View.INVISIBLE);
                tvLoading.setVisibility(View.VISIBLE);
                new LoadSearchAdverts().execute("http://api.catalist.co.za/search_api.php?" +
                        "searchPhrase=" + searchPhrase +
                        "&category=" + category +
                        "&institution=" + institution);
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

    private class LoadSearchAdverts extends AsyncTask<String, Integer, List<Advert>> {

        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            tvLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Advert> doInBackground(String... params) {

            List<Advert> searchAdvertList = null;

            try{
                searchAdvertList = SharedMethods.getResults(params[0]);
                return searchAdvertList;
            } catch (Exception e) {
                e.printStackTrace();
                return searchAdvertList;
            }
        }


        /* Shoe results in a list vew or show no results text for search criteria
        * */
        @Override
        protected void onPostExecute(List <Advert> result) {
            super.onPostExecute(result);

            if(result == null){
                lvAdverts.setVisibility(View.GONE);
                tvNoReults.setVisibility(View.VISIBLE);
                String noCategory = "No category celected";
                String noInstitution = "No institution selected";

                if(category.equalsIgnoreCase("Select category")){
                    category = noCategory;
                }
                if(institution.equalsIgnoreCase("Select Institution")){
                    institution = noInstitution;
                }

                tvNoReults.setText("No results for: \"" + searchPhrase + "\"" +
                        "\nCategory: " + category +
                        "\nInstitution: " + institution);
            }else{
                //lvAdverts.setVisibility(View.VISIBLE);
                tvNoReults.setVisibility(View.GONE);
                AdvertAdapter adapter = new AdvertAdapter(getApplicationContext(), R.layout.advert_for_results, result);
                lvAdverts.setAdapter(adapter);
            }

            swipeList.setRefreshing(false);

            lvAdverts.setVisibility(View.VISIBLE);
            tvLoading.setVisibility(View.GONE);
        }
    }
}