package io.file;

import exception.NoSuchFileTypeException;
import io.ConsolePrinter;
import io.DataReader;

public class FileManagerBuilder {

    private ConsolePrinter printer;
    private DataReader reader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public FileManager build() {
        printer.printLine("Wybierz format danych:");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL:
                return new SerializableFileManger();
            case CSV:
                return new CSVFileManager();
            default:
                throw new NoSuchFileTypeException("Nieobsługiwany typ danych.");
        }
    }

    private FileType getFileType() {
        boolean typeOK = false;
        FileType result = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOK = true;
            } catch (IllegalArgumentException e) {
                printer.printLine("Nieobsługiwany typ danych, wybierz jeszcze raz.");
            }
        } while (!typeOK);
        return result;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }
}
