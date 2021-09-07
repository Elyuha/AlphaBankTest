package alfabanktest.test.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "gif", url = "${gif.url}")
public interface GifFeign {
    @RequestMapping(value = "/random?api_key={api_key}&tag={status}&rating=g",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getGif(@PathVariable("api_key") String api_key,
                         @PathVariable("status") String status);
}
