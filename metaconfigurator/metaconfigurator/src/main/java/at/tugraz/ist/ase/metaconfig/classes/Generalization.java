package at.tugraz.ist.ase.metaconfig.classes;

import java.util.ArrayList;

public class Generalization {
	private String label;
	private ArrayList<Class> children;
	
	public ArrayList<Class> getChildren() {
		return this.children;
	}
}
