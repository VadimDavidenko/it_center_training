package notes.action;

import notes.domain.Notebook;
import notes.domain.Store;
import notes.service.Menu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Вадим on 17.02.2016.
 */

@WebServlet("/storeServlet")
public class StoreServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        service(req, resp);
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        String option = parameterMap.get("storeMenu")[0];

        switch (option) {
            case "receive":
                receiveService(req, resp);
                break;
            case "remove":
                removeService(req, resp);
                break;
            case "sale":
                salesService(req, resp);
        }
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for(Map.Entry<String, String[]> entry : entries) {
            req.setAttribute(entry.getKey(), entry.getValue()[0]);
        }
        List<Notebook> notebookList = Menu.noteService.getAllNotebooks();
        req.setAttribute("notebookList", notebookList);
        List<Store> storeList = Menu.noteService.getAllStores();
        req.setAttribute("storeList", storeList);

        req.getRequestDispatcher(Menu.STORE_PAGE).forward(req, resp);
    }

    /*
     * Принять на склад партию ноутбуков (id ноутбука, количество, цена)
     */
    public void receiveService(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        Long noteId = Long.valueOf(parameterMap.get("notebookId")[0]);
        Integer amountReceive = Integer.valueOf(parameterMap.get("amountReceive")[0]);
        Double priceReceive = Double.valueOf(parameterMap.get("priceReceive")[0]);

        Long storeId = Menu.noteService.receive(noteId, amountReceive, priceReceive);
        req.setAttribute("server_msg", Menu.STORE_RECEIVE_MSG + String.valueOf(storeId));
    }

    /*
     * Списать со склада ноутбуки (ключ, количество)
     */
    public void removeService(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        Long storeId = Long.valueOf(parameterMap.get("storeIdRemove")[0]);
        Integer amountRemove = Integer.valueOf(parameterMap.get("amountRemove")[0]);

        Store store = Menu.noteService.getStoreById(storeId);
        if (store != null) {
            if (Menu.noteService.removeFromStore(store, amountRemove)) {
                req.setAttribute("server_msg", Menu.STORE_REMOVE_MSG + String.valueOf(storeId));
            } else {
                req.setAttribute("server_msg", Menu.STORE_REMOVE_ERR_MSG + String.valueOf(storeId));
            }
        } else {
            req.setAttribute("server_msg", Menu.NO_SUCH_ENTITY_MSG);
        }
    }

    /*
     * Продать указанное количество ноутбуков со склада(id склада, количество)
     */
    public void salesService(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        Long storeId = Long.valueOf(parameterMap.get("storeIdSale")[0]);
        Integer amountSale = Integer.valueOf(parameterMap.get("amountSale")[0]);

        Store store = Menu.noteService.getStoreById(storeId);
        if (store != null) {
            Long saleId = Menu.noteService.sale(storeId, amountSale);
            if (saleId != 0) {
                Menu.noteService.removeFromStore(store, amountSale);
                req.setAttribute("server_msg", Menu.SALE_STORE_MSG + String.valueOf(storeId));
            } else {
                req.setAttribute("server_msg", Menu.SALE_STORE_ERR_MSG + String.valueOf(storeId));
            }
        } else {
            req.setAttribute("server_msg", Menu.NO_SUCH_ENTITY_MSG);
        }


    }
}
