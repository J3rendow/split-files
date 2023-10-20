package com.picpay.splitfiles.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Service
public class FileService {

    // TODO: The split files are located at the root of the project.

    public void splitFile(String fileName, int lines) throws IOException {

        final String SPLIT_DELIMITER = ";";
        final String INPUT_FILE = "output_file_";
        int FILE_COUNT = 1;
        int LINE_COUNT = 0;



        File file = ResourceUtils.getFile("classpath:" + fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");

        String line;

        // Read the header line outside the loop
        String header = reader.readLine();

        BufferedWriter writer = new BufferedWriter(new FileWriter(INPUT_FILE + FILE_COUNT + ".csv"));

        // Write the header line to the first file
        writer.write(header);
        writer.newLine();
        LINE_COUNT++;

        while ((line = reader.readLine()) != null) {
            // Split the line by commas and format the date
            String[] fields = line.split(SPLIT_DELIMITER);

//            TODO: In the near future, add an option to format a date field into other formats.
//            Date date = null;
//            if(!isValidFormat(fields[1])){
//                try {
//                    date = originalFormat.parse(fields[1]);
//                    fields[1] = targetFormat.format(date);

//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }

            String formattedLine = String.join(SPLIT_DELIMITER, fields);

            if (LINE_COUNT < lines) {
                writer.write(formattedLine);
                writer.newLine();
                LINE_COUNT++;
            } else {
                writer.close();
                LINE_COUNT = 0;
                FILE_COUNT++;
                writer = new BufferedWriter(new FileWriter(INPUT_FILE + FILE_COUNT + ".csv"));

                // Write the header line to each new file
                writer.write(header);
                writer.newLine();
                LINE_COUNT++;

                writer.write(formattedLine);
                writer.newLine();
                LINE_COUNT++;
            }

        }

        reader.close();
        writer.close();
    }


    public static boolean isValidFormat(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}





