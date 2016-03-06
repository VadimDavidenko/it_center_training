package notes.service;

import notes.domain.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Вадим on 14.02.2016.
 *
 * Принять на склад партию ноутбуков (id ноутбука, количество, цена)
 * Продать указанное количество ноутбуков со склада(id склада, количество)
 *
 * Изменить процессор
 * Изменить память
 * Изменить имя производителя
 * Изменить тип ноутбука
 * Списать со склад ноутбуки (ключ, количество)
 *
 * Показать все ноутбуки на складе (пользователь указывает размер порции)
 * Показать все ноутбуки которых больше указанного количества
 * Показать все ноутбуки по указанному имени производителя процессора
 * Показать все ноутбуки на складе
 * Показать типы ноутбуков, оставшиеся на складе по каждому производителю
 * Получить объем продаж ноутбуков в среднем за день (в штуках)
 *
 */
public interface NotebookService {

    Long insertCPU(CPU cpu);

    Long insertMemory(Memory memory);

    Long insertVendor(Vendor vendor);

    Long insertNotebook(Notebook notebook);

    boolean updateCPU(CPU cpu);

    boolean updateMemory(Memory memory);

    boolean updateVendor(Vendor vendor);

    boolean updateNotebook(Notebook notebook);


    Long receive(Long noteId, int amount, double price);

    boolean removeFromStore(Store store, int amount);


    Long sale(Long storeId, int amount);


    List getNotebooksByPortion(int size);

    List getNotebooksGtAmount(int amount);

    List getNotebooksByCpuVendor(Vendor cpuVendor);

    List getNotebooksFromStore();

    List getNotebooksStorePresent();

    Map getSalesByDays();


    Notebook getNotebookById(Long id);

    Vendor getVendorById(Long id);

    CPU getCPUById(Long id);

    Memory getMemoryById(Long id);

    Store getStoreById(Long id);


    List getAllNotebooks();

    List getAllVendors();

    List getAllCPUs();

    List getAllMemories();

    List getAllStores();
}
