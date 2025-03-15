package CSVReader;

import java.io.*;
import java.time.LocalTime;
import java.util.List;

public interface CSVReaderInterface{
    void parseHeader() throws IOException;
    boolean next() throws IOException;
    List<String> getColumnLabels();
    int getRecordLength();
    boolean isMissing(int columnIndex);
    boolean isMissing(String columnLabel);
    String get(int columnIndex);
    String get(String columnLabel);
    Integer getInt(int columnIndex) throws NumberFormatException;
    Integer getInt(String columnLabel);
    Long getLong(int columnIndex);
    Long getLong(String columnLabel);
    Double getDouble(int columnIndex);
    Double getDouble(String columnLabel);
    LocalTime getTime(int columnIndex, String format);
    LocalTime getTime(String columnLabel, String format);
    LocalTime getDate(int columnIndex, String format);
    LocalTime getDate(String columnLabel, String format);
}