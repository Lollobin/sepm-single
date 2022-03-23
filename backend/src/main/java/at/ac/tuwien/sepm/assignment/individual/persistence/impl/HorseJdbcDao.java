package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class HorseJdbcDao implements HorseDao {

    private static final String TABLE_NAME = "horse";
    private static final Logger LOGGER = LoggerFactory.getLogger(HorseJdbcDao.class);
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final HorseMapper MAPPER = new HorseMapper();

    private final JdbcTemplate jdbcTemplate;

    public HorseJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Horse> getAll() {
        LOGGER.info("Getting all horses");
        return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRow);
    }

    @Override
    public Horse save(HorseDto horseDto) {
        LOGGER.info("Saving {}", horseDto.toString());
        final String sql = "INSERT INTO " + TABLE_NAME +
                " (name, description, dateOfBirth, sex, ownerId, fatherId, motherId)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, horseDto.name());
            stmt.setString(2, horseDto.description());
            stmt.setString(3, horseDto.dateOfBirth().toString());
            stmt.setString(4, horseDto.sex().toString());
            stmt.setString(5, horseDto.ownerId() == null ? null : horseDto.ownerId().toString());
            stmt.setString(6, horseDto.fatherId() == null ? null : horseDto.fatherId().toString());
            stmt.setString(7, horseDto.motherId() == null ? null : horseDto.motherId().toString());
            return stmt;
        }, keyHolder);

        Horse horse = MAPPER.dtoToEntity(horseDto);
        horse.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        return horse;
    }

    @Override
    public Horse update(Long horseId, HorseDto horseDto) {
        LOGGER.info("Updating horse with ID {} to match {}", horseId, horseDto);

        this.getOneById(horseId);

        final String sql = "UPDATE " + TABLE_NAME +
                " SET name = ?, description = ?, dateOfBirth = ?, sex = ?, ownerId = ?, fatherId = ?, motherId = ?" +
                " WHERE id = ?;";

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, horseDto.name());
            stmt.setString(2, horseDto.description());
            stmt.setString(3, horseDto.dateOfBirth().toString());
            stmt.setString(4, horseDto.sex().toString());
            stmt.setString(5, horseDto.ownerId() == null ? null : horseDto.ownerId().toString());
            stmt.setString(6, horseDto.fatherId() == null ? null : horseDto.fatherId().toString());
            stmt.setString(7, horseDto.motherId() == null ? null : horseDto.motherId().toString());
            stmt.setString(8, horseId.toString());
            return stmt;
        });

        Horse horse = MAPPER.dtoToEntity(horseDto);
        horse.setId(horseId);

        return horse;
    }

    @Override
    public Horse getOneById(Long id) {
        LOGGER.info("Get horse with id {}", id);

        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Horse> horses = jdbcTemplate.query(sql, this::mapRow, id);
        if (horses.isEmpty()) {
            throw new NotFoundException(String.format("Could not find horse with id %s", id));
        }

        return horses.get(0);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Delete horse with id {}", id);
        //todo set entries in fatherId or motherId equal to id to null

        final String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        int status = jdbcTemplate.update(sql, id.toString());

        if (status != 1) {
            throw new NotFoundException(String.format("Could not find horse with id %s", id));
        }
    }

    @Override
    public List<Horse> searchParent(Date dateOfBirth, Sex parentSex, String searchString) {
        LOGGER.info("Getting possible " + parentSex + " parents of Horse(" + dateOfBirth + "): " + searchString);

        final String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE LOWER(name) LIKE ?" +
                " AND sex = ?" +
                " AND dateOfBirth <= ?" +
                "LIMIT 5";

        return jdbcTemplate.query(sql, this::mapRow,
                "%" + searchString.toLowerCase() + "%",
                parentSex.toString(),
                dateOfBirth);
    }

    private Horse mapRow(ResultSet result, int rownum) throws SQLException {
        LOGGER.trace("Mapping row {} onto horse", rownum);
        Horse horse = new Horse();
        horse.setId(result.getLong("id"));
        if(result.wasNull()) horse.setId(null);
        horse.setName(result.getString("name"));
        horse.setDescription(result.getString("description"));
        horse.setDateOfBirth(result.getDate("dateOfBirth"));
        horse.setSex(Sex.valueOf(result.getString("sex")));
        horse.setOwnerId(result.getLong("ownerId"));
        if(result.wasNull()) horse.setOwnerId(null);
        horse.setFatherId(result.getLong("fatherId"));
        if(result.wasNull()) horse.setFatherId(null);
        horse.setMotherId(result.getLong("motherId"));
        if(result.wasNull()) horse.setMotherId(null);
        return horse;
    }
}
