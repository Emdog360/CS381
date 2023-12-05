import java.util.Random;
import java.util.Arrays;

public class Simulation {
    

    //retuen an array with normal distrubuted buyer price center at the given medium
    public int[] generateBuyers(int medium, int buyerNum){
        Random r = new Random();
        double sdmulti = 2.0; //95%
        //double sdmulti = 3; //99%
        int[] buyers = new int[buyerNum];
        double randseed = 0;
        for(int i = 0; i < buyerNum; i++){
            do{
            randseed = r.nextGaussian()+sdmulti;
            }
            while(randseed <= 0);
            buyers[i] = (int)((double)(medium)*randseed/sdmulti);
            randseed = 0;
        }
        Arrays.sort(buyers);
        for(int i = 0; i < buyerNum; i++){System.out.print(buyers[i]+" ");}
        return buyers;
    }

    public static void main(String[] args) {
        //usage: alg buyernumber
        Simulation test = new Simulation();
        // Example usage of the GSPGame class for 3 rounds
        int seller = 0;
        for(int i = 30; i<100; i++){
            int[] buyers = test.generateBuyers(i, Integer.valueOf(args[1]));
            GSPGame game = new GSPGame(seller, buyers, String.valueOf(args[0]));
            int roundsToPlay = 20;
            game.runGSPGame(roundsToPlay);
        }
        
    }
}
