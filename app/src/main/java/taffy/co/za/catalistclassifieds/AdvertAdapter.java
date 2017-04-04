package taffy.co.za.catalistclassifieds;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Retrofit;
import taffy.co.za.catalistclassifieds.models.Advert;

public class AdvertAdapter extends ArrayAdapter<Advert> {

    private static final String TAG = "AdvertAdapter";
    private List<Advert> advertsList;
    private int resource;
    private LayoutInflater inflater;
    final static String imageBaseUrl = "http://www.catalist.co.za/ad_images/";

    public AdvertAdapter(Context context, int resource, List<Advert> objects) {
        super(context, resource, objects);
        advertsList = objects;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
        }

        // setting visible controls
        TextView tvTitle, tvDescription, tvPrice, tvInstitution, tvCategory, tvPlacedDate;

        ImageView imgAdvert = (ImageView) convertView.findViewById(R.id.imgResultsImage);
        tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        tvInstitution = (TextView) convertView.findViewById(R.id.tvInstitution);
        tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
        tvPlacedDate = (TextView) convertView.findViewById(R.id.tvPlacedDate);

        // Get advert object
        Advert ad = advertsList.get(position);

        tvTitle.setText(ad.getTitle());
        tvDescription.setText(ad.getDescription());
        tvPrice.setText("R " + ad.getPrice());
        tvInstitution.setText(ad.getInstitution());
        tvCategory.setText(ad.getCategory());
        tvPlacedDate.setText(ad.getPlacedDate());

        // setting Invisible controls
        TextView tvID, tvCampus, tvName, tvEmail, tvPhome, tvWhatsapp, tvImageName;
        tvID = (TextView) convertView.findViewById(R.id.tvID);
        tvCampus = (TextView) convertView.findViewById(R.id.tvCampus);
        tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);
        tvPhome = (TextView) convertView.findViewById(R.id.tvPhone);
        tvWhatsapp = (TextView) convertView.findViewById(R.id.tvWhatsapp);
        tvImageName = (TextView) convertView.findViewById(R.id.tvImageName);

        tvID.setText(ad.getAdvertId());
        tvCampus.setText(ad.getCampus());
        tvName.setText(ad.getName());
        tvEmail.setText(ad.getName());
        tvEmail.setText(ad.getEmail());
        tvPhome.setText(ad.getPhone());
        tvWhatsapp.setText(ad.getWhatsapp());
        tvImageName.setText(ad.getImageName());

        String id = ad.getAdvertId();

        Picasso.with(getContext()).load(imageBaseUrl + tvImageName.getText().toString())
                .placeholder(R.drawable.loading)
                .error(R.drawable.placeholder_image)
                .into(imgAdvert);

        return convertView;
    }
}

