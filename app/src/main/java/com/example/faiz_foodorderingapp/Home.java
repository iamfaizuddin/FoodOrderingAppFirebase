package com.example.faiz_foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.example.faiz_foodorderingapp.Common.Common;
import com.example.faiz_foodorderingapp.Model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.support.v7.widget.LinearLayoutManager;
import com.example.faiz_foodorderingapp.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Order, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Orders");

        // Add order floating button on the bottom right
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this,AddOrder.class);
                startActivity(cartIntent);
            }
        });

        // Initialising drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set Name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txtFullName);
        txtFullName.setText("Welcome " + Common.currentUser.getName());

        //Set recyclerview
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadOrders();

    }

    // loading all the orders from firebase
    public void loadOrders(){
        adapter = new FirebaseRecyclerAdapter<Order, MenuViewHolder>(Order.class,R.layout.order_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, final Order model, final int position) {
                viewHolder.orderid.setText("OrderId : " + model.getOrderId());
                viewHolder.orderdate.setText("OrderDate : " + model.getOrderDueDate());
                viewHolder.ordername.setText("Name : " + model.getCustomerName());
                viewHolder.orderphone.setText("Phone : " + model.getCustomerPhone());
                viewHolder.orderaddress.setText("Address : " + model.getCustomerAddress() + " " + model.getCity() + " " + model.getCountry());
                viewHolder.ordertotal.setText("Total : " + model.getOrderTotal());

                viewHolder.orderedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent cartIntent = new Intent(Home.this,AddOrder.class);
//                        startActivity(cartIntent);
                    }
                });

                viewHolder.orderdelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                                .child("Orders").child(String.valueOf(model.getOrderId()));
                        mPostReference.removeValue();

                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_addorder){
            Intent orderIntent = new Intent(Home.this, AddOrder.class);
            startActivity(orderIntent);
        }else if(id == R.id.nav_orders){
            Intent orderIntent = new Intent(Home.this, Home.class);
            startActivity(orderIntent);
        }else if(id == R.id.nav_log_out){
            Intent signIn = new Intent(Home.this, SignIn.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

