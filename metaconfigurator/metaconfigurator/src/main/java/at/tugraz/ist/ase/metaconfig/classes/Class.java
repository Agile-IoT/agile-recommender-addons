package at.tugraz.ist.ase.metaconfig.classes;

import java.util.ArrayList;

public class Class {
	private String name;
	private ArrayList<Composition> compositions;
	private Generalization generalization;
	private ArrayList<Attribute> attributes;
	private ArrayList<Constraint> constraints;
	private String label;
	
	public Class(String name, ArrayList<Composition> compositions, Generalization generalization, ArrayList<Attribute> attributes, ArrayList<Constraint> constraints, String label) {
		super();
		this.name = name;
		this.compositions = compositions;
		this.generalization = generalization;
		this.attributes = attributes;
		this.constraints = constraints;
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Composition> getCompositions() {
		return compositions;
	}

	public Generalization getGeneralization() {
		return generalization;
	}

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}

	public String getLabel() {
		return label;
	}
}
