package CSVReader;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader implements CSVReaderInterface{
    private BufferedReader reader;
    private String delimiter;
    private boolean hasHeader;
    private String[] current;
    private List<String> columnLabels = new ArrayList<String>();
    private Map<String, Integer> columnLabelsToInt = new HashMap<>();

    public CSVReader(String filename, String delimiter, boolean hasHeader) throws IOException {
        this(new FileReader(filename), delimiter, hasHeader);
    }

    public CSVReader(String filename, String delimiter) throws IOException {
        this(filename, delimiter, true);
    }

    public CSVReader(String filename) throws IOException {
        this(filename, ";", true);
    }

    public CSVReader(Reader reader, String delimiter, boolean hasHeader) throws IOException {
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if (hasHeader) {
            parseHeader();
        }
    }

    @Override
    public void parseHeader() throws IOException {
        try {
            String line = reader.readLine();
            if (line == null) {
                return;
            }
            current = line.split(delimiter);
            for (int i = 0; i < current.length; i++) {
                columnLabels.add(current[i].trim());
                columnLabelsToInt.put(current[i].trim(), i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean next() throws IOException {
        try {
            String line = reader.readLine();
            if (line == null) {
                return false;
            }

            current = line.split(delimiter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

//            if (current.length != columnLabels.size()) {
//                System.err.printf("Warning: Mismatch in column count. Expected: %d, Got: %d%n",
//                        columnLabels.size(), current.length);
//            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getColumnLabels() {
        return columnLabels;
    }

    @Override
    public int getRecordLength() {
        return current.length;
    }

    @Override
    public boolean isMissing(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columnLabels.size() || columnIndex >= current.length) {
            return true;
        }
        if (current[columnIndex] == null || current[columnIndex].isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isMissing(String columnLabel) {
        int columnIndex = columnLabelsToInt.get(columnLabel);
        return isMissing(columnIndex);
    }

    @Override
    public String get(int columnIndex) {
        if (isMissing(columnIndex)) {
            return new String("");
        }
        return current[columnIndex];
    }

    @Override
    public String get(String columnLabel) {
        int columnIndex = columnLabelsToInt.get(columnLabel);
        return get(columnIndex);
    }

    @Override
    public Integer getInt(int columnIndex) throws NumberFormatException {
        if (isMissing(columnIndex)) {
            return null;
        }
        try {
            return Integer.parseInt(get(columnIndex));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer getInt(String columnLabel) {
        if (isMissing(columnLabel)) {
            return null;
        }
        int columnIndex = columnLabelsToInt.get(columnLabel);
        try {
            return Integer.parseInt(get(columnIndex));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long getLong(int columnIndex) {
        if (isMissing(columnIndex)) {
            return null;
        }
        try {
            return Long.parseLong(get(columnIndex));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long getLong(String columnLabel) {
        if (isMissing(columnLabel)) {
            return null;
        }
        int columnIndex = columnLabelsToInt.get(columnLabel);
        try {
            return Long.parseLong(get(columnIndex));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Double getDouble(int columnIndex) {
        if (isMissing(columnIndex)) {
            return null;
        }
        try {
            return Double.parseDouble(get(columnIndex));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Double getDouble(String columnLabel) {
        if (isMissing(columnLabel)) {
            return null;
        }
        int columnIndex = columnLabelsToInt.get(columnLabel);
        try {
            return Double.parseDouble(get(columnIndex));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LocalTime getTime(int columnIndex, String format){
        String value = get(columnIndex);
        LocalTime time = LocalTime.parse(value, DateTimeFormatter.ofPattern(format));
        return time;
    }

    @Override
    public LocalTime getTime(String columnLabel, String format){
        String value = get(columnLabel);
        LocalTime time = LocalTime.parse(value, DateTimeFormatter.ofPattern(format));
        return time;
    }

    @Override
    public LocalTime getDate(int columnIndex, String format){
        String value = get(columnIndex);
        LocalTime date = LocalTime.parse(value, DateTimeFormatter.ofPattern(format));
        return date;
    }

    @Override
    public LocalTime getDate(String columnLabel, String format){
        String value = get(columnLabel);
        LocalTime date = LocalTime.parse(value, DateTimeFormatter.ofPattern(format));
        return date;
    }
}