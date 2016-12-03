package HelperClasses;

import android.graphics.Bitmap;

import com.king.king_lens.Home_Sliding.Beanclass;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 15/7/16.
 */
public class UserConstants {
    public static final String BASE_URL = "http://104.236.11.215/web/";
    public static final String IMAGE_FOLDER = "public/storage/images/";
    public static boolean tab1ExecutionDone = false;
    public static ArrayList<Beanclass> tab1ImageArray = new ArrayList<>();

    public static boolean tab2ExecutionDone = false;
    public static ArrayList<Beanclass> tab2ImageArray = new ArrayList<>();

    public static boolean tab3ExecutionDone = false;
    public static ArrayList<Beanclass> tab3ImageArray = new ArrayList<>();

    public static String collection_Brand_id = "";
    public static String collection_Category_id = "";
    public static String product_Collection_id = "";
    public static String product_Product_id = "";

    public static boolean returnToProductView = false;

    public static String paymentAmount = "";
}
