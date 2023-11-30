import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

class GSPGame {
    private int s;
    private int[] b;
    private int[] pastBuyers;
    private String alg;
    private Random rand;
    private int waitP;
    private int maxPos;

    public GSPGame(int seller, int[] buyers, String alg) {
        this.s = seller;
        this.b = shuffleArray(buyers);
        this.alg = alg;
        this.pastBuyers = zeroedArray(buyers.length);
        
        this.rand = new Random();
        this.waitP = rand.nextInt(this.pastBuyers.length);

        this.maxPos = this.pastBuyers.length;
    }

    public float runGSPGame(int rounds) {
        float result = 0f;
        int choice = 0;
        int maxRecord = 0;
        int highRecord = 0;

        prepParams(this.alg, this.b.length);
        for (int round = 1; round <= rounds; round++) {
            for (int i = 0; i < this.b.length; i++) {
                result = useAlg(this.s, this.b[i], i, (b.length-1));
                if (result > 0f) {
                    choice = this.b[i];
                    //Change this!
                    if(this.b[i] == this.maxPos || (b[i] == this.maxPos-1 && this.s == this.maxPos)){
                        maxRecord += 1;
                    }
                    if(Float.valueOf(this.b[i])/Float.valueOf(this.s) >= 0.99){
                        highRecord += 1;
                    }
                    this.b[i] = this.s;
                    this.s = choice;
                    break;
                }
            }
            this.b = shuffleArray(this.b);
            resetParams(this.alg, this.b.length);
            System.out.println("Round " + round + ": Chosen value = " + choice);
            
        }
        System.out.println("Maximal Choices occurred " + maxRecord + " times");
        System.out.println("99th percentile occurred " + (highRecord-maxRecord) + " times");
        return choice;
    }

    private float useAlg(int sellerVal, int buyerVal, int round, int maxRound){
        return useAlg(sellerVal, buyerVal, round, maxRound, this.alg);
    }

    private float useAlg(int sellerVal, int buyerVal, int round, int maxRound, String alg){
        if(alg.equals("greedy")){
            if(sellerVal < buyerVal || round == maxRound){
                return 1f;
            }
            else{
                return 0f;
            }
        }
        else if(alg.equals("unified")) {
            //We watch for period T, then select a value better
            //than all the other previous ones
            if(round <= this.waitP){
                this.pastBuyers[round]=buyerVal;
                return 0f;
            }
            else if(round != maxRound){
                for(int i=(round-1); i>-1; i--){
                    if(buyerVal < this.pastBuyers[i] || buyerVal < sellerVal){
                            return 0f;
                        }
                }
            }
            return 1f;
        }
        return 0f;
    }

    private int[] shuffleArray(int[] array) {
        int[] temp = new int[array.length];
        ArrayList<Integer> indices = new ArrayList<>();
        
        for (int i = 0; i < array.length; i++) {
            indices.add(i);
        }
    
        Collections.shuffle(indices);

        for (int i = 0; i < array.length; i++) {
            temp[i] = array[indices.get(i)];
        }
        return temp;
    }

    private int[] zeroedArray(int length){
        int[] toReturn = new int[length];
        for(int i = 0; i<length; i++){
            toReturn[i] = 0;
        }
        return toReturn;
    }

    private void prepParams(String alg, int curBuyersLength){
        if(alg.equals("unified")){
            this.waitP = this.rand.nextInt(curBuyersLength);
        }
    }

    private void resetParams(String alg, int curBuyersLength){
        if(alg.equals("unified")){
            this.waitP = this.rand.nextInt(curBuyersLength);
        }    
    }
}

public class GSP {
    public static void main(String[] args) {
        // Example usage of the GSPGame class for 3 rounds
        int seller = 0;
        int[] buyers = new int[Integer.valueOf(args[1])];
        for(int i = 1; i <= Integer.valueOf(args[1]); i++){
            buyers[i-1] = i;
        }
        GSPGame game = new GSPGame(seller, buyers, String.valueOf(args[0]));
        int roundsToPlay = 20;
        game.runGSPGame(roundsToPlay);
    }
}
