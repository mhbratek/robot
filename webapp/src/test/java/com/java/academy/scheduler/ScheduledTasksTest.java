package com.java.academy.scheduler;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ScheduledTasksTest {

    @Test
    public void shouldInitializeListWithAllScrappers() {

        //given
        ScheduledTasks scheduledTasks = new ScheduledTasks();

        //when
        int totalNumOfScrappers = 6;

        //ten
        assertEquals(scheduledTasks.initBookStores().size(), totalNumOfScrappers);
    }

}