/*
 * 
 */
package mdse.emf.main;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class EnumerationStructure presents the enumerations for the stereotype
 * types.
 *
 * @author Javaria imtiaz
 */
public class EnumerationStructure {

	/** The name describes the name of the enumeration. */
	private String name;

	/** The values describes the values for the enumeration type. */
	private ArrayList<EnumerationValue> values = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public EnumerationStructure clone() {
		EnumerationStructure es = new EnumerationStructure(this.name);
		for (EnumerationValue ev : this.values) {
			es.addValue(ev.clone());
		}
		return es;
	}

	/**
	 * Instantiates a new enumeration structure.
	 *
	 * @param name the name
	 */
	public EnumerationStructure(String name) {
		this.name = name;
		this.values = new ArrayList<EnumerationValue>();
	}

	/**
	 * Gets the name instance.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name instance.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the values instance.
	 *
	 * @return the values
	 */
	public ArrayList<EnumerationValue> getValues() {
		return values;
	}

	/**
	 * Sets the values instances.
	 *
	 * @param values the new values
	 */
	public void setValues(ArrayList<EnumerationValue> values) {
		this.values = values;
	}

	/**
	 * Adds the value in the values.
	 *
	 * @param value the value
	 */
	public void addValue(EnumerationValue value) {
		this.values.add(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String temp = "EnumerationStructure [name=" + name + "]";
		for (int i = 0; i < values.size(); i++) {
			temp += " " + values.get(i).toString();
		}
		return temp;
	}

}
