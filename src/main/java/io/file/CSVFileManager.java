package io.file;

import exception.DataExportException;
import exception.DataInportException;
import exception.InvalidDataException;
import model.Book;
import model.Library;
import model.Magazine;
import model.Publication;

import java.io.*;
import java.util.Scanner;

public class CSVFileManager implements FileManager {
    private static final String FILE_NAME = "Library.csv";
    @Override
    public Library importData() {
        Library library = new Library();
        try (var sc = new Scanner(new File(FILE_NAME))){
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Publication publication = createObjectFromString(line);
                library.addPublication(publication);
            }
        } catch (FileNotFoundException e) {
            throw new DataInportException("Brak pliku " + FILE_NAME);
        }
        return library;
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
        Publication[] publications = library.getPublications();
        try (
                var fw = new FileWriter(FILE_NAME);
                BufferedWriter br = new BufferedWriter(fw);
        ){
            for (Publication publication : publications) {
                br.write(publication.toCSV());
                br.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku: " + FILE_NAME);
        }
    }
}
