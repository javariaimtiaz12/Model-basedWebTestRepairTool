import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.w3c.tidy.Tidy;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

/**
 * A class that find detail DOM-level differences between two HTML files;
 * 
 * @author Javaria Imtiaz
 * @version 1.0
 */
public class DomDiff {

	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	static Font font = new Font("Verdana", Font.PLAIN, 12);
	static GraphicsConfiguration gc;
	static GridLayout mainGrid = new GridLayout(2, 1);
	static GridLayout textGrid = new GridLayout(1, 2);
	static JPanel upperpanel = new JPanel();

	static ArrayList<String> originalSimilarWidgets;
	static ArrayList<String> revisedSimilarWidgets;

	static StyledDocument doc1;
	static StyledDocument doc2;
	static StyledDocument doc3;

	static JTextPane textarea1;
	static JTextPane textarea2;
	static JTextPane textarea3;
	static int nameerrors = 0;
	static int iderrors = 0;
	static int typeerrors = 0;
	static int classerrors = 0;
	static int hreferrors = 0;
	static int valueerrors = 0;
	static int onclickerrors = 0;
	static int radselerrors = 0;
	static int raditemsnotsameerrors = 0;
	static int selitemsnotsameerrors = 0;
	static int btntextchangeerrors = 0;
	static int mandatoryerrors = 0;
	static int lengtherrors = 0;

	static int totalnameerrors = 0;
	static int totaliderrors = 0;
	static int totaltypeerrors = 0;
	static int totalclasserrors = 0;
	static int totalhreferrors = 0;
	static int totalvalueerrors = 0;
	static int totalonclickerrors = 0;
	static int totalradselerrors = 0;
	static int totalraditemsnotsameerrors = 0;
	static int totalselitemsnotsameerrors = 0;
	static int totalbtntextchangeerrors = 0;
	static int totalmandatoryerrors = 0;
	static int totallengtherrors = 0;
	static int totalerrorcount = 0;

	static ArrayList<String> arrayListOriginal = new ArrayList<String>();
	static ArrayList<String> arrayListRevised = new ArrayList<String>();
	static ArrayList<String> arrayListChangeType = new ArrayList<String>();
	static ArrayList<OldNew> idlist = new ArrayList<OldNew>();
	static ArrayList<OldNew> namelist = new ArrayList<OldNew>();

	static ArrayList<OldNew> similarywidgetidlist = new ArrayList<OldNew>();
	static ArrayList<OldNew> similarywidgetnamelist = new ArrayList<OldNew>();
	static ArrayList<OldNew> similarywidgethreflist = new ArrayList<OldNew>();
	static ArrayList<OldNew> similarywidgettypelist = new ArrayList<OldNew>();
	static ArrayList<OldNew> similarywidgetonclicklist = new ArrayList<OldNew>();
	static ArrayList<OldNew> similarywidgetvaluelist = new ArrayList<OldNew>();

	static String originalFileContent = "";
	static String revisedFileContent = "";

	static ArrayList<OldNew> linklist = new ArrayList<OldNew>(); // IKR its an unintended pun
	static ArrayList<SelectboxArray> selectlistold = new ArrayList<SelectboxArray>();
	static ArrayList<SelectboxArray> selectlistnew = new ArrayList<SelectboxArray>();
	static ArrayList<SelectboxArray> radiolistold = new ArrayList<SelectboxArray>();
	static ArrayList<SelectboxArray> radiolistnew = new ArrayList<SelectboxArray>();
	static String firstfilepath = "";
	static String secondfilepath = "";
	static String testCasesPath = "";
	static EmptyBorder emp = new EmptyBorder(10, 10, 10, 10);
	Insets insets = new Insets(10, 10, 10, 10);
	// static JTextPane textarea4;0
	static String hold = "";
	static int iterator = 0;
	static SimpleAttributeSet keyWordBold = new SimpleAttributeSet();
	static SimpleAttributeSet keyWordRedWhite = new SimpleAttributeSet();
	static SimpleAttributeSet keyWordGreen = new SimpleAttributeSet();
	static SimpleAttributeSet keyWordOrange = new SimpleAttributeSet();
	static SimpleAttributeSet keyWordPurple = new SimpleAttributeSet();
	static SimpleAttributeSet keyWordGray = new SimpleAttributeSet();

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

