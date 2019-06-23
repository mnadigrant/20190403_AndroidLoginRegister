package com.example.loginandregisteruser;

import helper.SQLiteHandler;
import helper.SessionManager;
import volley.AppController;
import volley.Config_URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class Activity_Main extends Activity implements TaskRecyclerViewAdapter.OnTicketListener {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    private Button clickbtn;
    private RecyclerView myTaskrecyclerview;
    private List<Tasks> mtaskdata = Tasks.getAllTasks();

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        clickbtn = (Button) findViewById(R.id.fetchbtn);

        clickbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fetchdata();
            }
        });



        myTaskrecyclerview = findViewById(R.id.task_recycler_view);

        TaskRecyclerViewAdapter taskRecyclerViewAdapter = new TaskRecyclerViewAdapter(getApplicationContext(),mtaskdata,this);
        myTaskrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myTaskrecyclerview.setAdapter(taskRecyclerViewAdapter);



        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void fetchdata() {
        // Tag used to cancel the request
        String tag_string_req = "req_task";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Config_URL.URL_TASKS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Task Response: " , response.toString());

                try {

                    JSONObject jObj = new JSONObject(response);


                        JSONObject task = jObj.getJSONObject("task");
                    Gson gson = new Gson();
                    Tasks tasks = gson.fromJson(task.toString(), Tasks.class);
                    Tasks.addToTaskList(tasks);
                    Log.e("Tasks",tasks.toString());
                    boolean error = jObj.optBoolean("error");

                    // Check for error node in json
                    if (!error) {

//                        String description = task.getString("description");
//                        String title = task.getString("title");
//                        String done = task.getString("done");
//                        String id = task.getString("id");
//
//                        Tasks g_task = new Tasks(description,done,title,id);
//

                        // Launch main activity
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Error in login in", Toast.LENGTH_LONG).show();

                    }

                } catch(JSONException e){
                    // JSON error
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(Activity_Main.this, Activity_Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onTicketClick(int position) {

    }
}