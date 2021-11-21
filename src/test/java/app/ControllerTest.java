package app;

import com.google.gson.JsonArray;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void testingIfValidateDuplicateForEditValidatesAsIntended() {

        Controller test = new Controller();

        ObservableList<LocalEvent> testList = FXCollections.observableArrayList();

        LocalEvent localEvent = new LocalEvent("a-123-456-789", "Item 1", "$1.00");
        LocalEvent localEvent2 = new LocalEvent("b-123-456-789", "Item 2", "$2.00");
        LocalEvent localEvent3 = new LocalEvent("c-123-456-789", "Item 3", "$3.00");

        testList.addAll(localEvent, localEvent2, localEvent3);

        test.setMainTestList(testList);
        String duplicateSerial = "a-123-456-789";
        String uniqueSerial = "d-123-456-789";

        // Testing validate Duplicate method here.
        assertTrue(test.validateDuplicateForEdit(uniqueSerial));
        assertFalse(test.validateDuplicateForEdit(duplicateSerial));
    }

    // Testing for file not found exceptions
    @Test
    void testExceptionThrowOfOpenTSVFile() {
        Controller test1 = new Controller();
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            File file = new File("thisDoesNotExist");
            test1.openTSVFile(file);
        });
    }

    @Test
    void testExceptionThrowOfOpenJSONFile() {
        Controller test1 = new Controller();
        Assertions.assertThrows(NullPointerException.class, () -> {
            JsonArray test = new JsonArray();
            test1.openJSONFile(test);
        });
    }


}
