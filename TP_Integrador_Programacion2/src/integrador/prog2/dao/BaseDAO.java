import java.util.List;

public interface BaseDAO<T> {

    void create(T entity);

    List<T> readAll();

    T readByID(Long id);

    void update(T entity);

    void delete(Long id);
}
