package tcm.laq.bitcoinProjectLAQ.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tcm.laq.bitcoinProjectLAQ.application.AdminController;
import tcm.laq.bitcoinProjectLAQ.application.BidderController;
import tcm.laq.bitcoinProjectLAQ.application.dto.AdminDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidderDTO;

import java.util.List;

@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AdminRestController {

    AdminController adminController;

    public AdminRestController(AdminController adminController){
        this.adminController = adminController;
    }

    @CrossOrigin
    @GetMapping("/admins")
    public List<AdminDTO> getAllBidders(){
        return adminController.getAllAdmins();
    }

    @CrossOrigin
    @GetMapping("/admin/{id}")
    public AdminDTO getBidder(@PathVariable String id){
        return adminController.getAdmin(id);
    }

    @CrossOrigin
    @PostMapping("/admin")
    public void postBidder(@RequestBody AdminDTO bidder){
        adminController.setNewAdmin(bidder);
    }
}
