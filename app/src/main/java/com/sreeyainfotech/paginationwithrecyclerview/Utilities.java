package com.sreeyainfotech.paginationwithrecyclerview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by KSTL on 30-03-2017.
 */

public class Utilities {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_SERVER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");//yyyy-MM-dd'T'HH:mm:ss
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");//MMM dd, yyyy hh:mm:ss aa
    public static boolean loginFieldsVisible;
    public static boolean isLoged_In;
    public static int screenWidth, screenHeight;
    public static String pdfPath;
    public static String SortColName = "Name", SortColOrder = "asc";
    public static String ClosingStock_SortColName = "Name", ClosingStock_SortColOrder = "asc";


//    public static void showAlert(final Context mContext, String message) {
//        final AlertDialog.Builder alert_Dialog = new AlertDialog.Builder(mContext);
//        alert_Dialog.setCancelable(false);
//        alert_Dialog.setTitle("Alert");
//        alert_Dialog.setMessage(message);
//        alert_Dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                ((BaseActivity) mContext).finish();
//            }
//        });
//
//        alert_Dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        alert_Dialog.show();
//    }

    public static void hideKeyboard(View v) {
        InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isFailover())
            return false;
        else if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    public static void saveLoginPref(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("Login_Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getLoginPref(Context context, String key, String defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences("Login_Preferences", Context.MODE_PRIVATE);
        return sharedPref.getString(key, defaultValue);
    }

    public static void saveLoginbooleanPref(Context context, String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences("Login_Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getLoginBooleanPref(Context context, String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences("Login_Preferences", Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, value);
    }

    public static void clearLoginPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("Login_Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }


    public static void savebooleanPref(Context context, String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences("Login_Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBooleanPref(Context context, String key, boolean defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences("Login_Preferences", Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, defaultValue);
    }

    public static void savePref(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("MobileApp_Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPref(Context context, String key, String defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences("MobileApp_Preferences", Context.MODE_PRIVATE);
        return sharedPref.getString(key, defaultValue);
    }

    public static void clearPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("MobileApp_Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

    public static void showToast(Context mContext, String mesg) {
        Toast.makeText(mContext, mesg, Toast.LENGTH_LONG).show();
    }

    public static String getConvertedDate(String date) {
        try {
            SIMPLE_DATE_FORMAT_SERVER.setTimeZone(TimeZone.getTimeZone("UTC"));
            return SIMPLE_DATE_FORMAT.format(SIMPLE_DATE_FORMAT_SERVER.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }


    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String fileSize(int size) {
        String hrSize = "";
        int k = size;
        //double m = size / 1024.00;
        //double g = size / 1048576.00;
        //double t = size / 1073741824.00;

        double bytes = size;
        double kilobytes = (bytes / 1024);
        double m = (kilobytes / 1024);
        double g = (m / 1024);
        double t = (g / 1024);

        DecimalFormat dec = new DecimalFormat("0.00");

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else {
            hrSize = dec.format(size).concat(" KB");
        }

        return hrSize;
    }

    public static Bitmap StringToBitMap(String encodedString) {
        Bitmap bitmap = null;
        try {
            byte[] encodeByte = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return bitmap;

        }
    }

    /**
     * function md5 encryption for passwords
     *
     * @param //password
     * @return passwordEncrypted
     */
    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }


    public static String getCurrentVersion(Context mContext) {
        String version = "";
        try {
            version = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getCountryCode(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimCountryIso();
    }


    public static String getFormatedDate(Date dateString) {
        SimpleDateFormat format = new SimpleDateFormat("d");
        String date = format.format(dateString);

        if (date.endsWith("1") && !date.endsWith("11"))
            format = new SimpleDateFormat("MMMM d'st', yyyy");
        else if (date.endsWith("2") && !date.endsWith("12"))
            format = new SimpleDateFormat("MMMM d'nd', yyyy");
        else if (date.endsWith("3") && !date.endsWith("13"))
            format = new SimpleDateFormat("MMMM d'rd', yyyy");
        else
            format = new SimpleDateFormat("MMMM d'th', yyyy");

        return format.format(dateString);
    }

    public static String getFormatedMonthDate(Date dateString) {
        SimpleDateFormat format = new SimpleDateFormat("d");
        String date = format.format(dateString);

        if (date.endsWith("1") && !date.endsWith("11"))
            format = new SimpleDateFormat("d'st' MMM");
        else if (date.endsWith("2") && !date.endsWith("12"))
            format = new SimpleDateFormat("d'nd' MMM");
        else if (date.endsWith("3") && !date.endsWith("13"))
            format = new SimpleDateFormat("d'rd' MMM");
        else
            format = new SimpleDateFormat("d'th' MMM");

        return format.format(dateString);
    }

    public static String getFormatedDecimalNumber(double number) {
        DecimalFormat ft = new DecimalFormat("##,##,###.##");
        return ft.format(number);
    }

    public static String getddMMyyyyFormat(Date dateString) {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        return dt.format(dateString);
    }

    public static String getyyyyMMddFormat(Date dateString) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        return dt.format(dateString);
    }

//    public static void applyFontToMenuItem(MenuItem mi, Context mContext) {
//        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/helvetica_normal.OTF");
//        SpannableString mNewTitle = new SpannableString(mi.getTitle());
//        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        mi.setTitle(mNewTitle);
//    }

    public static Calendar getFirstDayOfWeek(Calendar calendar) {
        Calendar cal = (Calendar) calendar.clone();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            cal.set(Calendar.DAY_OF_YEAR, --day);
        }
        return cal;
    }

    public static Calendar getLastDayOfWeek(Calendar calendar) {
        Calendar cal = (Calendar) calendar.clone();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            cal.set(Calendar.DAY_OF_YEAR, ++day);
        }
        return cal;
    }

    public static String getCurrentFinancialYear() {

        String financialYear = "";
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        if (month < 4) {
            Log.d("Financial Year :", (year - 1) + "-" + year);

            Calendar preCalender = Calendar.getInstance();
            preCalender.add(Calendar.YEAR, -1);
            preCalender.set(Calendar.MONTH, Calendar.APRIL);
            int preFirstDate = preCalender.getActualMinimum(Calendar.DATE);
            int preMonth = preCalender.get(Calendar.MONTH);
            int preYear = preCalender.get(Calendar.YEAR);

            Calendar currCalender = Calendar.getInstance();
            currCalender.set(Calendar.MONTH, Calendar.MARCH);
            int currFirstDate = currCalender.getActualMaximum(Calendar.DATE);
            int currMonth = currCalender.get(Calendar.MONTH);
            int currYear = currCalender.get(Calendar.YEAR);

            financialYear = preYear + "-" + preMonth + "-" + preFirstDate + " " + currYear + "-" + currMonth + "-" + currFirstDate;

        } else {
            Log.d("Financial Year :", year + "-" + (year + 1));

            Calendar preCalender = Calendar.getInstance();
            preCalender.set(Calendar.MONTH, Calendar.APRIL);
            int preFirstDate = preCalender.getActualMinimum(Calendar.DATE);
            int preMonth = preCalender.get(Calendar.MONTH);
            int preYear = preCalender.get(Calendar.YEAR);


            Calendar currCalender = Calendar.getInstance();
            currCalender.add(Calendar.YEAR, +1);
            currCalender.set(Calendar.MONTH, Calendar.MARCH);
            int currFirstDate = currCalender.getActualMaximum(Calendar.DATE);
            int currMonth = currCalender.get(Calendar.MONTH) + 1;
            int currYear = currCalender.get(Calendar.YEAR);

            financialYear = preYear + "-" + preMonth + "-" + preFirstDate + " " + currYear + "-" + currMonth + "-" + currFirstDate;

        }

        return financialYear;
    }

    public static String getQuarter(String whichQuarter, int month) {
        String lastQuarter = "", quarter_Str = "";
        int Quarter_month;
       /* if (whichQuarter.equalsIgnoreCase("CurrentQuarter")) {
            Quarter_month = (month / 3);
        } else {
            Quarter_month = (month / 3)  + 1;
        }*/
        if (month >= 1 && month <= 3)
        {
            quarter_Str = "Q4";
        }
        if (month >= 4 && month <= 6)
        {
            quarter_Str = "Q1";
        }
        if (month >= 7 && month <= 9)
        {
            quarter_Str = "Q2";
        }
        if (month >= 10 && month <= 12)
        {
            quarter_Str = "Q3";
        }

        if(!whichQuarter.equalsIgnoreCase("CurrentQuarter")){
            if(quarter_Str.equalsIgnoreCase("Q1")){
                quarter_Str = "Q4";
            }else if(quarter_Str.equalsIgnoreCase("Q2")){
                quarter_Str = "Q1";
            }else if(quarter_Str.equalsIgnoreCase("Q3")){
                quarter_Str = "Q2";
            }else if(quarter_Str.equalsIgnoreCase("Q4")){
                quarter_Str = "Q3";
            }
        }

        switch (quarter_Str) {
            case "Q1":
                // return June 30
                Calendar Q2Start_Calender = Calendar.getInstance();
                Q2Start_Calender.set(Calendar.MONTH, Calendar.APRIL);
                int Q2Start_Date = Q2Start_Calender.getActualMinimum(Calendar.DATE);
                int Q2Start_Month = Q2Start_Calender.get(Calendar.MONTH) + 1;
                int Q2Start_Year = Q2Start_Calender.get(Calendar.YEAR);


                Calendar Q2End_Calender = Calendar.getInstance();
                Q2End_Calender.set(Calendar.MONTH, Calendar.JUNE);
                int Q2End_Date = Q2End_Calender.getActualMaximum(Calendar.DATE);
                int Q2End_Month = Q2End_Calender.get(Calendar.MONTH) + 1;
                int Q2End_Year = Q2End_Calender.get(Calendar.YEAR);

                lastQuarter = Q2Start_Year + "/" + Q2Start_Month + "/" + Q2Start_Date + " " + Q2End_Year + "/" + Q2End_Month + "/" + Q2End_Date;
                Log.d("Q2 :", Q2Start_Year + "/" + Q2Start_Month + "/" + Q2Start_Date + " " + Q2End_Year + "/" + Q2End_Month + "/" + Q2End_Date);
                break;

            case "Q2":
                // return September 30
                Calendar Q3Start_Calender = Calendar.getInstance();
                Q3Start_Calender.set(Calendar.MONTH, Calendar.JULY);
                int Q3Start_Date = Q3Start_Calender.getActualMinimum(Calendar.DATE);
                int Q3Start_Month = Q3Start_Calender.get(Calendar.MONTH) + 1;
                int Q3Start_Year = Q3Start_Calender.get(Calendar.YEAR);


                Calendar Q3End_Calender = Calendar.getInstance();
                Q3End_Calender.set(Calendar.MONTH, Calendar.SEPTEMBER);
                int Q3End_Date = Q3End_Calender.getActualMaximum(Calendar.DATE);
                int Q3End_Month = Q3End_Calender.get(Calendar.MONTH) + 1;
                int Q3End_Year = Q3End_Calender.get(Calendar.YEAR);

                lastQuarter = Q3Start_Year + "/" + Q3Start_Month + "/" + Q3Start_Date + " " + Q3End_Year + "/" + Q3End_Month + "/" + Q3End_Date;
                Log.d("Q2 :", Q3Start_Year + "/" + Q3Start_Month + "/" + Q3Start_Date + " " + Q3End_Year + "/" + Q3End_Month + "/" + Q3End_Date);
                break;

            case "Q3":
                // return December 31
                Calendar Q4Start_Calender = Calendar.getInstance();
                Q4Start_Calender.set(Calendar.MONTH, Calendar.OCTOBER);
                int Q4Start_Date = Q4Start_Calender.getActualMinimum(Calendar.DATE);
                int Q4Start_Month = Q4Start_Calender.get(Calendar.MONTH) + 1;
                int Q4Start_Year = Q4Start_Calender.get(Calendar.YEAR);


                Calendar Q4End_Calender = Calendar.getInstance();
                Q4End_Calender.set(Calendar.MONTH, Calendar.DECEMBER);
                int Q4End_Date = Q4End_Calender.getActualMaximum(Calendar.DATE);
                int Q4End_Month = Q4End_Calender.get(Calendar.MONTH) + 1;
                int Q4End_Year = Q4End_Calender.get(Calendar.YEAR);

                lastQuarter = Q4Start_Year + "/" + Q4Start_Month + "/" + Q4Start_Date + " " + Q4End_Year + "/" + Q4End_Month + "/" + Q4End_Date;
                Log.d("Q2 :", Q4Start_Year + "/" + Q4Start_Month + "/" + Q4Start_Date + " " + Q4End_Year + "/" + Q4End_Month + "/" + Q4End_Date);
                break;
            case "Q4":
                // return March 31
                Calendar Q1Start_Calender = Calendar.getInstance();
                Q1Start_Calender.set(Calendar.MONTH, Calendar.JANUARY);
                int Q1Start_Date = Q1Start_Calender.getActualMinimum(Calendar.DATE);
                int Q1Start_Month = Q1Start_Calender.get(Calendar.MONTH) + 1;
                int Q1Start_Year = Q1Start_Calender.get(Calendar.YEAR);


                Calendar Q1End_Calender = Calendar.getInstance();
                Q1End_Calender.set(Calendar.MONTH, Calendar.MARCH);
                int Q1End_Date = Q1End_Calender.getActualMaximum(Calendar.DATE);
                int Q1End_Month = Q1End_Calender.get(Calendar.MONTH) + 1;
                int Q1End_Year = Q1End_Calender.get(Calendar.YEAR);

                lastQuarter = Q1Start_Year + "/" + Q1Start_Month + "/" + Q1Start_Date + " " + Q1End_Year + "/" + Q1End_Month + "/" + Q1End_Date;
                Log.d("Q1 :", Q1Start_Year + "/" + Q1Start_Month + "/" + Q1Start_Date + " " + Q1End_Year + "/" + Q1End_Month + "/" + Q1End_Date);
                break;

        }
        return lastQuarter;
    }

    public static String dateFormater(String dateFromJSON) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
        Date date = null;
        String convertedDate = null;
        try {
            date = dateFormat.parse(dateFromJSON);
            convertedDate = getFormatedMonthDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

    public static String formatedDate(String dateFromJSON) {

        SimpleDateFormat old_dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        //SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
        Date date = null;
        String convertedDate = null;
        try {
            date = old_dateFormat.parse(dateFromJSON);
            convertedDate = getFormatedMonthDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDate;
    }
}
