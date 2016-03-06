package notes.dao;

import notes.domain.Memory;

import java.util.List;

/**
 * Created by Вадим on 14.02.2016.
 */
public interface MemoryDao {
    Long create(Memory memory);

    Memory read(Long id);

    boolean update(Memory memory);

    boolean delete(Memory memory);

    List findAll();
}
