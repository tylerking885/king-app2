package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalEventTest {

    @Test
    void testLocalEvent() {
        String serialNumber = "A-123-456-789";
        String name = "Item 1";
        String value = "$0.00";

        LocalEvent localEvent = new LocalEvent(serialNumber,name, value);
        assertEquals(serialNumber,localEvent.getSerialNumber());
        assertEquals(name, localEvent.getName());
        assertEquals(value, localEvent.getValue());

        localEvent.setSerialNumber("Z-000-000-000");
        assertEquals("Z-000-000-000", localEvent.getSerialNumber());
        localEvent.setName("Changed Name");
        assertEquals("Changed Name", localEvent.getName());
        localEvent.setValue("$100.00");
        assertEquals("$100.00", localEvent.getValue());

    }

    @Test
    void testLocalEventGettersSetters(){
        String serialNumber = "A-123-456-789";
        String name = "Item 1";
        String value = "$0.00";

        LocalEvent localEvent = new LocalEvent(serialNumber,name, value);

        assertEquals(localEvent.getSerialNumber(), serialNumber);
        assertEquals(localEvent.getName(), name);
        assertEquals(localEvent.getValue(), value);
        serialNumber = "b-987-654-321";
        name = "Item 2";
        value = "$1.00";
        localEvent.setSerialNumber(serialNumber);
        localEvent.setName(name);
        localEvent.setValue(value);

        assertEquals(localEvent.getSerialNumber(), serialNumber);
        assertEquals(localEvent.getName(), name);
        assertEquals(localEvent.getValue(), value);
    }
}