package ar.edu.utn.tup.model;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateAPI {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/178784df0112b0fa8703ebb4/latest/";

    public static double getExchangeRate(String fromCurrency, String toCurrency) throws Exception {
        URL url = new URL(API_URL + fromCurrency);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        JSONObject jsonResponse = new JSONObject(content.toString());
        return jsonResponse.getJSONObject("conversion_rates").getDouble(toCurrency);
    }
}
