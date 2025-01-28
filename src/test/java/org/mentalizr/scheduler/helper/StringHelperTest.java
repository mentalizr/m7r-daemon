package org.mentalizr.scheduler.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringHelperTest {

    @Test
    public void plausibility() {
        String string = "Hello World";
        String cutOff = "World";

        String expected = "Hello ";
        String actual = StringHelper.cutOff(string, cutOff);

        assertEquals(expected, actual);
    }


}