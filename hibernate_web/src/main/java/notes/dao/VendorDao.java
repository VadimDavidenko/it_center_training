package notes.dao;

import notes.domain.Vendor;

import java.util.List;

/**
 * Created by Вадим on 14.02.2016.
 */
public interface VendorDao {
    Long create(Vendor vendor);

    Vendor read(Long id);

    boolean update(Vendor vendor);

    boolean delete(Vendor vendor);

    List findAll();
}
