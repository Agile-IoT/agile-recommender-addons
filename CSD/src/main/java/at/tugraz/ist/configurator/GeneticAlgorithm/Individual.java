package at.tugraz.ist.configurator.GeneticAlgorithm;

import java.util.Random;

public class Individual {

    static int defaultGeneLength = 10;
    private int[] genes ;
    static int clusterID =0;
    // Cache
    private float fitness = -1;
    
    public Individual(int geneSize, int cl){
    	defaultGeneLength = geneSize;
    	clusterID = cl;
    	genes = new int[defaultGeneLength];
    }
    
    // Create a random individual
    public void generateIndividual(int geneSize, int cl) {
    	defaultGeneLength = geneSize;
    	clusterID = cl;
    	genes = new int[defaultGeneLength];
    	Random rand = new Random();
    	
    	boolean [] isUsed = new boolean[geneSize];
    	
    	String genestr = "";
        for (int i = 0; i < size(); i++) {
            int gene = rand.nextInt(geneSize);
            while(isUsed[gene]==true){
            	gene = rand.nextInt(geneSize);
            }
            genes[i] = gene;
            genestr += gene;
            isUsed[gene]=true;
        }
        //System.out.println("Generated gene: "+genestr);
        fitness = getFitness();
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }
    
    public int getGene(int index) {
        return genes[index];
    }
    
    public int[] getGenes() {
        return genes;
    }

    public void setGene(int index, int value) {
        genes[index] = value;
        fitness = -1;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    public float getFitness() {
        if (fitness == -1) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }
    
    public void setFitness(long val) {
        fitness = val;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}