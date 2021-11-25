package sklad;

import storage.*;
import storage.constructors.Order;
import storage.constructors.Position;
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
        PositionDao positionDao = DaoFactory.INSTANCE.getPositionDao();
      List<Product> productList = productDao.getAll();
      List<Position> positionList = positionDao.getAll();

    }
}
