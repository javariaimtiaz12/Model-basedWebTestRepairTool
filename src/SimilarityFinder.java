import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.w3c.tidy.Tidy;

/**
 * A class that Find Similarity between two file using Cosine Algorithms
 * 
 * @author Javaria Imtiaz
 * @version 1.0
 */
public class SimilarityFinder {

	String text1;
	String text2;

	static List<File> listOfFilesToCheckFolder1 = new ArrayList<File>();
	static List<File> listOfFilesToCheckFolder2 = new ArrayList<File>();

	int index = -1;
	double count = 0.0;

	public void findBestComparsonFiles(File folder1, File folder2) {

		File[] listOfFilesFolder1 = folder1.listFiles();
		File[] listOfFilesFolder2 = folder2.listFiles();

		for (int i = 0; i < listOfFilesFolder1.length; i++) {
			count = 0.0;
			if (listOfFilesFolder1[i].isFile()) {

				for (int j = 0; j < listOfFilesFolder2.length; j++) {
					if (listOfFilesFolder2[j].isFile()) {

						try {

							text1 = Files.readAllLines(Paths.get(listOfFilesFolder1[i].getAbsolutePath())).stream()
									.collect(Collectors.joining(" "));
							text2 = Files.readAllLines(Paths.get(listOfFilesFolder2[j].getAbsolutePath())).stream()
									.collect(Collectors.joining(" "));

						} catch (Exception e) {
							e.printStackTrace();
						}
						double similarity = score(text1, text2);

						if (similarity >= count) {
							count = similarity;
							index = j;
						}
						System.out.println(listOfFilesFolder1[i].getName() + " -> " + listOfFilesFolder1[j].getName()
								+ " Difference Count = " + similarity);

					}
				}

				listOfFilesToCheckFolder1.add(listOfFilesFolder1[i]);
				listOfFilesToCheckFolder2.add(listOfFilesFolder2[index]);
				// createxhtml(listOfFilesFolder1[i], listOfFilesFolder2[index]);
				System.out.println(
						"Compare: " + listOfFilesFolder1[i].getName() + " with " + listOfFilesFolder2[index].getName());

			}
		}

	}

	public static void createxhtml(File fXmlFile, File fXmlFile1) {
		try {
			System.out.println("PAth - " + fXmlFile.getAbsolutePath());
			System.out.println("PAth - " + fXmlFile.getCanonicalPath());
			System.out.println("PAth - " + fXmlFile.getPath());
			System.out.println("PAth - " + fXmlFile.getName());
			String str = FileUtils.readFileToString(fXmlFile, "UTF-8");
			// Document document = Jsoup.parse(str);
			// document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
			BufferedWriter writer = new BufferedWriter(new FileWriter(fXmlFile));
			writer.write(cleanNfo(str));
			writer.close();

			String str1 = FileUtils.readFileToString(fXmlFile1, "UTF-8");
			// Document document1 = Jsoup.parse(str1);
			// document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

			BufferedWriter writer1 = new BufferedWriter(new FileWriter(fXmlFile1));
			writer1.write(cleanNfo(str1));
			writer1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String cleanNfo(String sourceNfoContent) {
		try {
			Tidy tidy = new Tidy();
			tidy.setInputEncoding("UTF-8");
			tidy.setOutputEncoding("UTF-8");
			tidy.setWraplen(Integer.MAX_VALUE);
			tidy.setXmlOut(true);
			tidy.setSmartIndent(true);
			tidy.setXmlTags(true);
			tidy.setMakeClean(true);
			tidy.setForceOutput(true);
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			StringReader in = new StringReader(sourceNfoContent);
			StringWriter out = new StringWriter();
			tidy.parse(in, out);

			return out.toString();
		} catch (Exception e) {
		}
		return sourceNfoContent;
	}

	public double score(String text1, String text2) {
		// 1. Identify distinct words from both documents
		String[] text1Words = text1.split(" ");
		String[] text2Words = text2.split(" ");
		Map<String, Values> wordFreqVector = new HashMap<>();
		List<String> distinctWords = new ArrayList<>();

		// prepare word frequency vector by using Text1
		for (String text : text1Words) {
			String word = text.trim();
			if (!word.isEmpty()) {
				if (wordFreqVector.containsKey(word)) {
					Values vals1 = wordFreqVector.get(word);
					int freq1 = vals1.val1 + 1;
					int freq2 = vals1.val2;
					vals1.updateValues(freq1, freq2);
					wordFreqVector.put(word, vals1);
				} else {
					Values vals1 = new Values(1, 0);
					wordFreqVector.put(word, vals1);
					distinctWords.add(word);
				}
			}
		}

		// prepare word frequency vector by using Text2
		for (String text : text2Words) {
			String word = text.trim();
			if (!word.isEmpty()) {
				if (wordFreqVector.containsKey(word)) {
					Values vals1 = wordFreqVector.get(word);
					int freq1 = vals1.val1;
					int freq2 = vals1.val2 + 1;
					vals1.updateValues(freq1, freq2);
					wordFreqVector.put(word, vals1);
				} else {
					Values vals1 = new Values(0, 1);
					wordFreqVector.put(word, vals1);
					distinctWords.add(word);
				}
			}
		}

		// calculate the cosine similarity score.
		double vectAB = 0.0000000;
		double vectA = 0.0000000;
		double vectB = 0.0000000;
		for (int i = 0; i < distinctWords.size(); i++) {
			Values vals12 = wordFreqVector.get(distinctWords.get(i));
			double freq1 = vals12.val1;
			double freq2 = vals12.val2;

			vectAB = vectAB + freq1 * freq2;
			vectA = vectA + freq1 * freq1;
			vectB = vectB + freq2 * freq2;
		}

		return ((vectAB) / (Math.sqrt(vectA) * Math.sqrt(vectB)));
	}

	private class Values {

		private int val1;
		private int val2;

		private Values(int v1, int v2) {
			this.val1 = v1;
			this.val2 = v2;
		}

		public void updateValues(int v1, int v2) {
			this.val1 = v1;
			this.val2 = v2;
		}
	}// end of class values

}
