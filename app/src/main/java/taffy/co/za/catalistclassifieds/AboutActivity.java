package taffy.co.za.catalistclassifieds;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvParagraph1, tvParagraph2, tvParagraph3, tvParagraph4;
    Button btnMoreAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.about_title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // about screen information text views
        tvParagraph1 = (TextView)findViewById(R.id.txtParagraph1);
        tvParagraph2 = (TextView)findViewById(R.id.txtParagraph2);
        tvParagraph3 = (TextView)findViewById(R.id.txtParagraph3);
        tvParagraph4 = (TextView)findViewById(R.id.txtParagraph4);

        // More about button
        btnMoreAbout = (Button)findViewById(R.id.btnMoreAbout);
        btnMoreAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMoreAbout = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.catalist.co.za/about.php"));
                startActivity(openMoreAbout);
            }
        });
    }
}


