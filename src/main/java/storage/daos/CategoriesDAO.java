package storage.daos;

import storage.constructors.Categories;
import storage.EntityNotFoundException;

import java.util.List;

public interface CategoriesDAO {
    List<Categories> getAll();
    Categories save(Categories categories)  throws EntityNotFoundException;
    Categories delete(Long id)  throws EntityNotFoundException;
    Categories getbyID(Long id)  throws EntityNotFoundException;
}
