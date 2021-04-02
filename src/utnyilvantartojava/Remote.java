package utnyilvantartojava;

import com.mysql.cj.xdevapi.JsonParser;
import com.mysql.cj.xdevapi.JsonString;
import javafx.scene.control.Tab;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import java.util.Map;

abstract class Remote extends ViewController{


    JSONObject json;

    private static String jsonresult;

    public static final MediaType JSON= MediaType.get("application/json; charset=utf-8");
    static OkHttpClient client = new OkHttpClient();

    private String httpGet(String url) {
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


    private static String httpPost(String url, String json) {
        RequestBody body = RequestBody.create(json,JSON);
        System.err.println(url);
        System.err.println(json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            System.err.println(response.body().string());
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "A szerver nem válaszolt!";
        }

    }

    private  String createJson(String queryType,String nev,String varos,String cim){
        String json = new JSONObject()
                .put("lekerdezes",queryType)
                .put("nev",nev)
                .put("varos",varos)
                .put("cim",cim)
                .toString();
        return json;
    }

    private static String createJson(String queryType, String nev, String varos, String cim, String rendszam){
        String json = new JSONObject()
                .put("lekerdezes",queryType)
                .put("nev",nev)
                .put("varos",varos)
                .put("cim",cim)
                .put("rendszam",rendszam)
                .toString();
        return json;
    }

    static String jsonResult;
    public static boolean checkUser(Settings settings, Tab tab){

        jsonResult=createJson("check",settings.getNev(),settings.getVaros(),settings.getCim(),settings.getRendszam());
        String request= httpPost(Constants.WebapiUrl.TEST_LINK_FOR_DATABASE_WEBAPI,jsonResult);
        JSONObject json = new JSONObject(request);
        if((json.getInt("engedelyezve")==1))
            System.out.println("Engedélyezve");
        else {
            //showAlert("Hiba a program indítása közben!\n Validálás sikertelen!\n Van internet kapcsolat?", true, "err");
            tab.setDisable(true);
        }


        return true;
    }

    //Ellenőrzi hogy engedélyezve van e a felhasználó a mysql db-ben

    //!!!!!!!!!!!!!!!!!!!!! Ez itt a http kérés tesztje!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
/*
         //remote=new Remote();
        //System.out.println(remote.run(utnyilvUrl));
        //jsonResult=null;
        jsonResult = remote.createJson("check",settings.getNev(),settings.getVaros(),settings.getCim(),settings.getRendszam());
        System.out.println(jsonResult);
        System.out.println(utnyilvUrl);
        String req =remote.post(utnyilvUrl,jsonResult);

        json = new JSONObject(req);
        System.out.println(json.getInt("engedelyezve"));
       if((json.getInt("engedelyezve")==1))
           System.out.println("Engedélyezve");
       else {
           showAlert("Hiba a program indítása közben!\n Validálás sikertelen!\n Van internet kapcsolat?", true, "err");
           tabNyilv.setDisable(true);
       }

 */
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

// regisztrál a mysql-be
    // Remote remote=new Remote();
    //jsonResult=null;
    //jsonResult = remote.createJson("add",settings.getNev(),settings.getVaros(),settings.getCim(),settings.getRendszam());
    // System.out.println(remote.post(utnyilvUrl,jsonResult));

    //db.addRegToMySql(settings.getNev(), settings.getVaros(), settings.getCim(), settings.getRendszam());
// frissíti a hozzáférés idejét
    //db.updateRegMysql(settings.getNev(), settings.getVaros(), settings.getCim(), settings.getRendszam());
    //String utnyilvUrl = "https://mju7nhz6bgt5vfr4cde3xsw2yaq1.tfsoft.hu/";
    //String utnyilvUrl ="http://localhost/utnyilvDB/";

    //System.out.println(remote.run(utnyilvUrl));
    //jsonResult=null;
    //jsonResult = remote.createJson("mod",settings.getNev(),settings.getVaros(),settings.getCim(),settings.getRendszam());
    //System.out.println(jsonResult);
    //System.out.println(remote.post(utnyilvUrl,jsonResult));


}

