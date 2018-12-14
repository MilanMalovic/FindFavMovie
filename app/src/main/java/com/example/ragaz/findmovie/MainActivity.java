package com.example.ragaz.findmovie;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ragaz.findmovie.model.NavigationItem;
import com.example.ragaz.findmovie.model.Response;
import com.example.ragaz.findmovie.model.Search;
import com.example.ragaz.findmovie.web.APIContract;
import com.example.ragaz.findmovie.web.ApiCall;
import com.example.ragaz.findmovie.web.OMDBApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private RelativeLayout drawerPane;
    private CharSequence drawerTitle;
    private CharSequence title;

    private ArrayList<NavigationItem> navigationItems = new ArrayList<NavigationItem>();

    private AlertDialog dialog;

    private boolean landscapeMode = false;
    private boolean listShown = false;
    private boolean detailShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        Button serachButton = findViewById(R.id.searchButton);
        final EditText userInput = findViewById(R.id.userInput);

        serachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSerach(userInput.getText().toString());
            }
        });

        ListView listView = (ListView) findViewById(R.id.listofMovies);

    }


    private void doSerach(String userInput) {
        OMDBApiService service = ApiCall.createRetrofit();
        //TODO: s=Batman&plot=full&apikey=3ec74ff6

        Map<String, String> params = new HashMap<>();
        params.put(APIContract.SEARCH, userInput);
        params.put(APIContract.PLOT, "full");
        params.put(APIContract.API_KEY, "c0c005f5");

        Log.i("REZ", "neki ispis 2");

        Call<Response> call = service.searchODMApi(params);
        Log.i("REZ", "neki ispis 3");
        call.enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                List<String> list = new ArrayList<>();
                if (response.code() == 200){

                    final Response resp = response.body();


                    if(resp.getSearch() != null){


                        for (Search s :resp.getSearch()) {
                            Log.i("REZ", "neki ispis 4");
                            String movieName = s.getTitle();
                            list.add(movieName);
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "This movie doesnt exist :(", Toast.LENGTH_SHORT).show();
                    }


                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, list);
                    ListView listView = (ListView) findViewById(R.id.listofMovies);

                    listView.setAdapter(dataAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Search search = resp.getSearch().get(position);
                            String imdbKey = search.getImdbID();

                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            intent.putExtra("position", imdbKey);



                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.i("REZ", "nijeProslo");
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
