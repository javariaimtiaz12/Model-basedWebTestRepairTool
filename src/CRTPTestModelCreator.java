

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * A class that creates CRTP models (apply profile, apply stereotypes, set stereotype values)
 * 
 * @author Javaria Imtiaz
 * @version 1.0
 */




public class CRTPTestModelCreator {
public static void main(String[] args) throws IOException {
	ModelLoader umlModel = new ModelLoader();

	ActivityTestModelCreator ATMC= new ActivityTestModelCreator();
	UMLActivityDiagramFactory umlElem = new UMLActivityDiagramFactory();
	BufferedReader reader = new BufferedReader(new FileReader("TestCases/map.txt"));
    String text = reader .readLine();
    String[] strAray = text.split("=");
    String  test= strAray[0];
    String testName = strAray[1];
			String relPath = null;
			String path= "TestModelwithProfileApplied/"+testName+"-profile"+".uml";

			
		
			
			//*********Important Step is register URIs************
			URI baseUri = 
			URI.createURI("jar:file:"+relPath+"/EMF_Lib/org.eclipse.uml2.uml.resources-3.1.0.v201005031530.jar!/");
			URIConverter.URI_MAP.put(URI.createURI( UMLResource.LIBRARIES_PATHMAP ), 
			baseUri.appendSegment( "libraries" ).appendSegment( "" ));
			URIConverter.URI_MAP.put(URI.createURI( UMLResource.METAMODELS_PATHMAP 
			), baseUri.appendSegment( "metamodels" ).appendSegment( "" ));
			URIConverter.URI_MAP.put(URI.createURI( UMLResource.PROFILES_PATHMAP ), 
			baseUri.appendSegment( "profiles" ).appendSegment( "" ));
			
			

			//*********Start Modeling************
			UMLPackage.eINSTANCE.eClass();
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
			m.put("http://www.eclipse.org/uml2/2.0.0/UML", UMLPackage.eINSTANCE);
			ResourceSet resourceSet = new ResourceSetImpl();
			
			//STEP=01 (provide path of automatically generated activity diagram
				Resource resource = resourceSet.getResource(URI.createURI(path), true); //Receive activity diagram (See ActivityTestModelCreator)
			  
			//STEP=02 (start with package
				org.eclipse.uml2.uml.Package packageableElement = (org.eclipse.uml2.uml.Package) resource.getContents().get(0);
			    
			//STEP=03 (register profile which is available in activity diagram
				Profile p = (Profile) packageableElement.getAllAppliedProfiles().get(0);
			    System.out.println(p);
	
			//STEP=04 (extract main activity from activity diagram
			    Activity act= (Activity) packageableElement.getOwnedMembers().get(0);

			//STEP=05 (check whether stereotypes are applied on activity nodes
				for(ActivityNode node : act.getNodes()){
					System.out.println(node.getName()+" : "+node.getAppliedStereotypes().size());    //print number of stereotypes applied on the node
				}
				
	   //STEP=06 (apply stereotype on specific activity node)
				
			    Stereotype stereo = p.getOwnedStereotype("locator1");
			    Stereotype st2 = p.getOwnedStereotype("TestCommand1");
				 
			   
			    BufferedReader br = new BufferedReader(new FileReader("TestCases/TestInfo.txt"));  //Extract information from 
				String line="";	
				 String locator="";
				 String value ="";

				 while ((line = br.readLine()) != null)
   	                   {

				         for(ActivityNode node : act.getNodes())
				            {
						     String[] aux = line.split(",");

							    if (node.getName().contains("action") )
							       {		
							            node.applyStereotype(stereo);
				                          node.setValue(stereo, stereo.getOwnedAttributes().get(0).getName(), aux[0].trim());  //set values for locat
				                          node.setValue(stereo, stereo.getOwnedAttributes().get(1).getName(), aux[1].trim().replace("\"", ""));  //set values for locat

							       }
								    else 
								    {
								    	node.applyStereotype(st2);
								    	if (line.contains("assert"))
								        {
								        	String[] auxx= line.split(",");
								        	node.setValue(st2, st2.getOwnedAttributes().get(0).getName(), aux[0].trim());  //set values for locat
					                        node.setValue(st2, st2.getOwnedAttributes().get(1).getName(), aux[1].trim().replace("\"", ""));  //set values for locat
								        }
								    		
								    }
							 
							    line = br.readLine();

							  }
				         
   	                   }
				 
				 
			               
				
		   //STEP=07 (check whether stereotypes are applied on activity nodes
				for(ActivityNode node : act.getNodes()){
					System.out.println(node.getName()+" : "+node.getAppliedStereotypes().size());    //print number of stereotypes applied on the node
				}
			
				
				
				
			resource.getContents().add(packageableElement);
			
			resource.save(null);
				
			}

		
public String name;


public void setKey(String name)
{
	this.name=name;
}

public void setValue(String name)
{
	this.name=name;
}










			}