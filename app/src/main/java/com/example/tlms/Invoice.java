package com.example.tlms;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class Invoice extends AppCompatActivity implements ResultListener{

    TextView tv_trade_owner_name,tv_businesss_holding_name,tv_total_tax,tvTitle;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String selected_application_type,trade_owner_name,business_holding_name,selected_period;
    ArrayList<String> selected_trade_types;
    JSONArray jarray;
    JSONObject json;
    TableLayout table_tax_rate;
    BigDecimal total_tax_rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("inside oncreate", "Invoice created #####################################");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        tv_trade_owner_name = (TextView) findViewById(R.id.trade_owner_name);
        tv_businesss_holding_name = (TextView) findViewById(R.id.business_holding_name);
        table_tax_rate = (TableLayout) findViewById(R.id.table_tax_rate);
        tv_total_tax = (TextView)findViewById(R.id.total_tax);
        tvTitle = (TextView) findViewById(R.id.titletextView);
        tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        table_tax_rate.setBackgroundColor(Color.BLACK);
        selected_trade_types = new ArrayList<String>();
        total_tax_rate = BigDecimal.ZERO;

        Bundle bundle = getIntent().getExtras();
        selected_application_type = bundle.getString("selected_application_type");
        trade_owner_name = bundle.getString("trade_owner_name");
        business_holding_name = bundle.getString("business_holding_name");
        selected_trade_types = (ArrayList<String>) getIntent().getSerializableExtra("selected_trade_list");
        selected_period = bundle.getString("selected_period");
        tv_trade_owner_name.setText(trade_owner_name);
        tv_businesss_holding_name.setText(business_holding_name);
        createInvoiceRows(selected_trade_types);

    }


    public  void createInvoiceRows(ArrayList<String> selected_trade_types)
    {

       /* for (String value : selected_trade_types)
        {
            String type = "invoice";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this, this);
            backgroundWorker.execute(type,value);

        }*/
        String type = "invoice";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this, this);
        String formatted_selected_trade_types = selected_trade_types.toString()
                .replace("[", "") .replace("]", "");  //remove the left bracket

        backgroundWorker.execute(type,formatted_selected_trade_types);


    }


    @Override
    public void setResult(String result)
    {
        if (result.contains("fail") || result.contains("error")) {


        } else {
            //result =  result.split(",");
            createTableRow(result);
            //Log.e("TOTAL TAX",total_tax_rate.toString()+"##########################################");
        }

        //tv_total_tax.setText(total_tax_rate.toString());

    }

    public void createTableRow(String result)
    {
        BigDecimal total = BigDecimal.ZERO;
        String[] result_array_list = result.split(";");
        for(String value : result_array_list)
        {
            Log.e("RESULT ARRAY LIST",value+"##########################################################################");
            total = getTableRowData(value);

        }



        if(!selected_application_type.equals("new")){

            Calendar cal = Calendar.getInstance();
            mYear = cal.get(Calendar.YEAR);

            String[] years_period = selected_period.split("-");
            //Log.e("PERIOD ",years_period[0]+"#############################################");
            int year_period_int = Integer.valueOf(years_period[0]);
            int gap = mYear-year_period_int;
            gap = gap+1;
            //Log.e("GAP ",gap+"#############################################");
            total = total.multiply(BigDecimal.valueOf(gap));

        }
        Log.e("TOTAL",total.toString()+"##########################################");
        tv_total_tax.setText(total.toString());

    }

    public BigDecimal getTableRowData(String value)
    {
        try {

            jarray = new JSONArray((value));


            for (int i = 0; i < jarray.length(); i++) {
                try {
                    //Getting json object
                    json = jarray.getJSONObject(i);

                    TableRow row = new TableRow(this);
                    row.setBackgroundColor(Color.BLACK);
                    TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(tableRowParams);
                    TextView tv0 = new TextView(this);
                    tv0.setTextSize(15);
                    TextView tv1 = new TextView(this);
                    tv1.setTextSize(15);

                    tv0.append(json.getString("name_of_trade"));
                    tv0.append("\n");
                    tv1.append(json.getString("tax_rate"));

                    String str = json.getString("tax_rate");
                    Double obj = new Double(str);
                    BigDecimal tax_rate = BigDecimal.ZERO;
                    tax_rate = BigDecimal.valueOf(obj);
                    //Log.e(" TAX",tax_rate+"##########################################");
                    total_tax_rate = total_tax_rate.add(tax_rate);
                    //Log.e("TOTAL TAX",total_tax_rate+"##########################################");
                    tv1.append("\n");

                    tv0.setGravity(Gravity.CENTER);
                    tv0.setBackgroundColor(Color.WHITE);
                    tv1.setBackgroundColor(Color.WHITE);
                    tv1.setGravity(Gravity.RIGHT);
                    tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    //row.setId(Integer.valueOf(json.getString("s_no")));
                    row.addView(tv0);
                    row.addView(tv1);
                    table_tax_rate.addView(row);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return total_tax_rate;
    }

}
