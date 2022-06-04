import java.util.Random;

public class Genome implements Comparable<Genome>{
    protected MyLinkedList<Character> genes;
    private MyLinkedList<Character> target;
    private double mutationRate = 0.05;
    private char[] characters = {'A','B','C','D','E','F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '-', '’' };

    public Genome(){
        this.genes = new MyLinkedList<>();
        setTarget();
    }

    private void setTarget(){
        target = new MyLinkedList<>();
        String name = "CHRISTOPHER PAUL MARRIOTT";
        char[] c = name.toCharArray();
        for (char ch: c) {
            target.addBefore(ch);
        }
    }

    public Genome(Genome genome){
        this.genes = genome.genes;
        this.target = genome.target;
        this.mutationRate = genome.mutationRate;
        this.characters = genome.characters;
    }

    public void mutate(){
        //Rule 1
        if(isMutated()){
            if(genes.size() > 0){
                genes.first();
                int randInt = randPos(genes.size());
                for (int i = 0; i <= randInt; i++) {
                    genes.next();
                }
                genes.addBefore(randChar());
            } else{
                genes.addBefore(randChar());
            }

        }

        //Rule 2

        if(genes.size()>0){
            if(isMutated()){
                genes.first();
                for (int i = 0; i < randPos(genes.size()) ; i++) {
                    genes.next();
                }
                genes.remove();
            }
        }

        //Rule 3
        MyLinkedList<Character> mutiedThree = new MyLinkedList<>();
        for(genes.first(); genes.current() != null; genes.next()){
            if(isMutated()){
                mutiedThree.addBefore(randChar());
            } else{
                mutiedThree.addBefore(genes.current());
            }
        }
        genes = mutiedThree;


    }

    private char randChar(){
        Random ran = new Random();
        int r = ran.nextInt(characters.length);
        return characters[r];
    }
    private int randPos(int size){
        Random ran = new Random();
        return ran.nextInt(size);
    }
    private boolean isMutated(){
        double randomValue = Math.random()*100;
        return randomValue <= mutationRate*100;
    }
    //50 50 chance bool
    private boolean randCoin(){
        Random ran = new Random();
        return ran.nextInt(2) == 0;
    }

    public void crossover(Genome parent){

        MyLinkedList<Character> crossed = new MyLinkedList<>();

        genes.first();
        for(parent.genes.first(); parent.genes.current() != null && genes.current() != null; parent.genes.next()){
            if(randCoin()){
                crossed.addBefore(genes.current());
            } else{
                crossed.addBefore(parent.genes.current());
            }
            genes.next();
        }
        if(parent.genes.size() - genes.size() > 0){
            while(crossed.size() < parent.genes.size()){
                crossed.addBefore(parent.genes.current());
                parent.genes.next();
            }
        } else if(parent.genes.size() - genes.size() < 0){
            while(crossed.size() < genes.size()){
                crossed.addBefore(genes.current());
                genes.next();
            }
        }
        genes = crossed;
    }

    public int fitness(){
        int lengthDiff = Math.abs(genes.size() - target.size());
        int charDiff = 0;

        target.first();
        for(genes.first(); genes.current() != null && target.current() != null; genes.next()){

            if(genes.current().compareTo(target.current()) != 0){

                charDiff++;

            }

            target.next();
        }
        charDiff += Math.abs(genes.size() - target.size());


        return -(lengthDiff + charDiff);
    }

    public int compareTo(Genome other){
        return fitness() - other.fitness();
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(genes.first(); genes.current() != null; genes.next()){
            str.append(genes.current());
        }

        return "(\""+str+"\", "+fitness()+")";


    }

}
