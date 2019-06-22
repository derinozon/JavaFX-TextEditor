package main.Models;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.nashorn.api.scripting.URLReader;

import java.io.*;
import java.net.URL;

public class FileMachine {

	public static File GetFile (Stage s) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fileChooser.showOpenDialog(s);
		if (selectedFile != null) {
			return selectedFile;
		}
		else {
			return null;
		}
	}

	public static String ReadFile (File file) {
		StringBuilder stringBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
		}
		catch (IOException e) {
			e.printStackTrace();
		}


		String text;
		try {
			while ((text = bufferedReader.readLine()) != null) {
				stringBuffer.append(text).append(System.getProperty("line.separator"));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return stringBuffer.toString();
	}

	public static String ReadFile (URL url) {
		StringBuilder stringBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new URLReader(url));
		}
		catch (Exception e) {
			e.printStackTrace();
		}


		String text;
		try {
			while ((text = bufferedReader.readLine()) != null) {
				stringBuffer.append(text).append(System.getProperty("line.separator"));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return stringBuffer.toString();
	}

	public static void WriteFile (File file,String data) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,false))) {
			bw.write(data);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteFile (URL url,String data) {
		try {
			BufferedWriter out =new BufferedWriter(new FileWriter(new File(url.getFile())));
			out.write(data);
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File WriteFileAs (Stage s,String data) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		File file = fileChooser.showSaveDialog(s);
		if (file == null) {
			return null;
		}
		else {
			WriteFile(file,data);
			return file;
		}
	}
}
