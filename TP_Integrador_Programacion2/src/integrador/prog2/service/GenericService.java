package service;

import java.util.List;

public interface GenericService<T> {

    void create(T entity);

    List<T> readAll();

    T readByID(Long id);

    void update(T entity);

    void delete(Long id);
}
