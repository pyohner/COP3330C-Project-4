import java.util.Random;

public class Contender implements Runnable {
    //Attributes
    private String player; //player name
    private int crossbarHits=0; //crossbar hits, initialized to zero
    private int attempts=0; //total attempts, initialized to zero
    private int maxRest; //max rest time for player
    private int hitRatePercentage; //success rate for player
    Water bottle = new Water(); //a synchronized water bottle
    final int winningNumber; //field for the winning number of hits (set in constructor)

    //Constructor
    public Contender(String player, int maxRest, int hitRatePercentage, Water bottle) {
        this.player = player;
        this.maxRest = maxRest;
        this.hitRatePercentage = hitRatePercentage;
        this.bottle = bottle;
        this.winningNumber = 200; //the winning number of hits
    }

    //Getters
    public String getPlayer() {
        return player;
    }
    public int getCrossbarHits() {
        return crossbarHits;
    }
    public int getAttempts() {
        return attempts;
    }
    public int getMaxRest() {
        return maxRest;
    }
    public int getHitRatePercentage() {
        return hitRatePercentage;
    }
    public int getWinningNumber() {
        return winningNumber;
    }

    //Run method for threads
    public void run(){
        try{
            while (crossbarHits < winningNumber){  //while crossbar hits are less than the winning number
                Random randomNumber = new Random();
                int randomRestTime = randomNumber.nextInt(maxRest + 1); //random number up to the max rest time
                int chance = (int) Math.round(Math.random()*100); //generate random number for hit chance
                Thread.sleep(randomRestTime); //rest player
                System.out.println(player + " resting for " + randomRestTime + " milliseconds.");
                attempts++; //increment attempt
                if(chance <= hitRatePercentage) { //if random chance is within Rate Percentage, count as successful hit
                    crossbarHits++; //increment crossbar hit
                    System.out.println(player + " hit the crossbar! [Total hits: " + crossbarHits + "; Total attempts: " + attempts + "]");
                }
                if (crossbarHits < winningNumber) { //while crossbar hits are less than the winning number
                    synchronized (bottle) { //have player drink water from synchronized (one at a time) bottle
                        bottle.drink(player);
                    }
                }
            }
        } catch (InterruptedException e){ //when thread interrupted, this player has lost challenge
            System.out.println(player + " lost the challenge. [Total hits: " + crossbarHits + "; Total attempts: " + attempts + "]");
        }
    }
}