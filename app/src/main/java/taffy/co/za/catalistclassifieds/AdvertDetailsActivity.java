package taffy.co.za.catalistclassifieds;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import taffy.co.za.catalistclassifieds.constant_shared_methods.SharedMethods;

public class AdvertDetailsActivity extends AppCompatActivity {

    private TextView tvInstitution, tvTitle, tvDescription, tvPrice, tvName, tvplacedDate, tvReport;
    Button btnCall, btnSMS, btnEmailSeller;
    Toolbar toolbar;
    ImageView imgBack, imgSearch, adImageDetails;
    private Button btnYesDialog, btnCancelDialog;
    private Dialog openBrowserDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advert_details_activity);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // advert ID
        final String id = getIntent().getStringExtra("id");

        //setting advert image
        adImageDetails = (ImageView)findViewById(R.id.adImageDetails);
        String imageUrl = "http://www.catalist.co.za/ad_images/" + getIntent().getStringExtra("image_name");
        imageUrl.replace(" ", "%");
        Picasso.with(this).load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.placeholder_image)
                .into(adImageDetails);

        // setting institution
        String institution = getIntent().getStringExtra("institution");
        String campus = getIntent().getStringExtra("campus");
        tvInstitution = (TextView) findViewById(R.id.tvInstitution);
        tvInstitution.setText(institution + " - " + campus);

        // setting title
        String title = getIntent().getStringExtra("title");
        tvTitle = (TextView)findViewById(R.id.tvAdvertTitle);
        tvTitle.setText(title);

        // setting description
        String description = getIntent().getStringExtra("description");
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvDescription.setText(description);

        // setting price
        String price = getIntent().getStringExtra("price");
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvPrice.setText(price);

        // setting seller name
        String name = getIntent().getStringExtra("name");
        tvName = (TextView) findViewById(R.id.tvSellerName);
        tvName.setText("Contact - " + name);

        // report advert
        tvReport = (TextView) findViewById(R.id.tvReport);
        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openReportActivity = new Intent(AdvertDetailsActivity.this, ReportActivity.class);
                openReportActivity.putExtra("advert_id", id);
                startActivity(openReportActivity);
            }
        });

        //call button
        final String phone = getIntent().getStringExtra("phone");
        btnCall = (Button)findViewById(R.id.btnCallSeller);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCaller = new Intent(Intent.ACTION_DIAL);
                openCaller.setData(Uri.parse("tel:" + phone));
                startActivity(openCaller);
            }
        });

        //sms button
        btnSMS = (Button)findViewById(R.id.btnSmsSeller);
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSMS = new Intent(Intent.ACTION_VIEW);

                openSMS.setData(Uri.parse("smsto:" + phone));
                openSMS.putExtra("sms_body", "Catalist :-) is the " + getIntent().getStringExtra("title") + " still available?");
                startActivity(Intent.createChooser(openSMS, "Send Message"));
            }
        });

        //email button
        btnEmailSeller = (Button)findViewById(R.id.btnEmailSeller);
        btnEmailSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openEmail = new Intent(Intent.ACTION_SEND);
                openEmail.setType("text/plain");
                String[]recipientsList = {getIntent().getStringExtra("email")};
                openEmail.putExtra(Intent.EXTRA_EMAIL, recipientsList);
                openEmail.putExtra(Intent.EXTRA_SUBJECT, "Catalist: Advert response - " + getIntent().getStringExtra("title"));
                openEmail.putExtra(Intent.EXTRA_TEXT, "Catalist :-) \n Hi, is the " + getIntent().getStringExtra("title") + " still availavle?");
                startActivity(Intent.createChooser(openEmail, "Send Email"));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        // hide the search icon
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.getItem(0).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

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

}
