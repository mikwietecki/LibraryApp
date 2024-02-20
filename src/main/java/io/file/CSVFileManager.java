package io.file;

import exception.DataExportException;
import exception.DataInportException;
import exception.InvalidDataException;
import model.*;

import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

public class CSVFileManager implements FileManager {
    private static final String PUBLICATIONS_FILE_NAME = "Library.csv";
    private static final String USERS_FILE_NAME = "Library_users.csv";
    @Override
    public Library importData() {
        Library library = new Library();
        importPublications(library);
        importUsers(library);
        return library;
    }

    private void importUsers(Library library) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(line -> createUserFromString(line))
                    .forEach(library::addUser);
        } catch (FileNotFoundException e) {
            throw new DataInportException("Brak pliku " + USERS_FILE_NAME);
        } catch (IOException e) {
            throw new DataInportException("Błąd odczytu pliku " + USERS_FILE_NAME);
        }
    }

    private LibraryUser createUserFromString(String line) {
        String[] split = line.split(";");
        String firstName = split[0];
        String lastName = split[1];
        String pesel = split[2];
        return new LibraryUser(firstName,lastName,pesel);
    }

    private void importPublications(Library library) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PUBLICATIONS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(line -> createObjectFromString(line))
                    .forEach(library::addPublication);
        } catch (FileNotFoundException e) {
            throw new DataInportException("Brak pliku " + PUBLICATIONS_FILE_NAME);
        } catch (IOException e) {
            throw new DataInportException("Błąd odczytu pliku " + PUBLICATIONS_FILE_NAME);
        }
    }

    private Publication createObjectFromString(String line) {
        String[] split = line.split(";");
        String type = split[0];
        if (Book.TYPE.equals(type)) {
            return createBook(split);
        } else if(Magazine.TYPE.equals(split[0])) {
            return createMagazine(split);
        }
        throw new InvalidDataException("Nieznany typ publikacji " + type);
    }

    private Publication createMagazine(String[] split) {
        String title = split[1];
        String publisher = split[2];
        int year = Integer.parseInt(split[3]);
        int month = Integer.parseInt(split[4]);
        int day = Integer.parseInt(split[5]);
        String language = split[6];
        return new Magazine(title,publisher,language,year,month,day);
    }

    private Publication createBook(String[] split) {
        String title = split[1];
        String publisher = split[2];
        int year = Integer.parseInt(split[3]);
        String author = split[4];
        int pages = Integer.parseInt(split[5]);
        String isbn = split[6];
        return new Book(title,author,year,pages,publisher,isbn);
    }

    @Override
    public void exportData(Library library) {
        exportPublications(library);
        exportUsers(library);
    }

    private void exportUsers(Library library) {
        Collection<LibraryUser> users = library.getUsers().values();
        exportToCSV(users, USERS_FILE_NAME);
    }

    private void exportPublications(Library library) {
        Collection<Publication> publications = library.getPublications().values();
        exportToCSV(publications, PUBLICATIONS_FILE_NAME);
    }

    private <T extends CSVConvertible> void exportToCSV(Collection<T> collection, String fileName) {
        try (
                var fw = new FileWriter(USERS_FILE_NAME);
                BufferedWriter br = new BufferedWriter(fw);
        ){
            for (T element : collection) {
                br.write(element.toCSV());
                br.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku: " + USERS_FILE_NAME);
        }
    }
}
