package com.example.ragaz.findmovie;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ragaz.findmovie.model.Movie;
import com.example.ragaz.findmovie.web.APIContract;
import com.example.ragaz.findmovie.web.ApiCall;
import com.example.ragaz.findmovie.web.OMDBApiService;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends Activity {


    private static int NOTIFICATION_ID = 1;
    private int position = 0;
    Button buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);
        Button Buy = findViewById(R.id.btn_watch);

        final String imdbKey = getIntent().getStringExtra("position");

        getMovieByID(imdbKey);
    }

    private void getMovieByID(String id) {
        Log.i("REZ", "getMovieByID2");
        OMDBApiService service = ApiCall.createRetrofit();

        Map<String, String> params = new HashMap<>();
        params.put(APIContract.IMDB_KEY, id);
        params.put(APIContract.API_KEY, "c0c005f5");


        Call<Movie> call = service.getInfoDataByID(params);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {
                    Movie m = response.body();


                    ImageView imageView = SecondActivity.this.findViewById(R.id.iv_image);
                    Picasso.with(SecondActivity.this).load(m.getPoster()).into(imageView);

                    TextView tv_title = SecondActivity.this.findViewById(R.id.tv_title);
                    tv_title.setText(String.format("Title: %s", m.getTitle()));

                    TextView tv_year = SecondActivity.this.findViewById(R.id.tv_year);
                    tv_year.setText(String.format("Year: %s", m.getYear()));

                    TextView tv_actors = SecondActivity.this.findViewById(R.id.tv_actors);
                    tv_actors.setText(String.format("Actors: %s", m.getActors()));

                    TextView tv_writer = SecondActivity.this.findViewById(R.id.tv_writer);
                    tv_writer.setText(String.format("Writers: %s", m.getWriter()));

                    TextView tv_director = SecondActivity.this.findViewById(R.id.tv_director);
                    tv_director.setText(String.format("Director: %s", m.getDirector()));


                    TextView tv_language = SecondActivity.this.findViewById(R.id.tv_language);
                    tv_language.setText(String.format("Language: %s", m.getLanguage()));


                    TextView tv_country = SecondActivity.this.findViewById(R.id.tv_country);
                    tv_country.setText(String.format("Country: %s", m.getCountry()));


                    RatingBar rb_rating = SecondActivity.this.findViewById(R.id.rb_rating);
                    rb_rating.setRating(Float.parseFloat(m.getImdbRating()));


                    Button buy = SecondActivity.this.findViewById(R.id.btn_watch);
                    buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(SecondActivity.this, " Stavljeno u korpu ", Toast.LENGTH_LONG).show();
                            clickMe();

                        }
                    });
                }


            }

            private void clickMe(){

                NotificationCompat.Builder mBuilder;
                mBuilder = new NotificationCompat.Builder(SecondActivity.this).setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Korpa")
                        .setContentText("Ukupnon filmova");

                NotificationManager  nm= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                nm.notify(0, mBuilder.build());



            }




            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(SecondActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}

