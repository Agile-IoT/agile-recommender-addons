package at.tugraz.ist.ase.metaconfig.classes;

import java.util.ArrayList;

public class ConRequires extends Constraint {
	private ArrayList<String> list;

	public ConRequires(String type, ArrayList<String> list) {
		super(type);
		this.list = new ArrayList<String>();
	}
}
