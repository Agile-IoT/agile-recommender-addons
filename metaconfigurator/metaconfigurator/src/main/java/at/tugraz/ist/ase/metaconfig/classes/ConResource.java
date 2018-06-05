package at.tugraz.ist.ase.metaconfig.classes;

import java.util.ArrayList;

public class ConResource extends Constraint {
	private String operator;
	private ArrayList<String> list;
	
	
	public ConResource(String type, String operator, ArrayList<String> list) {
		super(type);
		this.operator = operator;
		this.list = new ArrayList<String>();
	}
}
