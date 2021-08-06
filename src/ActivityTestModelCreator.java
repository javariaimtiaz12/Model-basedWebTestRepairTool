

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.lang.model.element.Element;
import javax.swing.UIDefaults.ActiveValue;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.ActivityImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Constraint;
//import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.StringExpression;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;
import java.io.FileReader;
import java.io.FileWriter;  

/**
 * A class that creates UML activity Test Models.
 * 
 * @author Javaria Imtiaz
 * @version 1.0
 */

public class ActivityTestModelCreator {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static
	UMLReader  md= new UMLReader();
	TestParser testParse= new TestParser();
	static SPlitFUn sf= new SPlitFUn();
	public static void main(String[] args) throws IOException {
	TestParser.parseTestCases();
	createRequiredActivity();
	applyProfile();
		sf.splitTest();
	}

	public String name;
	public String value;
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setValue(String value) {
		this.value=value;
	}
	
	

	
		
		
	//*********Create Activity Test model************

	
	public static void createRequiredActivity() throws IOException {
		
		  final AtomicInteger count = new AtomicInteger(0); 

	    
        BufferedReader br = new BufferedReader(new FileReader("TestCases/map.txt"));
          String lineRead;
          String text = br .readLine();
          String[] strArray = text.split("=");
          String test= strArray[0]; 
 
          String testName = strArray[1];
       ///   System.out.println(testName);
          UMLActivityDiagramFactory umlElem = new UMLActivityDiagramFactory();
  	      org.eclipse.uml2.uml.Package pkg = umlElem.createPackage("Demo Package");
  	      Activity act= (Activity) umlElem.createActivityDia(pkg, testName);  
          
          
			while ((lineRead = br.readLine()) != null) 
			{

				if (lineRead.contains("Assert"))
					 {
						 umlElem.createOpaqueNode(pkg, "assert");

					 }
				 if (lineRead.contains("locator"))
					 {
						 umlElem.createOpaqueNode(pkg, "action"+count.incrementAndGet());

					 }
				 if (lineRead.contains("wait"))
				 {
					 umlElem.createOpaqueNode(pkg, "wait"+count.incrementAndGet());

				 }
			
	         	}
	    
		saveModel("NewProfile", pkg, testName);
	    br.close();
			
	}
	
	
	
	
	//*********Apply Profile on Activity Test model************

	
	public static void applyProfile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("TestCases/map.txt"));
        String lineRead;
        String text = br .readLine();
        String[] strArray = text.split("=");
        String  test= strArray[0];
        String testName = strArray[1];
        System.out.println(testName);
		UMLActivityDiagramFactory umlElem = new UMLActivityDiagramFactory();
		String umlActivityFilePath = "NewProfile/"+testName+".uml";
		org.eclipse.uml2.uml.Package pkg = umlElem.loadPackage(umlActivityFilePath);
		Activity act= (Activity) pkg.getPackagedElement("Login");
		String umlFilePath = "CRTP/Profile.profile.uml";   //location of profile
		Profile testProfile= umlElem.loadApplyProfile(umlFilePath, pkg);	
		
		saveModel("TestModelwithProfileApplied", pkg, testName+"-profile");
		System.out.println(" success ");	
	}
	

	

	public static void save(org.eclipse.uml2.uml.Package package_, URI uri) {
		ResourceSet RESOURCE_SET;
		String transDir = "EMF_Lib/";
		RESOURCE_SET = new ResourceSetImpl();
		String JAR_FILE_ECLIPSE_UML2_UML_RESOURCES = "jar:file:" + transDir
				+ "org.eclipse.uml2.uml.resources-3.1.0.v201005031530.jar!/";
		File test = new File(JAR_FILE_ECLIPSE_UML2_UML_RESOURCES.substring(9,
				JAR_FILE_ECLIPSE_UML2_UML_RESOURCES.length() - 2))
				.getAbsoluteFile();
		if (!test.exists()) {
			throw new NullPointerException(
					"JAR_FILE_ECLIPSE_UML2_UML_RESOURCES PATH ERROR, "
							+ test.toString() + "not found!");
		}
		RESOURCE_SET.getPackageRegistry().put(UMLPackage.eNS_URI,
				UMLPackage.eINSTANCE);
		RESOURCE_SET.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
		Map<URI, URI> _resourceSetURIMap = RESOURCE_SET.getURIConverter()
				.getURIMap();
		URI model = URI.createURI(JAR_FILE_ECLIPSE_UML2_UML_RESOURCES);
		_resourceSetURIMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP),
				model.appendSegment("libraries").appendSegment(""));
		_resourceSetURIMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP),
				model.appendSegment("metamodels").appendSegment(""));
		_resourceSetURIMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP),
				model.appendSegment("profiles").appendSegment(""));		
		Resource resource = RESOURCE_SET.createResource(uri);
		EList<EObject> contents = resource.getContents();
		contents.add(package_);
		for (TreeIterator<Object> allContents = UMLUtil.getAllContents(
				package_, true, false); allContents.hasNext();) {
			EObject eObject = (EObject) allContents.next();
		}
		try {
			resource.save(null);
			System.out.println("Done.");
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
	
		public static EObject apply_Stereotype(NamedElement namedElement, Stereotype stereotype) {
		EObject eob = null;
		
		if(namedElement == null || stereotype == null)
			return null;

		eob = namedElement.applyStereotype(stereotype);
		if (eob != null) {
			System.out.printf("Stereotype '%s' applied to element '%s'.",
					stereotype.getQualifiedName(),
					namedElement.getQualifiedName());
			System.out.println();
		}
		return eob;
	}

	public static void saveModel(String save_Path, org.eclipse.uml2.uml.Package pkg_, String file_Name) {
		URI outputURI2 = URI
				.createFileURI(new File(save_Path).getAbsolutePath())
				.appendSegment(file_Name)
				.appendFileExtension(UMLResource.FILE_EXTENSION);
		System.out.printf("Saving the Model to %s", outputURI2.toFileString());
		System.out.println();
		save(pkg_, outputURI2);
	}


	
}
	
	