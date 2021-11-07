package storage.MysqlDaos;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import storage.EntityNotFoundException;
import storage.constructors.Position;
import storage.daos.PositionDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlPositionDao implements PositionDao {
    private JdbcTemplate jdbcTemplate;

    public MysqlPositionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Position> getAll() {
        String sql = "SELECT * FROM positions";
        return jdbcTemplate.query(sql, new RowMapper<Position>() {
            @Override
            public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                int floor = rs.getInt("floor");
                int positonNumber = rs.getInt("positionNumber");
                String shelf = rs.getString("shelf");
                double height = rs.getDouble("height");
                double weight = rs.getDouble("weight");
                double lenght = rs.getDouble("lenght");
                double BearingCapacity = rs.getDouble("BearingCapacity");
                return new Position(id, floor, positonNumber, shelf, height, weight, lenght, BearingCapacity);
            }
        });
    }

    @Override
    public Position save(Position position) throws EntityNotFoundException {
        Long posiiton1 = position.getIdPosiiton();
        if (posiiton1 == null)//INSERT
        {
            String sql = "INSERT INTO positions(floor,positionNumber,shelf,height,weight,lenght,BearingCapacity) VALUE(?,?,?,?,?,?,?)";
            int pocet = jdbcTemplate.update(sql, position.getFloor(), position.getPositionNumber(), position.getShelf(), position.getHeight(), position.getWeight(), position.getLength(), position.getBearingCapacity());
            if (pocet == 1) {
                return position;
            }
        } else {//update
            String sql = "UPDATE positions SET floor = ?,positionNumber = ?,shelf = ?,height = ?,weight = ?,lenght = ?,BearingCapacity = ? where id = ?";
            int pocet = jdbcTemplate.update(sql, position.getFloor(), position.getPositionNumber(), position.getShelf(), position.getHeight(), position.getWeight(), position.getLength(), position.getBearingCapacity(), position.getIdPosiiton());
            if (pocet >= 1) {
                return position;
            } else {
                throw new EntityNotFoundException("positia nie existuje");

            }
        }
        return position;
    }

    @Override
    public Position delete(Long id) throws EntityNotFoundException {
        Position posToDelete = getById(id);
        String sql = "DELETE from positions where id =" + id;
        int pocet = jdbcTemplate.update(sql);
        return posToDelete;
    }

    @Override
    public Position getById(Long id) throws EntityNotFoundException {
        try {
            String sql = "SELECT * FROM positions where id = " + id;
            return jdbcTemplate.queryForObject(sql, new RowMapper<Position>() {
                @Override
                public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
                    long id = rs.getLong("id");
                    int floor = rs.getInt("floor");
                    int positonNumber = rs.getInt("positionNumber");
                    String shelf = rs.getString("shelf");
                    double height = rs.getDouble("height");
                    double weight = rs.getDouble("weight");
                    double lenght = rs.getDouble("lenght");
                    double BearingCapacity = rs.getDouble("BearingCapacity");
                    return new Position(id, floor, positonNumber, shelf, height, weight, lenght, BearingCapacity);
                }
            });
        } catch (DataAccessException e) {
           throw new EntityNotFoundException("posiitia nie existuje");
        }
    }
}
