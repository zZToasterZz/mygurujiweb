package in.co.srdt.myguruji.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/fetchfile")
public class FileServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	    String filename = request.getParameter("img");
	    //File file = null;
	    //System.out.println("filename :" + filename);
	    File file = new File("empImages/" + filename);
	    if(!file.exists()){
	    	file = new File("empImages/img_avatar2.png");
		}
		response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	    response.setHeader("Content-Length", String.valueOf(file.length()));
	    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	    Files.copy(file.toPath(), response.getOutputStream());
	}
}
