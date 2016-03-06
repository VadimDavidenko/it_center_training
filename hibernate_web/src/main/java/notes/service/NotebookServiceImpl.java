package notes.service;

import notes.dao.*;
import notes.domain.*;

import java.util.*;

/**
 * Created by Вадим on 14.02.2016.
 */
public class NotebookServiceImpl implements NotebookService {

    private NotebookDao notebookDao;
    private VendorDao vendorDao;
    private CPUDao cpuDao;
    private MemoryDao memoryDao;
    private StoreDao storeDao;
    private SalesDao salesDao;

    private static int pageCounter = -1;

    private NotebookServiceImpl() {}

    public NotebookServiceImpl(NotebookDao notebookDao, VendorDao vendorDao, CPUDao cpuDao,
                               MemoryDao memoryDao, StoreDao storeDao, SalesDao salesDao) {
        this.notebookDao = notebookDao;
        this.vendorDao = vendorDao;
        this.cpuDao = cpuDao;
        this.memoryDao = memoryDao;
        this.storeDao = storeDao;
        this.salesDao = salesDao;
    }
    ////////////////////////////////////////////////////////////
    // Inserts

    public Long insertCPU(CPU cpu) {
        if (cpu == null) return 0L;
        return cpuDao.create(cpu);
    }

    public Long insertMemory(Memory memory) {
        if (memory == null) return 0L;
        return memoryDao.create(memory);
    }

    public Long insertVendor(Vendor vendor) {
        if (vendor == null) return 0L;
        return vendorDao.create(vendor);
    }

    public Long insertNotebook(Notebook notebook) {
        if (notebook == null) return 0L;
        return notebookDao.create(notebook);
    }

    ////////////////////////////////////////////////////////////
    // Updates

    public boolean updateCPU(CPU cpu) {
        return cpuDao.update(cpu);
    }

    public boolean updateMemory(Memory memory) {
        return memoryDao.update(memory);
    }

    public boolean updateVendor(Vendor vendor) {
        return vendorDao.update(vendor);
    }

    public boolean updateNotebook(Notebook notebook) {
        return notebookDao.update(notebook);
    }

    ////////////////////////////////////////////////////////////
    // Store services

    public Long receive(Long noteId, int amount, double price) {
        Store store = new Store();
        store.setAmount(amount);
        store.setPrice(price);
        Notebook note = getNotebookById(noteId);
        store.setNotebook(note);

        return storeDao.create(store);
    }

    public boolean removeFromStore(Store store, int amount) {
        Integer currentAmount = store.getAmount();

        if (currentAmount.compareTo(amount) > 0) {
            Integer newAmount = currentAmount - amount;
            Double newPrice = store.getPrice() / currentAmount * newAmount;
            store.setAmount(newAmount);
            store.setPrice(newPrice);

            return storeDao.update(store);
        }
        return false;
    }

    public Long sale(Long storeId, int amount) {
        Store store = getStoreById(storeId);
        if (store == null) return 0L;

        Integer currentAmount = store.getAmount();
        if (currentAmount.compareTo(amount) >= 0) {
            Sales sale = new Sales();
            sale.setAmount(amount);
            sale.setStore(store);
            sale.setDate(new Date());

            return salesDao.create(sale);
        } else {
            return 0L;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters by Id

    public Notebook getNotebookById(Long id) {
        return notebookDao.read(id);
    }

    public Vendor getVendorById(Long id) {
        return vendorDao.read(id);
    }

    public CPU getCPUById(Long id) {
        return cpuDao.read(id);
    }

    public Memory getMemoryById(Long id) {
        return memoryDao.read(id);
    }

    public Store getStoreById(Long id) {
        return storeDao.read(id);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Get all (list)

    public List<Notebook> getAllNotebooks() {
        return notebookDao.findAll();
    }

    public List<Vendor> getAllVendors() {
        return vendorDao.findAll();
    }

    public List<CPU> getAllCPUs() {
        return cpuDao.findAll();
    }

    public List<Memory> getAllMemories() {
        return memoryDao.findAll();
    }

    public List<Store> getAllStores() {
        return storeDao.findAll();
    }

    /////////////////////////////////////////////////////////////
    // Reports

    public List<Notebook> getNotebooksByPortion(int size) {
        if (size == 0) {
            pageCounter = -1;
            return null;
        } if (size < 0) {
            if (pageCounter > 0) pageCounter--;
        } else {
            pageCounter++;
        }
        return (List<Notebook>) notebookDao.findByPortion(pageCounter, Math.abs(size));
    }

    public List<Notebook> getNotebooksGtAmount(int amount) {
        return (List<Notebook>) notebookDao.findGtAmount(amount);
    }

    public List<Notebook> getNotebooksByCpuVendor(Vendor cpuVendor) {
        return (List<Notebook>) notebookDao.findByCpuVendor(cpuVendor);
    }

    public List<Notebook> getNotebooksFromStore() {
        return (List<Notebook>) notebookDao.findAllOnStore();
    }

    public List<Store> getNotebooksStorePresent() {
        return (List<Store>) storeDao.findOnStorePresent();
    }

    public Map<Date, Integer> getSalesByDays() {
        return (Map<Date, Integer>) salesDao.findAllByDays();
    }


}
