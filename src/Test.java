import java.io.File;
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
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;



public class Test {
public static void main(String[] args) {
			String relPath = null;
			try {
				relPath = new File(".").getCanonicalPath();
				System.out.println(relPath);
		
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
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
				Resource resource = resourceSet.getResource(URI.createURI("Blank Package.uml"), true); //Receive activity diagram
			  
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
				ActivityNode node1= (ActivityNode) act.getNodes().get(1);
			    Stereotype st = p.getOwnedStereotype("locator1");
			    System.out.println(st.getName());
			    node1.applyStereotype(st);

		   //STEP=07 (check whether stereotypes are applied on activity nodes
				for(ActivityNode node : act.getNodes()){
					System.out.println(node.getName()+" : "+node.getAppliedStereotypes().size());    //print number of stereotypes applied on the node
				}
	      

		
			
				
			}

		












			}