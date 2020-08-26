package com.ss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.net.HttpStatus;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.ss.interfaces.IHttpRequest;
import com.ss.object.Data.FarmModel;
import java.util.ArrayList;
import java.util.List;

public class HttpRequests {

    private final String URL = "https://server-rank-game.herokuapp.com/tuyen";
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private Preferences pref = GMain.prefs;
    private static HttpRequests instance;
    private IHttpRequest iHttpRequest;

    private Gson gson = new Gson();

    public static HttpRequests getInstance() {
        return instance == null ? instance = new HttpRequests() : instance;
    }

    public void getAllMoney() {

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(URL + "/get-all-money");

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("failed!");
                    return;
                }

                String result = httpResponse.getResultAsString();
                JsonArray jsonArray = gson.fromJson(result, JsonArray.class);

                List<FarmModel> lsRankModel = new ArrayList<>();
                for (int i=0; i<jsonArray.size(); i++) {
                    FarmModel rankModel = gson.fromJson(jsonArray.get(i), FarmModel.class);
                    lsRankModel.add(rankModel);
                }

                System.out.println("LIST: " + lsRankModel.size());

                iHttpRequest.finished(lsRankModel);

            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void cancelled() {

            }
        });

    }

    public void postNewMoney(long money) {

        NameGenerator name = new NameGenerator((int) (Math.random() * 10));
        String namePlayer = name.getName();

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl(URL + "/post-money");
        request.setHeader("Content-Type", CONTENT_TYPE);
        request.setContent("money="+money+"&name="+namePlayer);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                int status = httpResponse.getStatus().getStatusCode();
                String result = httpResponse.getResultAsString();

                if (status == 200) {
                    FarmModel rankModel = gson.fromJson(result, FarmModel.class);
                    String id = rankModel.get_id();
                    pref.putString("_id", id);
                    pref.putLong("best_money", money);
                    pref.flush();
                }

            }

            @Override
            public void failed(Throwable t) {

                pref.putLong("best_money", money);
                pref.flush();

            }

            @Override
            public void cancelled() {

            }
        });

    }

    public void updateMoney(String id, long money) {

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl(URL + "/update-money");
        request.setHeader("Content-Type", CONTENT_TYPE);
        request.setContent("_id="+id+"&money="+money);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                int status = httpResponse.getStatus().getStatusCode();
                String result = httpResponse.getResultAsString();

                if (status == 200) {
                    pref.putLong("best_money", money);
                    pref.flush();
                }

            }

            @Override
            public void failed(Throwable t) {

                pref.putLong("best_money", money);
                pref.flush();

            }

            @Override
            public void cancelled() {
                System.out.println("Cancel");
            }
        });

    }

    public void setiHttpRequest(IHttpRequest iHttpRequest) {
        this.iHttpRequest = iHttpRequest;
    }

}
