/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deshpande;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Vinayak
 */
@WebServlet(name = "ApplicationServlet", urlPatterns = {"/applications"})
@MultipartConfig(
        fileSizeThreshold = 5_242_880, //5MB
        maxFileSize = 20_971_520L //20MB
)
public class ApplicationServlet extends HttpServlet {

    private Map<Integer, Application> applicationDatabase = new LinkedHashMap<>();
    private Map<Integer, Application> oldApplicationDatabase = new LinkedHashMap<>();
    private static SortedSet<Application> activeApps;
    private volatile int APPLICATION_ID_SEQUENCE = 1;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        activeApps = new TreeSet<>();
        int page = 1;
        int appsPerPage = 4;
        int begin = 0;
        int end = 0;
        applicationDatabase.entrySet().forEach((var entry) -> {
            if (entry.getValue().isActive() == true) {
                activeApps.add(entry.getValue());
            }
        });
        int maxPages = activeApps.size() / appsPerPage + 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.equals("")) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) {
                    page = 1;
                } else if (page > maxPages) {
                    page = maxPages;
                }
            } catch (NumberFormatException nfe) {
                page = 1;
            }
        }
        begin = (page - 1) * appsPerPage;
        end = begin + appsPerPage - 1;
        HttpSession session = request.getSession();
        System.out.println(activeApps + "\n");
        session.setAttribute("activeApps", activeApps);
        session.setAttribute("begin", begin);
        session.setAttribute("end", end);
        session.setAttribute("maxPages", maxPages);
        session.setAttribute("currentPage", page);
        request.getRequestDispatcher("/WEB-INF/jsp/view/applicationlist.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }
        String action = request.getParameter("action");
        if (action == null) {
            action = "applications";
        }
        if (action.equals("applications")) {
            processRequest(request, response);
        }
        if (action.equals("view")) {
            viewApplication(request, response);
        }
        if(action.equals("download")){
            downloadAttachment(request, response);
        }
        if(action.equals("deactivate")){
            deactivateApplication(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Job job = (Job) session.getAttribute("job");
        String jobTitle = job.getTitle();
        int jobId = job.getId();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        Part filePart = request.getPart("resumeUpload");
        Double desiredSalary;
        try {
            desiredSalary = Double.parseDouble(request.getParameter("desiredSalary"));
        } catch (NullPointerException npe) {
            desiredSalary = 15.00;
        }

        LocalDate earliestStartDate;
        try {
            earliestStartDate = LocalDate.parse(request.getParameter("earliestStartDate"));
        } catch (NullPointerException npe) {
            earliestStartDate = LocalDate.now();
        }
        Attachment resumeUpload = null;
        if (filePart != null && filePart.getSize() > 0) {
            resumeUpload = processAttachment(filePart);

        }

        int id;
        synchronized (this) {
            id = APPLICATION_ID_SEQUENCE++;
        }
        Instant dateTimeSubmitted = Instant.now();
        Application application = new Application(jobTitle, firstName, lastName, email, phone, resumeUpload, desiredSalary, earliestStartDate, id, jobId, dateTimeSubmitted);
        if (application.hasErrors()) {
            session.setAttribute("application", application);
            request.getRequestDispatcher("/WEB-INF/jsp/view/job.jsp").forward(request, response);
        } else {
            synchronized (this) {
                applicationDatabase.put(id, application);
                response.sendRedirect("jobs");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Attachment processAttachment(Part filePart) throws IOException {
        Attachment attachment = new Attachment();
        try ( InputStream inputStream = filePart.getInputStream();  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            int read;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            attachment.setName(filePart.getSubmittedFileName());
            attachment.setContents(outputStream.toByteArray());
        }
        return attachment;
    }

    private void viewApplication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getParameter("id") == null) {
            response.sendRedirect("applications");
            return;
        }
        Application application = new Application();
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            for (Application a : activeApps) {
                if (a.getId() == id) {
                    application = a;
                }
            }
        } catch (Exception ex) {
            response.sendRedirect("applications");
            return;
        }

        session.setAttribute("application", application);
        request.getRequestDispatcher("/WEB-INF/jsp/view/application.jsp").forward(request, response);
    }

    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Application application;
        Attachment attachment;
        try {
            application = (Application) session.getAttribute("application");
            attachment = application.getResumeUpload();
        }catch(NullPointerException npe){
            viewApplication(request, response);
            return;
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
        response.setContentType("application/octet-stream");

        try ( ServletOutputStream stream = response.getOutputStream()) {
            stream.write(attachment.getContents());
        }
    }

    private void deactivateApplication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Application application;
        try{
            application = (Application) session.getAttribute("application");
        } catch(NullPointerException npe){
            viewApplication(request, response);
            return;
        }
        System.out.println("App: " + application);
        applicationDatabase.remove(application.getId());
        application.setActive(false);
        oldApplicationDatabase.put(application.getId(), application);
        System.out.println("Database: " + applicationDatabase + "\n");
        session.removeAttribute("activeApps");
        response.sendRedirect("applications");
        
    }
}
