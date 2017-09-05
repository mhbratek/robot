package com.java.academy.view;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class JsonViewResolverTest {

    @Test
    public void viewIsInstanceOfJackson2JsonView() throws Exception {
        JsonViewResolver jsonViewResolver = new JsonViewResolver();
        assertTrue(jsonViewResolver.resolveViewName(null, null) instanceof MappingJackson2JsonView);
    }
}
