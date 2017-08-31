package com.java.academy.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TempSender {

    private final static String USER_AGENT = "Mozilla/5.0";
    private static String aaaa = "{\"books\":[{\"title\":\"Dowiesz się ostatnia\",\"author\":\"M. Hill\",\"category\":\"book\",\"promoDetails\":\" -30%\",\"price\":27.870000000000000994759830064140260219573974609375,\"imgUrl\":\"http://www.gandalf.com.pl/o/dowiesz-sie-ostatnia,pd,760260.jpg\",\"url\":\"http://www.gandalf.com.pl/b/dowiesz-sie-ostatnia/\",\"bookstore\":{\"name\":\"Gandalf\",\"url\":\"http://www.gandalf.com.pl\"}}]}";


    // HTTP Post request
    public static void sendingPostRequest(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type","application/json");

        String postJsonData = aaaa;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postJsonData);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + postJsonData);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //printing result from response
        System.out.println(response.toString());
    }
}
