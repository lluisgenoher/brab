package tcm.laq.bitcoinProjectLAQ.persistence;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tcm.laq.bitcoinProjectLAQ.application.dto.AdminDTO;
import tcm.laq.bitcoinProjectLAQ.application.dto.BidderDTO;
import tcm.laq.bitcoinProjectLAQ.application.exception.UserNotFoundException;

import java.util.List;

@Repository
public class AdminDAO implements tcm.laq.bitcoinProjectLAQ.application.daos.AdminDAO {
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<AdminDTO> adminDTORowMapper = (resultSet, i) -> {
        AdminDTO admin = new AdminDTO();

        admin.setId(resultSet.getString("id"));
        admin.setName(resultSet.getString("name"));
        admin.setSurname(resultSet.getString("surname"));
        admin.setPassword(resultSet.getString("password"));
        admin.setEmail(resultSet.getString("email"));
        return admin;
    };

    public AdminDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ResultSetExtractorImpl<AdminDTO> adminsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(AdminDTO.class);

    @Override
    public AdminDTO addAdmin(AdminDTO adminDTO) {
        final var query = "INSERT INTO absuser(id, name, surname, password, email) values(?,?,?,?,?,?)";
        jdbcTemplate.update(query, adminDTO.getId(), adminDTO.getName(), adminDTO.getSurname(), adminDTO.getPassword(), adminDTO.getEmail());
        final var query1 = "INSERT INTO admin(id, blocked_money) values(?,?)";
        jdbcTemplate.update(query1,adminDTO.getId(),adminDTO.getBlockedMoney());

        return this.getAdmin(adminDTO.getId());
    }

    @Override
    public List<AdminDTO> getAdmins() {
        final var query = "select a.id, a.name, a.surname, a.password, a.email, b.blocked_money from absuser a join admin b on a.id=b.id";
        return jdbcTemplate.query(query,adminsRowMapper);
    }

    @Override
    public AdminDTO getAdmin(String id) {
        final var query = "select a.id, a.name, a.surname, a.password, a.email, b.blocked_money from absuser a join admin b on a.id=b.id where a.id=?";
        try{
            return jdbcTemplate.queryForObject(query, adminDTORowMapper,id);

        }catch(EmptyResultDataAccessException e){
            throw new UserNotFoundException(id);
        }
    }
}
