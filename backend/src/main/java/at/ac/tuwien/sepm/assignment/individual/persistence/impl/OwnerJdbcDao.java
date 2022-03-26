package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.dto.OwnerSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.mapper.OwnerMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class OwnerJdbcDao implements OwnerDao {

    private static final String TABLE_NAME = "owner";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerJdbcDao.class);

    private final OwnerMapper ownerMapper;
    private final JdbcTemplate jdbcTemplate;

    public OwnerJdbcDao(OwnerMapper ownerMapper, JdbcTemplate jdbcTemplate) {
        this.ownerMapper = ownerMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Owner> getAll() {
        LOGGER.info("Getting all owners");
        return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRow);
    }

    @Override
    public Owner save(OwnerDto ownerDto) {
        final String sql = "INSERT INTO " + TABLE_NAME
                + " (firstName, lastName, email)"
                + " VALUES (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, ownerDto.firstName());
            stmt.setString(2, ownerDto.lastName());
            stmt.setString(3, ownerDto.email());
            return stmt;
        }, keyHolder);

        Owner owner = ownerMapper.dtoToEntity(ownerDto);
        owner.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        return owner;
    }

    @Override
    public List<Owner> searchOwner(OwnerSearchDto ownerSearchDto) {
        LOGGER.info("Getting possible owners matching '{}'", ownerSearchDto.name());
        final String sql = SQL_SELECT_ALL
                + " WHERE LOWER(CONCAT(firstName, lastName)) like ?"
                + " LIMIT 5";

        return jdbcTemplate.query(sql, this::mapRow,
                "%" + ownerSearchDto.name().toLowerCase() + "%");
    }

    /**
     * Maps row from sql result onto owner entity.
     *
     * @param result ResultSet of the sql query
     * @param rowNum Row to convert
     * @return owner entity with values from ResultSet
     * @throws SQLException if there is an error with the database
     */
    private Owner mapRow(ResultSet result, int rowNum) throws SQLException {
        LOGGER.trace("Mapping row {} onto owner", rowNum);

        Owner owner = new Owner();
        owner.setId(result.getLong("id"));
        owner.setFirstName(result.getString("firstName"));
        owner.setLastName(result.getString("lastName"));
        owner.setEmail(result.getString("email"));
        if (result.wasNull()) owner.setEmail(null);

        return owner;
    }
}
