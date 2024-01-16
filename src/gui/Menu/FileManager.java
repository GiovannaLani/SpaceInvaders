package gui.Menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {
	private static String userDir = System.getProperty("user.dir");
	private static String datFilePath = userDir + File.separator + "level.dat";

	public static void writeFile(String[][] lEnemies) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(datFilePath))) {
			oos.writeObject(lEnemies);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String[][] readFile() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datFilePath))) {
			String[][] lEnemies = (String[][]) ois.readObject();
			return lEnemies;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
