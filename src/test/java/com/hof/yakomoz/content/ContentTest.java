package com.hof.yakomoz.content;

import org.junit.Assert;
import org.junit.Test;

public class ContentTest {

    @Test
    public void shouldContentStartWithInProgressStateWhenContentCreated(){
        Content content = new Content();
        Assert.assertNotNull(content.getState());
        Assert.assertEquals(content.getState(),State.IN_PROGRESS);
    }
}
