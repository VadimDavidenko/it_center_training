***********************************************************
Задание 7 (14-е занятие)
Магазин ноутбуков


1. Структура схемы
************************************************************
1. Создать сущности для базы данных "Магазин ноутбуков":
Тип ноутбука(производитель, модель, дата производства, процессор, память)
Производители(имя)
Процессоры(производитель, частота, модель)
Память(производитель, размер)
Партия ноутбуков на складе(тип ноутбука, количество, цена)
Продажи(склад ноутбуков, дата продажи, количество)

domain
Notebook
Vendor
CPU
Memory
Store
Sales


2. Заготовка магазина
***************************************************************
2. Создать приложение магазин ноутбуков со следующими функциями:
Создать процессор
Создать память
Создать производителя
Создать тип ноутбука

Принять на склад партию ноутбуков (id ноутбука, количество, цена)
Продать указанное количество ноутбуков со склада(id склада, количество)

dao
NotebookDao
Long create(Notebook notebook)
Notebook read(Long id)
boolean update(Notebook notebook)
boolean delete(Notebook notebook)
List findAll()

VendorDao
Long create(Vendor vendor)
Vendor read(Long id)
boolean update(Vendor vendor)
boolean delete(Vendor vendor)
List findAll()

CPUDao
Long create(CPU cpu)
CPU read(Long id)
boolean update(CPU cpu)
boolean delete(CPU cpu)
List findAll()

MemoryDao
Long create(Memory memory)
Memory read(Long id)
boolean update(Memory memory)
boolean delete(Memory memory)
List findAll()

StoreDao
Long create(Store store)
Store read(Long id)
boolean update(Store store)
boolean delete(Store store)
List findAll()

SalesDao
Long create(Sales sales)
Sales read(Long ig)
boolean update(Sales sales)
boolean delete(Sales sales)
List findAll()

NotebookDaoImpl
VendorDaoImpl
CPUDaoImpl
MemoryDaoImpl
StoreDaoImpl
SalesDaoImpl

service
NotebookService
Long receive(Long noteId, int amount, double price)
Long sale(Long storeId, int amount)
NotebookServiceImpl
view
Menu
main()


3. Редактирование данных
******************************************************************
3. Добавить в приложение ноутбуков следующие функции

Изменить процессор
Изменить память
Изменить имя производителя
Изменить тип ноутбука
Списать со склад ноутбуки (ключ, количество)

NotebookService
---------------------------------
boolean updateCPU(CPU cpu)
boolean updateMemory(Memory memory)
boolean updateVendor(Vendor vendor)
boolean updateNotebook(Notebook notebook)
boolean removeFromStore(Store store, int amount)


4. Отчеты
*****************************************************************
4. Добавить в приложение ноутбуков следующие функции:
Показать все ноутбуки на складе (пользователь указывает размер порции)
Показать все ноутбуки которых больше указанного количества
Показать все ноутбуки по указанному имени производителя процессора
Показать все ноутбуки на складе
Показать типы ноутбуков, оставшиеся на складе по каждому производителю
Получить объем продаж ноутбуков в среднем за день (в штуках)

NotebookService
----------------------------------
List getNotebooksByPortion(int size)
List getNotebooksGtAmount(int amount)
List getNotebooksByCpuVendor(Vendor cpuVendor)
List getNotebooksFromStore()
List getNotebooksStorePresent()
Map getSalesByDays()
