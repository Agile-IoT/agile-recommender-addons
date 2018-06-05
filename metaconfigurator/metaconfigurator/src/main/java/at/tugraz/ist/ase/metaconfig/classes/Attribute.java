package at.tugraz.ist.ase.metaconfig.classes;

public class Attribute {
	private String name;
	private String type;
	private Object value;
	private String domain;
	private String label;
	
	public Attribute(String name, String type, Object value, String domain, String label) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
		this.domain = domain;
		this.label = label;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	public String getDomain() {
		return domain;
	}
}
