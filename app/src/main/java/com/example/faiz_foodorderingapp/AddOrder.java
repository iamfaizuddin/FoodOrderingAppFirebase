package com.example.faiz_foodorderingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.faiz_foodorderingapp.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import android.Manifest;
import com.example.faiz_foodorderingapp.Location.GPSTracker;
import android.test.mock.MockPackageManager;
import android.location.Address;
import android.location.Geocoder;
import java.util.*;
import android.app.*;
import android.widget.*;
import java.text.SimpleDateFormat;
import com.google.firebase.database.Query;

public class AddOrder extends AppCompatActivity{
    MaterialEditText edtorderdueDate, edtcustmerName, edtcustomerphone, edtcustomeraddress,edttotalAmount,edtcity,edtcountry;
    Button btnAddOrder;
    String city, country;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    final Calendar myCalendar = Calendar.getInstance();
    int orderid = 0;
    // GPSTracker class
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        edtorderdueDate = findViewById(R.id.edtorderdueDate);
        edtcustmerName = findViewById(R.id.edtcustmerName);
        edtcustomerphone = findViewById(R.id.edtcustomerphone);
        edtcustomeraddress = findViewById(R.id.edtcustomeraddress);
        edttotalAmount = findViewById(R.id.edttotalAmount);
        edtcity = findViewById(R.id.edtcity);
        edtcountry = findViewById(R.id.edtcountry);

        btnAddOrder = findViewById(R.id.btnaddOrder);

        // Inititalizing Firebase Database for orders
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Orders");

        // Setting Up Location
        try {
            if (ActivityCompat.checkSelfPermission(AddOrder.this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Setting up Calender
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updatedate();
            }
        };

        // setting current date to order date by default
        updatedate();

        // getting current city and country default
        getcityandcountry();

        //Click event for city
        edtcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcityandcountry();
            }
        });

        //Click event for country
        edtcountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcityandcountry();
            }
        });

        //Click event for order date
        edtorderdueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddOrder.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Add order click event
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = "Hyderabad";
                country = "India";

                if(edtorderdueDate.getText().toString().length() > 0 && edtcustmerName.getText().toString().length() > 0
                        && edtcustomerphone.getText().toString().length() > 5 && edtcustomeraddress.getText().toString().length() > 0
                        && edttotalAmount.getText().toString().length() > 0 && city.length() > 0 && country.length() > 0)
                {
                    final ProgressDialog mDialog = new ProgressDialog(AddOrder.this);
                    mDialog.setMessage("Please Wait...");
                    mDialog.show();

                    // Getting the last orderId
                    DatabaseReference orderstable = database.getReference().child("Orders");;
                    Query lastQuery = orderstable.orderByKey().limitToLast(1);
                    lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot data : dataSnapshot.getChildren())
                            {
                                String key = data.getKey();
                                orderid = Integer.parseInt(key);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //Handle possible errors.
                        }
                    });

                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Adding +1 to existing orderid
                            orderid = orderid + 1;

                            //adding order to database
                            Order order = new Order(orderid,edtorderdueDate.getText().toString(), edtcustmerName.getText().toString(),
                                    edtcustomeraddress.getText().toString(),edtcustomerphone.getText().toString()
                            ,edttotalAmount.getText().toString(),city, country);
                            table_user.child(String.valueOf(orderid)).setValue(order);

                            Toast.makeText(AddOrder.this, "Order added successfully !", Toast.LENGTH_SHORT).show();

                            // Redirecting to Orders (Home)
                            mDialog.dismiss();
                            Intent orderIntent = new Intent(AddOrder.this, Home.class);
                            startActivity(orderIntent);
                            finish();

                        }
                        @Override
                        public void onCancelled (DatabaseError databaseError){

                        }
                    });
                }else{
                    Toast.makeText(AddOrder.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatedate() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtorderdueDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void getcityandcountry(){
        try {
            gps = new GPSTracker(AddOrder.this);
            if(gps.canGetLocation()){
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                Geocoder gcd = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
                city = addresses.get(0).getLocality();
                country = addresses.get(0).getCountryName();

                edtcity.setText(city);
                edtcountry.setText(country);
            }else{
                Toast.makeText(AddOrder.this, "failed", Toast.LENGTH_SHORT).show();
                gps.showSettingsAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
