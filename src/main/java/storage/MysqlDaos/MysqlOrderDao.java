package storage.MysqlDaos;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import org.springframework.jdbc.core.RowMapper;
import storage.DaoFactory;
import storage.EntityNotFoundException;

import storage.constructors.Order;

import storage.constructors.Product;

import storage.daos.OrderDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MysqlOrderDao implements OrderDao {
    private JdbcTemplate jdbcTemplate;

    public MysqlOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> getAll() {
        return jdbcTemplate.query("SELECT order.idOrder,order.Name,Summ,OrderStatus,SalesMan,DateTime,products.idProduct,products.name as product_name,productsinorder.count from `order` \n" +
                "left outer join productsinorder on order.idOrder =productsinorder.idOrder \n" +
                "left  outer join products on products.idProduct =  productsinorder.`idProducts` order by order.idOrder;\t", new ResultSetExtractor<List<Order>>() {
            @Override
            public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Order> orderList = new ArrayList<>();
                Order order = null;
                while (rs.next()) {
                    Long id = rs.getLong("idOrder");
                    if (order == null || id != order.getIdOrder()) {
                        String name = rs.getString("Name");
                        double Summ = rs.getDouble("Summ");
                        String OrderStatus = rs.getString("OrderStatus");
                        Long SalesMan = rs.getLong("SalesMan");
                        Date DateTime = rs.getTimestamp("DateTime");
                        order = new Order(id, name, Summ, OrderStatus, DaoFactory.INSTANCE.getUserDao().getByid(SalesMan), DateTime);
                        orderList.add(order);
                    }

                    if (rs.getString("product_name") == null) {
                        continue;
                    }else {
                        Long idproduct = rs.getLong("idProduct");
                        int count = rs.getInt("count");
                        order.getProductsInOrder().put(DaoFactory.INSTANCE.getProductDao().getbyId(idproduct),count);

                    }
                }
                return orderList;
            }
        });
    }

    @Override
    public Order createOrder(Order order) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.ENGLISH);
        Order order1 = null;
        String sql = "INSERT INTO `paz1c_project`.`order` (`Name`, `Summ`, `OrderStatus`, `SalesMan`, `DateTime`) VALUES (?,?,?,?,?);";
        int pocet = jdbcTemplate.update(sql, order.getName(), order.getSumm(), order.getOrderStatus(), order.getSalesMan().getIdUser(), formatter.format(order.getDateTime()));
        if (pocet == 1) {
            order1 = getIdFromOrder(order);
            for (Product product : order.getProductsInOrder().keySet()) {
                Long idOfProduct = product.getIdProduct();
                int count = order.getProductsInOrder().get(product);
                sql = "INSERT INTO `paz1c_project`.`productsinorder` (`idOrder`, `idProducts`, `count`) VALUES (?,?,?);";
                jdbcTemplate.update(sql, order1.getIdOrder(), idOfProduct, count);
            }
        } else {
            throw new EntityNotFoundException("chyba vytvorenia objednavky");
        }
        return order;
    }

    @Override
    public Map<Product,Integer> getProductInOrder(Order order) {
        String sql = "SELECT * FROM paz1c_project.productsinorder where idOrder ="+order.getIdOrder();
        Map<Product,Integer> map = new HashMap<>();
        return jdbcTemplate.queryForObject(sql, new RowMapper<Map<Product,Integer>>() {
            @Override
            public Map<Product,Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
               Long idProducts = rs.getLong("idProducts");
               int count = rs.getInt("count");
              map.put(DaoFactory.INSTANCE.getProductDao().getbyId(idProducts),count);
              return map;
            }
        });
    }

    @Override
    public Order update(Order order) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.ENGLISH);
        String sql = "UPDATE `paz1c_project`.`order` SET `Name` = ?, `Summ` = ?, `OrderStatus` = ?, `SalesMan` = ?, `DateTime` = ?> WHERE `idOrder` = ?;";
        int pocet = jdbcTemplate.update(sql,order.getName(),order.getSumm(),order.getOrderStatus(),order.getSalesMan().getIdUser(),formatter.format(order.getDateTime()),order.getIdOrder());
        if(pocet == 1)
        {
            return order;
        }
        else {
            throw new EntityNotFoundException("chyba updatumu");
        }
    }

    @Override
    public Order getIdFromOrder(Order order) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.ENGLISH);
        String sql = "SELECT * FROM paz1c_project.order WHERE Name ='" + order.getName() + "'  and Summ = " + order.getSumm()
                + " and OrderStatus = '" + order.getOrderStatus() + "' and SalesMan = " + order.getSalesMan().getIdUser() + " and DateTime ='" + formatter.format(order.getDateTime())+"'";
        try {
            return jdbcTemplate.queryForObject(sql, new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idOrder");
                    String name = rs.getString("Name");
                    double Summ = rs.getDouble("Summ");
                    String OrderStatus = rs.getString("OrderStatus");
                    Long SalesMan = rs.getLong("SalesMan");
                    Date DateTime = rs.getTimestamp("DateTime");
                    return new Order(id,name,Summ,OrderStatus,DaoFactory.INSTANCE.getUserDao().getByid(SalesMan),DateTime);
                }
            });
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("order nie existuje");
        }
    }

}
