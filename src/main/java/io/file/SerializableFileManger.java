package io.file;

import exception.DataExportException;
import exception.DataInportException;
import model.Library;

import java.io.*;

public class SerializableFileManger implements FileManager {
    private static final String FILE_NAME = "Library.o";
    @Override
    public Library importData() {
        try (
                var fis = new FileInputStream(FILE_NAME);
                var ois = new ObjectInputStream(fis);
        ){
            return (Library) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new DataInportException("Brak pliku: " + FILE_NAME);
        } catch (IOException e) {
            throw new DataInportException("Błąd odczytu danych z pliku: " + FILE_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataInportException("Niezgodne dane w pliku: " + FILE_NAME);
        }
    }

    @Override
    public void exportData(Library library) {
        try (
                var fos = new FileOutputStream(FILE_NAME);
                var ous = new ObjectOutputStream(fos);
        ){
            ous.writeObject(library);
        } catch (FileNotFoundException e) {
            throw new DataExportException("Brak pliku: " + FILE_NAME);
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku: " + FILE_NAME);
        }
    }
}
