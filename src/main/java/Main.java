import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alex on 07.06.2018.
 */
public class Main {

    public static String CURRENCY_UAH = "UAH";
    public static String CURRENCY_GBP = "GBP";
    public static String CURRENCY_EUR = "EUR";

    public static void main(String[] args) {
        System.out.println(exchange(0, "UAH"));

    }


    private static String exchange(int amount, String to_Currency){
        if(!to_Currency.equals(CURRENCY_GBP) && !to_Currency.equals(CURRENCY_EUR) && !to_Currency.equals(CURRENCY_UAH))
        {
            return "Wrong Currency value! Select one of available : UAH, GBP, EUR";
        }
        if(amount <= 0){
            return "Amount must be bigger then zero";
        }
        try {
            URL url = new URL("https://free.currencyconverterapi.com/api/v5/convert?q=USD_"+to_Currency+"&compact=ultra");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
          //  conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br1 = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            StringBuilder sb = new StringBuilder();
            System.out.println("Output from Server .... \n");
            while ((output = br1.readLine()) != null) {
                sb.append(output);
            }
            JSONObject jsonObject = new JSONObject(sb.toString());

            conn.disconnect();

            Float value = Float.parseFloat(jsonObject.get("USD_"+to_Currency).toString())*amount;
            return value.toString();

        } catch (MalformedURLException e) {

            return e.toString();

        } catch (IOException e) {

            return e.toString();

        }


    }
}
