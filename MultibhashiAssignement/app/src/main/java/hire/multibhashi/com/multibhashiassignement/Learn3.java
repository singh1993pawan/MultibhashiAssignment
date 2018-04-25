package hire.multibhashi.com.multibhashiassignement;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Learn3 extends AppCompatActivity {


    String aud_url;
    MediaPlayer mediaplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn3);


        ImageButton btn = (ImageButton) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Learn3.this, MainActivity.class);
                startActivity(intent);
            }
        });


        ImageButton imageButton = (ImageButton) findViewById(R.id.play);

        mediaplayer = new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                try {

                    mediaplayer.setDataSource(aud_url);
                    mediaplayer.prepare();


                } catch (IllegalArgumentException e) {

                    e.printStackTrace();
                } catch (SecurityException e) {

                    e.printStackTrace();
                } catch (IllegalStateException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }

                mediaplayer.start();


            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LearnApi.URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LearnApi categoryMenuApi = retrofit.create(LearnApi.class);

        Call<Model> categoryMenuCall = categoryMenuApi.getDataPlay();

        categoryMenuCall.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                Model list = response.body();
                LessonDatum list2 = list.getLessonData().get(4);


                aud_url = list2.getAudioUrl().toString().trim();

            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
