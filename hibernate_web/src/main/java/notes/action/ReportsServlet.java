package notes.action;

import notes.domain.Notebook;
import notes.domain.Store;
import notes.domain.Vendor;
import notes.service.Menu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by v.davidenko on 16.02.2016.
 */

@WebServlet("/reportsServlet")
public class ReportsServlet  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        service(req, resp);
    }

    /*
     * Показать все ноутбуки на складе (пользователь указывает размер порции)
     * Показать все ноутбуки которых больше указанного количества
     * Показать все ноутбуки по указанному имени производителя процессора
     * Показать все ноутбуки на складе
     * Показать типы ноутбуков, оставшиеся на складе по каждому производителю
     *
     * Получить объем продаж ноутбуков в среднем за день (в штуках)
     */
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        String option = parameterMap.get("reportMenu")[0];

        switch (option) {
            case "byPortion":
                Integer portion = Integer.parseInt(parameterMap.get("portion")[0]);
                List<Notebook> noteList = Menu.noteService.getNotebooksByPortion(portion);
                req.setAttribute("noteList", noteList);
                break;

            case "byCPU":
                Long vendorId = Long.valueOf(parameterMap.get("cpuVendor")[0]);
                Vendor vendor = Menu.noteService.getVendorById(vendorId);
                noteList = Menu.noteService.getNotebooksByCpuVendor(vendor);
                req.setAttribute("noteList", noteList);
                break;

            case "storeAll":
                noteList = Menu.noteService.getNotebooksFromStore();
                req.setAttribute("noteList", noteList);
                break;

            case "gtAmount":
                Integer amount = Integer.parseInt(parameterMap.get("gtAmount")[0]);
                noteList = Menu.noteService.getNotebooksGtAmount(amount);
                req.setAttribute("noteList", noteList);
                break;

            case "storePresent":
                List<Store> storeList = Menu.noteService.getNotebooksStorePresent();
                req.setAttribute("storeList", storeList);
                break;

            case "salesByDays":
                Map<Date, Integer> salesMap = Menu.noteService.getSalesByDays();
                req.setAttribute("salesMap", salesMap);
                break;
        }
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for(Map.Entry<String, String[]> entry : entries) {
            req.setAttribute(entry.getKey(), entry.getValue()[0]);
        }
        List<Vendor> vendorList = Menu.noteService.getAllVendors();
        req.setAttribute("vendorList", vendorList);
        req.getRequestDispatcher(Menu.REPORTS_LIST_PAGE).forward(req, resp);
    }

}
