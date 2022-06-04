import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Population pop = new Population();

        while (pop.mostFit.fitness() < 0) {
            System.out.println("Generation " + pop.generation + " " + pop.mostFit);
            pop.nextGeneration();

        }
        System.out.println("Generation " + pop.generation + " " + pop.mostFit);

    }

}