/*
 * 
 */
package mdse.emf.main;

// TODO: Auto-generated Javadoc
/**
 * The Class StereotypeAttribute presents the details of the stereotype in a UML
 * profile.
 *
 * @author Javaria imtiaz
 */
public class StereotypeAttribute {

	/** The name describes the name of the stereotype. */
	private String name;

	/** The type describes the type of the stereotype. */
	private String type;

	/** The visibility describes the visibility of the stereotype. */
	private String visibility;

	/** The default value describes the default value of the stereotype. */
	private String defaultValue = null;

	/**
	 * Instantiates a new stereotype attribute.
	 *
	 * @param name the name
	 * @param type the type
	 */
	public StereotypeAttribute(String name, String type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Instantiates a new stereotype attribute.
	 *
	 * @param name       the name
	 * @param type       the type
	 * @param visibility the visibility
	 */
	public StereotypeAttribute(String name, String type, String visibility) {
		this.name = name;
		this.type = type;
		this.setVisibility(visibility);
	}

	/**
	 * Instantiates a new stereotype attribute.
	 *
	 * @param name         the name
	 * @param type         the type
	 * @param visibility   the visibility
	 * @param defaultValue the default value
	 */
	public StereotypeAttribute(String name, String type, String visibility, String defaultValue) {
		super();
		this.name = name;
		this.type = type;
		this.setVisibility(visibility);
		this.defaultValue = defaultValue;
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
	 * Gets the type instance.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type instance.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the default value instance.
	 *
	 * @return the default value
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the default value instance.
	 *
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
		return "StereotypeAttribute [name=" + name + ", type=" + type + ", visibility=" + visibility + ", defaultValue="
				+ defaultValue + "]";
	}

}
