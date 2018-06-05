package at.tugraz.ist.ase.metaconfig.classes;

public class Composition {
	private String label;
	private Class connectedClass;
	private int lowerBound;
	private int upperBound;
	
	public Class getConnectedClass() {
		return this.connectedClass;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}
}
