package com.electroline.myapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class FileLogger {

	private String filePath;
	private FileWriter fileWriter;
	private BufferedReader fileReader;
	private String readedLogFile = "";
	LocalTime localTime;
	ZoneId zone1;

	public FileLogger(String logFolderUrl) {
		zone1 = ZoneId.of("Europe/Warsaw");
		localTime = LocalTime.now(zone1);
		File logFolder = new File(logFolderUrl);
		logFolder.mkdirs();
		filePath = logFolderUrl + "log_" + LocalDate.now() + "_" +
				(localTime.getHour() < 10 ? "0" + localTime.getHour() : localTime.getHour()) + "-" +
				(localTime.getMinute() < 10 ? "0" + localTime.getMinute() : localTime.getMinute()) + "-" +
				(localTime.getSecond() < 10 ? "0" + localTime.getSecond() : localTime.getSecond()) + ".txt";
		try {
			fileWriter = new FileWriter(filePath);
			writeLogMessage("FileLogger() -> Start logging");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeLogMessage(String message) {
		/* Odczyt danych z pliku */
		try {
			fileReader = new BufferedReader(new FileReader(filePath));
			String readedLine = "";
			readedLogFile = "";
			while ((readedLine = fileReader.readLine()) != null) {
				readedLogFile += readedLine + "\r\n";
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/* ********** */

		/* Zapis danych do pliku */
		try {
			fileWriter = new FileWriter(filePath);
			localTime = LocalTime.now(zone1);
			fileWriter.write(readedLogFile + LocalDate.now() + "_" +
					(localTime.getHour() < 10 ? "0" + localTime.getHour() : localTime.getHour()) + "-" +
					(localTime.getMinute() < 10 ? "0" + localTime.getMinute() : localTime.getMinute()) + "-" +
					(localTime.getSecond() < 10 ? "0" + localTime.getSecond() : localTime.getSecond()) + ": " + message + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/* ********** */
	}
}