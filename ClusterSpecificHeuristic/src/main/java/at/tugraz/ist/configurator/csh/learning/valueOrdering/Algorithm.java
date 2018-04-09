package at.tugraz.ist.configurator.csh.learning.valueOrdering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Algorithm {

    /* GA parameters */
    private final double uniformRate = 0.5;
    private final double mutationRate = 0.015;
    private final int tournamentSize = 5;
    private final boolean elitism = true;
    
    public int geneSize = 30;
    
    
    // Evolve a population
    public Population evolvePopulation(Population pop, int clusterIndex, int maxDomain) {
    	
    	geneSize = pop.getIndividual(0).size();
        Population newPopulation = new Population(pop.size(),geneSize ,false, clusterIndex);

        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        
        //System.out.println("crossover");
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop,clusterIndex);
            
            Individual indiv2 = tournamentSelection(pop,clusterIndex);
           
            Individual newIndiv = crossover(indiv1, indiv2, clusterIndex);
            newPopulation.saveIndividual(i, newIndiv);
        }
        //System.out.println("endofcrossover");
        
        //System.out.println("Mutate population");
        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }
        //System.out.println("endof Mutate");

        return newPopulation;
    }

    // Crossover individuals
    private Individual crossover(Individual indiv1, Individual indiv2,int clusterIndex) {
        
    	Individual newSol = new Individual(indiv1.size(),clusterIndex);
    	//newSol.generateIndividual(indiv1.size(),clusterIndex);
        //boolean[] usedValues = new boolean[indiv1.size()];
        int index =0;
        int next1=0;
        int next2=0;
        
        // Loop through genes
        while(index<indiv1.size()) {
            // Crossover
            if (Math.random() <= uniformRate) {
            	int old_indiv1 = indiv1.getGene(index);
            	int old_indiv2 = indiv2.getGene(index);
            	
            	indiv1.setGene(index,old_indiv2);
            	indiv2.setGene(index,old_indiv1);
            	
            }
            index++;
        }
        return newSol;
    }
    
    // Mutate an individual
    private void mutate(Individual indiv) {
    	Random rand = new Random();
        // Loop through genes
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
               
            	int oldI = indiv.getGene(i);
            	int changedI=oldI+1;
            	if(changedI==10)
            		changedI = 0;
            	
                indiv.setGene(i, changedI);
            }
        }
    }

    // Select individuals for crossover
    private Individual tournamentSelection(Population pop,int clusterIndex) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, geneSize, true, clusterIndex);
        
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }


}