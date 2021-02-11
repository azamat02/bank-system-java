package main.spring.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebFilter("/*")
public class CustomFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(!req.getRequestURI().contains("/css") && !req.getRequestURI().contains("/js") && !req.getRequestURI().contains("/images")) {
            Date date = new Date();
            String dt = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(date);
            String str = dt+" | INFO: Logging Request " + req.getMethod() + " : " + req.getRequestURI() + "\n";

            String fileName = "C:\\Users\\azama\\IdeaProjects\\bank-system\\src\\main\\resources\\logs\\logs.txt";

            FileWriter writer = new FileWriter(fileName,true);

            writer.append(str);
            writer.close();

            System.out.println("FILTERING");
        }


        filterChain.doFilter(servletRequest,servletResponse);
    }
}
