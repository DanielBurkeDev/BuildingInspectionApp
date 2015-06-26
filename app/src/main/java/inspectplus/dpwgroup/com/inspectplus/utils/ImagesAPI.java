package inspectplus.dpwgroup.com.inspectplus.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import inspectplus.dpwgroup.com.inspectplus.models.ImageUploadModel;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;

/**
 * Created by skatgroovey on 19/06/2015.
 */
public interface ImagesAPI {
//    public static final String BASE_URL = "http://10.0.3.2";
    public static final String BASE_URL = "http://dpw.developerexpert.com";

    @Multipart
    @POST("/demo/dpw/services.php")
    void upload(@PartMap Map<String, String> params,
                @Part("imageFile") TypedFile file,
                Callback<ImageUploadModel> cb);

//    @FormUrlEncoded
//    @POST("/servicesample/services.php")
//    void upload(@Field("command") String command,
//                @Field("token") String token,
//                @Field("projectId") String projectId,
//                @Field("inspectionEventId") String inspectionEventId,
//                @Field("notes") String notes,
//                @Field("metadata") String metadata,
//                @Field("dateAcquired") String dateAcquired,
//                @Field("Access") String Access,
//                @Field("imageFile") TypedFile file,
//                //  Callback<ImageUploadModel> cb);
//                Callback<Response> cb);


//    @Multipart
//    @POST("/servicesample/services.php")
//    void upload(@Part("command") String command,
//                @Part("token") String token,
//                @Part("projectId") String projectId,
//                @Part("inspectionEventId") String inspectionEventId,
//                @Part("notes") String notes,
//                @Part("metadata") String metadata,
//                @Part("dateAcquired") String dateAcquired,
//                @Part("Access") String Access,
//                @Part("imageFile") TypedFile file,

//    @Multipart
//    @POST("/servicesample/services.php")
//    void upload( @Part("imageFile") TypedFile file,

   //            Callback<ImageUploadModel> cb);
    //            Callback<Response> cb);
   // Callback<String> cb);
   //          Callback<JSONObject> cb);


//    @Multipart
//    @POST("/servicesample/services.php")
//    void upload(@Part("imageFile") TypedFile file,
//                @PartMap Map<String, String> params,

//               Callback<JsonObject> cb);
//               Callback<JsonElement> cb);
//    //               Callback<Response> response);
//              Callback<String> cb);



//    @FormUrlEncoded
//    @POST("/servicesample/services.php")
//    void paramsUp(@Field("command") String command,
//                @Field("token") String token,
//                @Field("projectId") String projectId,
//                @Field("inspectionEventId") String inspectionEventId,
//                @Field("notes") String notes,
//                @Field("metadata") String metadata,
//                @Field("dateAcquired") String dateAcquired,
//                @Field("Access") String Access,
//                //  Callback<ImageUploadModel> cb);
//                //Callback<Response> cb);
//                  Callback<JsonElement> cb);

}
