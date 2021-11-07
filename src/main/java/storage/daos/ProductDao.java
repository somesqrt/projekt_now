package storage.daos;

import storage.constructors.Categories;
import storage.constructors.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    Product save(Product product);
    Product delete(Long id);
    Product getbyId(Long id);
    List<Product> getbyCategory(Categories categories);
}
