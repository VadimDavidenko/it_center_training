package notes.service;

import notes.util.HibernateUtil;
import notes.dao.*;
import notes.domain.*;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Вадим on 14.02.2016.
 */

@WebServlet("/menuServlet")
public class Menu extends HttpServlet {

    public final static String NOTEBOOK_PAGE = "notes/notebook.jsp";
    public final static String VENDOR_PAGE = "notes/vendor.jsp";
    public final static String CPU_PAGE = "notes/cpu.jsp";
    public final static String MEMORY_PAGE = "notes/memory.jsp";
    public final static String STORE_PAGE = "notes/store.jsp";
    public final static String REPORTS_PAGE = "notes/reports.jsp";
    public final static String REPORTS_LIST_PAGE = "notes/reports_list.jsp";

    public final static String NO_SUCH_ENTITY_MSG = "Entity with such Id does not exist in database!";
    public final static String ADD_SUCCESS_MSG = "New entity added successfully";
    public final static String UPDATE_SUCCESS_MSG = "Entity data updated successfully";
    public final static String STORE_RECEIVE_MSG = "Notebooks received on Store ";
    public final static String STORE_REMOVE_MSG = "Notebooks removed from Store ";
    public final static String STORE_REMOVE_ERR_MSG = "Notebooks number to remove is greater then existent on Store ";
    public final static String SALE_STORE_MSG = "Notebooks sold from Store ";
    public final static String SALE_STORE_ERR_MSG = "Notebooks number to sale is greater then existent on Store ";

    public static NotebookService noteService;

    @Override
    public void init() throws ServletException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        NotebookDao notebookDao = new NotebookDaoImpl(sessionFactory);
        VendorDao vendorDao = new VendorDaoImpl(sessionFactory);
        CPUDao cpuDao = new CPUDaoImpl(sessionFactory);
        MemoryDao memoryDao = new MemoryDaoImpl(sessionFactory);
        StoreDao storeDao = new StoreDaoImpl(sessionFactory);
        SalesDao salesDao = new SalesDaoImpl(sessionFactory);

        noteService = new NotebookServiceImpl(notebookDao, vendorDao, cpuDao, memoryDao, storeDao, salesDao);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        main(req, resp);
    }

    /*
     * Main menu
     */
    public void main(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        String menuOption = parameterMap.get("menuOption")[0];
        switch (menuOption) {
            case "entity":
                entityService(req, resp);
                break;
            case "store":
                storeService(req, resp);
                break;
            case "reports":
                reportsService(req, resp);
        }
    }

    /*
     * Go to Store menu page
     */
    public void storeService(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Notebook> notebookList = noteService.getAllNotebooks();
        req.setAttribute("notebookList", notebookList);

        List<Store> storeList = noteService.getAllStores();
        req.setAttribute("storeList", storeList);

        req.getRequestDispatcher(STORE_PAGE).forward(req, resp);
    }

    /*
     * Go to Reports menu page
     */
    public void reportsService(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Vendor> vendorList = noteService.getAllVendors();
        req.setAttribute("vendorList", vendorList);
        req.getRequestDispatcher(REPORTS_PAGE).forward(req, resp);
    }

    /*
     * Add/Modify entity menu (Notebook type, Vendor, CPU, Memory)
     */
    public void entityService(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        String option = parameterMap.get("entityMenu")[0];

        List<Vendor> vendorList = noteService.getAllVendors();
        List<CPU> cpuList = noteService.getAllCPUs();
        List<Memory> memoryList = noteService.getAllMemories();

        switch (option) {
            case "notebook":
                req.setAttribute("vendorList", vendorList);
                req.setAttribute("cpuList", cpuList);
                req.setAttribute("memoryList", memoryList);
                req.getRequestDispatcher(NOTEBOOK_PAGE).forward(req, resp);
                break;
            case "vendor":
                req.getRequestDispatcher(VENDOR_PAGE).forward(req, resp);
                break;
            case "cpu":
                req.setAttribute("vendorList", vendorList);
                req.getRequestDispatcher(CPU_PAGE).forward(req, resp);
                break;
            case "memory":
                req.setAttribute("vendorList", vendorList);
                req.getRequestDispatcher(MEMORY_PAGE).forward(req, resp);
                break;
        }
    }
}
