package notes.action;

import notes.domain.Memory;
import notes.domain.Vendor;
import notes.service.Menu;
import notes.util.Utils;
import notes.domain.CPU;
import notes.domain.Notebook;

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
 * Created by v.davidenko on 15.02.2016.
 */

@WebServlet("/noteServlet")
public class NoteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Vendor> vendorList = Menu.noteService.getAllVendors();
        List<CPU> cpuList = Menu.noteService.getAllCPUs();
        List<Memory> memoryList = Menu.noteService.getAllMemories();

        Map<String, String[]> parameterMap = req.getParameterMap();
        String action = parameterMap.get("action")[0];

        if (action.equals("find")) {
            Long id = Long.valueOf(parameterMap.get("selectedId")[0]);
            Notebook note = Menu.noteService.getNotebookById(id);

            if (note == null) {
                req.setAttribute("selectedId",parameterMap.get("selectedId")[0]);
                req.setAttribute("server_msg", Menu.NO_SUCH_ENTITY_MSG);
            } else {
                req.setAttribute("id", String.valueOf(note.getId()));
                req.setAttribute("model", note.getModel());
                req.setAttribute("date", note.getManufactureDateStr());
                req.setAttribute("vendorId", String.valueOf(note.getVendor().getId()));
                req.setAttribute("cpuId", String.valueOf(note.getCpu().getId()));
                req.setAttribute("memoryId", String.valueOf(note.getMemory().getId()));
                req.setAttribute("vendorList", vendorList);
                req.setAttribute("cpuList", cpuList);
                req.setAttribute("memoryList", memoryList);
            }
            req.getRequestDispatcher(Menu.NOTEBOOK_PAGE).forward(req, resp);
            return;
        }

        if (action.equals("save")) {
            String id = parameterMap.get("id")[0];
            String model = parameterMap.get("model")[0].trim();
            Date date = Utils.stringToDate(parameterMap.get("date")[0].trim(), "dd.MM.yyyy");

            Long vendorId = Long.valueOf(parameterMap.get("vendorId")[0]);
            Vendor vendor = Menu.noteService.getVendorById(vendorId);
            Long cpuId = Long.valueOf(parameterMap.get("cpuId")[0]);
            CPU cpu = Menu.noteService.getCPUById(cpuId);
            Long memoryId = Long.valueOf(parameterMap.get("memoryId")[0]);
            Memory memory = Menu.noteService.getMemoryById(memoryId);

            Notebook note = new Notebook();
            note.setModel(model);
            note.setManufactureDate(date);
            note.setVendor(vendor);
            note.setCpu(cpu);
            note.setMemory(memory);

            if (id.isEmpty()) {
                if (!Menu.noteService.insertNotebook(note).equals(0L)) {
                    req.setAttribute("server_msg", Menu.ADD_SUCCESS_MSG);
                }
            } else {
                note.setId(Long.valueOf(id));
                if (Menu.noteService.updateNotebook(note)) {
                    req.setAttribute("server_msg", Menu.UPDATE_SUCCESS_MSG);
                }
            }
            Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
            for(Map.Entry<String, String[]> entry : entries) {
                req.setAttribute(entry.getKey(), entry.getValue()[0]);
            }
            req.setAttribute("vendorList", vendorList);
            req.setAttribute("cpuList", cpuList);
            req.setAttribute("memoryList", memoryList);

            req.getRequestDispatcher(Menu.NOTEBOOK_PAGE).forward(req, resp);
        }
    }

}
