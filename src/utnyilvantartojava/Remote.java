package utnyilvantartojava;

import javafx.scene.control.Tab;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class Remote implements Runnable {

    private String url;
    private String json;
    private Settings settings;
    // final String URL=Constants.WebapiUrl.TEST_LINK_FOR_DATABASE_WEBAPI;
    final String URL = Constants.WebapiUrl.REMOTE_LINK_FOR_DATABASE_WEBAPI;


    public Remote(Settings settings) {
        this.settings = settings;
    }

    public Remote(String url, String jsonStr) {
        this.url = url;
        this.json = jsonStr;

    }


    public final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public String httpGet(String url) {
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


    private String httpPost(String url, String json) {
        RequestBody body = RequestBody.create(json, JSON);
        System.err.println(url);
        System.err.println(json);
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

    private String createJson(String queryType, String cim) {
        String json = new JSONObject()
                .put("lekerdezes", queryType)
                .put("cim", cim)
                .toString();
        return json;
    }

    private String createJson(String queryType, String nev, String rendszam) {
        String json = new JSONObject()
                .put("lekerdezes", queryType)
                .put("nev", nev)
                .put("rendszam", rendszam)
                .toString();
        return json;
    }

    public void updateTimeStamp(Settings settings) {
        String jsonResult = createJson("mod", settings.getNev(), settings.getRendszam());
        httpPost(URL, jsonResult);
    }


    public boolean checkUser(Tab tab) { //tab az utyilvántartó oldal amit le kell tiltani ha megbukik az ellenőrzésen a felhasználó

        boolean result;
        String jsonResult = createJson("check", settings.getNev(), settings.getRendszam());
        String request = httpPost(URL, jsonResult);
        JSONObject json = new JSONObject(request);
        if ((json.getInt("engedelyezve") == 1)) {
            System.out.println("Engedélyezve");
            result = true;
        } else {
            //showAlert("Hiba a program indítása közben!\n Validálás sikertelen!\n Van internet kapcsolat?", true, "err");
            tab.setDisable(true);
            result = false;
        }


        return result;
    }

    public void regUser() {
        String jsonResult = createJson("reg", settings.getNev(), settings.getRendszam());
        httpPost(URL, jsonResult);
    }

    @Override
    public void run() {

    }

    public Integer getDistanceFromGmaps(String txtDistance, String sAddress, String tAddress) {
        Integer distance;
        String response = "";
        String gUrl = "";
        if (txtDistance != "" || txtDistance != null) {
            gUrl = "https://www.google.hu/maps/dir/" + sAddress + "/" + tAddress;
            System.out.println(gUrl);
// itt lekérdezi a távolságot a google mapstól visszakap egy html oldalt amiben szerepel a távolság
            Remote remote = new Remote(settings);
            response = remote.httpGet(gUrl);
        }
//kikeresi a " km" szöveg kezdőindexét ez van a gmapstól visszakapott kódban a távolság után közvetlenűl
        int index = response.indexOf(" km");
        //kiszedi a távolságot megfelelően kerekíti és beírja a textboxba
        String sub = response.substring(index - 6, index);
        System.out.println(sub);
        sub = sub.replace(',', '.');
        distance = (int) Math.round(Double.parseDouble(sub.substring(sub.indexOf("\"") + 1)));
//visszaadja a távolságot

        return distance;
    }

    // URL beolvasása
   /* public static String getURL(String url) {
        StringBuilder response = null;
        try {
            java.net.URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            in.close();
        } catch (Exception e) {
            System.out.println("hiba");
        }
        return response.toString();
    }

    //ez lesz  az openstreetmaps lekérdezés ha kész lesz

    /*public void getDistanceFromOsm(String sAddress, String tAddress) {
        if (txtDistance.getText() != "" || txtDistance != null) {
            String startUrl = "192.168.1.20:8080/search/search?q=" + sAddress + "&format=xml&polygon_geojson=1&addressdetails=1";
            String targetUrl = "192.168.1.20:8080/search/search?q=" + tAddress + "&format=xml&polygon_geojson=1&addressdetails=1";
            System.out.println(startUrl);
            System.out.println(targetUrl);
            WV.getEngine().load(startUrl);
            String value = getURL(WV.getEngine().getLocation());
            System.out.println(value);
            //String gUrl = "192.168.1.20:5500/" + sAddress + "/" + tAddress;
            //System.out.println(gUrl);
            //  WV.getEngine().load(gUrl); // lekérdezi a távolságot a google mapstól
            //String gotUrl = getURL(WV.getEngine().getLocation());
            //System.out.println(gotUrl);
            /*int index = gotUrl.indexOf(" km");
                        String sub = gotUrl.substring(index - 6, index);
                        System.out.println(sub);
                        sub = sub.replace(',', '.');
                        distance = (int) Math.round(Double.parseDouble(sub.substring(sub.indexOf("\"") + 1)));
                        txtDistance.setText(distance.toString());
                        btnBev.setDisable(false);
                        cbClient.setDisable(false);
                        txtDistance.setEditable(false);


        }

    }   */

}


