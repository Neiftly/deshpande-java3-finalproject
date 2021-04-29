/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deshpande;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Vinayak
 */
@WebServlet(name = "JobServlet", urlPatterns = {"/jobs"})
public class JobServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String FILE_PATH = "WEB-INF/assets/";
    private static final String FILE_NAME = "job-data.csv";

    private static SortedSet<Job> jobs;
    private static SortedSet<Job> activeJobs;

    private void readFromFile(HttpServletRequest request, HttpServletResponse response) throws ParserConfigurationException, MalformedURLException, IOException, SAXException {
        if (jobs == null) {
            try ( Scanner in = new Scanner(new File(getServletContext().getRealPath(FILE_PATH + FILE_NAME)))) {
                jobs = new TreeSet<>();
                activeJobs = new TreeSet<>();
                int lineCount = 0;
                String line = in.nextLine();
                String[] fields;
                int id;
                boolean active;
                LocalDate dateCreated;
                String title;
                String city;
                String state;
                boolean fullTime;
                String department;
                String experience;
                String wageCategory;
                double salary;
                while (in.hasNextLine()) {
                    lineCount++;
                    line = in.nextLine();
                    fields = line.split(",");
                    id = Integer.parseInt(fields[0]);
                    active = Boolean.valueOf(fields[1]);
                    System.out.println(active);
                    dateCreated = LocalDate.parse(fields[2]);
                    title = fields[3];
                    city = fields[4];
                    state = fields[5];
                    fullTime = Boolean.valueOf(fields[6]);
                    department = fields[7];
                    experience = fields[8];
                    wageCategory = fields[9];
                    salary = Double.parseDouble(fields[10]);
                    jobs.add(new Job(id, active, dateCreated, title, city,
                            state, fullTime, department, experience,
                            wageCategory, salary));
                    if (active) {
                        activeJobs.add(new Job(id, active, dateCreated, title, city,
                                state, fullTime, department, experience,
                                wageCategory, salary));
                    }
                }

            } catch (FileNotFoundException fnfe) {
                response.setContentType("text/html; charset=UTF-8");
                try ( PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Data error</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Error reading data</h1>");
                    out.println("</body>");
                    out.println("</html>");
                }

                return;
            }
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            readFromFile(request, response);
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            return;
        }
        
        int page = 1;
        int jobsPerPage = 4;
        int begin = 0;
        int end = 0;
        int maxPages = activeJobs.size() / jobsPerPage + 1;
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
        begin = (page - 1) * jobsPerPage;
        end = begin + jobsPerPage - 1;
        HttpSession session = request.getSession();
        session.setAttribute("activeJobs", activeJobs);
        session.setAttribute("begin", begin);
        session.setAttribute("end", end);
        session.setAttribute("maxPages", maxPages);
        session.setAttribute("currentPage", page);
        request.getRequestDispatcher("/WEB-INF/jsp/view/jobList.jsp").forward(request, response);

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
            action = "songs";
        }
        processRequest(request, response);
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
        processRequest(request, response);
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

}