	public static void main(String args[]) {
		// implement modified changes, deleted etc
		textarea1 = new JTextPane();
		textarea2 = new JTextPane();
		textarea3 = new JTextPane();
		doc1 = textarea1.getStyledDocument();
		doc2 = textarea2.getStyledDocument();
		doc3 = textarea3.getStyledDocument();
		textarea1.setFont(font);
		textarea1.setSize(200, 200);
		textarea1.setBorder(BorderFactory.createLineBorder(Color.black));
		JScrollPane jsp1 = new JScrollPane(textarea1);
		JScrollPane jsp2 = new JScrollPane(textarea2);
		JScrollPane jsp3 = new JScrollPane(textarea3);

		ArrayList<Delta> arrListModified = new ArrayList<Delta>();
		ArrayList<Delta> arrListDeleted = new ArrayList<Delta>();
		ArrayList<Delta> arrListDoesntexist = new ArrayList<Delta>();
		ArrayList<Delta> arrListOthers = new ArrayList<Delta>();
		upperpanel.setLayout(textGrid);

		// textarea2 = new JTextPane();
		textarea2.setFont(font);
		textarea2.setSize(200, 200);
		textarea2.setBorder(BorderFactory.createLineBorder(Color.black));
		textarea3.setBorder(BorderFactory.createLineBorder(Color.black));

		JFrame frame = new JFrame(gc);
		frame.setTitle("Dom Difference");
		frame.setSize(600, 400);
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setForeground(Color.BLACK);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jsp1.setBorder(new LineBorder(Color.GRAY, 10));
		jsp2.setBorder(new LineBorder(Color.GRAY, 10));
		jsp3.setBorder(new LineBorder(Color.GRAY, 10));

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(mainGrid);
		upperpanel.setBackground(Color.BLACK);
		upperpanel.add(jsp1);
		upperpanel.add(jsp2);
		// upperpanel.setBackground(Color.BLACK);
		frame.add(upperpanel);
		frame.add(jsp3);
		frame.add(jsp3);
		frame.setResizable(false);
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });

		Object[] options1 = { "OK", "Cancel" };
		JPanel panel = new JPanel(new GridLayout(0, 3));

		JTextField textField = new JTextField(30);
		JTextField textField1 = new JTextField(30);
		JTextField textField2 = new JTextField(30);
		panel.add(textField);
		panel.add(textField1);
		panel.add(textField2);
		textField.setEditable(false);
		textField1.setEditable(false);
		textField2.setEditable(false);
		JButton browseBtn = new JButton("Select Folder 1");
		JButton browseBtn1 = new JButton("Select Folder 2");
		JButton browseBtn2 = new JButton("Choose Test Cases Folder");
		panel.add(browseBtn);
		panel.add(browseBtn1);
		panel.add(browseBtn2);
		browseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// display/center the jdialog when the button is pressed
				JFileChooser chooser = new JFileChooser();
				// FileNameExtensionFilter filter = new FileNameExtensionFilter("HTML Files",
				// "html");
				// chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
					textField.setText(chooser.getSelectedFile().getPath().toString());
				}
			}
		});
		browseBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// display/center the jdialog when the button is pressed
				JFileChooser chooser = new JFileChooser();

				// FileNameExtensionFilter filter = new FileNameExtensionFilter("HTML Files",
				// "html");
				// chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
					textField1.setText(chooser.getSelectedFile().getPath().toString());
				}
			}
		});
		browseBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// display/center the jdialog when the button is pressed
				JFileChooser chooser = new JFileChooser();

				// FileNameExtensionFilter filter = new FileNameExtensionFilter("HTML Files",
				// "html");
				// chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
					textField2.setText(chooser.getSelectedFile().getPath().toString());
				}
			}
		});
		int result = JOptionPane.showOptionDialog(null, panel, "Enter a Number", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options1, null);
		if (result == JOptionPane.OK_OPTION) {
			if (textField.getText().toString().trim().equals("") || textField1.getText().toString().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "Select both files");
			} else {
				firstfilepath = textField.getText().toString();
				secondfilepath = textField1.getText().toString();
				testCasesPath = textField2.getText().toString();
				System.out.println(firstfilepath);
				System.out.println(secondfilepath);
			}
			System.out.println("IM OK");
		} else if (result == JOptionPane.CANCEL_OPTION) {
			System.out.println("CANCEL");
			System.exit(0);
		}

		/*
		 * JFileChooser chooser = new JFileChooser(); FileNameExtensionFilter filter =
		 * new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		 * chooser.setFileFilter(filter); int returnVal = chooser.showOpenDialog(null);
		 * if (returnVal == JFileChooser.APPROVE_OPTION) {
		 * System.out.println("You chose to open this file: " +
		 * chooser.getSelectedFile().getName()); }
		 */

		// Find the Best Similar files from folders using Vector Comperison
		SimilarityFinder sim = new SimilarityFinder();
		sim.findBestComparsonFiles(new File(firstfilepath), new File(secondfilepath));

		// Get Sort list of best Similar files
		List<File> listOfFilesToCheckFolder1 = SimilarityFinder.listOfFilesToCheckFolder1;
		List<File> listOfFilesToCheckFolder2 = SimilarityFinder.listOfFilesToCheckFolder2;

		StyleConstants.setForeground(keyWordGray, Color.WHITE);
		StyleConstants.setBackground(keyWordGray, Color.GRAY);
		StyleConstants.setBold(keyWordGray, true);
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setBold(keyWordBold, true);
		StyleConstants.setFontSize(keyWordBold, 13);

		// List of Files
		// Comparing the Files Folder 1 with Folder 2
		for (int fileIndex = 0; fileIndex < listOfFilesToCheckFolder1.size(); fileIndex++) {

			int folder1 = fileIndex + 1;

			StyleConstants.setForeground(keyWordGreen, Color.BLACK);
			StyleConstants.setBackground(keyWordGreen, Color.GREEN);
			StyleConstants.setBold(keyWordGreen, true);
			StyleConstants.setForeground(keyWordPurple, Color.WHITE);
			StyleConstants.setBackground(keyWordPurple, Color.BLUE);
			StyleConstants.setBold(keyWordPurple, true);
			try {
				StyleConstants.setForeground(keyWordOrange, Color.BLACK);
				StyleConstants.setBackground(keyWordOrange, Color.ORANGE);
				StyleConstants.setBold(keyWordOrange, true);
				doc3.insertString(
						doc3.getLength(), "Comparing File " + listOfFilesToCheckFolder1.get(fileIndex).toString()
								+ "with ->  " + listOfFilesToCheckFolder2.get(fileIndex).toString() + "\n\n",
						keyWordOrange);
			} catch (BadLocationException e2) {

				e2.printStackTrace();
			}
			// Create Temprory Files
			getRefinedFiles(listOfFilesToCheckFolder1.get(fileIndex).toString(),
					"C:\\GeneratedFiles\\file" + folder1 + "Folder1.html", "C:\\GeneratedFiles");
			getRefinedFiles(listOfFilesToCheckFolder2.get(fileIndex).toString(),
					"C:\\GeneratedFiles\\file" + folder1 + "Folder2.html", "C:\\GeneratedFiles");

			// Get Files Text into String List
			ArrayList<String> original = fileToLines1("C:\\GeneratedFiles\\file" + folder1 + "Folder1.html");
			ArrayList<String> revised = fileToLines2("C:\\GeneratedFiles\\file" + folder1 + "Folder2.html");

			new DifferenceFinder();

			// Compute diff. Get the Patch object. Patch is the container for computed
			// deltas.
			Patch patch = DiffUtils.diff(original, revised);

			try (Stream<String> lines = Files.lines(Paths.get("C:\\GeneratedFiles\\file" + folder1 + "Folder1.html"))) {

				originalFileContent = lines.collect(Collectors.joining(System.lineSeparator()));

			} catch (IOException e) {
				e.printStackTrace();
			}

			try (Stream<String> lines = Files.lines(Paths.get("C:\\GeneratedFiles\\file" + folder1 + "Folder2.html"))) {

				revisedFileContent = lines.collect(Collectors.joining(System.lineSeparator()));

			} catch (IOException e) {
				e.printStackTrace();
			}

			ArrayList<String> originalDataa = XpathParser.getPageElements(originalFileContent);
			ArrayList<String> revisedDataa = XpathParser.getPageElements(revisedFileContent);

			findSimilarSelectAndRadio(new File("C:\\GeneratedFiles\\file" + folder1 + "Folder1.html"),
					new File("C:\\GeneratedFiles\\file" + folder1 + "Folder2.html"));

			try {
				doc3.insertString(doc3.getLength(), "\n Jsoup Elements \n\n", keyWordGray);
				doc3.insertString(doc3.getLength(),
						"-----------------------------------------------------------------------------------------------------------------------------\n\n",
						keyWordGray);

				// Finding Similar Widgets using Jsoup Elements
				findSimilarWidgets(patch, new File("C:\\GeneratedFiles\\file" + folder1 + "Folder1.html"),
						new File("C:\\GeneratedFiles\\file" + folder1 + "Folder2.html"), originalDataa, revisedDataa);

			} catch (BadLocationException e2) {
				e2.printStackTrace();
			}

//			try {
//				doc3.insertString(doc3.getLength(), "\n Diff Util  Elements \n\n", keyWordGray);
//				doc3.insertString(doc3.getLength(),
//						"-----------------------------------------------------------------------------------------------------------------------------\n\n",
//						keyWordGray);
//
//				// Finding Similar Widgets using Jsoup Elements
//				findSimilarWidgets(patch, new File("D:\\GeneratedFiles\\file" + folder1 + "Folder1.html"),
//						new File("D:\\GeneratedFiles\\file" + folder1 + "Folder2.html"), originalDataa, revisedDataa);
//
//			} catch (BadLocationException e2) {
//				e2.printStackTrace();
//			}

			SimpleAttributeSet keyWordErrorLine = new SimpleAttributeSet();
			StyleConstants.setForeground(keyWordErrorLine, Color.WHITE);
			StyleConstants.setBackground(keyWordErrorLine, Color.BLACK);
			StyleConstants.setBold(keyWordErrorLine, true);

			StyleConstants.setForeground(keyWord, Color.RED);
			StyleConstants.setBackground(keyWord, Color.YELLOW);
			StyleConstants.setBold(keyWord, true);

			StyleConstants.setForeground(keyWordRedWhite, Color.WHITE);
			StyleConstants.setBackground(keyWordRedWhite, Color.RED);
			StyleConstants.setBold(keyWordRedWhite, true);
			Element root;
			Element element;
			System.out.println("Pathc is - " + patch);
			for (Delta delta : patch.getDeltas()) {

				String[] arr = new String[3];
				String[] orig = new String[3];
				String[] rev = new String[3];

				try {
					doc3.insertString(doc3.getLength(),
							"Error starting from line# " + delta.getOriginal().getPosition()
									+ " in Original Document w.r.t error at line# " + delta.getRevised().getPosition()
									+ " in Revised Document\n",
							keyWordErrorLine);
				} catch (BadLocationException e1) {

					e1.printStackTrace();
				}

				arrayListOriginal.add(delta.getOriginal().getLines().toString());
				arrayListRevised.add(delta.getRevised().getLines().toString());
				if (delta.getType().toString().equals("CHANGE")) {
					arrayListChangeType.add("MODIFIED");
					arrListModified.add(delta);
					StyleConstants.setForeground(keyWord, Color.RED);
					StyleConstants.setBackground(keyWord, Color.YELLOW);
					StyleConstants.setBold(keyWord, true);
				} else if (delta.getType().toString().equals("DELETE")) {
					arrayListChangeType.add("DELETED");
					arrListDeleted.add(delta);
					StyleConstants.setForeground(keyWord, Color.WHITE);
					StyleConstants.setBackground(keyWord, Color.RED);
					StyleConstants.setBold(keyWord, true);

				} else if (delta.getType().toString().equals("INSERT")) {
					arrayListChangeType.add("DOESN'T EXIST");
					arrListDoesntexist.add(delta);
					StyleConstants.setForeground(keyWord, Color.BLACK);
					StyleConstants.setBackground(keyWord, Color.GREEN);
					StyleConstants.setBold(keyWord, true);
				} else {
					arrayListChangeType.add(delta.getType().toString());
					arrListOthers.add(delta);
					StyleConstants.setForeground(keyWord, Color.WHITE);
					StyleConstants.setBackground(keyWord, Color.BLUE);
					StyleConstants.setBold(keyWord, true);
				}

				System.out.println("ArrayList: " + delta);
				try {
					doc3.insertString(doc3.getLength(), "TYPE OF CHANGE: " + arrayListChangeType.get(iterator) + "\n",
							keyWordBold);
				} catch (BadLocationException e1) {

					e1.printStackTrace();
				}
				boolean exists = false;

				arr = delta.toString().split(",");
				orig = delta.getOriginal().toString().split(",");
				rev = delta.getRevised().toString().split(",");

				int num = Integer.parseInt(arr[1].replace("position: ", "").trim());

				int sizeoriginal = Integer.parseInt(orig[1].replace("size: ", "").trim());
				int sizerevised = Integer.parseInt(rev[1].replace("size: ", "").trim());
				// System.out.println(num);
				System.out.println(delta.toString());
				// System.out.println(delta.toString());
				if (sizeoriginal == 0) {
					System.out.println(delta.getRevised().getLines() + " This is non existent");
					String line = delta.getRevised().getLines().toString().replace("[", "");
					line = line.replace("]", "");
					System.out.println(line);
					if (textarea1.getText().contains(line)) {
						System.out.println("EXISTS");

						int countorigline = 0;
						int countrevline = 0;
						countorigline = (textarea1.getText().split(line)).length - 1;
						countrevline = (textarea2.getText().split(line)).length - 1;

						System.out
								.println("COUNT OF ORIGINAL: " + countorigline + ", COUNT OF REVISED: " + countrevline);
						if (countorigline == countrevline) {
							System.out.println(
									"Number of times \"" + line + "\" occurs is the same in both the documents");
						} else {
							System.out.println(
									"Number of times \"" + line + "\" occurs is NOT the same in both the documents");

						}
						exists = true;

					} else {
						System.out.println("NOPE");

						exists = false;
					}
				} else if (sizerevised == 0) {
					System.out.println(delta.getOriginal().getLines() + " This is non existent");
					String line = delta.getOriginal().getLines().toString().replace("[", "");
					line = line.replace("]", "");
					System.out.println(line);
					if (textarea1.getText().contains(line)) {
						System.out.println("EXISTS");

						int countorigline = 0;
						int countrevline = 0;
						countorigline = (textarea1.getText().split(line)).length - 1;
						countrevline = (textarea2.getText().split(line)).length - 1;

						System.out
								.println("COUNT OF ORIGINAL: " + countorigline + ", COUNT OF REVISED: " + countrevline);
						if (countorigline == countrevline) {
							System.out.println(
									"Number of times \"" + line + "\" occurs is the same in both the documents");
						} else {
							System.out.println(
									"Number of times \"" + line + "\" occurs is NOT the same in both the documents");

						}
						exists = true;

					} else {
						System.out.println("NOPE");

						exists = false;
					}
				} else {
					// System.out.println("INSIDE ELSE");
					// attribute check

					if (delta.getOriginal().getLines().toString().contains(" name")) {
						AttributeCheck(delta, "name");

					} else if (delta.getRevised().getLines().toString().contains(" name")) {
						try {
							doc3.insertString(doc3.getLength(), "\"name\" attribute missing in the ORIGINAL version\n",
									null);
						} catch (BadLocationException e) {

							e.printStackTrace();
						}
						nameerrors++;
					}
					if (delta.getOriginal().getLines().toString().contains(" id")) {
						AttributeCheck(delta, "id");

					} else if (delta.getRevised().getLines().toString().contains(" id")) {
						try {
							doc3.insertString(doc3.getLength(), "\"id\" attribute missing in the ORIGINAL version\n",
									null);
						} catch (BadLocationException e) {

							e.printStackTrace();
						}
						iderrors++;
					}
					if (delta.getOriginal().getLines().toString().contains(" class")) {
						AttributeCheck(delta, "class");
					} else if (delta.getRevised().getLines().toString().contains(" class")) {
						try {
							doc3.insertString(doc3.getLength(), "\"class\" attribute missing in the ORIGINAL version\n",
									null);
						} catch (BadLocationException e) {

							e.printStackTrace();
						}
						classerrors++;
					}
					if (delta.getOriginal().getLines().toString().contains(" href")) {
						AttributeCheck(delta, "href");
					} else if (delta.getRevised().getLines().toString().contains(" href")) {
						try {
							doc3.insertString(doc3.getLength(), "\"href\" attribute missing in the ORIGINAL version\n",
									null);
						} catch (BadLocationException e) {

							e.printStackTrace();
						}
						hreferrors++;
					}
					if (delta.getOriginal().getLines().toString().contains(" type")) {
						AttributeCheck(delta, "type");
					} else if (delta.getRevised().getLines().toString().contains(" type")) {
						try {
							doc3.insertString(doc3.getLength(), "\"type\" attribute missing in the ORIGINAL version\n",
									null);
						} catch (BadLocationException e) {

							e.printStackTrace();
						}
						typeerrors++;
					}
					if (delta.getOriginal().getLines().toString().contains(" onclick")) {
						AttributeCheck(delta, "onclick");
					} else if (delta.getRevised().getLines().toString().contains(" onclick")) {
						System.out.println("ONCLICK HIT");
						try {
							doc3.insertString(doc3.getLength(),
									"\"onclick\" attribute missing in the ORIGINAL version\n", null);
						} catch (BadLocationException e) {

							e.printStackTrace();
						}
						onclickerrors++;
					}
					if (delta.getOriginal().getLines().toString().contains(" value")) {
						AttributeCheck(delta, "value");
					} else if (delta.getRevised().getLines().toString().contains(" value")) {
						try {
							doc3.insertString(doc3.getLength(), "\"value\" attribute missing in the ORIGINAL version\n",
									null);
						} catch (BadLocationException e) {

							e.printStackTrace();
						}
						valueerrors++;
					}
					if (delta.getOriginal().getLines().toString().contains(" style")) {

					} else if (delta.getRevised().getLines().toString().contains(" style")) {
						try {
							doc3.insertString(doc3.getLength(), "\"style\" attribute missing in the ORIGINAL version\n",
									null);
						} catch (BadLocationException e) {

							e.printStackTrace();
						}
						valueerrors++;
					}

					// check button text change
					if ((delta.getOriginal().getLines().toString().contains("<input") && delta.getOriginal().getLines()
							.toString().substring(delta.getOriginal().getLines().toString().indexOf(" type"))
							.replaceAll(" ", "").contains("type=\"button\""))
							&& (delta.getOriginal().getLines().toString().contains("<input")
									&& delta.getRevised().getLines().toString()
											.substring(delta.getRevised().getLines().toString().indexOf(" type"))
											.replaceAll(" ", "").contains("type=\"button\""))) {
						if (delta.getOriginal().getLines().size() >= delta.getRevised().getLines().size()) {
							for (int i = 0; i < delta.getRevised().size(); i++) {

								if ((delta.getOriginal().getLines().get(i).toString().contains("<input") && delta
										.getOriginal().getLines().get(i).toString()
										.substring(delta.getOriginal().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getRevised().getLines().get(i).toString().contains("<input")
												&& delta.getRevised().getLines().get(i).toString()
														.substring(delta.getRevised().getLines().get(i).toString()
																.indexOf(" type"))
														.replaceAll(" ", "").contains("type=\"button\""))) {
									inputToInputButtonCheck(delta, i, "<input", "<input");
								} else if ((delta.getOriginal().getLines().get(i).toString().contains("<input") && delta
										.getOriginal().getLines().get(i).toString()
										.substring(delta.getOriginal().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getRevised().getLines().get(i).toString().contains("<button"))) {
									inputToInputButtonCheck(delta, i, "<input", "<button");
								} else if (delta.getOriginal().getLines().get(i).toString().contains("<button")
										&& delta.getRevised().getLines().toString().contains("<button")) {
									inputToInputButtonCheck(delta, i, "<button", "<button");
								}

							}
						} else {
							for (int i = 0; i < delta.getOriginal().size(); i++) {
								if ((delta.getOriginal().getLines().get(i).toString().contains("<input") && delta
										.getOriginal().getLines().get(i).toString()
										.substring(delta.getOriginal().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getRevised().getLines().get(i).toString().contains("<input")
												&& delta.getRevised().getLines().get(i).toString()
														.substring(delta.getRevised().getLines().get(i).toString()
																.indexOf(" type"))
														.replaceAll(" ", "").contains("type=\"button\""))) {
									inputToInputButtonCheck(delta, i, "<input", "<input");
								} else if ((delta.getOriginal().getLines().get(i).toString().contains("<input") && delta
										.getOriginal().getLines().get(i).toString()
										.substring(delta.getOriginal().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getRevised().getLines().get(i).toString().contains("<button"))) {
									inputToInputButtonCheck(delta, i, "<input", "<button");

								} else if (delta.getOriginal().getLines().get(i).toString().contains("<button")
										&& delta.getRevised().getLines().toString().contains("<button")) {
									inputToInputButtonCheck(delta, i, "<button", "<button");
								}
							}
						}
					} else if ((delta.getRevised().getLines().toString().contains("<input") && delta.getRevised()
							.getLines().toString().substring(delta.getRevised().getLines().toString().indexOf(" type"))
							.replaceAll(" ", "").contains("type=\"button\""))) {
						if (delta.getOriginal().getLines().size() >= delta.getRevised().getLines().size()) {
							for (int i = 0; i < delta.getRevised().size(); i++) {

								if ((delta.getRevised().getLines().get(i).toString().contains("<input") && delta
										.getRevised().getLines().get(i).toString()
										.substring(delta.getRevised().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getOriginal().getLines().get(i).toString().contains("<input")
												&& delta.getOriginal().getLines().get(i).toString()
														.substring(delta.getOriginal().getLines().get(i).toString()
																.indexOf(" type"))
														.replaceAll(" ", "").contains("type=\"button\""))) {
									inputToInputButtonCheck(delta, i, "<input", "<input");
								} else if ((delta.getRevised().getLines().get(i).toString().contains("<input") && delta
										.getRevised().getLines().get(i).toString()
										.substring(delta.getRevised().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getOriginal().getLines().get(i).toString().contains("<button"))) {
									inputToInputButtonCheck(delta, i, "<button", "<input");
								} else if (delta.getOriginal().getLines().get(i).toString().contains("<button")
										&& delta.getRevised().getLines().toString().contains("<button")) {
									inputToInputButtonCheck(delta, i, "<button", "<button");
								}

							}
						} else {
							for (int i = 0; i < delta.getRevised().size(); i++) {
								if ((delta.getRevised().getLines().get(i).toString().contains("<input") && delta
										.getRevised().getLines().get(i).toString()
										.substring(delta.getRevised().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getOriginal().getLines().get(i).toString().contains("<input")
												&& delta.getOriginal().getLines().get(i).toString()
														.substring(delta.getOriginal().getLines().get(i).toString()
																.indexOf(" type"))
														.replaceAll(" ", "").contains("type=\"button\""))) {
									inputToInputButtonCheck(delta, i, "<input", "<input");
								} else if ((delta.getRevised().getLines().get(i).toString().contains("<input") && delta
										.getRevised().getLines().get(i).toString()
										.substring(delta.getRevised().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getOriginal().getLines().get(i).toString().contains("<button"))) {
									inputToInputButtonCheck(delta, i, "<button", "<input");

								} else if (delta.getOriginal().getLines().get(i).toString().contains("<button")
										&& delta.getRevised().getLines().toString().contains("<button")) {
									inputToInputButtonCheck(delta, i, "<button", "<button");
								}
							}
						}
					} else if (delta.getOriginal().getLines().toString().contains("<button")
							&& delta.getRevised().getLines().toString().contains("<button")) {
						if (delta.getOriginal().getLines().size() >= delta.getRevised().getLines().size()) {
							for (int i = 0; i < delta.getRevised().size(); i++) {
								if (delta.getOriginal().getLines().get(i).toString().contains("<button")
										&& delta.getRevised().getLines().get(i).toString().contains("<button")) {
									inputToInputButtonCheck(delta, i, "<button", "<button");
								}
								if ((delta.getOriginal().getLines().get(i).toString().contains("<input") && delta
										.getOriginal().getLines().get(i).toString()
										.substring(delta.getOriginal().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getRevised().getLines().get(i).toString().contains("<input")
												&& delta.getRevised().getLines().get(i).toString()
														.substring(delta.getRevised().getLines().get(i).toString()
																.indexOf(" type"))
														.replaceAll(" ", "").contains("type=\"button\""))) {
									inputToInputButtonCheck(delta, i, "<input", "<input");
								} else if ((delta.getOriginal().getLines().get(i).toString().contains("<input") && delta
										.getOriginal().getLines().get(i).toString()
										.substring(delta.getOriginal().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getRevised().getLines().get(i).toString().contains("<button"))) {
									inputToInputButtonCheck(delta, i, "<input", "<button");

								}
							}
						} else {
							for (int i = 0; i < delta.getOriginal().size(); i++) {
								if (delta.getOriginal().getLines().get(i).toString().contains("<button")
										&& delta.getRevised().getLines().get(i).toString().contains("<button")) {
									inputToInputButtonCheck(delta, i, "<button", "<button");
								}
								if ((delta.getOriginal().getLines().get(i).toString().contains("<input") && delta
										.getOriginal().getLines().get(i).toString()
										.substring(delta.getOriginal().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getRevised().getLines().get(i).toString().contains("<input")
												&& delta.getRevised().getLines().get(i).toString()
														.substring(delta.getRevised().getLines().get(i).toString()
																.indexOf(" type"))
														.replaceAll(" ", "").contains("type=\"button\""))) {
									inputToInputButtonCheck(delta, i, "<input", "<input");
								} else if ((delta.getOriginal().getLines().get(i).toString().contains("<input") && delta
										.getOriginal().getLines().get(i).toString()
										.substring(delta.getOriginal().getLines().get(i).toString().indexOf(" type"))
										.replaceAll(" ", "").contains("type=\"button\""))
										&& (delta.getRevised().getLines().get(i).toString().contains("<button"))) {
									inputToInputButtonCheck(delta, i, "<input", "<button");

								}
							}
						}
					}
				}
				// Mandatory field check

				if (delta.getOriginal().toString().contains(" required ")
						|| delta.getOriginal().toString().contains(" required>")) {
					mandatoryCheck(delta);
				} else if (delta.getRevised().toString().contains(" required ")
						|| delta.getRevised().toString().contains(" required>")) {
					mandatoryCheck(delta);
				}
				// minlength attribute check
				if (delta.getOriginal().toString().toLowerCase().contains("minlength")) {
					minlengthCheck(delta, "minlength");
				} else if (delta.getRevised().toString().toLowerCase().contains("minlength")) {
					minlengthCheck(delta, "minlength");

				} else if (delta.getOriginal().toString().toLowerCase().contains("pattern")) {
					minlengthCheck(delta, "pattern");
				} else if (delta.getRevised().toString().toLowerCase().contains("pattern")) {
					minlengthCheck(delta, "pattern");
				}

				if (delta.getOriginal().getLines().toString().contains("<select>")) {

					// check select box and compare
					String selecttagorig = "";
					selecttagorig = getOriginalSelectTagString(delta, selecttagorig);
					ArrayList<String> optionsorig = new ArrayList<String>();
					optionsorig = getOptionsArray(selecttagorig, optionsorig);

					if (delta.getRevised().getLines().toString().contains("<select>")) {
						// check revised and if that also contains select
						String selecttagrev = "";
						selecttagrev = getRevisedSelectTagString(delta, selecttagrev);
						ArrayList<String> optionsrev = new ArrayList<String>();
						optionsrev = getOptionsArray(selecttagrev, optionsrev);
						ArrayList<String> selectorigexclusiveitems = new ArrayList<String>();
						ArrayList<String> selectrevexclusiveitems = new ArrayList<String>();
						// compare two select list options
						boolean mutualexists = false;
						for (int i = 0; i < optionsorig.size(); i++) {
							// compare radio & select lists with base as orig
							for (int j = 0; j < optionsrev.size(); j++) {
								if (optionsorig.get(i).trim().equalsIgnoreCase(optionsrev.get(j).trim())) {
									mutualexists = true;
									break;
								}
								if ((optionsrev.size() == j - 1)
										&& (!optionsorig.get(i).trim().equalsIgnoreCase(optionsrev.get(j).trim()))) {
									selectorigexclusiveitems.add(optionsorig.get(i).trim().toString());
								}
							}
						}
						if (mutualexists) {
							for (int i = 0; i < optionsrev.size(); i++) {
								// compare radio & select lists with base as orig
								for (int j = 0; j < optionsorig.size(); j++) {
									if (optionsrev.get(i).trim().equalsIgnoreCase(optionsorig.get(j).trim())) {

										break;
									}
									if ((optionsorig.size() == j - 1) && (!optionsrev.get(i).trim()
											.equalsIgnoreCase(optionsorig.get(j).trim()))) {
										selectrevexclusiveitems.add(optionsrev.get(i).trim().toString());
										selitemsnotsameerrors++;
									}
								}
							}
							try {
								doc3.insertString(doc3.getLength(),
										"type \"radio\" in original document has been changed to \"select\" in the revised document\nHere are the list of differences:\nItems only in original list:\n"
												+ selectorigexclusiveitems.size() + "\n",
										null);
								if (!(selectorigexclusiveitems.isEmpty())) {
									for (int i = 0; i < selectorigexclusiveitems.size(); i++) {

										doc3.insertString(doc3.getLength(), selectorigexclusiveitems.get(i) + "\n",
												null);
									}
								} else {
									doc3.insertString(doc3.getLength(), "NONE\n", null);
								}

								doc3.insertString(doc3.getLength(),
										"Items only in revised list:\n" + selectrevexclusiveitems.size() + "\n", null);

								if (!(selectrevexclusiveitems.isEmpty())) {
									for (int i = 0; i < selectrevexclusiveitems.size(); i++) {
										doc3.insertString(doc3.getLength(), selectrevexclusiveitems.get(i) + "\n",
												null);
									}
								} else {
									doc3.insertString(doc3.getLength(), "NONE\n", null);

								}

							} catch (BadLocationException e) {

								e.printStackTrace();
							}
						} else {
							try {
								selitemsnotsameerrors++;
								doc3.insertString(doc3.getLength(),
										"No mutual items exist between both radio and select\nOriginal List\n", null);
								for (int i = 0; i < optionsorig.size(); i++) {

									doc3.insertString(doc3.getLength(), optionsorig.get(i) + "\n", null);
								}
								doc3.insertString(doc3.getLength(), "Revised list:\n", null);
								for (int i = 0; i < optionsrev.size(); i++) {

									doc3.insertString(doc3.getLength(), optionsrev.get(i) + "\n", null);
								}
							} catch (BadLocationException e) {

								e.printStackTrace();
							}
						}
					}
				} else if (delta.getRevised().toString().contains("<select>")) {
					System.out
							.println("I FOUND SELECT AND THE CONTENT IS: " + delta.getRevised().getLines().toString());

				}

				root = doc1.getDefaultRootElement();
				element = root.getElement(num);
//            try {
//				System.out.println(doc1.getText(element.getStartOffset(),element.getEndOffset()-element.getStartOffset()));
//			} catch (BadLocationException e) {
//				
//				e.printStackTrace();
//			}
				int start = element.getStartOffset();
				int end = element.getEndOffset();

				try {

					hold = doc1.getText(start, end - start);
					// System.out.println(hold);
					doc1.remove(start, end - start);
					if (exists) {
						doc1.insertString(start, hold, null);
						System.out.println("INSIDE EXISTS");
						// doc3.insertString(0, delta.toString() + " exists but position is different
						// \n", null);
					} else {
						doc1.insertString(start, hold, keyWord);
						System.out.println("NOT INSIDE EXISTS");
					}

					exists = false;
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
				iterator = iterator + 1;

				try {
					doc3.insertString(doc3.getLength(),
							"_________________________________________________________________________ \n", null);
				} catch (BadLocationException e) {

					e.printStackTrace();
				}

				try {
					doc3.insertString(doc3.getLength(), "Original List:\n", keyWordBold);
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
				for (int i = 0; i < delta.getOriginal().size(); i++) {
					try {
						doc3.insertString(doc3.getLength(), delta.getOriginal().getLines().get(i) + "\n", null);
					} catch (BadLocationException e) {

						e.printStackTrace();
					}
				}
				try {
					doc3.insertString(doc3.getLength(), "Revised List:\n", keyWordBold);
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
				for (int i = 0; i < delta.getRevised().size(); i++) {
					try {
						doc3.insertString(doc3.getLength(), delta.getRevised().getLines().get(i) + "\n", null);
					} catch (BadLocationException e) {

						e.printStackTrace();
					}
				}
				try {
					doc3.insertString(doc3.getLength(),
							"========================================================================================\n",
							null);
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
			}
			try {

				doc3.insertString(doc3.getLength(), "Number of different attribute change type errors:\n",
						keyWordRedWhite);
				// Count of All Errors
				totalnameerrors = totalnameerrors + nameerrors;
				totaliderrors = totaliderrors + iderrors;
				totaltypeerrors = totaltypeerrors + typeerrors;
				totalclasserrors = totalclasserrors + classerrors;
				totalhreferrors = totalhreferrors + hreferrors;
				totalvalueerrors = totalvalueerrors + valueerrors;
				totalonclickerrors = totalonclickerrors + onclickerrors;
				totalradselerrors = totalradselerrors + radselerrors;
				totalraditemsnotsameerrors = totalraditemsnotsameerrors + raditemsnotsameerrors;
				totalmandatoryerrors = totalmandatoryerrors + mandatoryerrors;
				totallengtherrors = totallengtherrors + lengtherrors;
				totalerrorcount = totalerrorcount + nameerrors + iderrors + classerrors + hreferrors + onclickerrors
						+ typeerrors + valueerrors + radselerrors + raditemsnotsameerrors + selitemsnotsameerrors
						+ mandatoryerrors + lengtherrors;

				// Total Results of Errors,
				doc3.insertString(doc3.getLength(),
						"Name Errors: " + nameerrors + "\nID Errors: " + iderrors + "\nType Errors: " + typeerrors
								+ "\nClass Errors: " + classerrors + "\nHref Errors: " + hreferrors
								+ "\nOnclick Errors: " + onclickerrors + "\nValue Errors: " + valueerrors
								+ "\nRadio&Select Errors: " + radselerrors + "\nRadio Items Different Errors: "
								+ raditemsnotsameerrors + "\nSelect Items Different Errors: " + selitemsnotsameerrors
								+ "\nMandatory/Required Errors: " + mandatoryerrors
								+ "\nMin Length not Same/Pattern Errors: " + lengtherrors + "\n",
						null);
				doc3.insertString(doc3.getLength(),
						"Total Number of changes: " + (nameerrors + iderrors + classerrors + hreferrors + onclickerrors
								+ typeerrors + valueerrors + radselerrors + raditemsnotsameerrors
								+ selitemsnotsameerrors + mandatoryerrors + lengtherrors) + "\n",
						keyWordBold);
				doc3.insertString(doc3.getLength(),
						"-------------------------------------------------------------------------- \n\n",
						keyWordOrange);
			} catch (BadLocationException e) {

				e.printStackTrace();
			}
			try {

				doc3.insertString(doc3.getLength(),
						"\n\n_____________________________________________________________ ____________________________________________________________\n\n",
						null);
				doc3.insertString(doc3.getLength(),
						"\nTest Case Output, which Tell Testcases are Successfull or Not.\n", null);

				try {
					File folder = new File(testCasesPath);
					File[] listOfFiles = folder.listFiles();

					for (int i = 0; i < listOfFiles.length; i++) {
						if (listOfFiles[i].isFile()) {
							System.out.println(
									"==========================================================================================");
							checkTestCase(listOfFiles[i].toString());
						} else if (listOfFiles[i].isDirectory()) {
							System.out.println("Directory " + listOfFiles[i].getName());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				for (int i = 0; i < idlist.size(); i++) {
					try {

						int count = 0;

						for (int j = 0; j < similarywidgetidlist.size(); j++) {
							if ((similarywidgetidlist.get(j).oldValue).equals(idlist.get(i).oldValue)) {
								count++;
							}
						}
//						if (count == 0) {
//							String xpath_new_file = XpathParser.xpathFinderFromFile("id", idlist.get(i).newValue,
//									new File("D:\\GeneratedFiles\\file" + folder1 + "Folder2.html"));
//							similarywidgetidlist
//									.add(new OldNew(idlist.get(i).oldValue, idlist.get(i).newValue, xpath_new_file));
//						}
					} catch (Exception e) {

					}
				}
				System.out.println("Size of Name List: - " + namelist.size());
				System.out.println("Size of ID List: - " + idlist.size());
				for (int i = 0; i < namelist.size(); i++) {

					try {
						int count = 0;

						for (int j = 0; j < similarywidgetnamelist.size(); j++) {
							if ((similarywidgetnamelist.get(j).oldValue).equals(namelist.get(i).oldValue)) {
								count++;
							}
						}
//						if (count == 0) {
//							String xpath_new_file = XpathParser.xpathFinderFromFile("name", namelist.get(i).newValue,
//									new File("D:\\GeneratedFiles\\file" + folder1 + "Folder2.html"));
//							
//							similarywidgetnamelist.add(
//									new OldNew(namelist.get(i).oldValue, namelist.get(i).newValue, xpath_new_file));
//						}
					} catch (Exception e) {

					}

				}

				doc3.insertString(doc3.getLength(), "\nOld and New Ids of Similar Widgets\n", keyWordBold);
				for (int i = 0; i < similarywidgetidlist.size(); i++) {

					doc3.insertString(doc3.getLength(),
							"\nID LIST: " + similarywidgetidlist.get(i).oldValue + ", "
									+ similarywidgetidlist.get(i).newValue + " ----- "
									+ similarywidgetidlist.get(i).getNewxpath(),
							null);
				}

				doc3.insertString(doc3.getLength(),
						"\n__________________________________________________________________________________________________________________________________________________",
						null);

				doc3.insertString(doc3.getLength(), "\n\nOld and New names of Similar Widgets\n", keyWordBold);

				for (int i = 0; i < similarywidgetnamelist.size(); i++) {

					doc3.insertString(doc3.getLength(),
							"\nNAME LIST: " + similarywidgetnamelist.get(i).oldValue + ", "
									+ similarywidgetnamelist.get(i).newValue + " ----- "
									+ similarywidgetnamelist.get(i).getNewxpath(),
							null);
				}

				doc3.insertString(doc3.getLength(),
						"\n__________________________________________________________________________________________________________________________________________________",
						null);

				doc3.insertString(doc3.getLength(), "\nOld and New Types of Similar Widgets\n", keyWordBold);
				for (int i = 0; i < similarywidgettypelist.size(); i++) {

					doc3.insertString(doc3.getLength(),
							"\nID LIST: " + similarywidgettypelist.get(i).oldValue + ", "
									+ similarywidgettypelist.get(i).newValue + " ----- "
									+ similarywidgettypelist.get(i).getNewxpath(),
							null);
				}

				doc3.insertString(doc3.getLength(),
						"\n__________________________________________________________________________________________________________________________________________________",
						null);

				doc3.insertString(doc3.getLength(), "\nOld and New Href of Similar Widgets\n", keyWordBold);
				for (int i = 0; i < similarywidgethreflist.size(); i++) {

					doc3.insertString(doc3.getLength(), "\nID LIST: " + similarywidgethreflist.get(i).oldValue + ", "
							+ similarywidgethreflist.get(i).newValue, null);
				}

				doc3.insertString(doc3.getLength(),
						"\n__________________________________________________________________________________________________________________________________________________",
						null);

				doc3.insertString(doc3.getLength(), "\nOld and New Values of Similar Widgets\n", keyWordBold);
				for (int i = 0; i < similarywidgetvaluelist.size(); i++) {

					doc3.insertString(doc3.getLength(), "\nID LIST: " + similarywidgetvaluelist.get(i).oldValue + ", "
							+ similarywidgetvaluelist.get(i).newValue, null);
				}

				doc3.insertString(doc3.getLength(),
						"\n__________________________________________________________________________________________________________________________________________________",
						null);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		try

		{
			doc3.insertString(doc3.getLength(), "Total Of All Files \n ", keyWordPurple);
			doc3.insertString(doc3.getLength(), "Name Errors: " + totalnameerrors + "\nID Errors: " + totaliderrors
					+ "\nType Errors: " + totaltypeerrors + "\nClass Errors: " + totalclasserrors + "\nHref Errors: "
					+ totalhreferrors + "\nOnclick Errors: " + totalonclickerrors + "\nValue Errors: "
					+ totalvalueerrors + "\nRadio&Select Errors: " + totalradselerrors
					+ "\nRadio Items Different Errors: " + totalraditemsnotsameerrors
					+ "\nSelect Items Different Errors: " + totalselitemsnotsameerrors + "\nMandatory/Required Errors: "
					+ totalmandatoryerrors + "\nMin Length not Same/Pattern Errors: " + totallengtherrors + "\n", null);
			doc3.insertString(doc3.getLength(), "Total Number of changes (All Files Count): " + totalerrorcount + "\n",
					keyWordBold);
			doc3.insertString(doc3.getLength(),
					"-------------------------------------------------------------------------- \n\n", keyWordPurple);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String findAttributeValue(String widget, String attribute) {

		int numquotesorigg = 0;
		String nameorig = "";

		if (widget.contains(" " + attribute)) {
			String nametagorig = widget.substring(widget.indexOf(" " + attribute)).replaceAll(" ", "");

			if (nametagorig.contains(attribute + "=\"")) {

				for (int i = 0; i <= 200; i++) {
					nameorig = nameorig + nametagorig.charAt(nametagorig.indexOf(attribute + "=") + i);

					if (nametagorig.charAt(nametagorig.indexOf(attribute + "=") + i) == '\"'
//							|| nametagorig.indexOf(attribute + "=") + i == '\''
					) {
						numquotesorigg = numquotesorigg + 1;

						if (numquotesorigg == 2) {
							break;
						}
					}
				}
			}
		}
		return nameorig;
	}

	public static void findSimilarWidgets(Patch patch, File old_file, File new_file, ArrayList<String> originalData,
			ArrayList<String> revised) {

		try {
			doc3.insertString(doc3.getLength(), "\n Output Start -  \n\n", keyWordGray);
			doc3.insertString(doc3.getLength(),
					"-----------------------------------------------------------------------------------------------------------------------------\n\n",
					keyWordGray);

			// Code for Widget Check

			SimilarityFinder similarityFinder = new SimilarityFinder();

			String originalAttribute = "";
			String revisedAttribute = "";
			String originalId = null;
			String revisedId = null;
			double count = 0.0;
			double count_id = 0.0;
			double count_name = 0.0;
			String checkingStringRevised = "";
			// List of Widgets in Old File
			for (int i = 0; i < originalData.size(); i++) {

				if (originalData.get(i).contains("name") || originalData.get(i).contains("id")
						|| originalData.get(i).contains("href") || originalData.get(i).contains("value")) {
					originalAttribute = originalData.get(i).toString();
					revisedAttribute = "";
					count = 0.0;
					count_id = 0.0;
					count_name = 0.0;
					originalId = "";
					revisedId = "";

					String nameValueOfOriginal = null;
					String idValueOfOriginal = null;
					String hrefValueOfOriginal = null;
					String valueTextOfOriginal = null;

					// Get Attribute By Name if Exists
					if (originalData.get(i).contains("name")) {
						nameValueOfOriginal = findAttributeValue(originalAttribute, "name");
					}

					// Get Attribute By ID if Exists
					if (originalData.get(i).contains("id")) {

						idValueOfOriginal = findAttributeValue(originalAttribute, "id");
					}

					// Get Attribute By Href if Exists
					if (originalData.get(i).contains("href")) {
						hrefValueOfOriginal = findAttributeValue(originalAttribute, "href");
					}

					// Get Attribute By Value if Exists
					if (originalData.get(i).contains("value")) {
						valueTextOfOriginal = findAttributeValue(originalAttribute, "value");
					}

					// List of Widgets in New File
					for (int j = 0; j < revised.size(); j++) {

						if (revised.get(j).contains("name") || revised.get(j).contains("id")
								|| revised.get(j).contains("href") || revised.get(j).contains("value")) {

							double id_score = 0;
							double name_score = 0;
							double value_score = 0;
							double href_score = 0;
							double score = 0;

							String revisedAttributee = revised.get(j);

							String nameValueOfRevised = null;
							String idValueOfRevised = null;
							String hrefValueOfRevised = null;
							String valueTextOfRevised = null;

							if (revised.get(j).contains("name")) {
								nameValueOfRevised = findAttributeValue(revisedAttributee, "name");

							}
							if (revised.get(j).contains("id")) {
								idValueOfRevised = findAttributeValue(revisedAttributee, "id");

							}
							if (revised.get(j).contains("href")) {
								hrefValueOfRevised = findAttributeValue(revisedAttributee, "href");

							}
							if (revised.get(j).contains("value")) {
								valueTextOfRevised = findAttributeValue(revisedAttributee, "value");
							}

							// Check Similarity of Widgets if(Widgets id, name, href,value is Same)
							if (originalData.get(i).contains("name") && revised.get(j).contains("name")
									&& nameValueOfOriginal.equals(nameValueOfRevised)) {

//									name_score = findAttributeSimilarityScore(
//											deltaOriginal.getOriginal().getLines().toString(),
//											deltaRevise.getRevised().getLines().toString(), "name");

								revisedAttribute = revised.get(j);
								break;

							}
							if (originalData.get(i).contains("id") && revised.get(j).contains("id")
									&& idValueOfOriginal.equals(idValueOfRevised)) {

//									id_score = findAttributeSimilarityScore(
//											deltaOriginal.getOriginal().getLines().toString(),
//											deltaRevise.getRevised().getLines().toString(), "id");

								revisedAttribute = revised.get(j);

								break;

							}

							if (originalData.get(i).contains("value") && revised.get(j).contains("value")
									&& valueTextOfOriginal.equals(valueTextOfRevised)) {

//									value_score = findAttributeSimilarityScore(
//											deltaOriginal.getOriginal().getLines().toString(),
//											deltaRevise.getRevised().getLines().toString(), "value");

								revisedAttribute = revised.get(j);

							}

							if (originalData.get(i).contains("href") && revised.get(j).contains("href")
									&& hrefValueOfOriginal.equals(hrefValueOfRevised)) {

//									href_score = findAttributeSimilarityScore(
//											deltaOriginal.getOriginal().getLines().toString(),
//											deltaRevise.getRevised().getLines().toString(), "href");

								revisedAttribute = revised.get(j);
								break;

							}

							else {

								score = similarityFinder.score(originalData.get(i), revised.get(j));
								System.out.println("Original: - " + originalAttribute + "\n Revised: - "
										+ revised.get(j) + "\n + Score: - " + score);
								if (score >= 0.75) {
									if (score >= count) {
										count = score;
										revisedAttribute = revised.get(j);
									}
								}

							}

							// score = score + id_score + name_score + value_score + href_score;

						}

					}

					System.out.println("Finding Similarity between the Attributes");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("Original Attribute iss : " + originalAttribute + " \n Much Similar With \n "
							+ revisedAttribute);

					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");

					if (originalAttribute.contains("name") && revisedAttribute.contains("name")) {

						AttributeCheckForSimilarAttributes(originalAttribute, revisedAttribute, "name", old_file,
								new_file);

					} else if (revisedAttribute.contains("name")) {

//						try {
//
////							String org = originalAttribute.replace("[", "");
////							org = org.replace("]", "");
////							String rev = revisedAttribute.replace("[", "");
////							rev = rev.replace("]", "");
////
////							doc3.insertString(doc3.getLength(), "\"name\" attribute missing in the ORIGINAL version\nl",
////									null);
////
////							doc3.insertString(doc3.getLength(), "\n Similar Widgets are \n", keyWordBold);
////							doc3.insertString(doc3.getLength(), "" + org + " \n", null);
////							doc3.insertString(doc3.getLength(), " with \n", keyWordBold);
////							doc3.insertString(doc3.getLength(), "" + rev + " \n", null);
//
//						} catch (BadLocationException e) {
//
//							e.printStackTrace();
//						}

					}

					if (originalAttribute.contains(" id") && revisedAttribute.contains("id")) {

						AttributeCheckForSimilarAttributes(originalAttribute, revisedAttribute, "id", old_file,
								new_file);

					} else if (revisedAttribute.contains("id")) {
						try {
//							doc3.insertString(doc3.getLength(), "\"id\" attribute missing in the ORIGINAL version\n",
//									null);
						} catch (Exception e) {

							e.printStackTrace();
						}

					}

					if (originalAttribute.contains("href") && revisedAttribute.contains("href")) {
						AttributeCheckForSimilarAttributes(originalAttribute, revisedAttribute, "href", old_file,
								new_file);
					} else if (revisedAttribute.contains("href")) {
						try {
//							doc3.insertString(doc3.getLength(), "\"href\" attribute missing in the ORIGINAL version\n",
//									null);
						} catch (Exception e) {

							e.printStackTrace();
						}

					}

					if (originalAttribute.contains("type") && revisedAttribute.contains("type")) {
						AttributeCheckForSimilarAttributes(originalAttribute, revisedAttribute, "type", old_file,
								new_file);
					} else if (revisedAttribute.contains("type")) {
						try {
//							doc3.insertString(doc3.getLength(), "\"type\" attribute missing in the ORIGINAL version\n",
//									null);
						} catch (Exception e) {

							e.printStackTrace();
						}

					}

					if (originalAttribute.contains("value") && revisedAttribute.contains("value")) {
						AttributeCheckForSimilarAttributes(originalAttribute, revisedAttribute, "value", old_file,
								new_file);
					} else if (revisedAttribute.contains("value")) {
						try {
//							doc3.insertString(doc3.getLength(), "\"value\" attribute missing in the ORIGINAL version\n",
//									null);
						} catch (Exception e) {

							e.printStackTrace();
						}

					}

				}

			}

			doc3.insertString(doc3.getLength(),
					"\n\n-----------------------------------------------------------------------------------------------------------------------------\n",
					keyWordGray);
			doc3.insertString(doc3.getLength(), "\n Output End  \n\n", keyWordGray);
		} catch (

		Exception e) {
			e.printStackTrace();
		}

	}

	private static void AttributeCheckForSimilarAttributes(String orginal, String revised, String attribute,
			File old_file, File new_file) {
		try {
			String nametagorig = null;
			String nametagrev = null;
			String typeradcheckorig = "";
			String typeradcheckrev = "";
			if (!attribute.equals("typeoption") && !attribute.equals("optiontype")) {
				orginal.indexOf(" " + attribute);
				nametagorig = orginal.substring(orginal.indexOf(" " + attribute)).replaceAll(" ", "");
			}
			if (revised.contains(" " + attribute) || attribute.equals("typeoption") || attribute.equals("optiontype")) {
				if (!attribute.equals("typeoption") && !attribute.equals("optiontype")) {
					nametagrev = revised.substring(revised.indexOf(" " + attribute)).replaceAll(" ", "");
				}
				if (attribute.equals("typeoption") || attribute.equals("optiontype")
						|| nametagorig.contains(attribute + "=\"") || nametagrev.contains(attribute + "=\"")) {
					String nameorigtemp = nametagorig;
					String namerevtemp = nametagrev;
					String nameorig = "";
					String namerev = "";

					int numquotesorig = 0;
					if (attribute.equals("optiontype")) {

						nameorig = "<Select> - <option>";
						namerev = "radio";
					} else {
						if (!attribute.equals("typeoption")) {

							for (int i = 0; i <= 100; i++) {

								nameorig = nameorig + nameorigtemp.charAt(nameorigtemp.indexOf(attribute + "=") + i);
								if (nameorigtemp.charAt(nameorigtemp.indexOf(attribute + "=") + i) == '\"') {
									numquotesorig = numquotesorig + 1;

									if (numquotesorig == 2) {
										break;
									}
								}
							}
						}
					}
					numquotesorig = 0;
					int numquotesrev = 0;
					if (attribute.equals("typeoption")) {
						namerev = "<Select> - <option>";
						nameorig = "radio";
					} else {
						if (!attribute.equals("optiontype")) {
							for (int i = 0; i <= 100; i++) {
								namerev = namerev + namerevtemp.charAt(namerevtemp.indexOf(attribute + "=") + i);

								if (namerevtemp.charAt(namerevtemp.indexOf(attribute + "=") + i) == '\"') {
									numquotesrev = numquotesrev + 1;

									if (numquotesrev == 2) {
										break;
									}
								}
							}
						}
					}
					numquotesrev = 0;
					typeradcheckorig = nameorig;
					typeradcheckrev = namerev;

					System.out.println(nameorig + " - " + namerev);

					if (!nameorig.equals(namerev)) {
						try {
							String org = orginal.replace("[", "");
							org = org.replace("]", "");
							String rev = revised.replace("[", "");
							rev = rev.replace("]", "");
							doc3.insertString(doc3.getLength(), "---------------------------------- Type of Change: "
									+ attribute + " Modified. ----------------------------------\n\n", keyWordBold);

							doc3.insertString(doc3.getLength(), " " + nameorig + " is different from " + namerev + "\n",
									null);
							doc3.insertString(doc3.getLength(), "\n Similar Widgets are \n", keyWordBold);
							doc3.insertString(doc3.getLength(), "" + org + " \n", null);
							doc3.insertString(doc3.getLength(), " with \n", keyWordBold);
							doc3.insertString(doc3.getLength(), "" + rev + " \n", null);
							doc3.insertString(doc3.getLength(), " According to Cousine Algorithm \n", keyWordBold);
							doc3.insertString(doc3.getLength(),
									"_________________________________________________________________________________________________________\n\n",

									null);
						} catch (BadLocationException e) {

							e.printStackTrace();
						}

						if (attribute.equals("name")) {
							if (nametagorig.contains(attribute + "=\"") && nametagrev.contains(attribute + "=\"")) {
								System.out.println("ID''''''''''''''': " + nameorig + ", " + namerev);
								// String xpath_old_file = XpathParser.xpathFinderFromFile("name",
								// nameorig.substring(nameorig.indexOf("\"")), old_file);

								String xpath_new_file = XpathParser.xpathFinderFromFile("name",
										namerev.substring(namerev.indexOf("\"")), new_file);

								if (!nameorig.substring(nameorig.indexOf("\""))
										.equals(namerev.substring(namerev.indexOf("\"")))) {
									similarywidgetnamelist.add(new OldNew(nameorig.substring(nameorig.indexOf("\"")),
											namerev.substring(namerev.indexOf("\"")), xpath_new_file));
								}

							}
							// nameerrors++;
						} else if (attribute.equals("id")) {
							// repair this space issue
							if (nametagorig.contains(attribute + "=\"") && nametagrev.contains(attribute + "=\"")) {
								System.out.println("ID''''''''''''''': " + nameorig + ", " + namerev);

								String xpath_new_file = XpathParser.xpathFinderFromFile("id",
										namerev.substring(namerev.indexOf("\"")), new_file);
								if (!nameorig.substring(nameorig.indexOf("\""))
										.equals(namerev.substring(namerev.indexOf("\"")))) {

									similarywidgetidlist.add(new OldNew(nameorig.substring(nameorig.indexOf("\"")),
											namerev.substring(namerev.indexOf("\"")), xpath_new_file));
								}
							}
							// iderrors++;

						} else if (attribute.equals("type")) {
							if (nametagorig.contains(attribute + "=\"") && nametagrev.contains(attribute + "=\"")) {

								String xpath_new_file = XpathParser.xpathFinderFromFile("name",
										namerev.substring(namerev.indexOf("\"")), new_file);
								if (!nameorig.substring(nameorig.indexOf("\""))
										.equals(namerev.substring(namerev.indexOf("\"")))) {
									similarywidgettypelist.add(new OldNew(nameorig.substring(nameorig.indexOf("\"")),
											namerev.substring(namerev.indexOf("\"")), xpath_new_file));
								}
							}
							// typeerrors++;
						} else if (attribute.equals("optiontype")) {

//								String xpath_new_file = XpathParser.xpathFinderFromFile("name",
//										namerev.substring(namerev.indexOf("\"")), new_file);
//							
							similarywidgettypelist.add(new OldNew(nameorig, namerev, ""));
						} else if (attribute.equals("typeoption")) {

//							String xpath_new_file = XpathParser.xpathFinderFromFile("name",
//									namerev.substring(namerev.indexOf("\"")), new_file);
//						
							similarywidgettypelist.add(new OldNew(nameorig, namerev, ""));
						} else if (attribute.equals("class")) {
							// classerrors++;
						} else if (attribute.equals("href")) {
							if (nametagorig.contains(attribute + "=\"") && nametagrev.contains(attribute + "=\"")) {
								// System.out.println("ID''''''''''''''': " + nameorig + ", " + namerev);
								// similarywidgethreflist.add(new
								// OldNew(nameorig.substring(nameorig.indexOf("\"")),
								// namerev.substring(nameorig.indexOf("\""))));
							}
							// hreferrors++;
						} else if (attribute.equals("onclick")) {
							// onclickerrors++;
						} else if (attribute.equals("value")) {
							if (nametagorig.contains(attribute + "=\"") && nametagrev.contains(attribute + "=\"")) {
								// System.out.println("ID''''''''''''''': " + nameorig + ", " + namerev);
								// similarywidgetvaluelist.add(new
								// OldNew(nameorig.substring(nameorig.indexOf("\"")),
								// namerev.substring(nameorig.indexOf("\""))));
							}
							// valueerrors++;
						}
					} else if (nameorig.equals(namerev)) {

						System.out.println("Similar Widgets Name- Id + " + nameorig + " ----- " + namerev);
						if (attribute.equals("name")) {
							if (nametagorig.contains(attribute + "=\"") && nametagrev.contains(attribute + "=\"")) {

								String xpath_new_file = XpathParser.xpathFinderFromFile("name",
										nameorig.substring(nameorig.indexOf("\"")), new_file);

								if (!nameorig.substring(nameorig.indexOf("\""))
										.equals(namerev.substring(namerev.indexOf("\"")))) {
									System.out.println("Adding - " + nameorig + "  -  " + namerev);
									similarywidgetnamelist.add(new OldNew(nameorig.substring(nameorig.indexOf("\"")),
											namerev.substring(namerev.indexOf("\"")), xpath_new_file));
								}
							}
							// nameerrors++;
						} else if (attribute.equals("id")) {
							// repair this space issue
							if (nametagorig.contains(attribute + "=\"") && nametagrev.contains(attribute + "=\"")) {

								String xpath_new_file = XpathParser.xpathFinderFromFile("id",
										nameorig.substring(nameorig.indexOf("\"")), new_file);

								if (!nameorig.substring(nameorig.indexOf("\""))
										.equals(namerev.substring(namerev.indexOf("\"")))) {
									System.out.println("Adding - " + nameorig + "  -  " + namerev);
									similarywidgetidlist.add(new OldNew(nameorig.substring(nameorig.indexOf("\"")),
											namerev.substring(namerev.indexOf("\"")), xpath_new_file));
								}

							}
							// iderrors++;

						}

					}

				}

			} else {
				if (attribute.equals("id") || attribute.equals("name")) {
					try {
						String org = orginal.replace("[", "");
						org = org.replace("]", "");
						String rev = revised.replace("[", "");
						rev = rev.replace("]", "");
						doc3.insertString(doc3.getLength(), "---------------------------------- Type of Change: "
								+ attribute + " Modified. ----------------------------------\n\n", keyWordBold);
						// doc3.insertString(doc3.getLength(),
						// "\"" + attribute + "\" attribute missing in the revised version\n", null);
						doc3.insertString(doc3.getLength(), "\n Similar Widgets are \n", keyWordBold);
						doc3.insertString(doc3.getLength(), "" + org + " \n", null);
						doc3.insertString(doc3.getLength(), " with \n", keyWordBold);
						doc3.insertString(doc3.getLength(), "" + rev + " \n", null);
						doc3.insertString(doc3.getLength(), " According to Cousine Algorithm \n", keyWordBold);
						doc3.insertString(doc3.getLength(),
								"_________________________________________________________________________________________________________\n\n",
								null);
					} catch (BadLocationException e) {

						e.printStackTrace();
					}
				}
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}

	}

	public static double findAttributeSimilarityScore(String orginal, String revised, String attribute) {

		double score = 0;
		if (orginal.contains(attribute) == true) {
			if (revised.contains(attribute) == true) {

				try {
					String originall = orginal;
					String revisedd = revised;

					int numquotesorig = 0;
					int numquotesrev = 0;
					String namerev = "";
					String nameorig = "";
					String nameorigtemp = originall.substring(originall.indexOf(" " + attribute)).replaceAll(" ", "");
					String namerevtemp = revisedd.substring(revisedd.indexOf(" " + attribute)).replaceAll(" ", "");

					if (nameorigtemp.contains(attribute + "=\"") && namerevtemp.contains(attribute + "=\"")) {

						for (int i = 0; i <= 100; i++) {
							nameorig = nameorig + nameorigtemp.charAt(nameorigtemp.indexOf(attribute + "=") + i);

							if (nameorigtemp.charAt(nameorigtemp.indexOf(attribute + "=") + i) == '\"') {
								numquotesorig = numquotesorig + 1;

								if (numquotesorig == 2) {
									break;
								}
							}

						}

						for (int i = 0; i <= 100; i++) {

							namerev = namerev + namerevtemp.charAt(namerevtemp.indexOf("id" + "=") + i);

							if (namerevtemp.charAt(namerevtemp.indexOf("id" + "=") + i) == '\"') {
								numquotesrev = numquotesrev + 1;

								if (numquotesrev == 2) {
									break;
								}
							}

						}
					}

					SimilarityFinder similarityFinder = new SimilarityFinder();
					nameorig = nameorig.substring(nameorig.indexOf("\""));
					namerev = namerev.substring(namerev.indexOf("\""));
					score = similarityFinder.score(nameorig, namerev);
					/*
					 * if (score_of_id > count_id) { count_id = score_of_id; originalId = nameorig;
					 * revisedId = namerev; checkingStringRevised = revisedd;
					 * System.out.println(originalId); System.out.println(revisedId);
					 * System.out.println(checkingStringRevised); System.out.println(""); }
					 */
				} catch (Exception e) {

				}
			}
		}
		return score;
	}

	public static int distance(String a, String b) {
		a = a.toLowerCase();
		b = b.toLowerCase();
		// i == 0
		int[] costs = new int[b.length() + 1];
		for (int j = 0; j < costs.length; j++)
			costs[j] = j;
		for (int i = 1; i <= a.length(); i++) {
			// j == 0; nw = lev(i - 1, j)
			costs[0] = i;
			int nw = i - 1;
			for (int j = 1; j <= b.length(); j++) {
				int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
						a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
				nw = costs[j];
				costs[j] = cj;
			}
		}
		return costs[b.length()];
	}

	private static void getRefinedFiles(String srcpath, String destpath, String directoryName) {

		File file = new File(destpath);
		File directory = new File(directoryName);
		System.out.println("Path is: " + directory.toString());
		if (!directory.exists()) {
			directory.mkdir();
		}
		if (!file.exists()) {
			file.getParentFile().mkdir();
			try {
				file.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		boolean stylecheck = false;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(srcpath));
			FileWriter fileWriter = new FileWriter(destpath);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			String line = reader.readLine();
			while (line != null) {

				// read next line
				line = reader.readLine();
				if (line != null) {
					if (line.toString().contains("<style")) {
						stylecheck = true;
					}
					if (line.toString().contains("<script")) {
						stylecheck = true;
					}
					if (stylecheck == true) {

					} else {
						bufferedWriter.write(line);
						bufferedWriter.newLine();
					}
					if (line.toString().contains("</style")) {
						stylecheck = false;
					}
					if (line.toString().contains("</script")) {
						stylecheck = false;
					}
				}

			}
			bufferedWriter.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void inputToInputButtonCheck(Delta delta, int i, String originputtype, String revinputtype) {
		try {
			String origbtntext = "";
			String revbtntext = "";
			origbtntext = delta.getOriginal().getLines().get(i).toString()
					.substring(delta.getOriginal().getLines().get(i).toString().indexOf(originputtype));
			origbtntext = origbtntext.substring(origbtntext.indexOf(">") + 1);
			if (originputtype.equals("<button")) {
				origbtntext = origbtntext.replaceAll("</button>", "");
			}
			if (origbtntext.contains("<br>")) {
				origbtntext.replaceAll("<br>", "");
			}
			if (origbtntext.contains("</br>")) {
				origbtntext.replaceAll("</br>", "");
			}
			if (origbtntext.contains("<br/>")) {
				origbtntext.replaceAll("<br/>", "");
			}

			revbtntext = delta.getRevised().getLines().get(i).toString()
					.substring(delta.getRevised().getLines().get(i).toString().indexOf(revinputtype));
			revbtntext = revbtntext.substring(revbtntext.indexOf(">") + 1);
			if (revinputtype.equals("<button")) {
				revbtntext = revbtntext.replaceAll("</button>", "");
			}

			if (revbtntext.contains("<br>")) {
				revbtntext.replaceAll("<br>", "");
			}
			if (revbtntext.contains("</br>")) {
				revbtntext.replaceAll("</br>", "");
			}
			if (revbtntext.contains("<br/>")) {
				revbtntext.replaceAll("<br/>", "");
			}

			try {
				if (origbtntext.trim().equalsIgnoreCase(revbtntext.trim())) {
					doc3.insertString(doc3.getLength(), "Text of both the buttons is the same\n", null);
				} else {
					doc3.insertString(doc3.getLength(), "Text of both the button changed from \"" + origbtntext
							+ "\" to \"" + revbtntext + "\" the same\n", null);
				}
			} catch (BadLocationException e) {

				e.printStackTrace();
			}
		} catch (Exception ee) {

		}
	}

	private static String getRevisedSelectTagString(Delta delta, String selecttagrev) {
		for (int i = 0; i < delta.getRevised().getLines().size(); i++) {

			if (delta.getRevised().getLines().get(i).toString().contains("<select>")) {

				for (int j = i; j < delta.getRevised().getLines().size(); j++) {

					selecttagrev = selecttagrev + delta.getRevised().getLines().get(i).toString();
					if (delta.getRevised().getLines().get(j).toString().contains("</select>")) {
						break;
					}
				}
			}
			if (selecttagrev.contains("<select>")) {
				break;
			}
			System.out.println("SELECT OPTION REVISED: " + selecttagrev);

		}
		return selecttagrev;
	}

	private static String getOriginalSelectTagString(Delta delta, String selecttagorig) {
		System.out.println("'''''''INSIDE getOriginalSelectTagString");
		for (int i = 0; i < delta.getOriginal().getLines().size(); i++) {
			System.out.println("'''''''INSIDE getOriginalSelectTagString: " + i);
			if (delta.getOriginal().getLines().get(i).toString().contains("<select>")) {

				for (int j = i; j < delta.getOriginal().getLines().size(); j++) {
					System.out.println("FOUND: " + j);
					selecttagorig = selecttagorig + delta.getOriginal().getLines().get(j).toString();
					if (delta.getOriginal().getLines().get(j).toString().contains("</select>")) {
						System.out.println("SELECT OPTION: " + selecttagorig);
						break;
					}
				}
			}
			if (selecttagorig.contains("<select>")) {
				break;
			}

		}
		// System.out.println(selecttagorig);
		return selecttagorig;
	}

	private static void AttributeCheck(Delta delta, String attribute) {
		String nametagorig;
		String nametagrev;
		String typeradcheckorig = "";
		String typeradcheckrev = "";
		delta.getOriginal().getLines().toString().indexOf(" " + attribute);
		nametagorig = delta.getOriginal().getLines().toString()
				.substring(delta.getOriginal().getLines().toString().indexOf(" " + attribute)).replaceAll(" ", "");
		if (delta.getRevised().getLines().toString().contains(" " + attribute)) {
			nametagrev = delta.getRevised().getLines().toString()
					.substring(delta.getRevised().getLines().toString().indexOf(" " + attribute)).replaceAll(" ", "");

			System.out
					.println("SUBSTRING "
							+ delta.getOriginal().getLines().toString()
									.substring(delta.getOriginal().getLines().toString().indexOf(attribute))
							+ " BANKAI! " + nametagorig);

			if (nametagorig.contains(attribute + "=\"") || nametagrev.contains(attribute + "=\"")) {
				String nameorigtemp = nametagorig;
				String namerevtemp = nametagrev;
				String nameorig = "";
				String namerev = "";

				int numquotesorig = 0;

				for (int i = 0; i <= 100; i++) {
					nameorig = nameorig + nameorigtemp.charAt(nameorigtemp.indexOf(attribute + "=") + i);
					if (nameorigtemp.charAt(nameorigtemp.indexOf(attribute + "=") + i) == '\"') {
						numquotesorig = numquotesorig + 1;
						// nameorig=nameorig+nameorigtemp.charAt(nameorigtemp.indexOf("name"));
						if (numquotesorig == 2) {
							break;
						}
					}
				}
				numquotesorig = 0;
				int numquotesrev = 0;
				for (int i = 0; i <= 100; i++) {
					namerev = namerev + namerevtemp.charAt(namerevtemp.indexOf(attribute + "=") + i);

					if (namerevtemp.charAt(namerevtemp.indexOf(attribute + "=") + i) == '\"') {
						numquotesrev = numquotesrev + 1;
						// nameorig=nameorig+nameorigtemp.charAt(nameorigtemp.indexOf("name"));
						if (numquotesrev == 2) {
							break;
						}
					}
				}
				numquotesrev = 0;
				typeradcheckorig = nameorig;
				typeradcheckrev = namerev;

				System.out.println("Name Orig: " + nameorig.substring(nameorig.indexOf("\"")) + ", " + attribute
						+ " Rev: " + namerev.substring(namerev.indexOf("\"")));
				if (!nameorig.equals(namerev)) {
					try {
						doc3.insertString(doc3.getLength(), nameorig + " name is different from " + namerev + "\n",
								null);
					} catch (BadLocationException e) {

						e.printStackTrace();
					}

					if (attribute.equals("name")) {
						if (nametagorig.contains(attribute + "=\"") && nametagrev.contains(attribute + "=\"")) {
							System.out.println("ID''''''''''''''': " + nameorig + ", " + namerev);
							namelist.add(new OldNew(nameorig.substring(nameorig.indexOf("\"")),
									namerev.substring(nameorig.indexOf("\""))));
						}
						nameerrors++;
					} else if (attribute.equals("id")) {
						// repair this space issue
						if (nametagorig.contains(attribute + "=\"") && nametagrev.contains(attribute + "=\"")) {
							System.out.println("ID''''''''''''''': " + nameorig + ", " + namerev);
							idlist.add(new OldNew(nameorig.substring(nameorig.indexOf("\"")),
									namerev.substring(nameorig.indexOf("\""))));
						}
						iderrors++;

					} else if (attribute.equals("type")) {
						typeerrors++;
					} else if (attribute.equals("class")) {
						classerrors++;
					} else if (attribute.equals("href")) {
						if (nametagorig.contains(attribute + "=\"") && nametagrev.contains(attribute + "=\"")) {
							System.out.println("ID''''''''''''''': " + nameorig + ", " + namerev);
							linklist.add(new OldNew(nameorig.substring(nameorig.indexOf("\"")),
									namerev.substring(nameorig.indexOf("\""))));
						}
						hreferrors++;
					} else if (attribute.equals("onclick")) {
						onclickerrors++;
					} else if (attribute.equals("value")) {
						valueerrors++;
					}
				} else if (nameorig.equals(namerev)) {
					System.out.println("EEQUALLLSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
//					try {
//						doc3.insertString(doc3.getLength(), nameorig+" is unchanged "+namerev+"\n", null);
//					} catch (BadLocationException e) {
//						
//						e.printStackTrace();
//					}
				}

			}

		} else {
			try {
				doc3.insertString(doc3.getLength(), "\"" + attribute + "\" attribute missing in the revised version\n",
						null);
			} catch (BadLocationException e) {

				e.printStackTrace();
			}
		}
		// to check radio button
		if (delta.getOriginal().getLines().toString().contains("\"radio\"")) {
			int sizeradorig = delta.getOriginal().size();
			int sizeradrev = delta.getRevised().size();
			boolean exists = false;
			boolean itemnotfound = false;
			boolean mutualdoesntexists = false;

			// check if size of original and revised array is the same
			ArrayList<String> radiolistorig = new ArrayList<String>();
			ArrayList<String> radiolistrev = new ArrayList<String>();
			ArrayList<String> extractedtypeorig = new ArrayList<String>();
			ArrayList<String> extractedtyperev = new ArrayList<String>();
			getTypeArrayLists(delta, radiolistorig, radiolistrev, extractedtypeorig, extractedtyperev);

			try {

				doc3.insertString(doc3.getLength(),
						"\"type\" attribute exists, and here is the list in original and revised respectively\n", null);

				doc3.insertString(doc3.getLength(), "TYPE IN ORIGINAL LIST:\n" + extractedtypeorig.size() + "\n",
						keyWordBold);
				for (int i = 0; i < extractedtypeorig.size(); i++) {

					doc3.insertString(doc3.getLength(), extractedtypeorig.get(i) + "\n", null);
				}
				doc3.insertString(doc3.getLength(), "TYPE IN REVISED LIST:\n" + extractedtyperev.size() + "\n",
						keyWordBold);

				for (int i = 0; i < extractedtyperev.size(); i++) {
					doc3.insertString(doc3.getLength(), extractedtyperev.get(i) + "\n", null);
				}
			} catch (BadLocationException e) {

				e.printStackTrace();
			}
			if (extractedtyperev.size() > 0) {

				for (int i = 0; i < extractedtyperev.size(); i++) {
					for (int j = 0; j < extractedtypeorig.size(); j++) {
						if (extractedtypeorig.get(j).equals(extractedtyperev.get(i))) {
							// atleast one item exists and is the same
							exists = true;
							break;
						}
						if ((j == extractedtypeorig.size() - 1)
								&& (!extractedtyperev.get(i).equals(extractedtypeorig.get(j)))) {
							itemnotfound = true;
							raditemsnotsameerrors++;
						}

					}
				}
				radiolistnew.add(new SelectboxArray(extractedtyperev));
				radiolistold.add(new SelectboxArray(extractedtypeorig));
			}

			else {

				List<String> original = fileToLines1("C:\\TempDomFiles\\temp1.html");
				List<String> revised = fileToLines2("C:\\TempDomFiles\\temp2.html");
				Patch patchselect = DiffUtils.diff(original, revised);
				String selecttag = "";
				// search selectbox/checkbox
				for (int i = 0; i < patchselect.getDeltas().size(); i++) {
					if (patchselect.getDeltas().get(i).getRevised().toString().contains("<select>")) {
						for (int j = i; j < patchselect.getDeltas().size(); j++) {
							selecttag = selecttag + patchselect.getDeltas().get(i).getRevised().getLines().toString();
							{
								if (patchselect.getDeltas().get(i).getRevised().toString().contains("</select>")) {
									break;
								}

							}
						}
					}
					if (selecttag.trim() != "") {
						break;
					}
				}
				selecttag.contains("<option");

				int count = 0;
				while (true) {

					if (selecttag.indexOf("<option") != -1) {

						extractedtyperev.add(count,
								selecttag.substring(selecttag.indexOf("<option"), selecttag.indexOf("</option>")));
						extractedtyperev.set(count, extractedtyperev.get(count)
								.substring(extractedtyperev.get(count).indexOf(">") + 1).trim());
						selecttag = selecttag.substring(selecttag.indexOf(("</option>")) + 8);
						System.out.println("Options ORIG:" + extractedtyperev.get(count));
						count++;

					} else {
						break;

					}
				}

				ArrayList<String> origexclusiveitems = new ArrayList<String>();
				ArrayList<String> selectexclusiveitems = new ArrayList<String>();
				boolean mutualexists = false;
				for (int i = 0; i < extractedtypeorig.size(); i++) {
					// compare radio & select lists with base as orig
					for (int j = 0; j < extractedtyperev.size(); j++) {
						if (extractedtypeorig.get(i).trim().equalsIgnoreCase(extractedtyperev.get(j).trim())) {
							mutualexists = true;
							exists = true;
							break;
						}
						if ((extractedtyperev.size() == j - 1) && (!extractedtypeorig.get(i).trim()
								.equalsIgnoreCase(extractedtyperev.get(j).trim()))) {
							origexclusiveitems.add(extractedtypeorig.get(i).trim().toString());
							mutualdoesntexists = true;
						}
						System.out.println("OPTION HALOO: " + extractedtypeorig.get(i));
					}
				}
				radiolistold.add(new SelectboxArray(extractedtypeorig));
				selectlistnew.add(new SelectboxArray(extractedtyperev));

				if (mutualexists) {
					for (int i = 0; i < extractedtyperev.size(); i++) {
						// compare radio & select lists with base as orig
						for (int j = 0; j < extractedtypeorig.size(); j++) {
							if (extractedtyperev.get(i).trim().equalsIgnoreCase(extractedtypeorig.get(j).trim())) {

								break;
							}
							if ((extractedtypeorig.size() == j - 1) && (!extractedtyperev.get(i).trim()
									.equalsIgnoreCase(extractedtypeorig.get(j).trim()))) {
								selectexclusiveitems.add(extractedtyperev.get(i).trim().toString());
							}
						}
					}
					try {
						doc3.insertString(doc3.getLength(),
								"type \"radio\" in original document has been changed to \"select\" in the revised document\nHere are the list of differences:\nItems only in original list:\n"
										+ extractedtypeorig.size() + "\n",
								null);
						if (!(origexclusiveitems.isEmpty())) {
							for (int i = 0; i < origexclusiveitems.size(); i++) {

								doc3.insertString(doc3.getLength(), origexclusiveitems.get(i) + "\n", null);
							}
						} else {
							doc3.insertString(doc3.getLength(), "NONE\n", null);
						}

						doc3.insertString(doc3.getLength(),
								"Items only in original list:\n" + selectexclusiveitems.size() + "\n", null);

						if (!(selectexclusiveitems.isEmpty())) {
							for (int i = 0; i < extractedtyperev.size(); i++) {
								doc3.insertString(doc3.getLength(), extractedtyperev.get(i) + "\n", null);
							}
						} else {
							doc3.insertString(doc3.getLength(), "NONE\n", null);

						}

					} catch (BadLocationException e) {

						e.printStackTrace();
					}
				}

				else {

					radselerrors++;
					try {
						doc3.insertString(doc3.getLength(),
								"No mutual items exist between both radio and select\nOriginal List\n", null);
						for (int i = 0; i < extractedtypeorig.size(); i++) {

							doc3.insertString(doc3.getLength(), extractedtypeorig.get(i) + "\n", null);
						}
						doc3.insertString(doc3.getLength(), "Revised list:\n", null);
						for (int i = 0; i < extractedtyperev.size(); i++) {

							doc3.insertString(doc3.getLength(), extractedtyperev.get(i) + "\n", null);
						}
					} catch (BadLocationException e) {

						e.printStackTrace();
					}
				}

			}

		} else if (delta.getRevised().getLines().toString().contains("\"radio\"")) {
			System.out.println("'''''''''''''''''''''''inside RADIOOOOO SELECTTTTTTTTTTTTTTTTTTTTTTTTTT");
			int sizeradorig = delta.getOriginal().size();
			int sizeradrev = delta.getRevised().size();
			boolean exists = false;
			boolean itemnotfound = false;
			boolean mutualdoesntexists = false;
			// check if size of original and revised array is the same
			ArrayList<String> radiolistorig = new ArrayList<String>();
			ArrayList<String> radiolistrev = new ArrayList<String>();
			ArrayList<String> extractedtypeorig = new ArrayList<String>();
			ArrayList<String> extractedtyperev = new ArrayList<String>();
			getTypeArrayLists(delta, radiolistorig, radiolistrev, extractedtypeorig, extractedtyperev);

			System.out.println(radiolistorig.size() + ", " + radiolistrev.size());
			System.out.println(radiolistorig.get(0) + ", " + radiolistrev.get(0));
			System.out.println(
					extractedtypeorig.size() + ", " + extractedtyperev.size() + ", " + extractedtyperev.get(0));

			try {
				doc3.insertString(doc3.getLength(),
						"\"type\" attribute exists, and here is the list in original and revised respectively\n", null);
				doc3.insertString(doc3.getLength(), "TYPE IN ORIGINAL LIST:\n" + extractedtypeorig.size() + "\n",
						keyWordBold);
				for (int i = 0; i < extractedtypeorig.size(); i++) {

					doc3.insertString(doc3.getLength(), extractedtypeorig.get(i) + "\n", null);
				}
				doc3.insertString(doc3.getLength(), "TYPE IN REVISED LIST:\n" + extractedtyperev.size() + "\n",
						keyWordBold);

				for (int i = 0; i < extractedtyperev.size(); i++) {
					doc3.insertString(doc3.getLength(), extractedtyperev.get(i) + "\n", null);
				}
			} catch (BadLocationException e) {

				e.printStackTrace();
			}
			if (extractedtypeorig.size() > 0) {

				for (int i = 0; i < extractedtypeorig.size(); i++) {
					for (int j = 0; j < extractedtyperev.size(); j++) {
						if (extractedtyperev.get(j).equals(extractedtypeorig.get(i))) {
							// atleast one item exists and is the same
							exists = true;
							break;
						}
						if ((j == extractedtyperev.size() - 1)
								&& (!extractedtypeorig.get(i).equals(extractedtyperev.get(j)))) {
							itemnotfound = true;
						}

					}
				}
				radiolistold.add(new SelectboxArray(extractedtypeorig));
				radiolistnew.add(new SelectboxArray(extractedtyperev));

			}

			else {
				System.out.println("INSIDE ELSEEEEEE");
				List<String> original = fileToLines1("E:\\temmp\\old.html");
				List<String> revised = fileToLines2("E:\\temmp - Copy\\new.html");
				Patch patchselect = DiffUtils.diff(original, revised);
				String selecttag = "";
				// search selectbox/checkbox
				for (int i = 0; i < patchselect.getDeltas().size(); i++) {
					if (patchselect.getDeltas().get(i).getOriginal().getLines().toString().contains("<select>")) {
						System.out.println("INSIDE ELSEEEEEE: "
								+ patchselect.getDeltas().get(i).getOriginal().getLines().toString());
						for (int j = i; j < patchselect.getDeltas().size(); j++) {
							selecttag = selecttag + patchselect.getDeltas().get(i).getOriginal().getLines().toString();
							{
								if (patchselect.getDeltas().get(i).getOriginal().toString().contains("</select>")) {
									break;
								}

							}
						}
					}
					if (selecttag.trim() != "") {
						break;
					}
				}
				System.out.println("INSIDE ELSEEEEEE: " + extractedtypeorig.size());
				// selecttag.contains("<option");

				int count = 0;
				while (true) {

					if (selecttag.indexOf("<option") != -1) {

						extractedtypeorig.add(count,
								selecttag.substring(selecttag.indexOf("<option"), selecttag.indexOf("</option>")));
						extractedtypeorig.set(count, extractedtypeorig.get(count)
								.substring(extractedtypeorig.get(count).indexOf(">") + 1).trim());
						selecttag = selecttag.substring(selecttag.indexOf(("</option>")) + 8);
						System.out.println("Options ORIG:" + extractedtypeorig.get(count));
						count++;

					} else {
						break;

					}
				}
				// ArrayList<String> options = new ArrayList<String>();
				// getOptionsArray(selecttag, options);
				ArrayList<String> revexclusiveitems = new ArrayList<String>();
				ArrayList<String> selectexclusiveitems = new ArrayList<String>();
				boolean mutualexists = false;
				for (int i = 0; i < extractedtyperev.size(); i++) {
					// compare radio & select lists with base as orig
					for (int j = 0; j < extractedtypeorig.size(); j++) {
						if (extractedtyperev.get(i).trim().equalsIgnoreCase(extractedtypeorig.get(j).trim())) {
							mutualexists = true;
							exists = true;
							break;
						}
						if ((extractedtypeorig.size() == j - 1) && (!extractedtyperev.get(i).trim()
								.equalsIgnoreCase(extractedtypeorig.get(j).trim()))) {
							revexclusiveitems.add(extractedtyperev.get(i).trim().toString());
							mutualdoesntexists = true;
						}
						System.out.println("OPTION HALOO: " + extractedtypeorig.get(i));

					}
				}
				radiolistnew.add(new SelectboxArray(extractedtyperev));
				selectlistold.add(new SelectboxArray(extractedtypeorig));
				System.out.println("''''''EXTRACTED TYPE REV: " + extractedtyperev.size());
				System.out.println("''''''RADIO LIST NEW: " + radiolistnew.get(0).getValue().get(0));

				if (mutualexists) {
					for (int i = 0; i < extractedtypeorig.size(); i++) {
						// compare radio & select lists with base as orig
						for (int j = 0; j < extractedtyperev.size(); j++) {
							if (extractedtypeorig.get(i).trim().equalsIgnoreCase(extractedtyperev.get(j).trim())) {

								break;
							}
							if ((extractedtyperev.size() == j - 1) && (!extractedtypeorig.get(i).trim()
									.equalsIgnoreCase(extractedtyperev.get(j).trim()))) {
								selectexclusiveitems.add(extractedtypeorig.get(i).trim().toString());
							}
						}
					}
					try {
						doc3.insertString(doc3.getLength(),
								"type \"radio\" in original document has been changed to \"select\" in the revised document\nHere are the list of differences:\n",
								null);
						doc3.insertString(doc3.getLength(),
								"Items only in original list:\n" + extractedtypeorig.size() + "\n", keyWordBold);
						if (!(revexclusiveitems.isEmpty())) {
							for (int i = 0; i < revexclusiveitems.size(); i++) {

								doc3.insertString(doc3.getLength(), revexclusiveitems.get(i) + "\n", null);
							}
						} else {
							doc3.insertString(doc3.getLength(), "NONE\n", null);
						}

						doc3.insertString(doc3.getLength(),
								"Items only in original list:\n" + selectexclusiveitems.size() + "\n", keyWordBold);

						if (!(selectexclusiveitems.isEmpty())) {
							for (int i = 0; i < extractedtypeorig.size(); i++) {
								doc3.insertString(doc3.getLength(), extractedtyperev.get(i) + "\n", null);
							}
						} else {
							doc3.insertString(doc3.getLength(), "NONE\n", null);

						}

					} catch (BadLocationException e) {

						e.printStackTrace();
					}
				} else {
					try {
						doc3.insertString(doc3.getLength(),
								"------------------------------------------------------------------\n", null);
						doc3.insertString(doc3.getLength(), "No mutual items exist between both radio and select\n",
								null);
						doc3.insertString(doc3.getLength(), "Original List\n", keyWordBold);
						for (int i = 0; i < extractedtypeorig.size(); i++) {

							doc3.insertString(doc3.getLength(), extractedtypeorig.get(i) + "\n", null);
						}
						doc3.insertString(doc3.getLength(), "Revised list:\n", keyWordBold);
						for (int i = 0; i < extractedtyperev.size(); i++) {

							doc3.insertString(doc3.getLength(), extractedtyperev.get(i) + "\n", null);
						}
					} catch (BadLocationException e) {

						e.printStackTrace();
					}
				}

			}
			try {
				if (exists == false) {
					doc3.insertString(doc3.getLength(), "Items between two lists are not the same\n", null);
				} else if (exists == true && mutualdoesntexists == true) {
					doc3.insertString(doc3.getLength(), "Some items between two lists are not the same\n", null);
				} else if (exists == true && mutualdoesntexists == false) {
					doc3.insertString(doc3.getLength(), "All items between two lists are the same\n", null);

				}

			} catch (BadLocationException e) {

				e.printStackTrace();
			}

		}
		if (delta.getOriginal().getLines().toString().contains("type=\"button\"")
				|| delta.getOriginal().getLines().toString().contains("<button")
				|| delta.getRevised().getLines().contains("type=\"button\"")
				|| delta.getRevised().getLines().contains("<button")) {

			if (delta.getOriginal().getLines().size() >= delta.getRevised().getLines().size()) {

				for (int i = 0; i < delta.getRevised().getLines().size(); i++) {

					if (delta.getOriginal().getLines().get(i).toString().contains("<input")
							&& !delta.getRevised().getLines().get(i).toString().contains("<input")) {
						if (delta.getRevised().getLines().get(i).toString().contains("<button")) {
							try {
								doc3.insertString(doc3.getLength(),
										"TAG in original changed from \"input\" to \"button\" at line: " + i + "\n",
										null);
							} catch (BadLocationException e) {

								e.printStackTrace();
							}
						}
					} else if (delta.getRevised().getLines().get(i).toString().contains("<input")
							&& !delta.getOriginal().getLines().get(i).toString().contains("<input")) {

						if (delta.getOriginal().getLines().get(i).toString().contains("<button")) {
							try {
								doc3.insertString(doc3.getLength(),
										"TAG in original changed from \"button\" to \"input\" at line: " + i + "\n",
										null);
							} catch (BadLocationException e) {

								e.printStackTrace();
							}
						}
					}

				}
			} else {
				for (int i = 0; i < delta.getOriginal().getLines().size(); i++) {
					if (delta.getOriginal().getLines().get(i).toString().contains("<input")
							&& !delta.getRevised().getLines().get(i).toString().contains("<input")) {
						if (delta.getRevised().getLines().get(i).toString().contains("<button")) {
							try {
								doc3.insertString(doc3.getLength(),
										"TAG in original changed from \"input\" to \"button\"\n", null);
							} catch (BadLocationException e) {

								e.printStackTrace();
							}
						}
					} else if (delta.getRevised().getLines().get(i).toString().contains("<input")
							&& !delta.getOriginal().getLines().get(i).toString().contains("<input")) {
						if (delta.getOriginal().getLines().get(i).toString().contains("<button")) {
							try {
								doc3.insertString(doc3.getLength(),
										"TAG in original changed from \"button\" to \"input\"\n", null);
							} catch (BadLocationException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}
		}

	}

	public static void findSimilarSelectAndRadio(File oldFile, File newFile) {

		ArrayList<String> optionFromFile1 = new ArrayList<>();
		ArrayList<String> optionFromFile2 = new ArrayList<>();
		ArrayList<String> radioFromFile1 = new ArrayList<>();
		ArrayList<String> radioFromFile2 = new ArrayList<>();

		// Find Options From file - 1
		optionFromFile1 = XpathParser.getSpecificElement(originalFileContent, "option");
		// Find Options From file - 2
		optionFromFile2 = XpathParser.getSpecificElement(revisedFileContent, "option");
		// Find Radio button From file - 1
		radioFromFile2 = XpathParser.getSpecificElement(revisedFileContent, "radio");
		// Find Radio Button From file - 2
		radioFromFile1 = XpathParser.getSpecificElement(originalFileContent, "radio");

		// Compare Radios From File - 1 With Options From File - 2
		for (int i = 0; i < radioFromFile1.size(); i++) {

			String elementOrig = "";
			String elementRevised = "";
			elementOrig = radioFromFile1.get(i).toString();
			String nameValueOfOriginal = "";
			String idValueOfOriginal = "";
			String valueTextOfOriginal = "";

			if (elementOrig.contains("name")) {
				nameValueOfOriginal = findAttributeValue(elementOrig, "name");
			}

			// Get Attribute By ID if Exists
			if (elementOrig.contains("id")) {

				idValueOfOriginal = findAttributeValue(elementOrig, "id");
			}

			// Get Attribute By Value if Exists
			if (elementOrig.contains("value")) {
				valueTextOfOriginal = findAttributeValue(elementOrig, "value");
			}

			for (int j = 0; j < optionFromFile2.size(); j++) {

				String nameValueOfRevised = "";
				String idValueOfRevised = "";
				String valueTextOfRevised = "";
				// elementRevised = optionFromFile2.get(j).toString();

				if (elementOrig.contains("name")) {
					nameValueOfRevised = findAttributeValue(optionFromFile2.get(j).toString(), "name");
				}

				// Get Attribute By ID if Exists
				if (elementOrig.contains("id")) {

					idValueOfRevised = findAttributeValue(optionFromFile2.get(j).toString(), "id");
				}

				// Get Attribute By Value if Exists
				if (elementOrig.contains("value")) {
					valueTextOfRevised = findAttributeValue(optionFromFile2.get(j).toString(), "value");
				}

				if (elementOrig.contains("value") && optionFromFile2.get(j).toString().contains("value")
						&& valueTextOfOriginal.equals(valueTextOfRevised)) {
					elementRevised = optionFromFile2.get(j).toString();
					System.out.println("Similar Elements are - \n " + elementOrig + "\n" + elementRevised
							+ "\n Because Value Same");
					break;
				}
				if (elementOrig.contains("name") && optionFromFile2.get(j).toString().contains("name")
						&& nameValueOfOriginal.equals(nameValueOfRevised)) {
					elementRevised = optionFromFile2.get(j).toString();
					System.out.println("Similar Elements are - \n " + elementOrig + "\n" + elementRevised
							+ "\n Because Name Same");
					break;
				}
				if (elementOrig.contains("id") && optionFromFile2.get(j).toString().contains("id")
						&& idValueOfOriginal.equals(idValueOfRevised)) {
					elementRevised = optionFromFile2.get(j).toString();
					System.out.println(
							"Similar Elements are - \n " + elementOrig + "\n" + elementRevised + "\n Because ID Same");
					break;
				}

			}

			if (elementOrig.contains("value") && elementRevised.contains("value")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "value", oldFile, newFile);

			}

			if (elementOrig.contains("name") && elementRevised.contains("name")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "name", oldFile, newFile);

			}

			if (elementOrig.contains("id") && elementRevised.contains("id")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "id", oldFile, newFile);

			}

			if (elementOrig.contains("radio") && elementRevised.contains("option")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "typeoption", oldFile, newFile);

			}

		}

		// Compare Options From File - 1 With Radios From File - 2
		for (int i = 0; i < optionFromFile1.size(); i++) {

			String elementOrig = "";
			String elementRevised = "";
			elementOrig = optionFromFile1.get(i).toString();
			String nameValueOfOriginal = "";
			String idValueOfOriginal = "";
			String valueTextOfOriginal = "";

			if (elementOrig.contains("name")) {
				nameValueOfOriginal = findAttributeValue(elementOrig, "name");
			}

			// Get Attribute By ID if Exists
			if (elementOrig.contains("id")) {

				idValueOfOriginal = findAttributeValue(elementOrig, "id");
			}

			// Get Attribute By Value if Exists
			if (elementOrig.contains("value")) {
				valueTextOfOriginal = findAttributeValue(elementOrig, "value");
			}

			for (int j = 0; j < radioFromFile2.size(); j++) {

				String nameValueOfRevised = "";
				String idValueOfRevised = "";
				String valueTextOfRevised = "";
				// elementRevised = optionFromFile2.get(j).toString();

				if (elementOrig.contains("name")) {
					nameValueOfRevised = findAttributeValue(radioFromFile2.get(j).toString(), "name");
				}

				// Get Attribute By ID if Exists
				if (elementOrig.contains("id")) {

					idValueOfRevised = findAttributeValue(radioFromFile2.get(j).toString(), "id");
				}

				// Get Attribute By Value if Exists
				if (elementOrig.contains("value")) {
					valueTextOfRevised = findAttributeValue(radioFromFile2.get(j).toString(), "value");
				}

				if (elementOrig.contains("value") && radioFromFile2.get(j).toString().contains("value")
						&& valueTextOfOriginal.equals(valueTextOfRevised)) {
					elementRevised = radioFromFile2.get(j).toString();
					System.out.println("Similar Elements are - \n " + elementOrig + "\n" + elementRevised
							+ "\n Because Value Same");
					break;
				}
				if (elementOrig.contains("name") && radioFromFile2.get(j).toString().contains("name")
						&& nameValueOfOriginal.equals(nameValueOfRevised)) {
					elementRevised = radioFromFile2.get(j).toString();
					System.out.println("Similar Elements are - \n " + elementOrig + "\n" + elementRevised
							+ "\n Because Name Same");
					break;
				}
				if (elementOrig.contains("id") && radioFromFile2.get(j).toString().contains("id")
						&& idValueOfOriginal.equals(idValueOfRevised)) {
					elementRevised = radioFromFile2.get(j).toString();
					System.out.println(
							"Similar Elements are - \n " + elementOrig + "\n" + elementRevised + "\n Because ID Same");
					break;
				}

			}

			if (elementOrig.contains("option") && elementRevised.contains("radio")) {
				System.out.println("Working");
				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "optiontype", oldFile, newFile);

			}

			if (elementOrig.contains("value") && elementRevised.contains("value")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "value", oldFile, newFile);

			}

			if (elementOrig.contains("name") && elementRevised.contains("name")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "name", oldFile, newFile);

			}

			if (elementOrig.contains("id") && elementRevised.contains("id")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "id", oldFile, newFile);

			}

		}

//		// Compare Options From File - 1 With Options From File - 2
		for (int i = 0; i < optionFromFile1.size(); i++) {

			String elementOrig = "";
			String elementRevised = "";
			elementOrig = optionFromFile1.get(i).toString();
			String nameValueOfOriginal = "";
			String idValueOfOriginal = "";
			String valueTextOfOriginal = "";

			if (elementOrig.contains("name")) {
				nameValueOfOriginal = findAttributeValue(elementOrig, "name");
			}

			// Get Attribute By ID if Exists
			if (elementOrig.contains("id")) {

				idValueOfOriginal = findAttributeValue(elementOrig, "id");
			}

			// Get Attribute By Value if Exists
			if (elementOrig.contains("value")) {
				valueTextOfOriginal = findAttributeValue(elementOrig, "value");
			}

			for (int j = 0; j < optionFromFile2.size(); j++) {

				String nameValueOfRevised = "";
				String idValueOfRevised = "";
				String valueTextOfRevised = "";
				// elementRevised = optionFromFile2.get(j).toString();

				if (elementOrig.contains("name")) {
					nameValueOfRevised = findAttributeValue(optionFromFile2.get(j).toString(), "name");
				}

				// Get Attribute By ID if Exists
				if (elementOrig.contains("id")) {

					idValueOfRevised = findAttributeValue(optionFromFile2.get(j).toString(), "id");
				}

				// Get Attribute By Value if Exists
				if (elementOrig.contains("value")) {
					valueTextOfRevised = findAttributeValue(optionFromFile2.get(j).toString(), "value");
				}

				if (elementOrig.contains("value") && optionFromFile2.get(j).toString().contains("value")
						&& valueTextOfOriginal.equals(valueTextOfRevised)) {
					elementRevised = optionFromFile2.get(j).toString();
					System.out.println("Similar Elements are - \n " + elementOrig + "\n" + elementRevised
							+ "\n Because Value Same");
					break;
				}
				if (elementOrig.contains("name") && optionFromFile2.get(j).toString().contains("name")
						&& nameValueOfOriginal.equals(nameValueOfRevised)) {
					elementRevised = optionFromFile2.get(j).toString();
					System.out.println("Similar Elements are - \n " + elementOrig + "\n" + elementRevised
							+ "\n Because Name Same");
					break;
				}
				if (elementOrig.contains("id") && optionFromFile2.get(j).toString().contains("id")
						&& idValueOfOriginal.equals(idValueOfRevised)) {
					elementRevised = optionFromFile2.get(j).toString();
					System.out.println(
							"Similar Elements are - \n " + elementOrig + "\n" + elementRevised + "\n Because ID Same");
					break;
				}

			}

			if (elementOrig.contains("value") && elementRevised.contains("value")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "value", oldFile, newFile);

			}

			if (elementOrig.contains("name") && elementRevised.contains("name")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "name", oldFile, newFile);

			}

			if (elementOrig.contains("id") && elementRevised.contains("id")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "id", oldFile, newFile);

			}

		}

		// Compare Radios From File - 1 With Radios From File - 2
		for (int i = 0; i < radioFromFile1.size(); i++) {

			String elementOrig = "";
			String elementRevised = "";
			elementOrig = radioFromFile1.get(i).toString();
			String nameValueOfOriginal = "";
			String idValueOfOriginal = "";
			String valueTextOfOriginal = "";

			if (elementOrig.contains("name")) {
				nameValueOfOriginal = findAttributeValue(elementOrig, "name");
			}

			// Get Attribute By ID if Exists
			if (elementOrig.contains("id")) {

				idValueOfOriginal = findAttributeValue(elementOrig, "id");
			}

			// Get Attribute By Value if Exists
			if (elementOrig.contains("value")) {
				valueTextOfOriginal = findAttributeValue(elementOrig, "value");
			}

			for (int j = 0; j < radioFromFile2.size(); j++) {

				String nameValueOfRevised = "";
				String idValueOfRevised = "";
				String valueTextOfRevised = "";
				// elementRevised = optionFromFile2.get(j).toString();

				if (elementOrig.contains("name")) {
					nameValueOfRevised = findAttributeValue(radioFromFile2.get(j).toString(), "name");
				}

				// Get Attribute By ID if Exists
				if (elementOrig.contains("id")) {

					idValueOfRevised = findAttributeValue(radioFromFile2.get(j).toString(), "id");
				}

				// Get Attribute By Value if Exists
				if (elementOrig.contains("value")) {
					valueTextOfRevised = findAttributeValue(radioFromFile2.get(j).toString(), "value");
				}

				if (elementOrig.contains("value") && radioFromFile2.get(j).toString().contains("value")
						&& valueTextOfOriginal.equals(valueTextOfRevised)) {
					elementRevised = radioFromFile2.get(j).toString();
					System.out.println("Similar Elements are - \n " + elementOrig + "\n" + elementRevised
							+ "\n Because Value Same");
					break;
				}
				if (elementOrig.contains("name") && radioFromFile2.get(j).toString().contains("name")
						&& nameValueOfOriginal.equals(nameValueOfRevised)) {
					elementRevised = radioFromFile2.get(j).toString();
					System.out.println("Similar Elements are - \n " + elementOrig + "\n" + elementRevised
							+ "\n Because Name Same");
					break;
				}
				if (elementOrig.contains("id") && radioFromFile2.get(j).toString().contains("id")
						&& idValueOfOriginal.equals(idValueOfRevised)) {
					elementRevised = radioFromFile2.get(j).toString();
					System.out.println(
							"Similar Elements are - \n " + elementOrig + "\n" + elementRevised + "\n Because ID Same");
					break;
				}

			}

			if (elementOrig.contains("value") && elementRevised.contains("value")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "value", oldFile, newFile);

			}

			if (elementOrig.contains("name") && elementRevised.contains("name")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "name", oldFile, newFile);

			}

			if (elementOrig.contains("id") && elementRevised.contains("id")) {

				AttributeCheckForSimilarAttributes(elementOrig, elementRevised, "id", oldFile, newFile);

			}

		}

	}

	private static ArrayList<String> getOptionsArray(String selecttag, ArrayList<String> options) {
		int count = 0;
		while (true) {

			if (selecttag.indexOf("<option") != -1) {

				options.add(count, selecttag.substring(selecttag.indexOf("<option"), selecttag.indexOf("</option>")));
				options.set(count, options.get(count).substring(options.get(count).indexOf(">") + 1).trim());
				selecttag = selecttag.substring(selecttag.indexOf(("</option>")) + 8);
				System.out.println("Options:" + options.get(count));
				count++;

			} else {
				break;

			}
		}
		return options;
	}

	private static void getTypeArrayLists(Delta delta, ArrayList<String> radiolistorig, ArrayList<String> radiolistrev,
			ArrayList<String> extractedtypeorig, ArrayList<String> extractedtyperev) {

		for (int i = 0; i < delta.getOriginal().size(); i++) {
			if (delta.getOriginal().getLines().get(i).toString().trim().equals("<br/>")
					|| delta.getOriginal().getLines().get(i).toString().trim().equals("<br>")) {
				// skip if br tag appears
			} else if (delta.getOriginal().getLines().get(i).toString().trim().contains("<option")) {
				System.out.println("ADDED IN ORIG: " + delta.getOriginal().getLines().get(i).toString());
				radiolistorig.add(delta.getOriginal().getLines().get(i).toString());

			} else if (delta.getOriginal().getLines().get(i).toString().trim().contains("\"radio\"")) {
				radiolistorig.add(delta.getOriginal().getLines().get(i).toString());

			}

//					radiolistorig.add(delta.getOriginal().getLines().get(i).toString().substring(delta.getOriginal().getLines().get(i).toString().indexOf(">")));
//					radiolistorig.add(i, radiolistorig.get(i).substring(0, radiolistorig.get(i).indexOf("<")));
		}
		for (int i = 0; i < delta.getRevised().size(); i++) {
			if (delta.getRevised().getLines().get(i).toString().trim().equals("<br/>")
					|| delta.getRevised().getLines().get(i).toString().trim().equals("<br>")) {
				// skip if br tag appears
			} else if (delta.getRevised().getLines().get(i).toString().trim().contains("<option")) {
				radiolistrev.add(delta.getRevised().getLines().get(i).toString());

			} else if (delta.getRevised().getLines().get(i).toString().trim().contains("\"radio\"")) {
				radiolistrev.add(delta.getRevised().getLines().get(i).toString());

			}

		}
		for (int i = 0; i < radiolistorig.size(); i++) {
			System.out.println("Yahan ORIG: " + radiolistorig.get(i));
		}
		for (int i = 0; i < radiolistrev.size(); i++) {
			System.out.println("Yahan REV: " + radiolistrev.get(i));
		}
		// check if size of original and revised arraylist is the same
		int firstradlistsize = radiolistorig.size();
		int secondradlistsize = radiolistrev.size();

		getArrayListsRadioValue(delta, radiolistorig, radiolistrev, extractedtypeorig, extractedtyperev,
				firstradlistsize, secondradlistsize);
	}

	private static void getArrayListsRadioValue(Delta delta, ArrayList<String> radiolistorig,
			ArrayList<String> radiolistrev, ArrayList<String> extractedtypeorig, ArrayList<String> extractedtyperev,
			int firstradlistsize, int secondradlistsize) {
		if (firstradlistsize == secondradlistsize) {
			// if arraylist size is the same
			for (int i = 0; i < firstradlistsize; i++) {

				// checking if both strings contain type
				if (radiolistorig.get(i).contains(" type") && radiolistorig.get(i).contains("radio")) {
					if (radiolistrev.get(i).contains(" type")) {
						// if revised also contains type
						extractedtypeorig.add(radiolistorig.get(i));
						extractedtypeorig.set(i,
								extractedtypeorig.get(i).substring(extractedtypeorig.get(i).indexOf(">") + 1).trim());
						extractedtyperev.add(radiolistrev.get(i));
						extractedtyperev.set(i,
								extractedtyperev.get(i).substring(extractedtyperev.get(i).indexOf(">") + 1).trim());
						extractedtyperev.set(i, extractedtyperev.get(i).replaceAll("<br>", "").trim());
						extractedtyperev.set(i, extractedtyperev.get(i).replaceAll("</br>", "").trim());
						extractedtypeorig.set(i, extractedtypeorig.get(i).replaceAll("<br>", "").trim());
						extractedtypeorig.set(i, extractedtypeorig.get(i).replaceAll("</br>", "").trim());

					} else {
						// if original contains type but revised doesnt
						extractedtypeorig.add(radiolistorig.get(i));
						extractedtypeorig.set(i,
								extractedtypeorig.get(i).substring(extractedtypeorig.get(i).indexOf(">") + 1).trim());
						extractedtypeorig.set(i, extractedtypeorig.get(i).replaceAll("<br>", "").trim());
						extractedtypeorig.set(i, extractedtypeorig.get(i).replaceAll("</br>", "").trim());
					}
				} else if (radiolistrev.get(i).contains(" type") && radiolistrev.get(i).contains("radio")) {
					if (radiolistorig.get(i).contains(" type")) {
						// if revised also contains type
						extractedtyperev.add(radiolistrev.get(i));
						extractedtyperev.set(i,
								extractedtyperev.get(i).substring(extractedtyperev.get(i).indexOf(">")));
						extractedtypeorig.add(radiolistorig.get(i));
						extractedtypeorig.set(i,
								extractedtypeorig.get(i).substring(extractedtypeorig.get(i).indexOf(">")));
						extractedtyperev.set(i, extractedtyperev.get(i).replaceAll("<br>", "").trim());
						extractedtyperev.set(i, extractedtyperev.get(i).replaceAll("</br>", "").trim());
						extractedtypeorig.set(i, extractedtypeorig.get(i).replaceAll("<br>", "").trim());
						extractedtypeorig.set(i, extractedtypeorig.get(i).replaceAll("</br>", "").trim());
					} else {
						System.out.println("ORA ORA ORA ORA ORAAAAAAAAAAAAA");
						// if original contains type but revised doesnt
						extractedtyperev.add(radiolistrev.get(i));

						extractedtyperev.set(i,
								extractedtyperev.get(i).substring(extractedtyperev.get(i).indexOf(">") + 1).trim());
						extractedtyperev.set(i, extractedtyperev.get(i).replaceAll("<br>", "").trim());
						extractedtyperev.set(i, extractedtyperev.get(i).replaceAll("</br>", "").trim());
						System.out.println(extractedtyperev.get(i));
					}
				} else if (!radiolistorig.get(i).contains(" type")) {
					// if revised contains type but original
					if (radiolistrev.get(i).contains(" type") && radiolistorig.get(i).contains("radio")) {
						extractedtyperev.add(delta.getRevised().getLines().get(i).toString()
								.substring(delta.getRevised().getLines().get(i).toString().indexOf(">") + 1));
						extractedtyperev.set(i,
								extractedtyperev.get(i).substring(0, extractedtyperev.get(i).indexOf("<") + 1).trim());
						extractedtyperev.set(i, extractedtyperev.get(i).replaceAll("<br>", "").trim());
						extractedtyperev.set(i, extractedtyperev.get(i).replaceAll("</br>", "").trim());
					} else {
						// if both dont, which isn't possible so this code is unreachable
					}
				}

			}
		} else {
			// if arraylist size is not the same size

			for (int i = 0; i < firstradlistsize; i++) {
				System.out.println(
						"FAKLJFHALGHASJKGHALKJGHALJGHASJKDGHLAJKDGHLAKJSDHGAKSJDHGLAKSDJHGLKAJHGJKASDGHASJDGH");
				if (radiolistorig.get(i).contains(" type") && radiolistorig.get(i).contains("radio")) {
					// if contains type
					extractedtypeorig.add(delta.getOriginal().getLines().get(i).toString()
							.substring(delta.getOriginal().getLines().get(i).toString().indexOf(">") + 1));
					System.out.println("oyeeeeeeeee " + extractedtypeorig.get(i));
					if (extractedtypeorig.get(i).contains("<")) {
						System.out.println("CONTAINS <<<<<<<<<<<<<<<");
						extractedtypeorig.set(i,
								extractedtypeorig.get(i).substring(0, extractedtypeorig.get(i).indexOf("<")));
					}

				} else {
					// if doesnt contain type
				}
			}
			for (int i = 0; i < secondradlistsize; i++) {
				if (radiolistrev.get(i).contains(" type") && radiolistrev.get(i).contains("radio")) {

					// if contains type
					extractedtyperev.add(delta.getRevised().getLines().get(i).toString()
							.substring(delta.getRevised().getLines().get(i).toString().indexOf(">") + 1));
					if (extractedtyperev.contains("<"))
						extractedtyperev.set(i,
								extractedtyperev.get(i).substring(0, extractedtyperev.get(i).indexOf("<")));
				} else {
					// if doesnt contain type
				}
			}

		}
	}

	private static void mandatoryCheck(Delta delta) {
		if (delta.getOriginal().toString().contains(" required")
				&& delta.getRevised().toString().contains(" required")) {
			System.out.println("Both the documents have \"mandatory\" fields");
			try {
				doc3.insertString(doc3.getLength(), "Both the documents have \"mandatory\" fields\n", null);
			} catch (BadLocationException e) {

				e.printStackTrace();
			}

		} else {
			mandatoryerrors++;
			try {
				doc3.insertString(doc3.getLength(), "Both the documents DO NOT have \"mandatory\" fields\n", null);
			} catch (BadLocationException e) {

				e.printStackTrace();
			}
		}
	}

	private static void minlengthCheck(Delta delta, String minatt) {
		if (delta.getOriginal().toString().contains(minatt) && delta.getRevised().toString().contains(minatt)) {
			String minorig = delta.getOriginal().getLines().toString()
					.substring(delta.getOriginal().getLines().toString().indexOf(" " + minatt)).replaceAll(" ", "");
			String minrev = delta.getRevised().getLines().toString()
					.substring(delta.getRevised().getLines().toString().indexOf(" " + minatt)).replaceAll(" ", "");
			String minorigtemp = "";
			String minrevtemp = "";

			int numquotesorig = 0;
			for (int i = 0; i <= 100; i++) {
				minorigtemp = minorigtemp + minorig.charAt(minorig.indexOf(minatt + "=") + i);
				if (minorig.charAt(minorig.indexOf(minatt + "=") + i) == '\"') {
					numquotesorig = numquotesorig + 1;
					if (numquotesorig == 2) {
						break;
					}
				}
			}

			numquotesorig = 0;

			int numquotesrev = 0;
			for (int i = 0; i <= 100; i++) {
				minrevtemp = minrevtemp + minrev.charAt(minrev.indexOf(minatt + "=") + i);

				if (minrev.charAt(minorig.indexOf(minatt + "=") + i) == '\"') {
					numquotesrev = numquotesrev + 1;
					if (numquotesrev == 2) {
						break;
					}
				}
			}
			numquotesrev = 0;
			if (!minorigtemp.equals(minrevtemp)) {
				lengtherrors++;
				try {
					doc3.insertString(doc3.getLength(),
							minorigtemp + " \"" + minatt + "\" is different from " + minrevtemp + "\n", null);
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
			} else if (minorigtemp.equals(minrevtemp)) {
				System.out.println("EEQUALLLSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
//				try {
//					doc3.insertString(doc3.getLength(), minorigtemp+" \""+minatt+"\" is unchanged "+minrevtemp+"\n", null);
//				} catch (BadLocationException e) {
//					
//					e.printStackTrace();
//				}
			}

		} else {
			if (delta.getOriginal().toString().contains(minatt)) {
				try {
					doc3.insertString(doc3.getLength(),
							"First Document contains \"" + minatt + "\" attribute but Second doesn't\n", null);
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
			} else {
				try {
					doc3.insertString(doc3.getLength(),
							"Second Document contains \"" + minatt + "\" attribute but First doesn't\n", null);
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
			}
		}

	}

	private static ArrayList<String> fileToLines1(String filename) {

		ArrayList<String> lines = new ArrayList<>();
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			while ((line = in.readLine()) != null) {
				lines.add(line);
				try {
					doc1.insertString(doc1.getLength(), line + "\n", null);
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
				// textarea1.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	private static ArrayList<String> fileToLines2(String filename) {
		ArrayList<String> lines = new ArrayList<>();
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			while ((line = in.readLine()) != null) {
				lines.add(line);
				try {
					doc2.insertString(doc2.getLength(), line + "\n", null);
				} catch (BadLocationException e) {

					e.printStackTrace();
				}
//				textarea2.append(line);
//				textarea2.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	private static void checkTestCase(String filename) {
		boolean wontrun = false;
		InputStream is;
		ArrayList<String> listTestCases = new ArrayList<String>();
		ArrayList<String> listTestCasesRepaired = new ArrayList<String>();
		try {
			is = new FileInputStream(filename);

			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = "";

			line = buf.readLine();

			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line);

				line = buf.readLine();

			}
			String fileAsString = sb.toString();
			fileAsString = fileAsString.substring(fileAsString.indexOf("{") + 1, fileAsString.lastIndexOf("}"));
			System.out.println("Contents : " + fileAsString);
			int i = 0;
			while (true) {
				if (fileAsString.contains(";")) {
					listTestCases.add(fileAsString.substring(0, fileAsString.indexOf(";")));
					fileAsString = fileAsString.substring(fileAsString.indexOf(";") + 1);
					System.out.println("Contents : " + listTestCases.get(i));
					if (listTestCases.get(i).contains(".findElement")) {
						String tempvar = "";
						if (listTestCases.get(i).contains("By.id")) {
							tempvar = listTestCases.get(i).substring(listTestCases.get(i).indexOf("By.id") + 5).trim();
							tempvar = tempvar.substring(tempvar.indexOf("(") + 1, tempvar.indexOf(")"));
							for (int j = 0; j < similarywidgetidlist.size(); j++) {
								if (tempvar.trim().equals(similarywidgetidlist.get(j).oldValue.trim())
										&& !tempvar.trim().equals(similarywidgetidlist.get(j).newValue.trim())) {

									try {
										doc3.insertString(doc3.getLength(),
												"\nNot Successfull:  " + listTestCases.get(i)
														+ "           Old Value: ---        "
														+ similarywidgetidlist.get(j).oldValue.trim()
														+ "              New Value: ----          "
														+ similarywidgetidlist.get(j).newValue.trim(),
												null);
									} catch (BadLocationException e) {

										e.printStackTrace();
									}

									listTestCasesRepaired.add(
											listTestCases.get(i).replace(similarywidgetidlist.get(j).oldValue.trim(),
													similarywidgetidlist.get(j).newValue.trim()));
									wontrun = true;
									break;
								} else if ((j == similarywidgetidlist.size() - 1)
										&& (!tempvar.trim().equals(similarywidgetidlist.get(j).oldValue.trim()))) {

									try {
										doc3.insertString(doc3.getLength(), "\nSuccessfull: " + listTestCases.get(i),
												null);
									} catch (BadLocationException e) {

										e.printStackTrace();
									}
									listTestCasesRepaired.add(listTestCases.get(i));
								}
							}

							System.out.println("Tempvar: " + tempvar);
						} else if (listTestCases.get(i).contains("By.name")) {
							tempvar = listTestCases.get(i).substring(listTestCases.get(i).indexOf("By.name") + 7)
									.trim();
							tempvar = tempvar.substring(tempvar.indexOf("(") + 1, tempvar.indexOf(")"));
							for (int j = 0; j < similarywidgetnamelist.size(); j++) {
								if (tempvar.trim().equals(similarywidgetnamelist.get(j).oldValue.trim())
										&& !tempvar.trim().equals(similarywidgetnamelist.get(j).newValue.trim())) {
									try {
										doc3.insertString(doc3.getLength(),
												"\nNot Successfull:  " + listTestCases.get(i)
														+ "           Old Value: ---        "
														+ similarywidgetnamelist.get(j).oldValue.trim()
														+ "              New Value: ----          "
														+ similarywidgetnamelist.get(j).newValue.trim(),
												null);
									} catch (BadLocationException e) {

										e.printStackTrace();
									}

									listTestCasesRepaired.add(
											listTestCases.get(i).replace(similarywidgetnamelist.get(j).oldValue.trim(),
													similarywidgetnamelist.get(j).newValue.trim()));
									wontrun = true;
									break;
								} else if ((j == similarywidgetnamelist.size() - 1)
										&& (!tempvar.trim().equals(similarywidgetnamelist.get(j).oldValue.trim()))) {

									try {
										doc3.insertString(doc3.getLength(), "\nSuccessfull:  " + listTestCases.get(i),
												null);
									} catch (BadLocationException e) {

										e.printStackTrace();
									}

									listTestCasesRepaired.add(listTestCases.get(i));
								}
							}
							System.out.println("Tempvar: " + tempvar);
						} else if (listTestCases.get(i).contains("By.xpath")) {

							tempvar = listTestCases.get(i);
							String xpathtempvar = "";

							boolean editedbefore = false;

							if (tempvar.contains("id='")) {
								xpathtempvar = tempvar.substring(tempvar.indexOf("id='"));
								xpathtempvar = xpathtempvar.substring(0,
										xpathtempvar.indexOf("'", xpathtempvar.indexOf("'") + 1));
								xpathtempvar = "\"" + xpathtempvar.substring(xpathtempvar.indexOf("'") + 1).trim()
										+ "\"";

								System.out.println("XPATH TEMP VAR: " + xpathtempvar);

								for (int j = 0; j < idlist.size(); j++) {
									if (xpathtempvar.trim().equals(idlist.get(j).oldValue.trim())) {
										// System.out.println("contains");
										tempvar = tempvar.replace(idlist.get(j).oldValue.trim().replaceAll("\"", ""),
												idlist.get(j).newValue.trim().replaceAll("\"", ""));
										wontrun = true;
										System.out.println("YOYOYOYOYOYOYOYYOYO");
										break;
									} else if ((j == idlist.size() - 1)
											&& (!tempvar.trim().equals(idlist.get(j).oldValue.trim()))) {

									}
								}
								System.out.println("Tempvar: " + tempvar);
								editedbefore = true;
							}
							if (tempvar.contains("name='")) {
								xpathtempvar = tempvar.substring(tempvar.indexOf("name='"));
								xpathtempvar = xpathtempvar.substring(0,
										xpathtempvar.indexOf("'", xpathtempvar.indexOf("'") + 1));
								xpathtempvar = "\"" + xpathtempvar.substring(xpathtempvar.indexOf("'") + 1).trim()
										+ "\"";

								System.out.println("XPATH TEMP VAR: " + xpathtempvar);

								for (int j = 0; j < idlist.size(); j++) {
									if (xpathtempvar.trim().equals(namelist.get(j).oldValue.trim())) {
										// System.out.println("contains");
										tempvar = tempvar.replace(namelist.get(j).oldValue.trim().replaceAll("\"", ""),
												namelist.get(j).newValue.trim().replaceAll("\"", ""));
										wontrun = true;
										System.out.println("YOYOYOYOYOYOYOYYOYO");
										break;
									} else if ((j == namelist.size() - 1)
											&& (!tempvar.trim().equals(namelist.get(j).oldValue.trim()))) {

									}
								}
								System.out.println("Tempvar: " + tempvar);
							}

							if (tempvar.contains("input[") && tempvar.contains("sendKeys")) {
								xpathtempvar = tempvar.substring(tempvar.indexOf("sendKeys"));
								xpathtempvar = xpathtempvar.substring(xpathtempvar.indexOf("(") + 2,
										xpathtempvar.indexOf(")") - 1);
								System.out.println("XPATH: " + xpathtempvar);

								boolean existsinselect = false;
								boolean existsinradio = false;
								for (int j = 0; j < radiolistold.size(); j++) {

									for (int k = 0; k < radiolistold.get(j).getValue().size(); k++) {
										System.out.println("HEREEEEE" + selectlistold.get(j).getValue().get(k));

										if (xpathtempvar.equals(radiolistold.get(j).getValue().get(k))) {
											System.out.println("Hitttttt");
											for (int l = 0; l < selectlistnew.size(); l++) {
												for (int m = 0; m < radiolistnew.get(l).getValue().size(); m++) {
													if (xpathtempvar.equals(radiolistnew.get(i).getValue().get(i))) {
														existsinradio = true;
														break;
													}

												}
												if (existsinradio == true) {
													break;
												}
											}
											if (existsinradio == false) {
												for (int l = 0; l < selectlistnew.size(); l++) {
													for (int m = 0; m < selectlistnew.get(l).getValue().size(); m++) {
														if (xpathtempvar
																.equals(selectlistnew.get(l).getValue().get(m))) {
															System.out.println("HITTTTTT INSIDE RADIO NEW");
															existsinselect = true;
															tempvar = tempvar.replace("input[", "select[");
															tempvar = tempvar.replace(
																	String.valueOf(tempvar
																			.charAt(tempvar.indexOf("select[") + 6)),
																	String.valueOf(m));
															tempvar = tempvar.replace("sendKeys(",
																	"selectByVisibleText(");
															tempvar = tempvar.replace("\"label=" + xpathtempvar + "\"",
																	"\"" + xpathtempvar + "\"");
															System.out.println("TEMPVARRR: " + tempvar);
															wontrun = true;
															break;
														}
													}
													if (existsinselect == true) {
														break;
													}
												}
											}
											if (existsinselect == true || existsinradio == true) {
												break;
											}

										}
									}
									if (existsinselect == true || existsinradio == true) {
										break;
									}
								}

								listTestCasesRepaired.add(tempvar);

							}
							if (tempvar.contains("select") && tempvar.contains("selectByVisibleText")) {
								xpathtempvar = tempvar.substring(tempvar.indexOf("selectByVisibleText"));
								xpathtempvar = xpathtempvar.substring(xpathtempvar.indexOf("(") + 2,
										xpathtempvar.indexOf(")") - 1);
								System.out.println("XPATH: " + xpathtempvar);

								boolean existsinselect = false;
								boolean existsinradio = false;
								for (int j = 0; j < selectlistold.size(); j++) {

									for (int k = 0; k < selectlistold.get(j).getValue().size(); k++) {
										System.out.println("HEREEEEE" + selectlistold.get(j).getValue().get(k));

										if (xpathtempvar.equals(selectlistold.get(j).getValue().get(k))) {
											System.out.println("Hitttttt");
											for (int l = 0; l < selectlistnew.size(); l++) {
												for (int m = 0; m < selectlistnew.get(l).getValue().size(); m++) {
													if (xpathtempvar.equals(selectlistnew.get(i).getValue().get(i))) {
														existsinselect = true;
														break;
													}

												}
												if (existsinselect == true) {
													break;
												}
											}
											if (existsinselect == false) {
												for (int l = 0; l < radiolistnew.size(); l++) {
													for (int m = 0; m < radiolistnew.get(l).getValue().size(); m++) {
														if (xpathtempvar
																.equals(radiolistnew.get(l).getValue().get(m))) {
															System.out.println("HITTTTTT INSIDE RADIO NEW");
															existsinradio = true;
															tempvar = tempvar.replace("select[", "input[");
															tempvar = tempvar.replace(
																	String.valueOf(tempvar
																			.charAt(tempvar.indexOf("input[") + 6)),
																	String.valueOf(m));
															tempvar = tempvar.replace("selectByVisibleText(",
																	"sendKeys(");
															tempvar = tempvar.replace("\"" + xpathtempvar + "\"",
																	"\"label=" + xpathtempvar + "\"");
															System.out.println("TEMPVARRR: " + tempvar);
															wontrun = true;
															break;
														}
													}
													if (existsinradio == true) {
														break;
													}
												}
											}
											if (existsinselect == true || existsinradio == true) {
												break;
											}

										}
									}
									if (existsinselect == true || existsinradio == true) {
										break;
									}
								}

								listTestCasesRepaired.add(tempvar);
							}
						} else if (listTestCases.get(i).contains("By.css")) {
							tempvar = listTestCases.get(i).substring(listTestCases.get(i).indexOf("By.css") + 6).trim();
							tempvar = tempvar.substring(tempvar.indexOf("(") + 1, tempvar.indexOf(")"));
							System.out.println("Tempvar: " + tempvar);
						} else {
							// line doesn't compare element by By.
						}
					} else {
						listTestCasesRepaired.add(listTestCases.get(i));
					}
					i++;
				} else {
					break;
				}

			}
			for (int j = 0; j < listTestCasesRepaired.size(); j++) {
				System.out.println(listTestCasesRepaired.get(j));
			}

			try {
				if (wontrun == true) {
					System.out.println("\n\nThe test WONT run on modified version");
					doc3.insertString(doc3.getLength(), "\n\nThe test " + filename + " WONT run on modified version\n",
							keyWordRedWhite);
				} else {
					System.out.println("\n\nThe test WILL run on modified version");
					doc3.insertString(doc3.getLength(), "\n\nThe test " + filename + " WILL run on modified version\n",
							keyWordGreen);
				}

			} catch (BadLocationException e) {

				e.printStackTrace();
			}

			for (int j = 0; j < selectlistold.size(); j++) {
				for (int k = 0; k < selectlistold.get(j).getValue().size(); k++) {
					System.out.println("Select List OLD: " + selectlistold.get(j).getValue().get(k));
				}

			}
			for (int j = 0; j < selectlistnew.size(); j++) {
				for (int k = 0; k < selectlistnew.get(j).getValue().size(); k++) {
					System.out.println("Select List NEW: " + selectlistnew.get(j).getValue().get(k));
				}
			}
			for (int j = 0; j < radiolistold.size(); j++) {
				for (int k = 0; k < radiolistold.get(j).getValue().size(); k++) {
					System.out.println("Radio List OLD: " + radiolistold.get(j).getValue().get(k));
				}
			}
			for (int j = 0; j < radiolistnew.size(); j++) {
				for (int k = 0; k < radiolistnew.get(j).getValue().size(); k++) {
					System.out.println("Radio List NEW: " + radiolistnew.get(j).getValue().get(k));
				}
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}