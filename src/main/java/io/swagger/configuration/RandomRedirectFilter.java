package io.swagger.configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

public class RandomRedirectFilter implements Filter {

    //private final String[] paths = {"/page1", "/page2", "/page3"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //Random random = new Random();
        //int index = random.nextInt(paths.length);

        httpResponse.sendRedirect("/notAuthorized");
    }
}
