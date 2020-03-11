import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;

//TODO: Auto-generated Javadoc
/**
* A Class that finds the XPath of Web elements
* profile.
*
* @author Javaria imtiaz
*/


public class XpathParser {

	static ArrayList<String> listofChilds = new ArrayList<String>();

	private static String getXPath(Node root) {
		Node current = root;
		String output = "";
		while (current.getParentNode() != null) {
			Node parent = current.getParentNode();
			if (parent != null && parent.getChildNodes().getLength() > 1) {
				int nthChild = 1;
				Node siblingSearch = current;
				while ((siblingSearch = siblingSearch.getPreviousSibling()) != null) {
					// only count siblings of same type
					if (siblingSearch.getNodeName().equals(current.getNodeName())) {
						nthChild++;
					}
				}
				output = "/" + current.getNodeName() + "[" + nthChild + "]" + output;
			} else {
				output = "/" + current.getNodeName() + output;
			}
			current = current.getParentNode();
		}
		System.out.println("Path is:" + output);
		return output;
	}

	public static String xpathFinderFromFile(String attribute, String attribute_text, File file) {

		Document doc = null;
		try {
			doc = Jsoup.parse(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements elements = doc.body().select("*");
		ArrayList all = new ArrayList();
		for (Element element : elements) {

			Elements element1;
			element1 = element.getElementsByAttributeValueMatching(attribute, attribute_text.replace("\"", ""));
			for (Element element2 : element1) {

				StringBuilder path = new StringBuilder(element2.nodeName());
				String value = element2.ownText();
				Elements p_el = element2.parents();
				String node = "";
				int count = 0;
				for (Element el : p_el) {
					path.insert(0, el.nodeName() + '/');
				}
				all.add(path + "[text()='" + value + "']");
				if (value.isEmpty()) {
					return path + "[@" + attribute + "='" + attribute_text.replace("\"", "") + "']";
				} else {
					return path + "[text()='" + value + "']";
				}

			}

		}

		return "";

	}

	public static ArrayList<String> getPageElements(String content) {
		listofChilds = new ArrayList<String>();
		System.out.println("----------------------------");
		String data = content;
		ArrayList<String> childList = new ArrayList<String>();
		try {
			Document document = Jsoup.parse(content);
			Elements bodyElements = document.body().select("body");
			Element oneElement = bodyElements.first();
			List<org.jsoup.nodes.Node> childNodes = oneElement.childNodes();
			for (int i = 0; i < childNodes.size(); i++) {
				org.jsoup.nodes.Node node = childNodes.get(i);
				calculateNodeChilds(node);

			}

			for (int i = 0; i < listofChilds.size(); i++) {

				System.out.println("");
				System.out.println("" + listofChilds.get(i));
				System.out.println("");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return listofChilds;

	}

	public static ArrayList<String> getSpecificElement(String content, String element) {
		ArrayList<String> elementList = new ArrayList<String>();
		System.out.println("----------------------------");
		String data = content;
		ArrayList<String> childList = new ArrayList<String>();
		try {
			Document document = Jsoup.parse(content);
			if (element.equals("radio")) {
				Elements bodyElements = document.body().select("input");
				Element oneElement = bodyElements.first();
				List<org.jsoup.nodes.Node> childNodes = oneElement.childNodes();
				for (int i = 0; i < bodyElements.size(); i++) {
					// org.jsoup.nodes.Node node = childNodes.get(i);
					// calculateNodeChilds(node);
					if (bodyElements.get(i).toString().contains("radio")) {
						elementList.add(bodyElements.get(i).toString());
					}

				}
			} else {
				Elements bodyElements = document.body().select(element);
				Element oneElement = bodyElements.first();
				List<org.jsoup.nodes.Node> childNodes = oneElement.childNodes();
				for (int i = 0; i < bodyElements.size(); i++) {
					// org.jsoup.nodes.Node node = childNodes.get(i);
					// calculateNodeChilds(node);
					elementList.add(bodyElements.get(i).toString());

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return elementList;

	}

	public static String getSelectElement(String content, String attribute) {
		String elementList = "";
		System.out.println("----------------------------");
		String data = content;
		ArrayList<String> childList = new ArrayList<String>();
		try {
			Document document = Jsoup.parse(content);
			Elements bodyElements = document.body().select("select");
			Element oneElement = bodyElements.first();
			List<org.jsoup.nodes.Node> childNodes = oneElement.childNodes();
			for (int i = 0; i < bodyElements.size(); i++) {
				// org.jsoup.nodes.Node node = childNodes.get(i);
				// calculateNodeChilds(node);
				if (bodyElements.get(i).toString().contains(attribute)) {
					elementList = bodyElements.get(i).toString();
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return elementList;

	}

	public static ArrayList<String> calculateNodeChilds(org.jsoup.nodes.Node node) {

		List<org.jsoup.nodes.Node> childs = node.childNodes();
		if ((childs != null && childs.size() > 0)) {

			for (int j = 0; j < childs.size(); j++) {
				if (childs.get(j).toString().startsWith("<div") || childs.get(j).toString().startsWith("<form")
						|| childs.get(j).toString().startsWith("<li") || childs.get(j).toString().startsWith("<ul")
						|| childs.get(j).toString().startsWith("<table")
						|| childs.get(j).toString().startsWith("<datalist")
						|| childs.get(j).toString().startsWith("<select")
						|| childs.get(j).toString().startsWith("<label")
						|| childs.get(j).toString().startsWith("<thead") || childs.get(j).toString().startsWith("<tr")
						|| childs.get(j).toString().startsWith("<thead") | childs.get(j).toString().startsWith("<tbody")
						|| childs.get(j).toString().startsWith("<fieldset")
						|| childs.get(j).toString().startsWith("<tbody")) {
					org.jsoup.nodes.Node child = childs.get(j);

					if (childs.get(j).toString().startsWith("<datalist")
							|| childs.get(j).toString().startsWith("<select")) {
						listofChilds.add(childs.get(j).toString());
					}
					calculateNodeChilds(childs.get(j));
				} else {
					if (!childs.get(j).toString().matches("") && !childs.get(j).toString().contains("<br")
							&& childs.get(j).toString().length() > 5 && childs.get(j).toString().startsWith("<")) {
						if (childs.get(j).toString().startsWith("<a") || childs.get(j).toString().startsWith("<button")
								|| childs.get(j).toString().startsWith("<input")
								|| childs.get(j).toString().startsWith("<h")
								|| childs.get(j).toString().startsWith("<img")
								|| childs.get(j).toString().startsWith("<textarea")
								|| childs.get(j).toString().startsWith("<select")
								|| childs.get(j).toString().startsWith("<option")
								|| childs.get(j).toString().startsWith("<p")
								|| childs.get(j).toString().startsWith("<label")
								|| childs.get(j).toString().startsWith("<legend"))
							listofChilds.add(childs.get(j).toString());
					}

				}
			}

		} else {
			if (!node.toString().matches("") && !node.toString().contains("<br") && node.toString().length() > 5
					&& node.toString().startsWith("<")) {
				if (!node.toString().matches("") && !node.toString().contains("<br") && node.toString().length() > 5
						&& node.toString().startsWith("<")) {
					if (node.toString().startsWith("<a") || node.toString().startsWith("<button")
							|| node.toString().startsWith("<input") || node.toString().startsWith("<h")
							|| node.toString().startsWith("<img") || node.toString().startsWith("<textarea")
							|| node.toString().startsWith("<select") || node.toString().startsWith("<option")
							|| node.toString().startsWith("<p") || node.toString().startsWith("<label")
							|| node.toString().startsWith("<legend"))
						listofChilds.add(node.toString());
				}
			}
		}

		return listofChilds;
	}

	public static String getXpath(String ele) {
		String str = ele;
		String[] listString = null;
		if (str.contains("xpath"))
			listString = str.split("xpath:");
		else if (str.contains("id"))
			listString = str.split("id=");
		else if (str.contains("name"))
			listString = str.split("name=");
		else if (str.contains("class"))
			listString = str.split("class=");
		else if (str.contains("href"))
			listString = str.split("href=");
		else if (str.contains("type"))
			listString = str.split("type=");
		try {
			String last = listString[1].trim();
			return last.substring(0, last.length() - 1);
		} catch (Exception e) {
			return null;
		}
	}

	private static void test(Node node, Document doc) {

		String expression = null;
		try {
			expression = getXPath(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Node result = null;
		try

		{
			result = (Node) XPathFactory.newInstance().newXPath().compile(expression).evaluate(doc,
					XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result == node) {
			System.out.println("Test OK: " + expression);
		} else {
			System.out.println("Test Fail: " + expression);
		}
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			test(node.getChildNodes().item(i), doc);
		}
	}

}
