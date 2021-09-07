package alfabanktest.test.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "currency", url = "${currency.url}")
public interface CurrencyFeign {


    @RequestMapping(value = "/{date}?app_id={id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCurrency(@PathVariable("date") String date, @PathVariable("id") String id);
}
