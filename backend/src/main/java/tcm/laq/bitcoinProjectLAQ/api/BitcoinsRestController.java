package tcm.laq.bitcoinProjectLAQ.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import tcm.laq.bitcoinProjectLAQ.application.BitcoinsAPIController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/bitcoins")
@RestController
public class BitcoinsRestController {
    private BitcoinsAPIController bitcoinsAPIController;

    public BitcoinsRestController(BitcoinsAPIController controller){
        this.bitcoinsAPIController=controller;
    }

    @GetMapping("/value")
    public String getBitcoinValue() throws JsonProcessingException {
        return bitcoinsAPIController.getBitcoinValue();
    }

    @PostMapping("/buy/{amount}")
    public void updatePriceBitcoin(@PathVariable double amount) throws JsonProcessingException {
        bitcoinsAPIController.updatePriceBitcoin(amount);
    }
}
