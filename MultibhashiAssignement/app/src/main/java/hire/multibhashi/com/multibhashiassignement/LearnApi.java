package hire.multibhashi.com.multibhashiassignement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LearnApi {
    public static String URl = "http://www.akshaycrt2k.com/";

    @GET("getLessonData.php")
    Call<Model> getDataPlay();

}
