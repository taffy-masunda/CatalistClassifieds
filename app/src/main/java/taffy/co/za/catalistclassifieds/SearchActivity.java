package taffy.co.za.catalistclassifieds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    private String [] arrCategories = {"Select Category", "Accommodation", "Stationery", "Electronics", "Fashion",
            "Appliances", "Events", "Services", "Vehicles"};

    private String[] arrInstitution = {"Select Institution", "Vaal University", "University of Johannesburg", "Wits University", "UNISA", "University of Pretoria"};

    Toolbar toolbar;
    TextView tvToolBarTitle;
    Button btnSearchResults;
    private ImageView imgBack;
    EditText edtSearchPhrase;
    final Spinner spinCategory = null, spinInstitution = null;
    String searchPhrase, category, institution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        // Tool bar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.search_title));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Spinner spinCategory;
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrCategories);
        spinCategory = (Spinner) findViewById(R.id.spinCategory);
        spinCategory.setAdapter(categoryAdapter);

        Spinner spinInstitution;
        final ArrayAdapter<String> institutionAdapter = new ArrayAdapter<String>(SearchActivity.this, R.layout.support_simple_spinner_dropdown_item, arrInstitution);
        spinInstitution = (Spinner) findViewById(R.id.spinInstitution);
        spinInstitution.setAdapter(institutionAdapter);

        // get user input
        edtSearchPhrase = (EditText)findViewById(R.id.edtSearchPhrase);
        spinCategory = (Spinner)findViewById(R.id.spinCategory);
        spinInstitution = (Spinner)findViewById(R.id.spinInstitution);


        // Search Button
        btnSearchResults = (Button)findViewById(R.id.btnSearch);
        final Spinner finalSpinCategory = spinCategory;
        final Spinner finalSpinInstitution = spinInstitution;
        btnSearchResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchPhrase = edtSearchPhrase.getText().toString();
                category = finalSpinCategory.getSelectedItem().toString();
                institution = finalSpinInstitution.getSelectedItem().toString();

                // run the search method
                executeSearch(searchPhrase, category, institution);
            }
        });

    }


    /*
    *   Search and open results screen  */
    public void executeSearch(String searchPhrase, String category, String institution){

        //&& category.equalsIgnoreCase("Select Category") && institution.equalsIgnoreCase("Select Institution")
        if(searchPhrase.equals("") || searchPhrase.equals(" ")){
            Toast.makeText(SearchActivity.this, getString(R.string.search_validation_error), Toast.LENGTH_LONG).show();
            edtSearchPhrase.requestFocus();
        }
        else{
            Intent openSearchResults = new Intent(SearchActivity.this, SearchResultsActivity.class);
            openSearchResults.putExtra("searchPhrase", searchPhrase);
            openSearchResults.putExtra("category", category);
            openSearchResults.putExtra("institution", institution);
            startActivity(openSearchResults);
        }
    }
}
