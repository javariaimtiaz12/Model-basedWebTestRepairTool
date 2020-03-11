

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.mapping.ecore2xml.Ecore2XMLPackage;
import org.eclipse.emf.mapping.ecore2xml.util.Ecore2XMLResource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UML212UMLResource;
import org.eclipse.uml2.uml.resource.UML22UMLResource;
import org.eclipse.uml2.uml.resource.UMLResource;

////web
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.xmi.impl.RootXMLContentHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLContentHandlerImpl;
//import org.eclipse.uml2.types.TypesPackage;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UMLPlugin;
//import org.eclipse.uml2.uml.profile.standard.StandardPackage;
import org.eclipse.uml2.uml.resource.CMOF2UMLResource;
import org.eclipse.uml2.uml.resource.UML212UMLResource;
import org.eclipse.uml2.uml.resource.UML22UMLResource;
//import org.eclipse.uml2.uml.resource.UML302UMLResource;
//import org.eclipse.uml2.uml.resource.UML402UMLResource;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resource.XMI2UMLResource;
//import org.eclipse.uml2.uml.resources.ResourcesPlugin;
import org.eclipse.uml2.uml.util.UMLUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class UMLReader reads the UML file.
 *
 * @author javaria imtiaz
 */
public class UMLReader {

	/** The Constant RESOURCE_SET describes the resources set to load UML file. */
	protected static final ResourceSet RESOURCE_SET = new ResourceSetImpl();

