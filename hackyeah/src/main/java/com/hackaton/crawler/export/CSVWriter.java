package com.hackaton.crawler.export;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class CSVWriter {

	private String directory;

	public void write(String fileName, String header, List<String> lines) throws IOException {
		Path csvFilePath = Files.createFile(Paths.get(directory, fileName + ".csv"));

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath.toFile()))) {
			writer.write(header);
			writer.newLine();

			for (String line : lines) {
				writer.write(line);

				if (!line.equals(lines.get(lines.size() - 1))) {
					writer.newLine();
				}
			}
		}
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
}
