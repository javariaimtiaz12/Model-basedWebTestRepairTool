import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.BadLocationException;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
/**
 * A class that find  differences between two HTML files;
 * 
 * @author Javaria Imtiaz
 * @version 1.0
 */
public class DifferenceFinder {

	static File folder1 = new File("NewHtmlPages/view_all_bug_page.html");
	static File folder2 = new File("OldHtmlPages/view_all_bug_page.html");

	static File[] listOfFilesFolder1 = folder1.listFiles();
	static File[] listOfFilesFolder2 = folder2.listFiles();

	static List<File> listOfFilesToCheckFolder1 = new ArrayList<File>();
	static List<File> listOfFilesToCheckFolder2 = new ArrayList<File>();

	static int index = -1;
	static int count = 0;

	public static void main(String[] arg) {

		for (int i = 0; i < listOfFilesFolder1.length; i++) {
			count = 0;
			if (listOfFilesFolder1[i].isFile()) {

				for (int j = 0; j < listOfFilesFolder2.length; j++) {
					if (listOfFilesFolder2[j].isFile()) {

						try {

							int differenceCount = findDifferenceCount(listOfFilesFolder1[i], listOfFilesFolder2[j]);
							System.out.println(listOfFilesFolder1[i].getName() + " -> "
									+ listOfFilesFolder1[j].getName() + " Difference Count = " + differenceCount);

							if (differenceCount <= count) {
								System.out.println(listOfFilesFolder1[i].getName() + " -> "
										+ listOfFilesFolder1[j].getName() + " Difference Count = " + differenceCount);
								count = differenceCount;
								index = j;
							}

						} catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
				listOfFilesToCheckFolder1.add(listOfFilesFolder1[i]);
				listOfFilesToCheckFolder2.add(listOfFilesFolder2[index]);

				System.out.println(
						"Compare: " + listOfFilesFolder1[i].getName() + " with " + listOfFilesFolder2[index].getName());
			}
		}

	}

	public static int findDifferenceCount(File file1, File file2) throws BadLocationException {
		int totalCharacters = 0;
		int totalChanges = 0;
		List<String> original = fileToLines1(file1);
		List<String> revised = fileToLines1(file2);

		Patch patch = DiffUtils.diff(original, revised);

		for (Delta delta : patch.getDeltas()) {

			if (delta.getType().toString().equals("CHANGE") || delta.getType().toString().equals("DELETE")
					|| delta.getType().toString().equals("INSERT")) {
				totalChanges = totalChanges + 1;
			} else {
				totalChanges = totalChanges + 1;

			}
		}
		return totalChanges;
	}

	private static List fileToLines1(File file1) throws BadLocationException {

		List lines = new LinkedList();
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(file1));
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

}