	/**
	 * Registers the resources set and build base to load UML file.
	 */
	protected static void registerResourceFactories() {
		Map<String, Object> extensionFactoryMap = Resource.Factory.Registry.INSTANCE
				.getExtensionToFactoryMap();
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(Ecore2XMLResource.FILE_EXTENSION,
				Ecore2XMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(UML22UMLResource.FILE_EXTENSION,
				UML22UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION,UML22UMLResource.Factory.INSTANCE);
		/*extensionFactoryMap.put(UMLResource.FILE_EXTENSION,UML22UMLResource.Factory.INSTANCE);*/
		extensionFactoryMap.put(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI,
				UML22UMLResource.Factory.INSTANCE);
	}

	/**
	 * Registers packages into resources set.
	 *
	 * @param resourceSet the resource set
	 */
	protected static void registerPackages(ResourceSet resourceSet) {
		Map<String, Object> packageRegistry = resourceSet.getPackageRegistry();
		packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		packageRegistry.put(Ecore2XMLPackage.eNS_URI,
				Ecore2XMLPackage.eINSTANCE);
		packageRegistry.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		packageRegistry.put(UML212UMLResource.UML_METAMODEL_NS_URI,
				UMLPackage.eINSTANCE);
		packageRegistry.put("http://www.eclipse.org/uml2/2.0.0/UML",
				UMLPackage.eINSTANCE);
		
		///// web
//		packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);

		/*packageRegistry.put(UML2_TYPES_PACKAGE_4_0_NS_URI,
			TypesPackage.eINSTANCE);

		packageRegistry.put(TypesPackage.eNS_URI, TypesPackage.eINSTANCE);

		packageRegistry.put(UML2_UML_PACKAGE_2_0_NS_URI, UMLPackage.eINSTANCE);*/

//		packageRegistry.put(UML212UMLResource.UML_METAMODEL_NS_URI,
//			UMLPackage.eINSTANCE);

		/*packageRegistry.put(UML302UMLResource.UML_METAMODEL_NS_URI,
			UMLPackage.eINSTANCE);

		packageRegistry.put(UML402UMLResource.UML_METAMODEL_NS_URI,
			UMLPackage.eINSTANCE);*/

//		packageRegistry.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);

//		packageRegistry.put(UML212UMLResource.STANDARD_PROFILE_NS_URI,
//			StandardPackage.eINSTANCE);
//
//		packageRegistry.put(UML402UMLResource.STANDARD_L2_PROFILE_NS_URI,
//			StandardPackage.eINSTANCE);
//		packageRegistry.put(UML402UMLResource.STANDARD_L3_PROFILE_NS_URI,
//			StandardPackage.eINSTANCE);
//
//		packageRegistry.put(StandardPackage.eNS_URI, StandardPackage.eINSTANCE);
	}

	/**
	 * Loads the UML file and returns the instance of UML model.
	 *
	 * @param uri the uri
	 * @return the model
	 */
	public Model loadModel(String uri) {
		URI model = URI.createURI(uri);
		String transDir = "EMF_Lib/";
		registerPackages(RESOURCE_SET);
		registerResourceFactories();
		
		Map uriMap = RESOURCE_SET.getURIConverter().getURIMap();
		uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), model.appendSegment("libraries").appendSegment(""));
		uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), model.appendSegment("metamodels").appendSegment(""));
		uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), model.appendSegment("profiles").appendSegment(""));
		
		URIConverter.URI_MAP.put(
				URI.createURI("platform:/plugin/org.eclipse.uml2.uml/"),
				URI.createURI("jar:file:" + transDir
						+ "org.eclipse.uml2.uml_3.2.0.v201105231350.jar!/"));

		Resource resource = null;
		Model _model = null;
		try {
			resource = RESOURCE_SET.getResource(model, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		_model = (Model) EcoreUtil.getObjectByType(resource.getContents(),
				UMLPackage.Literals.MODEL);
		
		org.eclipse.uml2.uml.Package pkg = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(resource.getContents(),
				UMLPackage.Literals.PACKAGE);
		return _model;
	}
	
	public org.eclipse.uml2.uml.Package loadPackage(String uri) {
		URI model = URI.createURI(uri);
		String transDir = "EMF_Lib/";
		registerPackages(RESOURCE_SET);
		registerResourceFactories();
		
		Map uriMap = RESOURCE_SET.getURIConverter().getURIMap();
		uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), model.appendSegment("libraries").appendSegment(""));
		uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), model.appendSegment("metamodels").appendSegment(""));
		uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), model.appendSegment("profiles").appendSegment(""));
		
		URIConverter.URI_MAP.put(
				URI.createURI("platform:/plugin/org.eclipse.uml2.uml/"),
				URI.createURI("jar:file:" + transDir
						+ "org.eclipse.uml2.uml_3.2.0.v201105231350.jar!/"));

		Resource resource = null;
		try {
			resource = RESOURCE_SET.getResource(model, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		org.eclipse.uml2.uml.Package pkg = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(resource.getContents(),
				UMLPackage.Literals.PACKAGE);
		return pkg;
	}


	/**
	 * Loads the UML profile and returns the instance of UML Profile.
	 *
	 * @param uri the uri
	 * @return the profile
	 */
	public Profile loadProfile(String uri) {
		URI model = URI.createURI(uri);
		String transDir = "EMF_Lib/";
		registerPackages(RESOURCE_SET);
		registerResourceFactories();
		
		Map uriMap = RESOURCE_SET.getURIConverter().getURIMap();
		URI UMLuri = URI.createURI("jar:file:"+ transDir
				+ "org.eclipse.uml2.uml.resources_3.1.1.v201008191505.jar!/"); // for example
		
		uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), UMLuri.appendSegment("libraries").appendSegment(""));
		uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), UMLuri.appendSegment("metamodels").appendSegment(""));
		uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), UMLuri.appendSegment("profiles").appendSegment(""));
		
//		uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), model.appendSegment("libraries").appendSegment(""));
//		uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), model.appendSegment("metamodels").appendSegment(""));
//		uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), model.appendSegment("profiles").appendSegment(""));

		URIConverter.URI_MAP.put(
				URI.createURI("platform:/plugin/org.eclipse.uml2.uml/"),
				URI.createURI("jar:file:" + transDir
						+ "org.eclipse.uml2.uml_3.1.2.v201010261927.jar!/"));

		Resource resource = null;
		try {
			resource = RESOURCE_SET.getResource(model, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Profile _profile = (Profile) EcoreUtil.getObjectByType(
				resource.getContents(), UMLPackage.Literals.PROFILE);
		return _profile;
	}
}