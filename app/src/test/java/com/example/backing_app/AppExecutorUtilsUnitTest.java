package com.example.backing_app;

import com.example.backing_app.utils.AppExecutorUtils;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.concurrent.Executor;

public class AppExecutorUtilsUnitTest {

    @Test
    public void testAppExecutorInstance(){

        // Is the same, we dont create new instances

        AppExecutorUtils instance = AppExecutorUtils.getsInstance();

        assertEquals(instance,AppExecutorUtils.getsInstance());
    }

    @Test
    public void testAppExecutorDiskIO(){

        // We ensure is the same Executor object

        Executor disk = AppExecutorUtils.getsInstance().diskIO();

        assertEquals(disk, AppExecutorUtils.getsInstance().diskIO());

    }

    @Test
    public void testAppExecutorNetworkIO(){

        // We ensure is the same Executor object

        Executor networkIO = AppExecutorUtils.getsInstance().networkIO();

        assertEquals(networkIO, AppExecutorUtils.getsInstance().networkIO());
    }

    @Test
    public void testAppExecutorMainThread(){

        // We ensure is the same Executor object

        Executor mainThread = AppExecutorUtils.getsInstance().mainThread();

        assertEquals(mainThread, AppExecutorUtils.getsInstance().mainThread());
    }
}
