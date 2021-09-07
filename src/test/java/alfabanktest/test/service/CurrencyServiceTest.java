package alfabanktest.test.service;

import alfabanktest.test.rest.CurrencyFeign;
import alfabanktest.test.rest.GifFeign;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyServiceTest {

    @Value("${currency.id}")
    private String id;

    @Value("${gif.api_key}")
    private String api_key;

    @Value("${gif.rich}")
    private String rich;

    @Value("${gif.broke}")
    private String broke;

    @Autowired
    CurrencyService currencyService;

    @MockBean
    CurrencyFeign currencyFeign;

    @MockBean
    GifFeign gifFeign;

    @Test
    public void getGifRich() throws JSONException {

        Mockito.when(currencyFeign.getCurrency(LocalDate.now() + ".json", id))
                .thenReturn("{\n" +
               "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
               "  \"license\": \"https://openexchangerates.org/license\",\n" +
               "  \"timestamp\": 1631016000,\n" +
               "  \"base\": \"USD\",\n" +
               "  \"rates\": {\n" +
               "    \"AED\": 3.672989,\n" +
               "    \"RUB\": 73.25475,\n" +
               "    \"ZWL\": 322\n" +
               "  }\n" +
               "}");
        Mockito.when(currencyFeign.getCurrency(LocalDate.now().minusDays(1) + ".json", id))
                .thenReturn("{\n" +
                "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                "  \"license\": \"https://openexchangerates.org/license\",\n" +
                "  \"timestamp\": 1631016000,\n" +
                "  \"base\": \"USD\",\n" +
                "  \"rates\": {\n" +
                "    \"AED\": 3.672989,\n" +
                "    \"RUB\": 70.25475,\n" +
                "    \"ZWL\": 322\n" +
                "  }\n" +
                "}");
        Mockito.when(gifFeign.getGif(api_key, broke))
                .thenReturn("{\n" +
                        "    \"data\": {\n" +
                        "        \"embed_url\": \"https://giphy.com/embed/broke\",\n" +
                        "        \"caption\": \"\"\n" +
                        "    },\n" +
                        "    \"meta\": {\n" +
                        "        \"status\": 200,\n" +
                        "        \"msg\": \"OK\",\n" +
                        "        \"response_id\": \"d7ca5afec8fa540f10f4a4b50abf0d07e67230f3\"\n" +
                        "    }\n" +
                        "}");
        Mockito.when(gifFeign.getGif(api_key, rich))
                .thenReturn("{\n" +
                        "    \"data\": {\n" +
                        "        \"embed_url\": \"https://giphy.com/embed/rich\",\n" +
                        "        \"caption\": \"\"\n" +
                        "    },\n" +
                        "    \"meta\": {\n" +
                        "        \"status\": 200,\n" +
                        "        \"msg\": \"OK\",\n" +
                        "        \"response_id\": \"d7ca5afec8fa540f10f4a4b50abf0d07e67230f3\"\n" +
                        "    }\n" +
                        "}");

        String s = currencyService.getGif("RUB");
        Assert.assertEquals(s, "https://giphy.com/embed/rich");
        Mockito.verify(currencyFeign).getCurrency(LocalDate.now() + ".json", id);
        Mockito.verify(currencyFeign).getCurrency(LocalDate.now().minusDays(1) + ".json", id);
        Mockito.verify(gifFeign).getGif(api_key, rich);
    }
}