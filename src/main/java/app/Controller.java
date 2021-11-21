/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Tyler King
 */

package app;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private boolean unsavedChanges = false;
    private boolean editModeToggle = false;
    private boolean turnOffDialogueMessages = false;

    private double xOffset = 0;
    private double yOffset = 0;

    private LocalEvent localEventEdit;

    //javaFX control declarations.
    @FXML
    private TableView<LocalEvent> table;
    @FXML
    private TableColumn<LocalEvent, String> serialNumber;
    @FXML
    private TableColumn<LocalEvent, String> name;
    @FXML
    private TableColumn<LocalEvent, String> value;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    Button addButton;

    @FXML
    Button deleteButton;

    @FXML
    private Button buttonMin;

    @FXML
    private Button buttonClose;

    @FXML
    private Button editButton;

    @FXML
    private MenuButton fileMenuButton;

    @FXML
    private TextField descriptionTextFieldSN;

    @FXML
    private TextField descriptionTextFieldName;

    @FXML
    private TextField descriptionTextFieldValue;

    private final FileChooser fileChooser = new FileChooser();

    ObservableList<LocalEvent> listMaster = FXCollections.observableArrayList();
    ObservableList<LocalEvent> listFiltered = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set the serial number column for the table view.
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("SerialNumber"));

        // Set the name column for the table view.
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));

        // Set the value column for the table view.
        value.setCellValueFactory(new PropertyValueFactory<>("Value"));

        // Make the table view a static size.
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setFixedCellSize(20);

        // Set the table view to the master ObservableList.
        table.setItems(listMaster);
    }

    public void refreshDescriptionFields() {

        // clear out all the text fields.
        descriptionTextFieldSN.setText("");
        descriptionTextFieldName.setText("");
        descriptionTextFieldValue.setText("");
    }

    // String variables used for Regex.
    String validateTitle = "Validate Fields";
    String SNFormat = "[a-zA-Z]-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}";
    String valueFormat = "^\\$?(([1-9]\\d{0,2}(,\\d{3})*)|0)?\\.\\d{2}$";

    // Validates if any entries in the table are currently being selected.
    private boolean validateSelected(String validateTitle) {

        //Create a new Alert object of type WARNING.
        Alert alert = new Alert(Alert.AlertType.WARNING);

        // Grab the index of the current table selection.
        int selectedID = table.getSelectionModel().getSelectedIndex();

        // If nothing is selected show the WARNING and return false.
        if (selectedID == -1) {
            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("A list item must be selected.");
            alert.showAndWait();

            return false;
        }
        return true;
    }

    private boolean validateTextFields(String validateTitle) {

        //Create a new Alert object of type WARNING.
        Alert alert = new Alert(Alert.AlertType.WARNING);

        // Case 1 : SN is empty
        if ((descriptionTextFieldSN == null || descriptionTextFieldSN.getText().isEmpty())) {

            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("A Serial Number must be entered.");
            alert.showAndWait();

            return false;
        }

        // Case 2 : Name is empty
        if (descriptionTextFieldName == null || descriptionTextFieldName.getText().isEmpty()) {

            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("Item Name must be entered.");
            alert.showAndWait();

            return false;
        }

        // Case 3 : Value is empty
        if (descriptionTextFieldValue == null || descriptionTextFieldValue.getText().isEmpty()) {

            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("A value must be entered.");
            alert.showAndWait();

            return false;
        }

        // Case 4 : SN wrong format
        if (!descriptionTextFieldSN.getText().matches(SNFormat)) {
            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("Serial Number must be in A-XXX-XXX-XXX format.");
            alert.showAndWait();

            return false;
        }

        // Case 5 : Name > 256
        if (descriptionTextFieldName.getText().length() > 256) {

            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("Name value must not be greater than 256 characters.");
            alert.showAndWait();

            return false;
        }

        // Case 6 : Name < 2
        if (descriptionTextFieldName.getText().length() < 2) {

            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("Name value must not be less than 2 characters.");
            alert.showAndWait();

            return false;
        }

        // Case 7 : Value wrong format
        if ((!descriptionTextFieldValue.getText().matches(valueFormat))) {
            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("Value must be positive and in #.## format.");
            alert.showAndWait();

            return false;
        }

        return true;
    }

    private boolean validateDuplicateForAdd(String validateTitle) {

        //Create a new Alert object of type WARNING.
        Alert alert = new Alert(Alert.AlertType.WARNING);

        // Loop through the Master list using a LocalEvent variable {
        for (LocalEvent event : listMaster) {

            if (event.getSerialNumber().equals(descriptionTextFieldSN.getText())) {

                // then show the alert message and return false.
                alert.setTitle(validateTitle);
                alert.setHeaderText(null);
                alert.setContentText("A Serial Number with given values already exists. Please give \"Unique\" entries only.");
                alert.showAndWait();

                return false;
            }
        }
        return true;
    }

    public void addEntry() {

        // Check if the entry to be added is valid.
        if (validateTextFields(validateTitle) && validateDuplicateForAdd(validateTitle)) {

            // Set a String variable to the TextField value.
            String value = descriptionTextFieldValue.getText();

            // Check if the string has a '$' in it. {
            if (!value.contains("$")) {

                // Assign a String variable to be used for concatenating a '$'
                String valueWith$sign;
                valueWith$sign = "$" + value;

                // Create a new LocalEvent object with given TextField Values.
                LocalEvent localEvent = new LocalEvent(descriptionTextFieldSN.getText(), descriptionTextFieldName.getText(),
                        valueWith$sign);

                // Add the LocalEvent object w/ concatenated string to the master list.
                listMaster.add(localEvent);
            }

            // do the same as before only using original string that already had a '$'.
            else {
                LocalEvent localEvent = new LocalEvent(descriptionTextFieldSN.getText(), descriptionTextFieldName.getText(), value);
                listMaster.add(localEvent);
            }

            // Set the table to the master list.
            table.setItems(listMaster);

            // Refresh the Description TextFields.
            refreshDescriptionFields();
            revertSNTextField();
            revertNameTextField();
            revertValueTextField();

            // Technically after adding an entry there are now unsaved changes.
            unsavedChanges = true;
        }
    }

    public void deleteEntry() {

        // Check if an entry is indeed being selected.
        if (validateSelected(validateTitle)) {

            // Get the index of the table selection.
            int selectedID = table.getSelectionModel().getSelectedIndex();

            // Get all the items from the tables' selected index and remove them.
            table.getItems().remove(selectedID);

            // Refresh the table.
            table.refresh();

            // Set a boolean variable tracking changes being made to true.
            unsavedChanges = true;
        }
    }

    public void deleteAllEntries() {

        // Check if the table isn't empty.
        if (!table.getItems().isEmpty()) {
            // if not empty then Clear the table.
            table.getItems().clear();
            // Clear the master list.
            listMaster.clear();
            // Set a boolean variable tracking changes being made to true.
            unsavedChanges = true;
        }
    }

    @FXML
    private void sortEntriesBySerialNumber() {

        // Check if the master list isn't empty{
        if (!listMaster.isEmpty()) {

            // if not empty then Create a Comparator variable of type LocalEvent
            // and set it to the comparison of all the "Serial Numbers".
            Comparator<LocalEvent> comparator = Comparator.comparing(LocalEvent::getSerialNumber);

            // Sort the master list based off the comparator variable.
            listMaster.sort(comparator);

            // Set the table to the master list.
            table.setItems(listMaster);

            // Set a boolean variable tracking changes being made to true.
            unsavedChanges = true;
        }
    }

    @FXML
    private void sortEntriesByName() {

        // Check if the master list isn't empty{
        if (!listMaster.isEmpty()) {

            // if not empty then Create a Comparator variable of type LocalEvent
            // and set it to the comparison of all the "Names".
            Comparator<LocalEvent> comparator = Comparator.comparing(LocalEvent::getName);

            // Sort the master list based off the comparator variable.
            listMaster.sort(comparator);

            // Set the table to the master list.
            table.setItems(listMaster);

            // Set a boolean variable tracking changes being made to true.
            unsavedChanges = true;
        }
    }

    @FXML
    private void sortEntriesByLeastValue(){

        // Check if the master list isn't empty{
        if (!listMaster.isEmpty()) {

            // if not empty then Create a Comparator variable of type LocalEvent
            // and set it to the comparison of all the "Values".
            Comparator<LocalEvent> comparator = Comparator.comparing(LocalEvent::getValue);

            // Sort the master list based off the comparator variable.
            listMaster.sort(comparator);

            // Set the table to the master list.
            table.setItems(listMaster);

            // Set a boolean variable tracking changes being made to true.
            unsavedChanges = true;
        }
    }

    public void menuSavePicked() {

        // Create a Window variable and set it to the file menu variables getScene method return.
        Window stage = fileMenuButton.getScene().getWindow();

        // Set the title/ initial file name and extension filters for the file chooser.
        fileChooser.setTitle("Save Dialog");
        fileChooser.setInitialFileName("InventoryManagement");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TSV", "*.txt"), new FileChooser.ExtensionFilter("HTML", "*.html"),
                new FileChooser.ExtensionFilter("JSON", "*.json"));

        // try to acquire the file path to be saved from the file chooser.
        try {
            File file = fileChooser.showSaveDialog(stage);

            // If the path isn't null set the initial directory as the parent file.
            if (file != null) {

                fileChooser.setInitialDirectory(file.getParentFile());

                // Set a String variable as the files absolute path.
                String fileLoc = file.getAbsolutePath();

                // Check if the string ends in a 't'.
                if (fileLoc.endsWith("t")) {

                    // Set a boolean variable tracking changes being made to false.
                    unsavedChanges = false;

                    // Call the saveTSVFile method with the String variable as a parameter.
                    saveTSVFile(file);
                }

                // Check if the string ends in a 'l'.
                if (fileLoc.endsWith("l")) {

                    // Set a boolean variable tracking changes being made to false.
                    unsavedChanges = false;

                    // Call the saveHTMLFile method with the String variable as a parameter.
                    saveHTMLFIle(file);
                }

                // Check if the string ends in a 'n'.
                if (fileLoc.endsWith("n")) {

                    // Set a boolean variable tracking changes being made to false.
                    unsavedChanges = false;

                    // Call the saveJSONFile method with the String variable as a parameter.
                    saveJSONFIle(file);
                }
            }// Catch any exceptions and print the stack trace.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveTSVFile(File file)throws IOException {

        // Set a boolean variable tracking changes being made to false.
        unsavedChanges = false;

        // Create new PrintWriter object and pass it the File path.
        PrintWriter pw = new PrintWriter(file);

        // Loop through the Master list using a LocalEvent variable
        for (LocalEvent event : listMaster) {

            // Print write the event variable's get serial number method return.
            pw.write(event.getSerialNumber());

            // Print write a tab.
            pw.write("\t");

            // Print write the event variable's get name method return.
            pw.write(event.getName());

            // Print write a tab.
            pw.write("\t");

            // Print write the event variable's get value method return.
            pw.write(event.getValue());

            // Print write a new line.
            pw.println();
        }
        // flush and close the PrintWriter.
        pw.flush();
        pw.close();
    }

    private void saveHTMLFIle(File file)throws IOException{

        // Set a boolean variable tracking changes being made to false.
        unsavedChanges = false;

        // Create new PrintWriter object and pass it the File path.
        PrintWriter pw = new PrintWriter(file);

        // Print write the doctype tag.
        pw.write("<!DOCTYPE html>");

        // Print write the html tag.
        pw.write("<html>");

        // Print write the style tag.
        pw.write("<style>");

        // Print write style info.
        pw.write("table, th, td {");
        pw.write("  border:1px solid black; }");
        // Print write style tag close.
        pw.write("</style>");

        // print write body tag close.
        pw.write("<body>\n");

        // print write the table size.
        pw.write("<table style=\"width:100%\">");

        // print write the table row tag.
        pw.write("  <tr>");

        // print write the table headers for SN, Name, and Value.
        pw.write("    <th>Serial Number</th>");
        pw.write("    <th>Item Name</th>");
        pw.write("    <th>Item Value</th>");

        // Print write the table row tag close.
        pw.write("  </tr>");

        // Loop through the Master list using a LocalEvent variable {
        for (LocalEvent event : listMaster) {

            // Print write the table row tag.
            pw.write("  <tr>");

            // Print write the table data cell tag with event return elements followed by the td close tag.
            pw.write("<td>" + event.getSerialNumber() + "<t/d>");
            pw.write("<td>" + event.getName() + "<t/d>");
            pw.write("<td>" + event.getValue() + "<t/d>");
        }
        // Print write the table tag close , then body tag close, then html tag close.
        pw.write("</table>\n");
        pw.write("</body>");
        pw.write("</html>");
        // flush and close the PrintWriter.
        pw.flush();
        pw.close();
    }

    private void saveJSONFIle(File file)throws IOException{

        // Create an integer counter variable.
        int loopCounter = 0;

        // Set a boolean variable tracking changes being made to false.
        unsavedChanges = false;

        // Create new PrintWriter object and pass it the File path.
        PrintWriter pw = new PrintWriter(file);

        // Print write the JSON object opening.
        pw.write("{\n");

        // Print write the Json array opening.
        pw.write(" \"Items\" : [\n");

        // Loop through the Master list using a LocalEvent variable {
        for (LocalEvent event : listMaster) {

            // increment the counter.
            loopCounter++;

            // Print write the json elements. If at the end print the last value without the following ','
            pw.write("{\"Serial Number\": \"" + event.getSerialNumber() + "\",");
            pw.write("\"Name\": \"" + event.getName() + "\", ");

            if (loopCounter == listMaster.size())
                pw.write("\"Value\": \"" + event.getValue() + "\" }\n");
            else
                pw.write("\"Value\": \"" + event.getValue() + "\" },\n");
        }
        // Print write the Json array closing.
        pw.write("  ]\n");

        // Print write the JSON object closing.
        pw.write("}");

        // flush and close the PrintWriter.
        pw.flush();
        pw.close();
    }

    public void menuLoadPicked()throws IOException{

        // Create a Window variable and set it to the file menu variables getScene method return.
        Window stage = fileMenuButton.getScene().getWindow();

        // Set the title and try to acquire the file path to be opened from the file chooser.
        fileChooser.setTitle("Load Dialog");
        File file = fileChooser.showOpenDialog(stage);

        // If the path isn't null set the initial directory as the parent file.
        if (file != null) {
            fileChooser.setInitialDirectory(file.getParentFile());

            // Set a String variable as the files absolute path.
            String fileLoc = file.getAbsolutePath();

            // Check if the string ends in a 't'.
            if (fileLoc.endsWith("t")) {

                // call openTSVFile method with a File path passed in as a parameter.
                openTSVFile(file);
            }

            // Check if the string ends in a 'n'.
            if (fileLoc.endsWith("n")) {

                // Set a JsonObject to the return of the readJSONFile method.
                JsonObject jsonObject = readJSONFile(file);

                // Set a JsonArray variable to the return of the getJsonArray method.
                JsonArray items = getJsonArray(jsonObject);

                // call openJSONFile method with a File path passed in as a parameter.
                openJSONFile(items);
            }


            // Check if the string ends in a 'l'.
            if (fileLoc.endsWith("l")) {

                // call openHTMLFile method with a File path passed in as a parameter.
                openHTMLFile(file);
            }
        }
    }

    public void openHTMLFile(File file){
        // create JSOUPS Document object.
        org.jsoup.nodes.Document document = null;

        // try to set the document object to the return JSOUPS parse method of the HTML file.
        try {
            document = Jsoup.parse(file, "UTF-8");

            // catch an IOException and print the stack trace.
        } catch (IOException e) {
            e.printStackTrace();
        }
        // assert the document isn't null.
        assert document != null;

        //select the first table.
        Element Table = document.select("table").get(0);
        Elements rows = Table.select("tr");

        //first row is the col names so skip it. Loop through the rows.
        for (int i = 1; i < rows.size(); i++) {

            // Get the element from each row
            Element row = rows.get(i);
            Elements cols = row.select("td");

            // Set three String variables to HTML elements.
            String SN = cols.get(0).text();
            String name = cols.get(1).text();
            String value = cols.get(2).text();

            // Pass the String variables to a LocalEvent object.
            LocalEvent localEvent = new LocalEvent(SN, name, value);

            // Add the LocalEvent object to the master list.
            listMaster.add(localEvent);
        }
        // Set the table to the master list.
        table.setItems(listMaster);
    }

    public void openTSVFile(File file)throws IOException{

        // Create a new BufferedReader object with a File reader as a parameter.
        BufferedReader TSVReader = new BufferedReader(new FileReader(file));

        // Clear the master list.
        listMaster.clear();

        // Create a string variable as the read line.
        String line;

        // Continue looping until the read line is null.
        while ((line = TSVReader.readLine()) != null) {

            // Split the strings by the \t and store them in a string array.
            String[] lineItems = line.split("\t");

            // Trim each string and pass them to a LocalEvent object.
            String serialNumber = lineItems[0].trim();
            String name = lineItems[1].trim();
            String value = lineItems[2].trim();

            LocalEvent localEvent = new LocalEvent(serialNumber, name, value);

            // Add the LocalEvent object to the master list.
            listMaster.add(localEvent);
        }
        // Set the table to the master list.
        table.setItems(listMaster);
    }

    public JsonObject readJSONFile(File file)throws FileNotFoundException{

        // Read json file using parser and store it in obj.
        Object obj = JsonParser.parseReader(new FileReader(file));

        // Create json object to read internal values.
        return (JsonObject) obj;
    }

    public JsonArray getJsonArray(JsonObject jsonObject){

        // Reading products array from the file.
        return (JsonArray) jsonObject.get("Items");
    }

    public void openJSONFile(JsonArray items){

        // Loop through the JsonArray using a JsonElement variable. {
        for (JsonElement item : items) {

            // if the element is null break from the loop.
            if (item.isJsonNull())
                break;

            // get the string value of the JSON object
            // Assign them to three different String variables.
            String SN = item.getAsJsonObject().get("Serial Number").getAsString();
            String NAME = item.getAsJsonObject().get("Name").getAsString();
            String VALUE = item.getAsJsonObject().get("Value").getAsString();

            // Pass the String variables to a LocalEvent object.
            LocalEvent localEvent = new LocalEvent(SN, NAME, VALUE);

            // Add the LocalEvent object to the master list.
            listMaster.add(localEvent);
        }

        // Set the table to the master list.
        table.setItems(listMaster);
    }

    private boolean validateNameSearchFields(String validateTitle){

        //Create a new Alert object of type WARNING.
        Alert alert = new Alert(Alert.AlertType.WARNING);

        // if the Name Description Text field is null/empty.
        if (descriptionTextFieldName == null || descriptionTextFieldName.getText().isEmpty()) {

            // Show alert and return false;
            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("A Name must be entered.");
            alert.showAndWait();

            return false;
        }


        // if the Name Description Text field is > 256 characters.
        if (descriptionTextFieldName.getText().length() > 256) {

            // Show alert and return false;
            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("A Name must not exceed 256 characters.");
            alert.showAndWait();

            return false;
        }


        // if the Name Description Text field is < 2 characters.
        if (descriptionTextFieldName.getText().length() < 2) {

            // Show alert and return false;
            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("A Name must exceed 2 characters.");
            alert.showAndWait();

            return false;
        }

        return true;
    }

    private boolean validateSerialNumberSearchFields(String validateTitle){

        //Create a new Alert object of type WARNING.
        Alert alert = new Alert(Alert.AlertType.WARNING);

        // if the Serial Number Description Text field is null/empty.
        if ((descriptionTextFieldSN == null || descriptionTextFieldSN.getText().isEmpty())) {

            // Show alert and return false;
            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("A Serial Number must be entered.");
            alert.showAndWait();

            return false;
        }

        // if the Serial Number Description Text field format is invalid.
        if (!descriptionTextFieldSN.getText().matches(SNFormat)) {

            // Show alert and return false;
            alert.setTitle(validateTitle);
            alert.setHeaderText(null);
            alert.setContentText("Serial Number must be positive and in #.## format.");
            alert.showAndWait();

            return false;
        }

        return true;
    }

    @FXML
    private void searchSerialNumberOfEntries(){

        // check if the serial number format is valid from the description text box.
        if (validateSerialNumberSearchFields(validateTitle)) {

            // Set a string variable to the description text box value.
            String snSearchField = descriptionTextFieldSN.getText().trim();

            // set a boolean skip variable used for testing purposes to true.
            boolean skipRow = true;

            // Loop through the Master list using a LocalEvent variable {
            for (LocalEvent event : listMaster) {

                // check if boolean is true and continue.
                if (skipRow) {
                    skipRow = false;
                    continue;
                }

                // check if the string variable matches a result from the list.
                if (event.getSerialNumber().equalsIgnoreCase(snSearchField)) {

                    // If found clear a filtered list.
                    listFiltered.clear();

                    // Set the table to the freshly cleared list.
                    table.setItems(listFiltered);

                    // add the LocalEvent matched keyword to the filtered list
                    listFiltered.add(event);

                    // set the table to the filtered list
                    table.setItems(listFiltered);

                    // Finally, break from the loop.
                    break;
                }
            }
        }
    }

    @FXML
    private void searchNameOfEntries() {

        // check if the name format is valid from the description text box.
        if (validateNameSearchFields(validateTitle)) {

            // Set a string variable to the description text box value.
            String nameSearchField = descriptionTextFieldName.getText().trim();

            // set a boolean skip variable used for testing purposes to true.
            boolean skipRow = true;

            // clear a filtered list.
            listFiltered.clear();

            // Loop through the Master list using a LocalEvent variable {
            for (LocalEvent event : listMaster) {

                // check if boolean is true and continue.
                if (skipRow) {
                    skipRow = false;
                    continue;
                }

                // If found add the LocalEvent matched keyword to the filtered list
                if (event.getName().equalsIgnoreCase(nameSearchField)) {

                    listFiltered.add(event);
                }
            }
            // set the table to the filtered list
            table.setItems(listFiltered);
        }
    }

    public boolean validateDuplicateForEdit(String key){

        // Loop through the Master list using a LocalEvent variable
        for (LocalEvent event : listMaster) {

            // if any local event variables in the list match the key
            if (event.getSerialNumber().equals(key)) {
                if (!turnOffDialogueMessages) {


                    // Create a new Alert object of type WARNING.
                    Alert alert = new Alert(Alert.AlertType.WARNING);

                    // Show alert and return false;
                    alert.setTitle("Validate Duplicate Serial Numbers");
                    alert.setHeaderText(null);
                    alert.setContentText("A Serial Number with given values already exists. Please give \"Unique\" entries only.");
                    alert.showAndWait();
                }
                return false;
            }
        }
        return true;
    }
    // String variable used for determining a non edit.
    String backup ="";
    @FXML
    private void editEntry(){

        // First check if there is a valid selection {
        if(validateSelected(validateTitle)) {

            // Get the index of the current table selection.
            int index = listMaster.indexOf(table.getSelectionModel().getSelectedItem());

            if (editModeToggle) {

                // Set three String variables to the trimmed values of the text fields.
                String serialNumber = descriptionTextFieldSN.getText().trim();
                String name = descriptionTextFieldName.getText().trim();
                String value = descriptionTextFieldValue.getText().trim();

                // If there isn't an edit dupCheck is made true.
                boolean doDupCheck = !serialNumber.equals(backup);

                // If the text fields are valid and dupCheck passes
                if (validateTextFields(validateTitle) && (!doDupCheck||validateDuplicateForEdit(serialNumber))) {

                    // Get the event strings and set them in their respective text fields.
                    descriptionTextFieldSN.setText(serialNumber);
                    descriptionTextFieldName.setText(name);
                    descriptionTextFieldValue.setText(value);


                    // Set a LocalEvent edit with the given serial number and name values.
                    localEventEdit.setSerialNumber(serialNumber);
                    localEventEdit.setName(name);

                    // Check for the '$' character append on if necessary then do the same with value.
                    if (!value.contains("$")){
                        String valueWith$ = "$"+value;
                        localEventEdit.setValue(valueWith$);
                    }
                    else {
                        localEventEdit.setValue(value);
                    }

                    // Set the boolean edit toggle to false.
                    editModeToggle = false;

                    // Set the edit button to say edit.
                    editButton.setText("Edit");

                    // Wipe the TextFields
                    refreshDescriptionFields();
                    revertSNTextField();
                    revertNameTextField();
                    revertValueTextField();

                    // Set a boolean variable tracking changes being made to true.
                    unsavedChanges = true;
                }
            }
            else{
                // set the LocalEvent variable to list masters selected index.
                localEventEdit = listMaster.get(index);

                // Set a backup string to the serial number.
                backup = localEventEdit.getSerialNumber();

                // Set the description Text fields again.
                descriptionTextFieldSN.setText(String.valueOf(localEventEdit.getSerialNumber()));
                descriptionTextFieldSN.setStyle("-fx-border-color: blue;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+
                        "-fx-border-radius: 3 3 3 3, 3 3 3 3, 3 3 3 3, 3 3 3 3;"+"-fx-text-fill: white;");

                descriptionTextFieldName.setText(String.valueOf(localEventEdit.getName()));
                descriptionTextFieldName.setStyle("-fx-border-color: blue;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+
                        "-fx-border-radius: 3 3 3 3, 3 3 3 3, 3 3 3 3, 3 3 3 3;"+"-fx-text-fill: white;");

                descriptionTextFieldValue.setText(String.valueOf(localEventEdit.getValue()));
                descriptionTextFieldValue.setStyle("-fx-border-color: blue;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+
                        "-fx-border-radius: 3 3 3 3, 3 3 3 3, 3 3 3 3, 3 3 3 3;"+"-fx-text-fill: white;");

                // Change edit boolean to true
                editModeToggle = true;

                // Set button text to save
                editButton.setText("Save");
            }
            // Set table to the master list.
            table.setItems(listMaster);
            // Refresh the table.
            table.refresh();
        }
    }

    public boolean displayModalPopup() {

        // Create a new Alert object of type WARNING.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        // Information to be displayed in warning.
        alert.setTitle("Unsaved data warning");
        alert.setContentText("There is unsaved data. Do you want to continue and lose data ?");

        // Returns true if user clicks OK.
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }

    @FXML
    protected void handleMinAction() {

        // Get the scene of the stage.
        Stage stage = (Stage) buttonMin.getScene().getWindow();

        // Iconify the stage (Minimize).
        stage.setIconified(true);
    }

    @FXML
    public void handleCloseAction() {

        // First check for any unsaved changes
        if (unsavedChanges) {

            // Set the return of the Unsaved changes Alert to a boolean variable.
            boolean decision = displayModalPopup();

            // If user indeed wishes to close then close.
            if (decision) {
                Stage stage = (Stage) buttonClose.getScene().getWindow();
                stage.close();
            }
            // Else break from the close action.
            if (!decision)
                return;
        }

        // If there weren't any unsaved changes to begin with then just close.
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleClickAnchorPaneAction(MouseEvent event) {

        // Logic for clicking on the anchor pane. -> Will be used with handle Movement.
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    @FXML
    protected void handleClickSNTextField() {

        descriptionTextFieldSN.setStyle("-fx-border-color: blue;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+
                "-fx-border-radius: 3 3 3 3, 3 3 3 3, 3 3 3 3, 3 3 3 3;"+"-fx-text-fill: white;");

        if (descriptionTextFieldName.getText().isEmpty() && descriptionTextFieldValue.getText().isEmpty()) {
            descriptionTextFieldName.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
            descriptionTextFieldValue.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
        }
        else if (descriptionTextFieldValue.getText().isEmpty()) {

            descriptionTextFieldValue.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
        }
        else if (descriptionTextFieldName.getText().isEmpty()) {

            descriptionTextFieldName.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
        }
    }

    @FXML
    protected void handleClickNameTextField() {

        descriptionTextFieldName.setStyle("-fx-border-color: blue;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+
                "-fx-border-radius: 3 3 3 3, 3 3 3 3, 3 3 3 3, 3 3 3 3;"+"-fx-text-fill: white;");

        if (descriptionTextFieldValue.getText().isEmpty() && descriptionTextFieldSN.getText().isEmpty()) {
            descriptionTextFieldSN.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
            descriptionTextFieldValue.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
        }
        else if (descriptionTextFieldSN.getText().isEmpty()) {
            descriptionTextFieldSN.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
        }
        else if (descriptionTextFieldValue.getText().isEmpty()) {
            descriptionTextFieldValue.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
        }
    }

    @FXML
    protected void handleClickValueTextField() {

        descriptionTextFieldValue.setStyle("-fx-border-color: blue;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+
                "-fx-border-radius: 3 3 3 3, 3 3 3 3, 3 3 3 3, 3 3 3 3;"+"-fx-text-fill: white;");

        if (descriptionTextFieldName.getText().isEmpty() && descriptionTextFieldSN.getText().isEmpty()) {
            descriptionTextFieldName.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
            descriptionTextFieldSN.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
        }

        else if (descriptionTextFieldSN.getText().isEmpty()) {
            descriptionTextFieldSN.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
        }

        else if (descriptionTextFieldName.getText().isEmpty()) {
            descriptionTextFieldName.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
        }
    }

    public void revertSNTextField() {
        descriptionTextFieldSN.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
    }

    public void revertNameTextField() {
        descriptionTextFieldName.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
    }

    public void revertValueTextField() {
        descriptionTextFieldValue.setStyle("-fx-border-color: white;"+"-fx-background-color: #1b1b1c;"+"-fx-border-insets: 0, 0, 0, 0;"+"-fx-text-fill: white;");
    }

    @FXML
    protected void handleMovementAction(MouseEvent event) {

        // Logic for tracking movement.
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    public void hoverOnCloseBt(){

        // Sets the color of the button to red when hovering on it.
        buttonClose.setStyle("-fx-background-color: #fc0303;");
    }

    @FXML
    public void hoverOffCloseBt(){

        // Revert to normal when not hovering on the button.
        buttonClose.setStyle("-fx-background-color: #1b1b1c;"+"-fx-border-color: white;");
    }

    public void setMainTestList(ObservableList<LocalEvent> listTest) {
        listMaster = listTest;
        turnOffDialogueMessages = true;
    }
}