/*
 * 
 */
package mdse.emf.main;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class StereotypeStructure presents the stereotypes for the UML Profile.
 *
 * @author Javaria imtiaz
 */
public class StereotypeStructure {

	/** The name describes the name of the stereotype. */
	private String name;

	/**
	 * The derived class name describes the derived class name for the stereotype.
	 */
	private String derivedClassName;

	/** The attributes describes the list of attributes for the stereotype. */
	private ArrayList<StereotypeAttribute> attributes = null;

	/**
	 * Instantiates a new stereotype structure.
	 *
	 * @param name             the name
	 * @param derivedClassName the derived class name
	 */
	public StereotypeStructure(String name, String derivedClassName) {
		this.name = name;
		this.derivedClassName = derivedClassName;
		attributes = new ArrayList<StereotypeAttribute>();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the derived class name.
	 *
	 * @return the derived class name
	 */
	public String getDerivedClassName() {
		return derivedClassName;
	}

	/**
	 * Sets the derived class name.
	 *
	 * @param derivedClassName the new derived class name
	 */
	public void setDerivedClassName(String derivedClassName) {
		this.derivedClassName = derivedClassName;
	}

	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public ArrayList<StereotypeAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * Sets the attributes.
	 *
	 * @param attributes the new attributes
	 */
	public void setAttributes(ArrayList<StereotypeAttribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * Adds the attributes.
	 *
	 * @param attribute the attribute
	 */
	public void addAttributes(StereotypeAttribute attribute) {
		this.attributes.add(attribute);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String temp = "StereotypeStructure [name=" + name + ", derivedClassName=" + derivedClassName + "]";
		for (int i = 0; i < attributes.size(); i++) {
			temp += " " + attributes.get(i).toString();
		}
		return temp;
	}

}
