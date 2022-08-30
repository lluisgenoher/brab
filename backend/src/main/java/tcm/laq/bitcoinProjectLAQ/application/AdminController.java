package tcm.laq.bitcoinProjectLAQ.application;

import org.springframework.stereotype.Component;
import tcm.laq.bitcoinProjectLAQ.application.daos.AdminDAO;
import tcm.laq.bitcoinProjectLAQ.application.dto.AdminDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidderDTO;

import java.util.List;
import java.util.UUID;

@Component
public class AdminController {

    AdminDAO adminDAO;

    public AdminController(AdminDAO adminDAO){
        this.adminDAO = adminDAO;
    }

    public void setNewAdmin(AdminDTO admin){
        admin.setId(UUID.randomUUID().toString());
        adminDAO.addAdmin(admin);
    }

    public AdminDTO getAdmin(String id){
        return adminDAO.getAdmin(id);
    }

    public List<AdminDTO> getAllAdmins(){
        return adminDAO.getAdmins();
    }
}
