package alfabanktest.test.rest;

import alfabanktest.test.service.CurrencyService;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {
    @Autowired
    CurrencyService currencyService;

    @RequestMapping(value = "/ratio",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRatio(@RequestBody Request currencyName){
        try {
            if (currencyName.getCurrencyName() == null)
                throw new NullPointerException();
            return currencyService.getGif(currencyName.getCurrencyName());
        } catch (NullPointerException e){
            return "Имя валюты не может быть пустым";
        }
        catch (JSONException e){
            return "Такой валюты нет";
        }
    }
}
