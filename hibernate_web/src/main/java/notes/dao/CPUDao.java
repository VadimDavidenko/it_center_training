package notes.dao;

import notes.domain.CPU;

import java.util.List;

/**
 * Created by Вадим on 14.02.2016.
 */
public interface CPUDao {
    Long create(CPU cpu);

    CPU read(Long id);

    boolean update(CPU cpu);

    boolean delete(CPU cpu);

    List findAll();
}
