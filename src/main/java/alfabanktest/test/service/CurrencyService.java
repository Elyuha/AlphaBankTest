package alfabanktest.test.service;

import alfabanktest.test.rest.CurrencyFeign;
import alfabanktest.test.rest.GifFeign;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CurrencyService {
    @Value("${currency.id}")
    private String id;

    @Value("${gif.api_key}")
    private String api_key;

    @Value("${gif.rich}")
    private String rich;

    @Value("${gif.broke}")
    private String broke;

    @Autowired
    CurrencyFeign currencyFeign;

    @Autowired
    GifFeign gifFeign;

    public String getGif(String currencyName) throws JSONException {
        double today = new JSONObject(
                currencyFeign.getCurrency(
                        LocalDate.now() + ".json", id))
                .getJSONObject("rates").getDouble(currencyName);
        double yesterday = new JSONObject(
                currencyFeign.getCurrency(
                        LocalDate.now().minusDays(1) + ".json", id))
                .getJSONObject("rates").getDouble(currencyName);

        String gif;
        if (today > yesterday)
            gif = gifFeign.getGif(api_key, rich);
        else if(today < yesterday)
            gif = gifFeign.getGif(api_key, broke);
        else return "Гифки не будет, курс не поменялся!";

        return new JSONObject(gif)
                .getJSONObject("data")
                .getString("embed_url");
        
    }

    public GifFeign i(){
        return gifFeign;
    }
}
