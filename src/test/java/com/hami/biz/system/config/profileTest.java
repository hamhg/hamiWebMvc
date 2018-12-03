package com.hami.biz.system.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("base,dev")
@RunWith(SpringRunner.class)
public class profileTest {
    
    @Value("${core}")
    private String core;
    
    @Value("${PROFILE}")
    private String test;

    @Test
    public void contextLoads() {
        assertEquals(core, "core");
        assertEquals(test, "dev");
    }
}
