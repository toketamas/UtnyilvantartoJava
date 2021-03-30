package utnyilvantartojava;

import com.mysql.cj.xdevapi.JsonParser;
import com.mysql.cj.xdevapi.JsonString;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import java.util.Map;

public class Remote {

    public Remote(){}
    public static final MediaType JSON= MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public String run(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Kérés elküldve");
            return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            return "A szerver nem válaszolt!";
        }

    }


    public String post(String url, String json) {
        RequestBody body = RequestBody.create(json,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "A szerver nem válaszolt!";
        }

    }

    public String createJson(String queryType,String nev,String varos,String cim){
        String json = new JSONObject()
                .put("lekerdezes",queryType)
                .put("nev",nev)
                .put("varos",varos)
                .put("cim",cim)
                .toString();
        return json;
    }

    public String createJson(String queryType,String nev,String varos,String cim,String rendszam){
        String json = new JSONObject()
                .put("lekerdezes",queryType)
                .put("nev",nev)
                .put("varos",varos)
                .put("cim",cim)
                .put("rendszam",rendszam)
                .toString();
        return json;
    }




}

