package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.rest.HorseEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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
public class HorseJdbcDao implements HorseDao {

    private static final String TABLE_NAME = "horse";
    private static final Logger LOGGER = LoggerFactory.getLogger(HorseJdbcDao.class);
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

    private final JdbcTemplate jdbcTemplate;

    public HorseJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Horse> getAll() {
        LOGGER.info("Getting all horses");
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRow);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not query all horses", e);
        }
    }

    @Override
    public Horse save(HorseDto horseDto) {
        LOGGER.info("Saving {}", horseDto.toString());
        final String sql = "INSERT INTO " + TABLE_NAME +
                " (name, description, dateOfBirth, sex, ownerId)" +
                " VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, horseDto.name());
            stmt.setString(2, horseDto.description());
            stmt.setString(3, horseDto.dateOfBirth().toString());
            stmt.setString(4, horseDto.sex().toString());
            stmt.setString(5, horseDto.ownerId().toString());

            return stmt;
        }, keyHolder);

        HorseMapper mapper = new HorseMapper();
        return mapper.dtoToEntity(horseDto.withID(((Number) keyHolder.getKeys().get("id")).longValue()));
    }

    private Horse mapRow(ResultSet result, int rownum) throws SQLException {
        Horse horse = new Horse();
        horse.setId(result.getLong("id"));
        horse.setName(result.getString("name"));
        horse.setDescription(result.getString("description"));
        horse.setDateOfBirth(result.getDate("dateOfBirth"));
        horse.setSex(Sex.valueOf(result.getString("sex")));
        horse.setOwnerId(result.getLong("ownerId"));
        return horse;
    }
}
