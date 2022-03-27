package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.dto.ParentSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
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
public class HorseJdbcDao implements HorseDao {

    private static final String TABLE_NAME = "horse";
    private static final Logger LOGGER = LoggerFactory.getLogger(HorseJdbcDao.class);
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SELECT_ALL_JOINED = SQL_SELECT_ALL
            + " LEFT JOIN owner ON horse.ownerid = owner.id"
            + " LEFT JOIN horse AS father ON horse.fatherid = father.id"
            + " LEFT JOIN horse AS mother ON horse.motherid = mother.id";

    private final JdbcTemplate jdbcTemplate;

    public HorseJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(HorseDto horseDto) {
        LOGGER.trace("Saving {}", horseDto);

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

        return ((Number) keyHolder.getKeys().get("id")).longValue();
    }

    @Override
    public Horse update(Long horseId, HorseDto horseDto) {
        LOGGER.trace("Updating horse with ID {} to match {}", horseId, horseDto);

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
        return null;
    }

    @Override
    public Horse getOneById(Long id) {
        LOGGER.trace("Get horse with id {}", id);

        final String sql = SQL_SELECT_ALL_JOINED
                + " WHERE horse.id = ?";

        List<Horse> horses = jdbcTemplate.query(sql, this::mapRow, id);
        if (horses.isEmpty()) {
            throw new NotFoundException(String.format("Could not find horse with id %s", id));
        }

        return horses.get(0);
    }

    @Override
    public void delete(Long id) {
        LOGGER.trace("Delete horse with id {}", id);

        final String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        int status = jdbcTemplate.update(sql, id.toString());

        if (status != 1) {
            throw new NotFoundException(String.format("Could not find horse with id %s", id));
        }

        final String deleteFatherRelations = "UPDATE " + TABLE_NAME + " SET fatherId = NULL WHERE fatherId = ?";
        jdbcTemplate.update(deleteFatherRelations, id.toString());

        final String deleteMotherRelations = "UPDATE " + TABLE_NAME + " SET motherId = NULL WHERE motherId = ?";
        jdbcTemplate.update(deleteMotherRelations, id.toString());
    }

    @Override
    public List<Horse> searchParent(ParentSearchDto parentSearchDto) {
        LOGGER.trace("Getting possible '" + parentSearchDto.parentSex()
                + "' parents of Horse(" + parentSearchDto.dateOfBirth()
                + "): " + parentSearchDto.searchString());

        Object[] parameters = new Object[]{
                "%" + parentSearchDto.searchString().toLowerCase() + "%",
                parentSearchDto.parentSex().toString(),
                parentSearchDto.dateOfBirth(),
                parentSearchDto.dateOfBirth()
        };

        final String sql = SQL_SELECT_ALL_JOINED
                + " WHERE LOWER(horse.name) LIKE ?"
                + " AND horse.sex = ?"
                + " AND (? IS NULL OR horse.dateOfBirth < ?)"
                + "LIMIT 5";

        return jdbcTemplate.query(sql, this::mapRow, parameters);
    }

    @Override
    public List<Horse> getAllChildren(Long id) {
        LOGGER.trace("Get all children of horse with id {}", id);

        String sql = "";

        Sex sex = getOneById(id).getSex();
        if (sex == Sex.male) {
            sql = SQL_SELECT_ALL_JOINED + " WHERE horse.fatherId = ?";
        }
        if (sex == Sex.female) {
            sql = SQL_SELECT_ALL_JOINED + " WHERE horse.motherId = ?";
        }
        return jdbcTemplate.query(sql, this::mapRow, id);
    }

    @Override
    public List<Horse> searchHorse(HorseSearchDto horseSearchDto) {
        LOGGER.trace("Search horse: {}", horseSearchDto);

        Object[] parameters = new Object[]{
                horseSearchDto.name() == null ? null : "%" + horseSearchDto.name().toLowerCase() + "%",
                horseSearchDto.name() == null ? null : "%" + horseSearchDto.name().toLowerCase() + "%",
                horseSearchDto.description() == null ? null : "%" + horseSearchDto.description().toLowerCase() + "%",
                horseSearchDto.description() == null ? null : "%" + horseSearchDto.description().toLowerCase() + "%",
                horseSearchDto.dateOfBirth(),
                horseSearchDto.dateOfBirth(),
                horseSearchDto.sex() == null ? null : horseSearchDto.sex().toString(),
                horseSearchDto.sex() == null ? null : horseSearchDto.sex().toString(),
                horseSearchDto.owner() == null ? null : "%" + horseSearchDto.owner().toLowerCase() + "%",
                horseSearchDto.owner() == null ? null : "%" + horseSearchDto.owner().toLowerCase() + "%"
        };

        final String sql = SQL_SELECT_ALL_JOINED
                + " WHERE (? IS NULL OR LOWER(horse.name) LIKE ?)"
                + " AND (? IS NULL OR LOWER(horse.name) LIKE ?)"
                + " AND (? IS NULL OR horse.dateOfBirth = ?)"
                + " AND (? IS NULL OR horse.sex = ?)"
                + " AND (? IS NULL OR LOWER(CONCAT(owner.firstName, owner.lastName)) LIKE ?)";

        return jdbcTemplate.query(sql, this::mapRow, parameters);
    }

    /**
     * Maps row from sql result onto horse entity.
     *
     * @param result ResultSet of the sql query
     * @param rowNum Row to convert
     * @return horse entity with values from ResultSet
     * @throws SQLException if there is an error with the database
     */
    private Horse mapRow(ResultSet result, int rowNum) throws SQLException {
        LOGGER.trace("Mapping row {} onto horse", rowNum);

        Horse horse = new Horse();
        horse.setId(result.getLong("horse.id"));
        horse.setName(result.getString("horse.name"));
        horse.setDescription(result.getString("horse.description"));
        horse.setDateOfBirth(result.getDate("horse.dateOfBirth").toLocalDate());
        horse.setSex(Sex.valueOf(result.getString("horse.sex")));

        result.getLong("horse.ownerId");
        if (!result.wasNull()) {
            Owner owner = new Owner();
            owner.setId(result.getLong("owner.id"));
            owner.setFirstName(result.getString("owner.firstName"));
            owner.setLastName(result.getString("owner.lastName"));
            owner.setEmail(result.getString("owner.email"));
            if (result.wasNull()) owner.setEmail(null);
            horse.setOwner(owner);
        }

        result.getLong("horse.fatherId");
        if (!result.wasNull()) {
            Horse father = new Horse();
            father.setId(result.getLong(13));
            father.setName(result.getString(14));
            father.setDescription(result.getString(15));
            father.setDateOfBirth(result.getDate(16).toLocalDate());
            father.setSex(Sex.valueOf(result.getString(17)));
            horse.setFather(father);
        }

        result.getLong("horse.motherId");
        if (!result.wasNull()) {
            Horse mother = new Horse();
            mother.setId(result.getLong(21));
            mother.setName(result.getString(22));
            mother.setDescription(result.getString(23));
            mother.setDateOfBirth(result.getDate(24).toLocalDate());
            mother.setSex(Sex.valueOf(result.getString(25)));
            horse.setMother(mother);
        }
        return horse;
    }
}
