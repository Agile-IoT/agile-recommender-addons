package at.tugraz.ist.ase.metaconfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import at.tugraz.ist.ase.metaconfig.classes.Attribute;
import at.tugraz.ist.ase.metaconfig.classes.Class;
import at.tugraz.ist.ase.metaconfig.classes.Composition;
import at.tugraz.ist.ase.metaconfig.classes.Constraint;
import at.tugraz.ist.ase.metaconfig.classes.ConstraintDeserializer;
import at.tugraz.ist.ase.metaconfig.classes.Generalization;

public class Main {
	public static final String POTENTIAL_CLASSES = "potentialClasses";
	public static final String CLASS_INSTANTIATIONS = "classInstantiations";
	public static final String ATTRIBUTE_DOMAINS = "attributeDomains";
	public static final String ATTRIBUTE_ASSIGNMENTS = "attributeAssignments";
	public static final String GENERALIZATIONS = "generalizations";
	public static final String ASSOCIATIONS = "associations";
	public static final String CONSTRAINTS = "constraints";
	
	private static String domainDefinitionDir = "domain_definitions";
	private static String domainDefinitionExt = ".config.json";
	private static ArrayList<Class> configurators;

	public static void main(String[] args) {
		// read a configuration file
		String confFile = "metaconf.properties";
		if (args.length > 0) {
			confFile = args[0];
		}
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(confFile));
			domainDefinitionDir = properties.getProperty("domain_definition_directory");
			domainDefinitionExt = properties.getProperty("domain_definition_extension");
		} catch (FileNotFoundException e) {
			System.err.println("Configuration file " + confFile + " not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not load the configuration file " + confFile + ".");
			e.printStackTrace();
		}
		
		// read domain definition JSON
		File definitionDir = new File(domainDefinitionDir);
		File[] filesAndDirs = definitionDir.listFiles();
		if (filesAndDirs == null) {
			System.err.println(domainDefinitionDir + " does not exist.");
		} else {
			configurators = new ArrayList<Class>();
			
			for (File file : filesAndDirs) {
				if (file.isFile() && file.getPath().endsWith(domainDefinitionExt)) {
					// instantiate object structure
					try {
						GsonBuilder gsonBuilder = new GsonBuilder();
						gsonBuilder.registerTypeAdapter(Constraint.class, new ConstraintDeserializer());
						Gson gson = gsonBuilder.create();
						Class rootClass = gson.fromJson(new JsonReader(new FileReader(file)), Class.class);
						configurators.add(rootClass);
					} catch (JsonIOException e) {
						System.err.println("Could not read file " + file.getName() + ".");
						e.printStackTrace();
					} catch (JsonSyntaxException e) {
						System.err.println("JSON syntax error(s) in file " + file.getName() + ".");
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						System.err.println("Could not find file " + file.getName() + ".");
						e.printStackTrace();
					}
				}
			}
		}
		
		// generate ASP code
		for (Class configurator : configurators) {
			HashMap<String, ArrayList<String>> aspRepresentation = new HashMap<String, ArrayList<String>>();
			aspRepresentation.put(POTENTIAL_CLASSES, new ArrayList<String>());
			aspRepresentation.put(CLASS_INSTANTIATIONS, new ArrayList<String>());
			aspRepresentation.put(ATTRIBUTE_DOMAINS, new ArrayList<String>());
			aspRepresentation.put(ATTRIBUTE_ASSIGNMENTS, new ArrayList<String>());
			aspRepresentation.put(GENERALIZATIONS, new ArrayList<String>());
			aspRepresentation.put(ASSOCIATIONS, new ArrayList<String>());
			aspRepresentation.put(CONSTRAINTS, new ArrayList<String>());
			// TODO symmetry breaking and instantiation restrictions
			
			HashMap<String, ArrayList<String>> generatedAsp = generateAsp(aspRepresentation, configurator);
			
			System.out.println("% TESTING");
			System.out.println();
			System.out.println("% " + POTENTIAL_CLASSES);
			for (String line : generatedAsp.get(POTENTIAL_CLASSES)) {
				System.out.println(line);
			}
			System.out.println();
			System.out.println("% " + CLASS_INSTANTIATIONS);
			for (String line : generatedAsp.get(CLASS_INSTANTIATIONS)) {
				System.out.println(line);
			}
			System.out.println();
			System.out.println("% " + ATTRIBUTE_DOMAINS);
			for (String line : generatedAsp.get(ATTRIBUTE_DOMAINS)) {
				System.out.println(line);
			}
			System.out.println();
			System.out.println("% " + ATTRIBUTE_ASSIGNMENTS);
			for (String line : generatedAsp.get(ATTRIBUTE_ASSIGNMENTS)) {
				System.out.println(line);
			}
			System.out.println();
			System.out.println("% " + GENERALIZATIONS);
			for (String line : generatedAsp.get(GENERALIZATIONS)) {
				System.out.println(line);
			}
			System.out.println();
			System.out.println("% " + ASSOCIATIONS);
			for (String line : generatedAsp.get(ASSOCIATIONS)) {
				System.out.println(line);
			}
			System.out.println();
			System.out.println("% " + CONSTRAINTS);
			for (String line : generatedAsp.get(CONSTRAINTS)) {
				System.out.println(line);
			}
		}
	}
	
	private static HashMap<String, ArrayList<String>> generateAsp(HashMap<String, ArrayList<String>> currentAspRepresentation, Class currentClass) {
		// instantiation of potential classes
		// TODO edit number of of potential instantiations
		currentAspRepresentation.get(POTENTIAL_CLASSES).add("pclass_" + currentClass.getName() + "(1..10).");
		
		// instantiation of real classes
		currentAspRepresentation.get(CLASS_INSTANTIATIONS).add("0{ class_" + currentClass.getName() + "(X) }1 :- pclass_" + currentClass.getName() + "(X).");
		
		// attribute domains and assignments
		ArrayList<Attribute> attributes = currentClass.getAttributes();
		if (attributes != null) {
			for (Attribute attribute : attributes) {
				// attribute domains
				if (attribute.getDomain() != null) {
					currentAspRepresentation.get(ATTRIBUTE_DOMAINS).add("attr_dom_" + currentClass.getName() + "_" + attribute.getName() + "(" + attribute.getDomain() + ").");
					// attribute assignments
					currentAspRepresentation.get(ATTRIBUTE_ASSIGNMENTS).add("1{ attr_" + currentClass.getName() + "_" + attribute.getName() + "(X,Y) : attr_dom_" + currentClass.getName() + "_" + attribute.getName() + "(Y) }1 :- class_" + currentClass.getName() + "(X).");
				} else if (attribute.getValue() != null) {
					// TODO fixed values
					currentAspRepresentation.get(ATTRIBUTE_ASSIGNMENTS).add("attr_" + currentClass.getName() + "(X," + attribute.getValue().toString().replaceAll("\\.0", "") + ") :- class_" + currentClass.getName() + "(X).");
				}
			}
		}
		
		// generalization
		Generalization generalization = currentClass.getGeneralization();
		if (generalization != null) {
			ArrayList<Class> children = generalization.getChildren();
			if (children != null) {
				ArrayList<String> childNames = new ArrayList<String>();
				for (Class child : children) {
					currentAspRepresentation.get(GENERALIZATIONS).add("class_" + currentClass.getName() + "(X) :- class_" + child.getName() + "(X).");
					childNames.add(child.getName());
					generateAsp(currentAspRepresentation, child);
				}
				String choiceRule = childNames.toString().replaceAll("\\[", "1{ class_").replaceAll(", ", "(X); class_").replaceAll("]", "(X) }1 :- class_") + currentClass.getName() + "(X).";
				currentAspRepresentation.get(GENERALIZATIONS).add(choiceRule);
			}
		}
		
		// association
		ArrayList<Composition> compositions = currentClass.getCompositions();
		if (compositions != null) {
			for (Composition composition : compositions) {
				Class connectedClass = composition.getConnectedClass();

				// upper and lower bound
				currentAspRepresentation.get(ASSOCIATIONS).add(composition.getLowerBound() + "{ assoc_" + currentClass.getName() + "_" + connectedClass.getName() + "(X,Y) : class_" + connectedClass.getName() + "(Y) }" + composition.getUpperBound() + " :- class_" + currentClass.getName() + "(X).");
				currentAspRepresentation.get(ASSOCIATIONS).add("1{ assoc_" + currentClass.getName() + "_" + connectedClass.getName() + "(Y,X) : class_" + currentClass.getName() + "(Y) }1 :- class_" + connectedClass.getName() + "(X).");
				
				generateAsp(currentAspRepresentation, composition.getConnectedClass());
			}
		}
		
		// TODO symmetry breaking
		
		// TODO constraints
		
		
		return currentAspRepresentation;
	}
}
