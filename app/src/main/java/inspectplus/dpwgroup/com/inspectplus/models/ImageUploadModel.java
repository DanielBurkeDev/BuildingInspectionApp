package inspectplus.dpwgroup.com.inspectplus.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by skatgroovey on 19/06/2015.
 */
public class ImageUploadModel {
    @SerializedName("result")
    private  String result;
    @SerializedName("responseToken")
    private String responseToken;

    public ImageUploadModel(String result, String responseToken) {
        this.result = result;
        this.responseToken = responseToken;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResponseToken() {
        return responseToken;
    }

    public void setResponseToken(String responseToken) {
        this.responseToken = responseToken;
    }
}
