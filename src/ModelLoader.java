
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.mapping.ecore2xml.Ecore2XMLPackage;
import org.eclipse.emf.mapping.ecore2xml.util.Ecore2XMLResource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UML212UMLResource;
import org.eclipse.uml2.uml.resource.UML22UMLResource;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.core.runtime.*;

/**
 * A class that loads UML/Ecore model form specified path.
 * 
 * @author Javaria Imtiaz
 * @version 1.0
 */
public class ModelLoader {
	private final ResourceSet RESOURCE_SET;
	public ModelLoader() {
		RESOURCE_SET = new ResourceSetImpl();
	}
	
	/**
	 * A method that converts input path to uri and return uri.
	 * 
	 * @param path
	 * @return uri
	 * @throws Exception
	 */
	public String getFileURI(String path) throws Exception {
		File f = new File(path);
		String uri = f.toURI().toString();
		return uri;	
	}
	/**
	 * A method that loads UML/Ecore model from input URI.
	 * 
	 * @param uri:String
	 * @return model:Object
	 */
	public Object loadModel(String uri){
		URI modelUri = URI.createURI(uri);
		registerPackages(RESOURCE_SET);
		registerResourceFactories();
		String relPath = null;
		try {
			relPath = new File(".").getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		URIConverter.URI_MAP.put(URI.createURI("platform:/plugin/org.eclipse.uml2.uml/"), 
				URI.createURI("jar:file:"+relPath+"/EMF_Lib/org.eclipse.uml2.uml_3.1.2.v201010261927.jar!/"));
		Resource resource = null;
		try {	
			resource = RESOURCE_SET.getResource(modelUri, true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Object result;

		Model _model = (Model) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.MODEL);
		result = _model;
		// org.eclipse.uml2.uml.Package _model =
		// (org.eclipse.uml2.uml.Package)EcoreUtil.getObjectByType(resource.getContents(),
		// UMLPackage.Literals.PACKAGE);
		if (_model == null) {
			result = resource.getContents().get(0);			
		}
		return result;
	}	
	
	public void loadResources(){
		registerPackages(RESOURCE_SET);
		registerResourceFactories();
		String relPath = null;
		try {
			relPath = new File(".").getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		URIConverter.URI_MAP.put(URI.createURI("platform:/plugin/org.eclipse.uml2.uml/"), 
				URI.createURI("jar:file:"+relPath+"/EMF_Lib/org.eclipse.uml2.uml_3.1.2.v201010261927.jar!/"));
	}
	
	private void registerResourceFactories() 
	{
		Map extensionFactoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();		 
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);		 
		extensionFactoryMap.put(Ecore2XMLResource.FILE_EXTENSION, Ecore2XMLResource.Factory.INSTANCE);		 
		extensionFactoryMap.put(UML22UMLResource.FILE_EXTENSION, UML22UMLResource.Factory.INSTANCE);		 		 
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION, UML22UMLResource.Factory.INSTANCE);
		extensionFactoryMap.put(UMLResource.FILE_EXTENSION, UML22UMLResource.Factory.INSTANCE);		
		extensionFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION,new XMIResourceFactoryImpl());
	}

	private void registerPackages(ResourceSet resourceSet) 
	{
		Map packageRegistry = resourceSet.getPackageRegistry();
		packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		packageRegistry.put(Ecore2XMLPackage.eNS_URI, Ecore2XMLPackage.eINSTANCE);
		packageRegistry.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		packageRegistry .put(UML212UMLResource.UML_METAMODEL_NS_URI, UMLPackage.eINSTANCE);
		//for RSA
		packageRegistry.put("http://www.eclipse.org/uml2/2.0.0/UML",UMLPackage.eINSTANCE);
		//for Papyrus
		packageRegistry.put("http://www.eclipse.org/uml2/5.0.0/UML",UMLPackage.eINSTANCE);
	}
}
