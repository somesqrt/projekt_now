package sklad;

import storage.*;
import storage.constructors.Order;
import storage.constructors.Product;
import storage.constructors.User;
import storage.daos.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class MainApp {
    public static void main(String[] args) {
       ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
       UserDao userDao = DaoFactory.INSTANCE.getUserDao();
       RoleDao roleDao  =DaoFactory.INSTANCE.getRoleDao();
       CategoriesDAO categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
       OrderDao orderDao = DaoFactory.INSTANCE.getorderDao();

       List<User> users = userDao.getAll();
      List<Product> products = productDao.getAll();
        Map<Product,Integer> map = new HashMap<>();
        map.put(products.get(0),1);
        map.put(products.get(15),100);


       Date date =  new Date();
          Order order = new Order("test test",150.70,"Spracovane",users.get(2),date,map);
       orderDao.createOrder(order);
    //    System.out.println(orderDao.getIdFromOrder(order));
    }
}
