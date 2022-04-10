package in.co.srdt.myguruji.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import in.co.srdt.myguruji.utils.ApplicationContent;

@WebServlet("/getcontent")
public class ContentServlet extends HttpServlet
{
	@Autowired
	ApplicationContent contentUtil;
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	    String location = request.getParameter("location");
	    //System.out.println("LOCATION : "+location);
	    location = location.replaceAll("FORWARD_SLASH", "\\\\");
	    location = location.replaceAll("BACKWARD_SLASH", "/");
	    location = location.replaceAll("EXT_DOT", ".");
	    //System.out.println("LOCATION : "+location);
	    
	    String filename = new File("").getAbsolutePath()+File.separator+contentUtil.getBaseLocation()+File.separator+location;
	    //System.out.println(filename);
	    
	    File file = new File(filename);
	    if(!file.exists()){
	    	System.out.println("ERROR : File not found");
		}
	    
		response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	    response.setHeader("Content-Length", String.valueOf(file.length()));
	    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	    Files.copy(file.toPath(), response.getOutputStream());
	}
}