package storage.MysqlDaos;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import storage.constructors.Categories;
import storage.DaoFactory;
import storage.EntityNotFoundException;
import storage.constructors.Product;
import storage.daos.CategoriesDAO;
import storage.daos.ProductDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlProductDao implements ProductDao {
    private JdbcTemplate jdbcTemplate;

    public MysqlProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAll() {
        return jdbcTemplate.query("SELECT * FROM products", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idProduct");
                String name = rs.getString("name");
                String manufacture = rs.getString("manufacture");
                String EAN = rs.getString("EAN");
                Double weight = rs.getDouble("weight");
                String taste = rs.getString("taste");
                Double height = rs.getDouble("height");
                Double length = rs.getDouble("length");
                Double width = rs.getDouble("width");
                int piecesInPackage = rs.getInt("piecesInPackage");
                CategoriesDAO categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
                Categories cat = categoriesDAO.getbyID(rs.getLong("Categories"));
                return new Product(id,name,manufacture,EAN,weight,taste,height,length,width,piecesInPackage,cat);
            }
        });
    }

    @Override
    public Product save(Product product) {
        if(product.getIdProduct() == null)//Insert
        {
            String sql = "INSERT INTO products (name, manufacture, EAN, weight, taste, height, length, width, piecesInPackage, Categories)" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?);";
            jdbcTemplate.update(sql,product.getName(),product.getManufacture(),product.getEAN(),
                    product.getWeight(),product.getTaste(),product.getHeight(),product.getLength(),
                    product.getWidth(),product.getPiecesInPackage(),product.getCategories().getIdCategories());
        }
        else
        {
            String sql = "UPDATE products p SET name =?, manufacture = ?, EAN = ?, weight = ?, taste = ?, height = ?, length = ?, width = ?, piecesInPackage = ?, Categories = ? WHERE idProduct = ?";
            jdbcTemplate.update(sql,product.getName(),product.getManufacture(),product.getEAN(),
                    product.getWeight(),product.getTaste(),product.getHeight(),product.getLength(),
                    product.getWidth(),product.getPiecesInPackage(),product.getCategories().getIdCategories(),product.getIdProduct());
        }
        return product;
    }

    @Override
    public Product delete(Long id) {
       Product product = getbyId(id);
        String sql = "DELETE from products where idProduct =" + id;
        int count = jdbcTemplate.update(sql);
        if(count==1)
        {
            return product;
        }
        else
        {
            throw new EntityNotFoundException("product nie existuje");
        }
    }

    @Override
    public Product getbyId(Long id) {
        try {
            String sql = "SELECT * FROM products where idProduct = " + id;
            return jdbcTemplate.queryForObject(sql, new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idProduct");
                    String name = rs.getString("name");
                    String manufacture = rs.getString("manufacture");
                    String EAN = rs.getString("EAN");
                    Double weight = rs.getDouble("weight");
                    String taste = rs.getString("taste");
                    Double height = rs.getDouble("height");
                    Double length = rs.getDouble("length");
                    Double width = rs.getDouble("width");
                    int piecesInPackage = rs.getInt("piecesInPackage");
                    CategoriesDAO categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
                    Categories cat = categoriesDAO.getbyID(rs.getLong("Categories"));
                    return new Product(id,name,manufacture,EAN,weight,taste,height,length,width,piecesInPackage,cat);
                }
            });
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("product nie existuje");
        }
    }

    @Override
    public List<Product> getbyCategory(Categories categories) {
        System.out.println("SELECT * FROM products where Categories ="+categories.getIdCategories());
        return jdbcTemplate.query("SELECT * FROM products where Categories ="+categories.getIdCategories(), new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idProduct");
                String name = rs.getString("name");
                String manufacture = rs.getString("manufacture");
                String EAN = rs.getString("EAN");
                Double weight = rs.getDouble("weight");
                String taste = rs.getString("taste");
                Double height = rs.getDouble("height");
                Double length = rs.getDouble("length");
                Double width = rs.getDouble("width");
                int piecesInPackage = rs.getInt("piecesInPackage");
                CategoriesDAO categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
                Categories cat = categoriesDAO.getbyID(rs.getLong("Categories"));
                return new Product(id,name,manufacture,EAN,weight,taste,height,length,width,piecesInPackage,cat);
            }
        });
    }
}
