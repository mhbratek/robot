package com.java.academy.interceptors;

import com.java.academy.configuration.SpringMvcConfiguration;
import com.java.academy.configuration.SpringWebsiteInitializer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

/**
 * @author bratek
 * @since 05.09.17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringMvcConfiguration.class, SpringWebsiteInitializer.class})
@WebAppConfiguration
public class PerformanceMonitorInterceptorTest {


    private static final int HTTP_OK = 200;

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldRunInterceptorBeforeAndAfterGivenRequest() throws Exception {

        Exception ex = mock(Exception.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/robot/books");
        request.setMethod("GET");

        MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);

        HandlerInterceptor[] interceptors = handlerExecutionChain.getInterceptors();

        ModelAndView mav = handlerAdapter.handle(request, response, handlerExecutionChain.getHandler());

        for (HandlerInterceptor interceptor : interceptors){
            interceptor.preHandle(request, response, handlerExecutionChain.getHandler());
            interceptor.postHandle(request, response, handlerExecutionChain.getHandler(), mav);
            interceptor.afterCompletion(request, response, handlerExecutionChain.getHandler(), ex);
        }

        assertEquals(HTTP_OK, response.getStatus());
    }

}