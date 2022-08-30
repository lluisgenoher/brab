package tcm.laq.bitcoinProjectLAQ.application.daos;

import tcm.laq.bitcoinProjectLAQ.application.dto.AdminDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidderDTO;

import java.util.List;

public interface AdminDAO {
    public AdminDTO addAdmin(AdminDTO adminDTO);

    public List<AdminDTO> getAdmins();

    public AdminDTO getAdmin(String id);
}
