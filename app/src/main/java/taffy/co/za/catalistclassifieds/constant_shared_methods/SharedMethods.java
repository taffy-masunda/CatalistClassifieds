package taffy.co.za.catalistclassifieds.constant_shared_methods;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
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

import taffy.co.za.catalistclassifieds.AboutActivity;
import taffy.co.za.catalistclassifieds.AdvertAdapter;
import taffy.co.za.catalistclassifieds.MainActivity;
import taffy.co.za.catalistclassifieds.R;
import taffy.co.za.catalistclassifieds.SearchActivity;
import taffy.co.za.catalistclassifieds.SearchResultsActivity;
import taffy.co.za.catalistclassifieds.TipsAndSafetyActivity;
import taffy.co.za.catalistclassifieds.models.Advert;

/**
 * Created by TMasunda on 2017/03/29.
 */

public class SharedMethods {

    static Dialog openBrowserDialog, closeAppDialog;
    static Button btnYesDialog, btnCancelDialog;

    /*
    * Open the open browser dialog for posting advert  */
    public static void openBrowserDialog(final Context context){

        openBrowserDialog = new Dialog(context);
        openBrowserDialog.setContentView(R.layout.open_browser_dialog);
        openBrowserDialog.setTitle("Open Browser");
        openBrowserDialog.show();

        // Yes - open browser
        btnYesDialog = (Button) openBrowserDialog.findViewById(R.id.btnYes);
        btnYesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openBrowserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.catalist.co.za/m/post.php"));
                context.startActivity(openBrowserIntent);
                openBrowserDialog.dismiss();
            }
        });

        // Cancel - go back to the app
        btnCancelDialog = (Button) openBrowserDialog.findViewById(R.id.btnCancel);
        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowserDialog.cancel();
            }
        });
    }


    /*
     *  Open the confirm close app dialog */
    public static void closeAppDialog(final Context context){
        closeAppDialog = new Dialog(context);
        closeAppDialog.setContentView(R.layout.exit_app_activity);
        closeAppDialog.setTitle("Open Browser");
        closeAppDialog.show();

        // Yes - close app
        btnYesDialog = (Button) closeAppDialog.findViewById(R.id.btnYes);
        btnYesDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finishAffinity();
                System.exit(0);
            }
        });

        // Cancel - go back to the app
        btnCancelDialog = (Button) closeAppDialog.findViewById(R.id.btnCancel);
        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAppDialog.cancel();
            }
        });
    }


    /*
    *  Open Search activity  */
    public static boolean openSearchActivity(Context context){
        Intent openSearchActivity = new Intent(context, SearchActivity.class);
        context.startActivity(openSearchActivity);
        return true;
    }

    /*
    *  Open  Aboud activity  */
    public static void openAboutActivity(Context context){
        Intent openAboutActivity = new Intent(context, AboutActivity.class);
        context.startActivity(openAboutActivity);
    }


    /*
    *   Get results from url*/
    public static List<Advert> getResults(String urlPath) {
        URL url = null;
        String line = "", jsonResult = "";
        List<Advert> advertList = null;
        Gson gson = new Gson();

        try {
            url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();

            BufferedReader bf = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            // read through file
            while ((line = bf.readLine()) != null) {
                jsonResult += line;
                //publishProgress(1);
            }

            Type type = new TypeToken<List<Advert>>() {
            }.getType();
            advertList = gson.fromJson(jsonResult, type);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return advertList;
        } catch (IOException e) {
            e.printStackTrace();
            return advertList;
        }
        return advertList;
    }

    /*
    *   Open tips activity */
    public static void openTipsActivity(Context context) {
        Intent openTipsActivity = new Intent(context, TipsAndSafetyActivity.class);
        context.startActivity(openTipsActivity);
    }
}
