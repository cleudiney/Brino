package br.com.RatosDePC.Brpp.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordManagerUtils {
	private static StringBuilder wholekw = new StringBuilder(); // | keyword
																// |keyword |
																// ...
	private static StringBuilder wholekw2 = new StringBuilder();// | keyword
																// |keyword |
																// ...
	static ArrayList<String> keywords1 = new ArrayList<String>();
	static ArrayList<String> keywords2 = new ArrayList<String>();
	static ArrayList<String> libraries = new ArrayList<String>();
	static Path currentRelativePath = Paths.get("");
	static String s = currentRelativePath.toAbsolutePath().toString();
	static String libs = s + System.getProperty("file.separator") + "Arduino"
			+ System.getProperty("file.separator") + "libraries";
	static File f = new File(libs);

	public static void processLibraries() throws NullPointerException {
		for (String a : f.list()) {
			String keyPath = libs + System.getProperty("file.separator") + a
					+ System.getProperty("file.separator") + "keywords.txt";
			File lib = new File(keyPath);
			if (lib.exists()) {
				try {
					byte[] encoded = Files.readAllBytes(Paths.get(keyPath));
					String keys = new String(encoded);
					sortKeywords(keys);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				libraries.add(a);
			}
			// for (String libraries : libraries) {
			// libraryString += "|" + libraries;
			// }
		}
	}

	private static void sortKeywords(String l) throws FileNotFoundException {
		// String[] line = l.split(" ");
		Pattern keys = Pattern.compile("[A-Za-z]*\tKEYWORD\\d");
		Matcher m = keys.matcher(l);
		while (m.find()) {
			String k = m.group();
			String[] K = k.split("\t");
			if (K[1].contains("1")) {
				if (!keywords1.contains(K[0]))
					keywords1.add(K[0]);
			}
			if (K[1].contains("2"))
				keywords2.add(K[0]);
		}
		for (String keyword : keywords1) {
			if (keyword != null && !keyword.equals(""))
				wholekw.append("|" + keyword);
		}
		for (String keyword : keywords2) {
			if (keyword != null && !keyword.equals(""))
				wholekw2.append("|" + keyword);
		}
	}

	public static boolean generalKeywords(){
		
		return true;
	}
	
	public static String getKey() {
		return wholekw.toString();
	}

	public static String getKeyTwo() {
		return wholekw2.toString();
	}
}