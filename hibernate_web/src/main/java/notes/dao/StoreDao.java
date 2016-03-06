package notes.dao;

import notes.domain.Store;

import java.util.List;

/**
 * Created by Вадим on 14.02.2016.
 */
public interface StoreDao {
    Long create(Store store);

    Store read(Long id);

    boolean update(Store store);

    boolean delete(Store store);

    List findAll();

    List findOnStorePresent();
}
