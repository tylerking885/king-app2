/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Test
 *  Copyright 2021 Tyler King
 */

package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalEventTest {

    @Test
    void testLocalEventGettersSetters(){
        String serialNumber = "A-123-456-789";
        String name = "Item 1";
        String value = "$0.00";

        LocalEvent localEvent = new LocalEvent(serialNumber,name, value);

        // Three tests on getters
        assertEquals(localEvent.getSerialNumber(), serialNumber);
        assertEquals(localEvent.getName(), name);
        assertEquals(localEvent.getValue(), value);

        serialNumber = "b-987-654-321";
        name = "Item 2";
        value = "$1.00";
        localEvent.setSerialNumber(serialNumber);
        localEvent.setName(name);
        localEvent.setValue(value);

        // Three more tests after setting new variables.
        assertEquals(localEvent.getSerialNumber(), serialNumber);
        assertEquals(localEvent.getName(), name);
        assertEquals(localEvent.getValue(), value);
    }
}