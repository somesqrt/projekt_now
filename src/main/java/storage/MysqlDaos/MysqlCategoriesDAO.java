package storage.MysqlDaos;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import storage.constructors.Categories;
import storage.EntityNotFoundException;
import storage.daos.CategoriesDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlCategoriesDAO  implements CategoriesDAO {
    private JdbcTemplate jdbcTemplate;

    public MysqlCategoriesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Categories> getAll() {
        String sql = "SELECT * FROM categories order by idCategories asc;";
        return jdbcTemplate.query(sql, new RowMapper<Categories>() {
            @Override
            public Categories mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idCategories");
                String category = rs.getString("categoria");
                return new Categories(id,category);
            }
        });
    }

    @Override
    public Categories save(Categories categories) {
        if (categories.getIdCategories() == null)//INSERT
        {
            String sql = "INSERT INTO categories(categoria) VALUE(?)";
            int pocet = jdbcTemplate.update(sql, categories.getCategoria());
            if (pocet == 1) {
                return categories;
            }
        } else {//update
            String sql = "UPDATE categories SET categoria = ? where idCategories = ?";
            int pocet = jdbcTemplate.update(sql, categories.getCategoria(),categories.getIdCategories());
            if (pocet >= 1) {
                return categories;
            } else {
                throw new EntityNotFoundException("positia nie existuje");

            }
        }
        return categories;
    }

    @Override
    public Categories delete(Long id) {
        Categories catToDelete = getbyID(id);
        String sql = "DELETE from categories where idCategories =" + id;
        int pocet = jdbcTemplate.update(sql);
        return catToDelete;
    }

    @Override
    public Categories getbyID(Long id) throws EntityNotFoundException {
        try {
            String sql = "SELECT * FROM categories where idCategories = " + id;
            return jdbcTemplate.queryForObject(sql, new RowMapper<Categories>() {
                @Override
                public Categories mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idCategories");
                    String category = rs.getString("categoria");
                    return new Categories(id,category);
                }
            });
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("posiitia nie existuje");
        }
    }
}
