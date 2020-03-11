
public class OldNew {

	String newValue;
	String oldValue;

	String newxpath;

	public OldNew(String oldVal, String newVal, String newxpath1) {
		newValue = newVal;
		oldValue = oldVal;
		newxpath = newxpath1;

	}

	public OldNew(String oldVal, String newVal) {
		newValue = newVal;
		oldValue = oldVal;

	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewxpath() {
		return newxpath;
	}

	public void setNewxpath(String newxpath) {
		this.newxpath = newxpath;
	}

}
