package storage.daos;

import storage.EntityNotFoundException;
import storage.constructors.Position;

import java.util.List;

public interface PositionDao {
    List<Position> getAll();
    Position save(Position position) throws EntityNotFoundException;
    Position delete(Long id ) throws EntityNotFoundException;
    Position getById(Long id) throws EntityNotFoundException;
}
