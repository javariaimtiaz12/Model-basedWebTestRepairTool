import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityNode;

/**
 * A class that transform CRTP models into Selenium test cases
 * 
 * @author Javaria Imtiaz
 * @version 1.0
 */


public class CRTP_Model_Transformer {

	public static void main(String[] args) throws IOException {
		ModelLoader umlModel = new ModelLoader();
		BufferedReader reader = new BufferedReader(new FileReader("TestCases/map.txt"));
	    String text = reader .readLine();
	    String[] strAray = text.split("=");
	    String  test= strAray[0];
	    String testName = strAray[1];


	String umlFilePath= "CRTPmodels/Blank Package.uml"; //read UML activity diagram .uml file
		String uri = null;
		try {
			uri = umlModel.getFileURI(umlFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object objModel = umlModel.loadModel(uri);
		Model sourceModel;
		EList<PackageableElement> sourcePackagedElements = null;
		if (objModel instanceof Model) {
			sourceModel = (Model) objModel;
			sourcePackagedElements = sourceModel.getPackagedElements();
		} else if (objModel instanceof Package) {
			Package sourcePackage = (Package) objModel;
			sourcePackagedElements = sourcePackage.getPackagedElements();
		}

		for (PackageableElement element : sourcePackagedElements){
			//for nested package
			if(element.eClass() == UMLPackage.Literals.PACKAGE){
				org.eclipse.uml2.uml.Package nestedPackage = (org.eclipse.uml2.uml.Package) element;
				EList<PackageableElement> nestedPackagedElements = nestedPackage.getPackagedElements();
				for (PackageableElement nestedElement : nestedPackagedElements){
					printModelDetails(nestedElement);
				}
			}
			else
				printModelDetails(element);  //return packagable elements

		}
	}

	private static void printModelDetails(PackageableElement element) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader("TestCases/map.txt"));
	    String text = reader .readLine();
	    String[] strAray = text.split("=");
	    String  test= strAray[0];
	    String testName = strAray[1];
	
		BufferedWriter writer = new BufferedWriter (new FileWriter ("Generated_Tool_Indenpendent_TestCases/"+testName+".java")); 

		if (element.eClass() == UMLPackage.Literals.ACTIVITY)    //if the packagable element is activity 
		{
			Activity nod= (Activity)element;
			System.out.println(nod.getAppliedStereotypes().size());
		
			//get activity name
			 String ActivityName = nod.getName();
			 System.out.println(ActivityName);
			//get nodes
			EList<ActivityNode> nodes	=nod.getNodes(); // get all activity nodes
			if(!nodes.isEmpty()){
				for (ActivityNode attr: nodes)
				{
					System.out.println(attr.getName()+" : "+attr.getAppliedStereotypes().size());    //print number of stereotypes applied on the node
					EList<Property> attributes = stereotype.getOwnedAttributes();
					for (Property attribute : attributes) {
						if (!attribute.getLabel().equals("key")
								&& (!attribute.getLabel().equals("base_Class"))
								&& (!attribute.getLabel().equals("value"))) {
							StereotypeAttribute sa = new StereotypeAttribute(
									attribute.getLabel(), this.getType(attribute),
									attribute.getVisibility().getLiteral());
							if(ss != null)
								ss.addAttributes(sa);
							// System.out.println(sa.toString());
						} else {
							try {
								ss = new StereotypeStructure(stereotype.getLabel(),
										this.getType(attribute));
							} catch (Exception e) {
								ss = new StereotypeStructure(stereotype.getLabel(), null);
							}
						}
					}
							
				}
			}
			
			writer.close();
		}
		
	}
	
	/**
	 * Gets the type of the attribute.
	 *
	 * @param attribute the attribute
	 * @return the type
	 */
	private String getType(Property attribute){
		String temp = attribute.getType().toString();
		String[] type = temp.split("#");
		if (type != null && type.length > 1){
			temp = type[1];
			int indexOf = temp.indexOf(")");
			temp = temp.substring(0,indexOf);
			//System.out.println(temp);
			return temp;
		}
		else
			return attribute.getType().getLabel();
	}
}
