/*
 * 
 */
package mdse.emf.main;

// TODO: Auto-generated Javadoc
/**
 * The Class EnumerationValue presents the values for the enumerations.
 *
 * @author Javaria imtiaz
 */
public class EnumerationValue {

	/** The name of the Value. */
	private String name;

	/** The visibility of the Value. */
	private String visibility;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public EnumerationValue clone() {
		return new EnumerationValue(this.name, this.visibility);
	}

	/**
	 * Instantiates a new enumeration value.
	 *
	 * @param name       the name
	 * @param visibility the visibility
	 */
	public EnumerationValue(String name, String visibility) {
		this.name = name;
		this.visibility = visibility;
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
	 * Gets the visibility instance.
	 *
	 * @return the visibility
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * Sets the visibility instance.
	 *
	 * @param visibility the new visibility
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EnumerationValue [name=" + name + ", visibility=" + visibility + "]";
	}
}
