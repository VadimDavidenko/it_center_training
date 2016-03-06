package notes.action;

import notes.domain.CPU;
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
 * Created by v.davidenko on 15.02.2016.
 */

@WebServlet("/cpuServlet")
public class CPUServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Vendor> vendorList = Menu.noteService.getAllVendors();
        Map<String, String[]> parameterMap = req.getParameterMap();
        String action = parameterMap.get("action")[0];

        if (action.equals("find")) {
            Long id = Long.valueOf(parameterMap.get("selectedId")[0]);
            CPU cpu = Menu.noteService.getCPUById(id);
            if (cpu == null) {
                req.setAttribute("selectedId",parameterMap.get("selectedId")[0]);
                req.setAttribute("server_msg", Menu.NO_SUCH_ENTITY_MSG);
            } else {
                req.setAttribute("id", String.valueOf(cpu.getId()));
                req.setAttribute("model", cpu.getModel());
                req.setAttribute("vendorId", String.valueOf(cpu.getVendor().getId()));
                req.setAttribute("vendorList", vendorList);
                req.setAttribute("vendor", cpu.getVendor());
                req.setAttribute("frequency", cpu.getFrequency());
            }
            req.getRequestDispatcher(Menu.CPU_PAGE).forward(req, resp);
            return;
        }

        if (action.equals("save")) {
            String id = parameterMap.get("id")[0];
            String model = parameterMap.get("model")[0].trim();
            Long vendorId = Long.valueOf(parameterMap.get("vendorId")[0]);
            Vendor vendor = Menu.noteService.getVendorById(vendorId);
            String freq = parameterMap.get("frequency")[0].trim();

            CPU cpu = new CPU();
            cpu.setModel(model);
            cpu.setVendor(vendor);
            cpu.setFrequency(freq);

            if (id.isEmpty()) {
                if (!Menu.noteService.insertCPU(cpu).equals(0L)) {
                    req.setAttribute("server_msg", Menu.ADD_SUCCESS_MSG);
                }
            } else {
                cpu.setId(Long.valueOf(id));
                if (Menu.noteService.updateCPU(cpu)) {
                    req.setAttribute("server_msg", Menu.UPDATE_SUCCESS_MSG);
                }
            }
            Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
            for(Map.Entry<String, String[]> entry : entries) {
                req.setAttribute(entry.getKey(), entry.getValue()[0]);
            }
            req.setAttribute("vendorList", vendorList);
            req.getRequestDispatcher(Menu.CPU_PAGE).forward(req, resp);
        }
    }
}
