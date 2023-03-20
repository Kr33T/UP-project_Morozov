package com.example.up_project_morozov;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main extends AppCompatActivity {

    private AdapterQuotes pAdapter;
    private List<QuotesModel> listQuote = new ArrayList<>();

    private AdapterFeelings pAdapter2;
    private List<FeelingsModel> listFeeling = new ArrayList<>();

    ImageView imageProfile;
    TextView textHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView ivProducts = findViewById(R.id.quotesLV);
        pAdapter = new AdapterQuotes(Main.this, listQuote);
        ivProducts.setAdapter(pAdapter);
        new GetQuotes().execute();

        RecyclerView rvFeeling = findViewById(R.id.recyclerView);
        rvFeeling.setHasFixedSize(true);
        rvFeeling.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        pAdapter2 = new AdapterFeelings(listFeeling, Main.this);
        rvFeeling.setAdapter(pAdapter2);
        new GetFeeling().execute();

        imageProfile = findViewById(R.id.profileIMG);
        new AdapterQuotes.DownloadImageTask((ImageView) imageProfile).execute(OnBoarding.userImage);

        textHello = findViewById(R.id.greetingsTV);
        textHello.setText(textHello.getText().toString() + OnBoarding.nickName + "!");
    }

    private class GetQuotes extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                listQuote.clear();
                pAdapter.notifyDataSetInvalidated();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    QuotesModel tempProduct = new QuotesModel(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getString("description")
                    );
                    listQuote.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }

            }
            catch (Exception exception)
            {
                Toast.makeText(Main.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetFeeling extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                listFeeling.clear();
                pAdapter2.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    FeelingsModel tempProduct = new FeelingsModel(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getInt("position")
                    );
                    listFeeling.add(tempProduct);
                    pAdapter2.notifyDataSetChanged();
                }
                listFeeling.sort(Comparator.comparing(FeelingsModel::getPosition));
                pAdapter2.notifyDataSetChanged();
            }
            catch (Exception exception)
            {
                Toast.makeText(Main.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void nextMenu(View view)
    {
        startActivity(new Intent(this, Menu.class));
    }

    public  void nextProfile(View view)
    {
        startActivity(new Intent(this, Profile.class));
    }

    public void nextListen(View view)
    {
        startActivity(new Intent(this, Listen.class));
    }
}