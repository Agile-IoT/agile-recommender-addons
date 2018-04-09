package at.tugraz.ist.configurator.GeneticAlgorithm;

public class Population {

    Individual[] individuals;
    int clusterIndex=0;

    /*
     * Constructors
     */
    // Create a population
    public Population(int populationSize,int geneSize, boolean initialise, int cl) {
    	//System.out.println("Population size:"+populationSize);
    	clusterIndex = cl;
        individuals = new Individual[populationSize];
        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < populationSize; i++) {
                Individual newIndividual = new Individual(geneSize,cl);
                newIndividual.generateIndividual(geneSize,clusterIndex);
                //System.out.println("Individual generated:"+i);
                saveIndividual(i, newIndividual);
            }
        }
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];
        
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) { // FITTEST HAS BIGGER VALUE
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}