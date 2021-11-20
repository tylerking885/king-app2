/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Tyler King
 */

package app;

public class Controller {

    /*
        Class Controller will begin with all the javaFX control declarations.


        @Override
        public void initialize(URL location, ResourceBundle resources) {

           // Set the serial number column for the table view.

           // Set the name column for the table view.

           // Set the value column for the table view.

           // Make the table view a static size.

           // Set the table view to the master ObservableList.
        }

        // Validates if any entries in the table are currently being selected.
        private boolean validateSelected(String validateTitle) {

        //Create a new Alert object of type WARNING.

        // Grab the index of the current table selection.

        // If nothing is selected show the WARNING and return false.

        // Else return true.

        }


        private boolean validateFields(String validateTitle) {

            //Create a new Alert object of type WARNING.

            // Case 1 : SN is empty / Name is empty / Value is empty
            // If case is invalid. Show alert and return false;

            // Case 2 : SN is empty / Name > 256 / Value is empty.
            //If case is invalid. Show alert and return false;

            // Case 3 : SN is empty / Name < 2 / Value is empty
            //If case is invalid. Show alert and return false;

            // Case 4 : SN is empty / Value is empty
            //If case is invalid. Show alert and return false;

            // Case 5 : SN is empty / Name is empty / Value wrong format
            //If case is invalid. Show alert and return false;

            // Case 6 : SN is empty / Name < 2 / Value wrong format
            //If case is invalid. Show alert and return false;

            // Case 7 : SN is empty / Name > 256 / Value wrong format
            //If case is invalid. Show alert and return false;

            // Case 8 : SN wrong format / Name is empty / Value is empty
            //If case is invalid. Show alert and return false;

            // Case 9 : SN wrong format / Name is empty / Value wrong format
            //If case is invalid. Show alert and return false;

            // Case 10 : SN wrong format / Name < 2 / Value is empty
            //If case is invalid. Show alert and return false;

            // Case 11 : SN wrong format / Name < 2 / Value wrong format
            // If case is invalid. Show alert and return false;

            // Case 12 : SN wrong format / Name > 256 / Value is empty
            // If case is invalid. Show alert and return false;

            // Case 13 : SN wrong format / Name > 256 / Value wrong format
            // If case is invalid. Show alert and return false;

            // Case 14 : SN is empty / Value wrong format
            // If case is invalid. Show alert and return false;

            // Case 15 : SN is empty / Name is empty
            // If case is invalid. Show alert and return false;

            // Case 16 : Name is empty / Value is empty
            // If case is invalid. Show alert and return false;

            // Case 17 : Name is empty / Value format is wrong
            // If case is invalid. Show alert and return false;

            // Case 18 : Name > 256 / Value is empty
            // If case is invalid. Show alert and return false;

            // Case 19 : Name > 256 / Value wrong format
            // If case is invalid. Show alert and return false;

            // Case 20 : Name < 2 / Value is empty
            // If case is invalid. Show alert and return false;

            // Case 21 : Name < 2 / Value wrong format
            // If case is invalid. Show alert and return false;

            // Case 22 : SN is empty / Value wrong format
            // If case is invalid. Show alert and return false;

            // Case 23 : SN wrong format / Value is empty
            // If case is invalid. Show alert and return false;

            // Case 24 : SN wrong format / Value wrong format
            // If case is invalid. Show alert and return false;

            // Case 25 : SN is empty / Name > 256
            // If case is invalid. Show alert and return false;

            // Case 26 : SN wrong format / Name > 256
            // If case is invalid. Show alert and return false;

            // Case 27 : SN is empty / Name < 2
            // If case is invalid. Show alert and return false;

            // Case 28 : SN wrong format / Name < 2
            // If case is invalid. Show alert and return false;

            // Case 29 : SN is empty
            // If case is invalid. Show alert and return false;

            // Case 30 : Name is empty
            // If case is invalid. Show alert and return false;

            // Case 31 : Value is empty
            // If case is invalid. Show alert and return false;

            // Case 32 : SN wrong format
            // If case is invalid. Show alert and return false;

            // Case 33 : Name > 256
            // If case is invalid. Show alert and return false;

            // Case 34 : Name < 2
            // If case is invalid. Show alert and return false;

            // Case 35 : Value wrong format
            // If case is invalid. Show alert and return false;


        //Else return true
        }

        private boolean validateDuplicateForAdd(String validateTitle) {

            //Create a new Alert object of type WARNING.

            // Loop through the Master list using a LocalEvent variable {

                // If a Serial Number from the master list equals input from the text field.

                // then show the alert message and return false.

                }

            // Else return true.
        }

        public void addEntry() {
        // Check if the entry to be added is valid.

            // Set a String variable to the TextField value.

            // Check if the string has a '$' in it. {

                // Assign a String variable to be used for concatenating a '$'

                // Create a new LocalEvent object with given TextField Values.

                // Add the LocalEvent object w/ concatenated string to the master list.
                }

            // else do the same as before only using original string that already had a '$'.

         // Set the table to the master list.

         // Refresh the Description TextFields.

        }


        public void deleteEntry() {

        // Check if an entry is indeed being selected.

            // Get the index of the table selection.

            // Get all the items from the tables' selected index and remove them.

            // Refresh the table.

            // Set a boolean variable tracking changes being made to true.
        }

        public void deleteAllEntries() {

        // Check if the table isn't empty.

            // if not empty then Clear the table.

            // Clear the master list.

            // Set a boolean variable tracking changes being made to true.

        }

        @FXML
        private void sortEntriesBySerialNumber() {

        // Check if the master list isn't empty{

            // if not empty then Create a Comparator variable of type LocalEvent
            // and set it to the comparison of all the "Serial Numbers".

            // Sort the master list based off the comparator variable.

            // Set the table to the master list.
            // Set a boolean variable tracking changes being made to true.
            }
        }

        @FXML
        private void sortEntriesByName() {

        // Check if the master list isn't empty{

            // if not empty then Create a Comparator variable of type LocalEvent
            // and set it to the comparison of all the "Names".

            // Sort the master list based off the comparator variable.

            // Set the table to the master list.
            // Set a boolean variable tracking changes being made to true.
            }
        }

        @FXML
        private void sortEntriesByLeastValue() {

        // Check if the master list isn't empty{

            // if not empty then Create a Comparator variable of type LocalEvent
            // and set it to the comparison of all the "Values".

            // Sort the master list based off the comparator variable.

            // Set the table to the master list.
            // Set a boolean variable tracking changes being made to true.
            }
        }

        public void menuSavePicked() {

        // Create a Window variable and set it to the file menu variables getScene method return.

        // Set the title/ initial file name and extension filters for the file chooser.

        // try to acquire the file path to be saved from the file chooser.

        // If the path isn't null set the initial directory as the parent file.

            // Set a String variable as the files absolute path.

            // Check if the string ends in a 't'.  {

                   // Set a boolean variable tracking changes being made to false.
                   // Call the saveTSVFile method with the String variable as a parameter.
                }
            // Check if the string ends in a 'l'.  {

                    // Set a boolean variable tracking changes being made to false.
                    // Call the saveHTMLFile method with the String variable as a parameter.
                }
            // Check if the string ends in a 'n'.  {

                    // Set a boolean variable tracking changes being made to false.
                    // Call the saveJSONFile method with the String variable as a parameter.
                }
            }
         // Catch any exceptions and print the stack trace.
        }

        private void saveTSVFile(File file) throws IOException {

            // Set a boolean variable tracking changes being made to false.

            // Create new PrintWriter object and pass it the File path.

            // Loop through the Master list using a LocalEvent variable {

            // Print write the event variable's get serial number method return.

            // Print write a tab.

            // Print write the event variable's get name method return.

            // Print write a tab.

            // Print write the event variable's get value method return.

            // Print write a new line.

            }
        // flush and close the PrintWriter.
    }

    private void saveHTMLFIle(File file) throws IOException {

        // Set a boolean variable tracking changes being made to false.

        // Create new PrintWriter object and pass it the File path.

        // Print write the doctype tag.
        // Print write the html tag.
        // Print write the style tag.
        // Print write style info.
        // Print write style tag close.
        // print write body tag close.
        // print write the table size.
        // print write the table row tag.
        // print write the table headers for SN, Name, and Value.
        // Print write the table row tag close.

        // Loop through the Master list using a LocalEvent variable {

            // Print write the table row tag.
            // Print write the table data cell tag with event return elements followed by the td close tag.
        }
        // Print write the table tag close , then body tag close, then html tag close.

        // flush and close the PrintWriter.
    }

    private void saveJSONFIle(File file) throws IOException {

        // Create an integer counter variable.
        // Set a boolean variable tracking changes being made to false.
        // Create new PrintWriter object and pass it the File path.

        // Print write the JSON object opening.
        // Print write the Json array opening.


        // Loop through the Master list using a LocalEvent variable {

            // increment the counter.
            // Print write the json elements. If at the end print the last value without the following ','

        }
        // Print write the Json array closing.
        // Print write the JSON object closing.

        // flush and close the PrintWriter.
    }

    public void menuLoadPicked() throws IOException {

        // Create a Window variable and set it to the file menu variables getScene method return.

        // Set the title and try to acquire the file path to be opened from the file chooser.

        // If the path isn't null set the initial directory as the parent file.

            // Set a String variable as the files absolute path.

            // Check if the string ends in a 't'.  {

               // call openTSVFile method with a File path passed in as a parameter.
            }

            // Check if the string ends in a 'n'.  {

                // Set a JsonObject to the return of the readJSONFile method.
                // Set a JsonArray variable to the return of the getJsonArray method.
                // call openJSONFile method with a File path passed in as a parameter.
            }

            // Check if the string ends in a 'l'.  {

                // call openHTMLFile method with a File path passed in as a parameter.
            }

    }

    public void openHTMLFile (File file) {
        // create JSOUPS Document object.


        // try to set the document object to the return JSOUPS parse method of the HTML file.

        // catch an IOException and print the stack trace.

        // assert the document isn't null.
        //select the first table.


        //first row is the col names so skip it. Loop through the rows. {

            // Get the element from each row
            // Set three String variables to HTML elements.

            // Pass the String variables to a LocalEvent object.

            // Add the LocalEvent object to the master list.

        }
        // Set the table to the master list.
    }

    public void openTSVFile(File file) throws IOException {

        // Create a new BufferedReader object with a File reader as a parameter.
        // Clear the master list.
        // Create a string variable as the read line.
        // Continue looping until the read line is null. {

            // Split the strings by the \t and store them in a string array.

            // Trim each string and pass them to a LocalEvent object.

            // Add the LocalEvent object to the master list.
        }
        // Set the table to the master list.
    }

    public JsonObject readJSONFile(File file) throws FileNotFoundException {

        // Read json file using parser and store it in obj.


        // Create json object to read internal values.
    }

    public JsonArray getJsonArray(JsonObject jsonObject) {

        // Reading products array from the file.
    }

    public void openJSONFile(JsonArray items) {
        // Loop through the JsonArray using a JsonElement variable. {

            // if the element is null break from the loop.

            // get the string value of the JSON object
            // Assign them to three different String variables.


            // Pass the String variables to a LocalEvent object.

            // Add the LocalEvent object to the master list.

        }
        // Set the table to the master list.
    }

    private boolean validateNameSearchFields(String validateTitle) {

        //Create a new Alert object of type WARNING.

        // if the Name Description Text field is null/empty.{

            // Show alert and return false;
            }



        // if the Name Description Text field is > 256 characters. {

            // Show alert and return false;
            }


        // if the Name Description Text field is < 2 characters. {

            // Show alert and return false;
            }

        else return true.
    }

    private boolean validateSerialNumberSearchFields(String validateTitle) {

        //Create a new Alert object of type WARNING.

        // if the Serial Number Description Text field is null/empty.{

            // Show alert and return false;
            }

        // if the Serial Number Description Text field format is invalid.{

            // Show alert and return false;
            }

      else  return true;
    }

    @FXML
    private void searchSerialNumberOfEntries() {

        // check if the serial number format is valid from the description text box. {

            // Set a string variable to the description text box value.
            // set a boolean skip variable used for testing purposes to true.

            // Loop through the Master list using a LocalEvent variable {

                // check if boolean is true and continue.

                // check if the string variable matches a result from the list. {

                    // If found clear a filtered list.
                    // Set the table to the freshly cleared list.
                    // add the LocalEvent matched keyword to the filtered list
                    // set the table to the filtered list
                    // finally break from the loop.
                }
            }
        }
    }

    @FXML
    private void searchNameOfEntries() {

        // check if the name format is valid from the description text box. {

            // Set a string variable to the description text box value.
            // set a boolean skip variable used for testing purposes to true.
            // clear a filtered list.

            // Loop through the Master list using a LocalEvent variable {

                // check if boolean is true and continue.

                // If found add the LocalEvent matched keyword to the filtered list

            }
            // set the table to the filtered list
        }
    }

    public void refreshDescriptionFields() {

        // clear out all the text fields.
    }

    public boolean validateDuplicateForEdit(String key) {

        // Loop through the Master list using a LocalEvent variable {

            // if any local event variables in the list match the key {

                // Create a new Alert object of type WARNING.
                // Show alert and return false;
            }
        }
        else return true;
    }

    @FXML
    private void editEvent() {

        // First check if there is a valid selection {

            // Get the index of the current table selection.

            // If in edit mode {

                // Set three String variables to the trimmed values of the text fields.

                // If there isn't an edit being made true.

                // If the text fields are valid and dupcheck passes {

                    // Get the event strings and set them in their respective text fields.

                    // Set a LocalEvent edit with the given serial number and name values.
                    localEventEdit.setSerialNumber(serialNumber);
                    localEventEdit.setName(name);
                    // Check for the '$' character append on if necessary then do the same with value.

                    // Set the boolean edit togle to false.
                    // Set the edit button to say edit.
                    // wipe the textfields
                    // Set a boolean variable tracking changes being made to true.
                }
            }
            else {
               // set the LocalEvent variable to list masters selected index.

                // Set a backup string to the serial number.

                // Set the description Text fields again.

                // Change edit boolean to true
                // set button text to save
            }
           // Set table to the master list.
           // Refresh the table.
        }
    }
     */
}
