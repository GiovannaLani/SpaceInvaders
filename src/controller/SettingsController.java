package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SettingsController {
	public static String DATABASE_NAME;
	public static String CONFIG_FILE_NAME = "res/data/config.txt";
	
	public static void loadSettingsFile() {
		try {
			String property = "DATABASE";
			Properties properties = new Properties();
			BufferedReader br = new BufferedReader(new FileReader(CONFIG_FILE_NAME));
			properties.load(br);
			DATABASE_NAME = properties.getProperty(property);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
