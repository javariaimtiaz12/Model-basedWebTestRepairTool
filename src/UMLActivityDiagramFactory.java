



import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.ActivityImpl;
import org.eclipse.uml2.uml.internal.impl.InitialNodeImpl;
import org.eclipse.uml2.uml.internal.impl.LiteralIntegerImpl;
import org.eclipse.uml2.uml.internal.impl.LiteralStringImpl;
import org.eclipse.uml2.uml.internal.impl.NodeImpl;

/**
 * A class that creates UML model elements (e.g. package, class, attribute, etc)
 * 
 * @author Javaria Imtiaz
 * @version 1.0
 */

public class UMLActivityDiagramFactory {



	/**
	 * A method that creates and returns UML model and set its name as specified in parameter.
	 * 
	 * @param name: String
	 * @return uml model: Model
	 */
	public Model createModel(String name) {
		Model model = UMLFactory.eINSTANCE.createModel();
		model.setName(name);
		return model;
	}

	
	
	/**
	 * A method that creates and returns UML package and set its name as specified in parameter.
	 * 
	 * @param name: String
	 * @return _package: org.eclipse.uml2.uml.Package
	 */
	public org.eclipse.uml2.uml.Package createPackage(String name) {
		org.eclipse.uml2.uml.Package _package= UMLFactory.eINSTANCE.createPackage();
		_package.setName(name);
		return _package;
	}

	public org.eclipse.uml2.uml.Property createProperty(String name) {
		org.eclipse.uml2.uml.Property prop= UMLFactory.eINSTANCE.createProperty();
		prop.setName(name);
		return prop;
	}
	
	public org.eclipse.uml2.uml.PackageableElement createActivityDia(org.eclipse.uml2.uml.Package _package, String name)
	{
		org.eclipse.uml2.uml.internal.impl.ActivityImpl	act =(ActivityImpl) UMLFactory.eINSTANCE.createActivity();
		org.eclipse.uml2.uml.PackageableElement pack= _package.createPackagedElement(name, act.eClass());
		
	return pack;
	}
	
	public org.eclipse.uml2.uml.ActivityNode createInitialNode (org.eclipse.uml2.uml.Package package_, String name) 
	{
		Activity act=(Activity) package_.getPackagedElements().get(0);
		ActivityNode ni=act.createNode(name, UMLFactory.eINSTANCE.createInitialNode().eClass());
		act.getNodes().add(ni);
		
		return ni;
	}
	
	public org.eclipse.uml2.uml.ActivityNode createOpaqueNode (org.eclipse.uml2.uml.Package package_, String name) 
	{
		Activity act=(Activity) package_.getPackagedElements().get(0);
		ActivityNode op=act.createNode(name, UMLFactory.eINSTANCE.createOpaqueAction().eClass());
		return op;
	};
	

	public org.eclipse.uml2.uml.ActivityNode createFinalNode (org.eclipse.uml2.uml.Package package_, String name) 
	{
		Activity act=(Activity) package_.getPackagedElements().get(0);
		ActivityNode fin=act.createNode(name, UMLFactory.eINSTANCE.createActivityFinalNode().eClass());
		fin.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		act.getNodes().add(fin);
		return fin;
	}
	
	public org.eclipse.uml2.uml.ActivityEdge createEdges(org.eclipse.uml2.uml.Activity act, ActivityNode source, ActivityNode target, String name, String gaurd, String weight)
	{
	   ActivityEdge edge	=act.createEdge(name, UMLFactory.eINSTANCE.createControlFlow().eClass());
	   edge.setSource(source);
	   edge.setTarget(target);
	   ValueSpecification valuespec	=edge.createGuard(gaurd, null,  UMLFactory.eINSTANCE.createOpaqueExpression().eClass());
	   ValueSpecification valuespec1	=edge.createWeight(weight, null, UMLFactory.eINSTANCE.createLiteralInteger().eClass());
	   edge.setGuard(valuespec);
       edge.setWeight(valuespec1);
	   act.getEdges().add(edge);  //edges added in activity
	   return edge;
	}
	
	public static void createIntegerDefaultValue(Property attribute, org.eclipse.uml2.uml.Package _package, Object newValue) {

		LiteralInteger li = UMLFactory.eINSTANCE.createLiteralInteger();

		LiteralIntegerImpl val = (LiteralIntegerImpl) attribute.createDefaultValue(li.getName(), createPrimitiveType(_package, "Integer"), li.eClass());

		val.setValue((int) newValue);

		attribute.setDefaultValue(val);

	}

	
	public static void createStringDefaultValue(Property attribute, org.eclipse.uml2.uml.Package _package, Object newValue) {

		LiteralString ls = UMLFactory.eINSTANCE.createLiteralString();

		LiteralStringImpl val = (LiteralStringImpl) attribute.createDefaultValue(ls.getName(), createPrimitiveType(_package, "String"), ls.eClass());

		val.setValue((String) newValue);

		attribute.setDefaultValue(val);

	}

	public static PrimitiveType createPrimitiveType(org.eclipse.uml2.uml.Package package_, String name) {

		PrimitiveType primitiveType = package_.createOwnedPrimitiveType(name);

		return primitiveType;

	}
	
	
	
	public void applyProfile(org.eclipse.uml2.uml.Package package_, Profile profile) {
		if (package_.applyProfile(profile) != null) {
			System.out.printf("Profile '%s' applied to package '%s'.", profile.getQualifiedName(), package_.getQualifiedName());
			System.out.println();
		}
	}
		
		public EObject applyStereotype(NamedElement namedElement, Stereotype stereotype) {
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
		
		public Stereotype retrieveStereoType(Profile profile_, final String stereotype) {
			Stereotype conceptStereotype = profile_.getOwnedStereotype(stereotype);
			return conceptStereotype;
		}
		
		public  Profile loadApplyProfile(String profile_Path, org.eclipse.uml2.uml.Package pkg_) {
			//ModelLoader md= new ModelLoader();
			UMLReader md = new UMLReader();
			Profile profile = md.loadProfile(profile_Path);
			System.out.println("Profile : " + profile.getQualifiedName()
					+ " is Loaded Sucessfully");
			applyProfile(pkg_, profile);
			return profile;
		}

		public org.eclipse.uml2.uml.Package loadPackage(String umlActivityFilePath) {
			UMLReader md = new UMLReader();
			org.eclipse.uml2.uml.Package pkg = md.loadPackage(umlActivityFilePath);
			System.out.println("MODEL : " + pkg.getQualifiedName()
					+ " is Loaded Sucessfully");
			return pkg;
		}
		
	}
	
	

	

