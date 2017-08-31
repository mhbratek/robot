package com.java.academy.controller;

import com.java.academy.configuration.SpringMvcConfiguration;
import com.java.academy.configuration.SpringWebsiteInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringMvcConfiguration.class, SpringWebsiteInitializer.class})
@WebAppConfiguration
public class MainControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void welcomePageTest() throws Exception {
        mockMvc.perform(get("/robot")).andExpect(status().isOk()).andExpect(view().name("start"));
    }

    @Test
    public void booksPageTest() throws Exception {
        mockMvc.perform(get("/robot/books")).andExpect(status().isOk()).andExpect(view().name("books"));
    }

    @Test
    public void booksTilesPageTest() throws Exception {
        mockMvc.perform(get("/robot/booksTiles")).andExpect(status().isOk()).andExpect(view().name("booksTiles"));
    }

    @Test
    public void shouldBeInternalError() throws Exception {
        mockMvc.perform(post("/robot/rest/add")).andExpect(status().isInternalServerError());
    }


}
