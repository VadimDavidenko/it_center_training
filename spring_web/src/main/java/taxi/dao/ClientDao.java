package taxi.dao;

import taxi.domain.Client;

import java.util.List;

/**
 * Created by Вадим on 28.02.2016.
 */
public interface ClientDao {

    Long create(Client client);

    Client read(Long id);

    boolean update(Client client);

    boolean delete(Client client);

    List findAll();

    List findByPortion(int page, int size);

    List findGtSum(int sum);

    List findLastMonth();
}
