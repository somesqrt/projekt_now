package storage.daos;

import storage.EntityNotFoundException;
import storage.constructors.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    User save(User user);
    User delete(Long id);
    User getByid(Long id) throws EntityNotFoundException;
    List<User> getBySubstring(String nameSubstring, String surnameSubstring);
}
