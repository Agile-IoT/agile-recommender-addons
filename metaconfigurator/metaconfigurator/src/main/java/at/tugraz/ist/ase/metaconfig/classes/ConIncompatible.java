package at.tugraz.ist.ase.metaconfig.classes;

import java.util.ArrayList;

public class ConIncompatible extends Constraint {
	private ArrayList<String> list;

	public ConIncompatible(String type, ArrayList<String> list) {
		super(type);
		this.list = new ArrayList<String>();
	}
}
