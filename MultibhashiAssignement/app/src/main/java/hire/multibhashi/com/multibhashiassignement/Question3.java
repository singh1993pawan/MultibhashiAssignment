package hire.multibhashi.com.multibhashiassignement;

import android.content.Intent;
import android.media.MediaPlayer;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Question3 extends AppCompatActivity {

    String aud_url;
    MediaPlayer mediaplayer;
    String speech;
    TextView concept,pro;
    String con,pr,last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        ImageButton imageButton = (ImageButton) findViewById(R.id.btn);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Question3.this, MainActivity .class);
                startActivity(intent);
            }
        });

        concept = (TextView)findViewById(R.id.ans);

        pro = (TextView) findViewById(R.id.bns);

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
                LessonDatum list2 = list.getLessonData().get(5);


                con = list2.getConceptName().toString().trim();
                pr = list2.getTargetScript().toString().trim();
                last = list2.getPronunciation().toString().trim();

                concept.setText(con);
                pro.setText(pr);
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(intent,10);

        }
        else {
            Toast.makeText(this, "Your device does not support Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        switch (requestCode)
        {
            case 10:
                if (resultCode == RESULT_OK && data !=null)
                {
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //   Toast.makeText(this, "bjbnhjbk"+result.get(0), Toast.LENGTH_SHORT).show();
                    speech = result.get(0);
                    // ans.setText(result.get(0));

                    int per = pecentageOfTextMatch(speech,last);
                    Toast.makeText(this, "Your voice is "+per+"% matched", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public int pecentageOfTextMatch(String s0, String s1) {
        int percentage = 0;
        // Trim and remove duplicate spaces
        s0 = s0.trim().replaceAll("\\s+", " ");
        s1 = s1.trim().replaceAll("\\s+", " ");
        percentage=(int) (100 - (float) LevenshteinDistance(s0, s1) * 100 / (float) (s0.length() + s1.length()));
        return percentage;
    }


    public int LevenshteinDistance(String s0, String s1) {

        int len0 = s0.length() + 1;
        int len1 = s1.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++)
            cost[i] = i;

        // dynamicaly computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {

            // initial cost of skipping prefix in String s1
            newcost[0] = j - 1;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {

                // matching current letters in both strings
                int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete),
                        cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

}
