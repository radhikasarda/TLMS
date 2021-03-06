package com.example.tlms;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BackgroundWorker extends AsyncTask<String,Void,String> {

    ResultListener rListener;
    Context context;
    AlertDialog alertDialog;



    BackgroundWorker(Context ctx, ResultListener rtl) {
        context = ctx;
        rListener = rtl;
        alertDialog = new AlertDialog.Builder(context).create();

    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        if (Objects.equals(type, "login")) {
            String login_url = BuildConfig.SERVER_URL+"login_validate.php";
            try {
                String username = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(Objects.equals(type, "new_registration")) {
            Log.e("inside bgworker","reg created #####################################");
            String new_registration_url = BuildConfig.SERVER_URL+"new_registration.php";
            try {
                String pan_no = params[1];
                String gst_no = params[2];
                String contact = params[3];
                String trade_owner_name = params[4];
                String business_holding_name = params[5];
                String email = params[6];
                String correspondance_address = params[7];
                String property_no = params[8];
                String locality= params[9];
                String ward_no=params[10];
                String property_type=params[11];

                URL url = new URL(new_registration_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("pan_no", "UTF-8") + "=" + URLEncoder.encode(pan_no, "UTF-8") + "&"
                        + URLEncoder.encode("gst_no", "UTF-8") + "=" + URLEncoder.encode(gst_no, "UTF-8")+"&"
                        + URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8")+"&"
                        + URLEncoder.encode("trade_owner_name", "UTF-8") + "=" + URLEncoder.encode(trade_owner_name, "UTF-8")+"&"
                        + URLEncoder.encode("business_holding_name", "UTF-8") + "=" + URLEncoder.encode(business_holding_name, "UTF-8")+"&"
                        +URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+"&"
                        +URLEncoder.encode("correspondance_address", "UTF-8") + "=" + URLEncoder.encode(correspondance_address, "UTF-8")+"&"
                        + URLEncoder.encode("property_no", "UTF-8") + "=" + URLEncoder.encode(property_no, "UTF-8")+"&"
                        + URLEncoder.encode("locality", "UTF-8") + "=" + URLEncoder.encode(locality, "UTF-8")+"&"
                        + URLEncoder.encode("ward_no", "UTF-8") + "=" + URLEncoder.encode(ward_no, "UTF-8")+"&"
                        +URLEncoder.encode("property_type", "UTF-8") + "=" + URLEncoder.encode(property_type, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (Objects.equals(type, "invoice")) {
            String invoice_url = BuildConfig.SERVER_URL+"get_tax_rate.php";
            String result = "";
            try {
                String value = params[1];
                Log.e("Value in BG",value+"----##############################");
                ArrayList<String> selected_trade_types = new ArrayList<String>(Arrays.asList(value.split(",")));
                for(String selected : selected_trade_types) {
                    Log.e("inside for each",selected+"###################################");
                    URL url = new URL(invoice_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("trade_type", "UTF-8") + "=" + URLEncoder.encode(selected.trim(), "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    //result = "";
                    String line = "";
                    String seperator = ";";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                        result += seperator;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public void onPreExecute() {



    }

    @Override
    public void onPostExecute(String result) {

        /*if (result.contains("fail") || result.contains("error")) {

            alertDialog.setTitle("Login Status");
            alertDialog.setMessage(result);
            alertDialog.show();

        } else {*/
        Log.e("result",result+"--################################################");
            rListener.setResult(result);

        //}

    }

    @Override
    public void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
