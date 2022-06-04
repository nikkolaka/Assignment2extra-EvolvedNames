import java.util.Random;

public class Population {
    public MyLinkedList<Genome> population = new MyLinkedList<Genome>();
    public Genome mostFit;
    public int generation = 0;
    private int size = 100;

    public Population(){
        while (population.size() <= size){
            population.addBefore(new Genome());
        }
        population.sort();
        setMostFit();
    }

    public void nextGeneration(){
        decimate();
        repop();
        population.sort();
        setMostFit();
        generation++;
    }

    private void setMostFit(){
        int count = 1;
        for(population.first(); population.current() != null; population.next()){
            Genome fittest = population.current();
            if(count == population.size()){
                mostFit = fittest;
                break;
            }
            count++;
        }
    }

    private void decimate(){
        population.first();
        for (int i = 0; i <= size/2; i++) {
            population.remove();
        }
    }
    private void repop(){
        MyLinkedList<Genome> parents = population;

        population.first();
        while(population.size() < size) {
            if(randCoin()){
                Genome gene = new Genome(randGene(parents));
                gene.mutate();
                population.addBefore(gene);
            } else{
                Genome gene = new Genome(randGene(parents));
                gene.crossover(randGene(parents));
                gene.mutate();
                population.addBefore(gene);
            }

        }
        population.first();
    }

    private Genome randGene(MyLinkedList<Genome> parents){
        Random ran = new Random();
        int randInt = ran.nextInt(parents.size());
        parents.first();
        for (int i = 0; i <= randInt; i++) {
            if(i == randInt){
                Genome gene = parents.current();
                return gene;
            }
            parents.next();

        }
        return parents.first();
    }

    private boolean randCoin(){
        Random ran = new Random();
        return ran.nextInt(2) == 0;
    }



}
