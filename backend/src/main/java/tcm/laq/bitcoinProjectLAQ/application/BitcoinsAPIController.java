package tcm.laq.bitcoinProjectLAQ.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.h2.util.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BitcoinsAPIController {
    private static final String URL = "http://stockmarkettrading.azurewebsites.net/stocks/bitcoins";
    private static final String URL_LAQ = "http://stockmarkettrading.azurewebsites.net/stocks/bitcoins/laq";
    RestTemplate restTemplate;

    public BitcoinsAPIController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getBitcoinValue() throws JsonProcessingException {
        var response = restTemplate.getForEntity(URL_LAQ, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        return root.path("unitPriceInEur").asText();
    }

    public void updatePriceBitcoin(double amount) {
        JsonObject json = new JsonObject();
        json.addProperty("groupId", "laq");
        json.addProperty("amount", amount);

        restTemplate.postForEntity( URL, json.toString(), String.class );
    }
}
