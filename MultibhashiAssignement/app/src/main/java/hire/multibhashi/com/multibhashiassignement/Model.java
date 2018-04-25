
package hire.multibhashi.com.multibhashiassignement;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("lesson_data")
    @Expose
    private List<LessonDatum> lessonData = null;

    public List<LessonDatum> getLessonData() {
        return lessonData;
    }

    public void setLessonData(List<LessonDatum> lessonData) {
        this.lessonData = lessonData;
    }

}
