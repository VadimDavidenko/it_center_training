package notes.action;

import notes.domain.Notebook;
import notes.domain.Vendor;
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
 * Created by v.davidenko on 19.02.2016.
 */

@WebServlet("/listServlet")
public class ListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceRefreshPage(req, resp);
    }

    public void serviceRefreshPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        String action = parameterMap.get("action")[0];

        if (action.equals("paging")) {
            Integer portion = Integer.parseInt(parameterMap.get("portion")[0]);
            Integer page = Integer.parseInt(parameterMap.get("page")[0]);   // -1 (prev) or 1 (next)
            List<Notebook> noteList = Menu.noteService.getNotebooksByPortion(portion * page);

            req.setAttribute("noteList", noteList);
            req.setAttribute("reportMenu", parameterMap.get("reportMenu")[0]);
            req.setAttribute("portion", parameterMap.get("portion")[0]);
            req.getRequestDispatcher(Menu.REPORTS_LIST_PAGE).forward(req, resp);

        } else if (action.equals("back")) {
            Menu.noteService.getNotebooksByPortion(0);   // reset paging to '0' page
            Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
            for (Map.Entry<String, String[]> entry : entries) {
                req.setAttribute(entry.getKey(), entry.getValue()[0]);
            }
            List<Vendor> vendorList = Menu.noteService.getAllVendors();
            req.setAttribute("vendorList", vendorList);
            req.getRequestDispatcher(Menu.REPORTS_PAGE).forward(req, resp);
        }
    }
}
