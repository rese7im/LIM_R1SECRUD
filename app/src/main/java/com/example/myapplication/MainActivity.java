package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // URLs for CRUD operations assuming server is running locally
    private static final String SERVER_URL = "http://192.168.1.109/R1SE_CRUD/create.php";
    private static final String UPDATE_URL = "http://192.168.1.109/R1SE_CRUD/update.php";
    private static final String DELETE_URL = "http://192.168.1.109/R1SE_CRUD/delete.php";

    // UI elements
    EditText id, name, email;
    Button submitButton, updateButton, deleteButton;
    String TempID, TempName, TempEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        id = findViewById(R.id.editText);
        name = findViewById(R.id.editText1);
        email = findViewById(R.id.editText2);
        submitButton = findViewById(R.id.button);
        updateButton = findViewById(R.id.update);
        deleteButton = findViewById(R.id.delete);

        // Set onClickListener for submitButton
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData();
                if (TempName.isEmpty() || TempEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter name and email", Toast.LENGTH_SHORT).show();
                } else {
                    InsertData();
                }
            }
        });

        // Set onClickListener for updateButton
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData();
                if (TempID.isEmpty() || TempName.isEmpty() || TempEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter ID, name, and email", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateData();
                }
            }
        });

        // Set onClickListener for deleteButton
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData();
                if (TempID.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter ID", Toast.LENGTH_SHORT).show();
                } else {
                    DeleteData();
                }
            }
        });
    }

    // Method to get data from EditText fields
    public void GetData() {
        TempID = id.getText().toString();
        TempName = name.getText().toString();
        TempEmail = email.getText().toString();
    }

    // Method to insert data into the server
    public void InsertData() {
        // AsyncTask to perform network operations on background thread
        class SendPostReqAsyncTask extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                // Get data from EditText fields
                String IDHolder = TempID;
                String NameHolder = TempName;
                String EmailHolder = TempEmail;

                try {
                    // Create URL object for server endpoint
                    URL url = new URL(SERVER_URL);
                    // Open HTTP connection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    // Set request method to POST
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    // Setup output stream for writing data to server
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    // Create key-value pairs for POST data
                    HashMap<String, String> postDataParams = new HashMap<>();
                    postDataParams.put("id", IDHolder);
                    postDataParams.put("name", NameHolder);
                    postDataParams.put("email", EmailHolder);

                    // Encode POST data
                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, String> param : postDataParams.entrySet()) {
                        if (postData.length() != 0)
                            postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                    }

                    // Write POST data to output stream
                    bufferedWriter.write(postData.toString());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // Get response code from server
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Data Inserted Successfully";
                    } else {
                        return "Error: " + responseCode;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            // Method to handle result after background task completes
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error inserting data", Toast.LENGTH_LONG).show();
                }
            }
        }

        // Execute AsyncTask
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    // Method to update data on the server
    public void UpdateData() {
        // AsyncTask to perform network operations on background thread
        class SendPostReqAsyncTask extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                // Fetch updated ID from EditText directly
                String IDHolder = id.getText().toString();
                String NameHolder = name.getText().toString();
                String EmailHolder = email.getText().toString();

                try {
                    // Create URL object for server endpoint
                    URL url = new URL(UPDATE_URL);
                    // Open HTTP connection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    // Set request method to POST
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    // Setup output stream for writing data to server
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    // Create key-value pairs for POST data
                    HashMap<String, String> postDataParams = new HashMap<>();
                    postDataParams.put("id", IDHolder);
                    postDataParams.put("name", NameHolder);
                    postDataParams.put("email", EmailHolder);

                    // Encode POST data
                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, String> param : postDataParams.entrySet()) {
                        if (postData.length() != 0)
                            postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                    }

                    // Write POST data to output stream
                    bufferedWriter.write(postData.toString());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // Get response code from server
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Data Updated Successfully";
                    } else {
                        return "Error: " + responseCode;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            // Method to handle result after background task completes
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error updating data", Toast.LENGTH_LONG).show();
                }
            }
        }

        // Execute AsyncTask
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    // Method to delete data from the server
    public void DeleteData() {
        // AsyncTask to perform network operations on background thread
        class SendPostReqAsyncTask extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    // Create URL object for server endpoint
                    URL url = new URL(DELETE_URL);
                    // Open HTTP connection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    // Set request method to POST
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    // Setup output stream for writing data to server
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    // Encode ID parameter
                    String idParam = "id=" + URLEncoder.encode(TempID, "UTF-8");

                    // Write ID parameter to output stream
                    bufferedWriter.write(idParam);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // Get response code from server
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Data Deleted Successfully";
                    } else {
                        return "Error: " + responseCode;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            // Method to handle result after background task completes
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error deleting data", Toast.LENGTH_LONG).show();
                }
            }
        }

        // Execute AsyncTask
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }
}
