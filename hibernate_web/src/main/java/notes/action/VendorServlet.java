package notes.action;

import notes.domain.Vendor;
import notes.service.Menu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by v.davidenko on 15.02.2016.
 */

@WebServlet("/vendorServlet")
public class VendorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        String action = parameterMap.get("action")[0];

        if (action.equals("find")) {
            Long id = Long.valueOf(parameterMap.get("selectedId")[0]);
            Vendor vendor = Menu.noteService.getVendorById(id);
            if (vendor == null) {
                req.setAttribute("selectedId",parameterMap.get("selectedId")[0]);
                req.setAttribute("server_msg", Menu.NO_SUCH_ENTITY_MSG);
            } else {
                req.setAttribute("id", String.valueOf(vendor.getId()));
                req.setAttribute("vendorName", vendor.getName());
            }
            req.getRequestDispatcher(Menu.VENDOR_PAGE).forward(req, resp);
            return;
        }

        if (action.equals("save")) {
            String id = parameterMap.get("id")[0];
            String vendorName = parameterMap.get("vendorName")[0].trim();
            Vendor vendor = new Vendor();
            vendor.setName(vendorName);

            if (id.isEmpty()) {
                if (!Menu.noteService.insertVendor(vendor).equals(0L)) {
                    req.setAttribute("server_msg", Menu.ADD_SUCCESS_MSG);
                }
            } else {
                vendor.setId(Long.valueOf(id));
                if (Menu.noteService.updateVendor(vendor)) {
                    req.setAttribute("server_msg", Menu.UPDATE_SUCCESS_MSG);
                }
            }
            Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
            for(Map.Entry<String, String[]> entry : entries) {
                req.setAttribute(entry.getKey(), entry.getValue()[0]);
            }
            req.getRequestDispatcher(Menu.VENDOR_PAGE).forward(req, resp);
        }
    }
}
