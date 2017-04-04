package taffy.co.za.catalistclassifieds.constant_shared_methods;

import android.content.Context;

import org.junit.Test;

import java.util.List;

import taffy.co.za.catalistclassifieds.SearchActivity;
import taffy.co.za.catalistclassifieds.models.Advert;

import static org.junit.Assert.*;

/**
 * Created by TMasunda on 2017/03/29.
 */

public class MethodsTests {


    /*
    *    Positive testing with correct URL */
    @Test
    public void testGetResultsPositive() {
        String url = "http://api.catalist.co.za/get_latest.php";
        List<Advert> resultsList = null;

        resultsList = SharedMethods.getResults(url);
        assertNotNull(resultsList);
    }


    /*
    *    Positive testing with correct URL */
    @Test
    public void testGetResultsNegative() {
        String url = "http://api.catalist.co.za/get_latest.php";
        List<Advert> resultsList = null;

        resultsList = SharedMethods.getResults(url);
        assertNotNull(resultsList);
    }

}