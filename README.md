# Library Management System
## Description

This project is a simple Library Management System implemented in Java. It provides basic functionalities to manage a library including adding, removing, and searching for publications (books and magazines), managing users (library members), and exporting/importing data from/to CSV or serialized files.

### Key Features:
  * Publication Management: Allows adding, removing, and searching for publications such as books and magazines.
  * User Management: Enables adding, removing, and searching for library users.
  * Data Export/Import: Supports exporting and importing data to/from CSV or serialized files.
  #### Classes:
  * Book: Represents a book with attributes like title, author, publisher, pages, and ISBN.
  * Magazine: Represents a magazine with attributes like title, publisher, language, and publication date.
  * LibraryUser: Represents a user of the library with attributes like first name, last name, and PESEL (Polish ID number).
  * Library: Manages publications and users in the library. Provides methods for adding, removing, and searching for publications/users.
  * FileManagerBuilder: Builds appropriate file managers (CSVFileManager or SerializableFileManager) based on user input.
  * CSVFileManager: Handles data import/export to/from CSV files.
  * SerializableFileManager: Handles data import/export using Java serialization.
  * ConsolePrinter: Provides methods for printing various collections of objects (books, magazines, users) to the console.
  * DataReader: Reads user input from the console for creating books, magazines, and library users.
  * Exceptions: Contains custom exceptions for handling various errors in the application (DataExportException, DataImportException, InvalidDataException, NoSuchFileTypeException, NoSuchOptionException, PublicationAlreadyExistsException, UserAlreadyExistsException).
## Technologies Used:
  * Java: The core language used for development.
  * Serialization: For storing and retrieving objects.
  * CSV: For importing and exporting data.
