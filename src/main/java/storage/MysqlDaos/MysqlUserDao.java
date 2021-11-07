package storage.MysqlDaos;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import storage.DaoFactory;
import storage.EntityNotFoundException;
import storage.constructors.User;
import storage.daos.RoleDao;
import storage.daos.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MysqlUserDao implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public MysqlUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from user", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idUser");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Date dateofbirth = rs.getDate("dateOfBirth");
                String login = rs.getString("login");
                String password = rs.getString("password");
                RoleDao roleDao = DaoFactory.INSTANCE.getRoleDao();
                return new User(id, name, surname, dateofbirth, login, password, roleDao.getByid(rs.getLong("role")));
            }
        });
    }

    @Override
    public User save(User user) {
        if (user.getIdUser() == null)//insert
        {
            String sql = "Insert into user(name,surname,dateOfBirth,login,password,role) value(?,?,?,?,?,?)";
            int pocet = jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getDateOfBirth(), user.getLogin(), user.getPassword(), user.getRole().getIdRole());
            if (pocet == 1) {
                return user;
            }
        } else {
            String sql = "Update user Set name = ?,surname= ?,dateOfBirth= ?,login= ?,password= ?,role= ? where idUser = ?";
            int pocet = jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getDateOfBirth(), user.getLogin(), user.getPassword(), user.getRole().getIdRole(), user.getIdUser());
            if (pocet >= 1) {
                return user;
            } else {
                throw new EntityNotFoundException("user nie existuje");

            }
        }
        return user;
    }

    @Override
    public User delete(Long id) {
        User deleted = getByid(id);
        String sql = "DELETE from user where idUser =" + id;
        int pocet = jdbcTemplate.update(sql);
        return deleted;
    }

    @Override
    public User getByid(Long id) {
        try {
            String sql = "SELECT * FROM user where idUser = " + id;
            return jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idUser");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    Date dateofbirth = rs.getDate("dateOfBirth");
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    RoleDao roleDao = DaoFactory.INSTANCE.getRoleDao();
                    return new User(id, name, surname, dateofbirth, login, password, roleDao.getByid(rs.getLong("role")));
                }
            });
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("User nie existuje");
        }

    }

    @Override
    public List<User> getBySubstring(String nameSubstring, String surnameSubstring) {
        return jdbcTemplate.query("SELECT * FROM paz1c_project.user where name like \"%"+nameSubstring+"%\" and surname like \"%"+surnameSubstring+"%\";", new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idUser");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Date dateofbirth = rs.getDate("dateOfBirth");
                String login = rs.getString("login");
                String password = rs.getString("password");
                RoleDao roleDao = DaoFactory.INSTANCE.getRoleDao();
                return new User(id, name, surname, dateofbirth, login, password, roleDao.getByid(rs.getLong("role")));
            }
        });
    }
}
