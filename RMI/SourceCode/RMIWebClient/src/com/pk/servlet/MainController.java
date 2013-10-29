package com.pk.servlet;

import com.pk.bean.FileResponse;
import com.pk.bean.UserInfo;
import com.pk.common.AppData;
import com.pk.service.ServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.DefaultFileItemFactory;

public class MainController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("error", "Login is required");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lsCommand = req.getParameter("_c");
        String uploadedFileName = "";
        byte data[] = null;

        try {
            if (FileUpload.isMultipartContent(req)) {
                DefaultFileItemFactory factory = new DefaultFileItemFactory();
                FileUpload upload = new FileUpload(factory);
                List items = upload.parseRequest(req);
                Iterator iter = items.iterator();

                while (iter.hasNext()) {
                    FileItem thisItem = (FileItem) iter.next();
                    if (thisItem.isFormField()) {
                        if (thisItem.getFieldName().equals("_c")) {
                            lsCommand = thisItem.getString();
                        }
                    }

                    if (!thisItem.isFormField()) {
                        InputStream fin = thisItem.getInputStream();
                        data = new byte[fin.available()];
                        fin.read(data);
                        String filePath = thisItem.getName();
                        StringTokenizer t = new StringTokenizer(filePath, "\\");

                        while (t.hasMoreElements()) {
                            uploadedFileName = (String) t.nextElement();
                        }

                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (lsCommand == null || lsCommand.trim().equals("")) {
            req.setAttribute("error", "Login error");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else if (lsCommand.equals("login")) {
            String lsUser = req.getParameter("username");
            String lsPassword = req.getParameter("password");

            AppData.host_address = getServletContext().getInitParameter("rmi_server_ip");
            AppData.host_port = Integer.parseInt(getServletContext().getInitParameter("rmi_server_port"));

            UserInfo userInfo = ServiceFactory.getUserService().login(lsUser, lsPassword);

            if (userInfo == null) {
                req.setAttribute("error", "Failed to connect to server: " + AppData.host_address);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            } else if (userInfo.getSessionId() != null) {
                AppData.userInfo = userInfo;
                req.setAttribute("name", userInfo.getName());
                List rows = ServiceFactory.getFileService().openTable();
                req.setAttribute("rows", rows);
                req.getRequestDispatcher("mainDeck.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Invalid login or password");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        }
        else if(lsCommand.equals("open_table")){
            List rows = ServiceFactory.getFileService().openTable();
            req.setAttribute("rows", rows);
            req.getRequestDispatcher("mainDeck.jsp").forward(req, resp);
        }
        else if (lsCommand.equals("l0out")) {
            req.getSession().invalidate();
            if (AppData.userInfo != null && AppData.userInfo.getSessionId() != null) {
                ServiceFactory.getUserService().logout();
            }
            req.setAttribute("msg", "Logout successfully");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else if (lsCommand.equals("register")) {
            String lsUser = req.getParameter("username");
            String lsPassword = req.getParameter("password");
            String lsName = req.getParameter("name");
            UserInfo userInfo = ServiceFactory.getUserService().register(lsUser, lsPassword, lsName);
            AppData.userInfo = userInfo;

            if (userInfo == null) {
                req.setAttribute("error", "Some error occurred during registration");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            } else if (userInfo.getErrorMessage() != null) {
                req.setAttribute("error", userInfo.getErrorMessage());
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            } else if (userInfo.getSessionId() != null) {
                List rows = ServiceFactory.getFileService().openTable();
                req.setAttribute("rows", rows);
                req.getRequestDispatcher("mainDeck.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        }  else if (lsCommand.equals("download")) {
            String filename = req.getParameter("file_name");

            byte fileData[] = ServiceFactory.getFileService().download(filename);

            resp.setContentType("application/octet-stream");
            resp.setHeader("Cache-Control", "no-cache");
            resp.setContentLength(fileData.length);
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            OutputStream outStream = resp.getOutputStream();
            outStream.write(fileData);
            outStream.close();
        } else if (lsCommand.equals("upload")) {
            try {
                FileResponse response = ServiceFactory.getFileService().upload(uploadedFileName,data);
                if(response==null){
                    req.setAttribute("error", "Error while uploading file: "+uploadedFileName);
                }
                else if(response.isStatus()){
                    req.setAttribute("msg", uploadedFileName + ", uploaded successfully.");
                }
                else{
                    req.setAttribute("error", response.getMessage());
                }

                List rows = ServiceFactory.getFileService().openTable();
                req.setAttribute("rows", rows);
                req.getRequestDispatcher("mainDeck.jsp").forward(req, resp);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if (lsCommand.equals("delete")) {
            String fileName = req.getParameter("file_name");
            FileResponse response = ServiceFactory.getFileService().delete(fileName);

            if(response==null){
                req.setAttribute("error", "Error while deleting file: "+fileName);
            }
            else if(response.isStatus()){
                req.setAttribute("msg", fileName + ", deleted successfully.");
            }
            else{
                req.setAttribute("error", response.getMessage());
            }

            List rows = ServiceFactory.getFileService().openTable();
            req.setAttribute("rows", rows);
            req.getRequestDispatcher("mainDeck.jsp").forward(req, resp);
        }
    }
}
