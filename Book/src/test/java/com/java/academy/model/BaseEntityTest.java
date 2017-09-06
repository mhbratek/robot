package com.java.academy.model;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BaseEntityTest {

    @DataProvider
        public Object[][] idProvider() {
            return new Object[][]{
                {100L},
                {200L},
                {1L}};
        }

    @Test (dataProvider = "idProvider")
    public void shouldSetCorrectId(Long id){

        //given
        BaseEntity entity = new BaseEntity();

        //when
        entity.setId(id);

        //then
        assertEquals(entity.getId(), id);
    }

}