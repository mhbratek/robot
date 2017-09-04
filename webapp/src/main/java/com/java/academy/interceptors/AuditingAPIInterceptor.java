package com.java.academy.interceptors;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
@Component
public class AuditingAPIInterceptor extends HandlerInterceptorAdapter {

    Logger logger = Logger.getLogger("auditLogger");


    public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object handler) throws Exception {

        if(request.getRequestURI().endsWith("/rest/add") && request.getMethod().equals("POST")){
            //TODO implementation about books in API
        }
        return true;
    }


    public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler,
                                Exception arg3) throws Exception {
        if(request.getRequestURI().endsWith("/rest/add") && response.getStatus() == 302){
            // TODO implementation about amount of books added
        }
    }

    private String getCurrentTime() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy 'o' hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return formatter.format(calendar.getTime());
    }
}
