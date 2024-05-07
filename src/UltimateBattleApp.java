/*
Phillip Yohner
COP 3330C - CRN 24680
February 9, 2024

Description:
The purpose of this program - Ultimate Battle App - is to simulate two famous Orlando City soccer players competing in a
crossbar challenge, where the goal is to kick the soccer ball and try to have it hit the top bar of the goal.
Each player has a name, a maximum rest duration, a hit percentage, and a water bottle they both share.
Once the competition begins, each player rests for some time up to their maximum rest time before their shot, and then
they attempt their kick.
If the random chance for a kick is within their success rate, the player will hit the crossbar.  Each attempt and
successful kick is recorded.
After each attempt, the player will drink water from their shared bottle for a predetermined amount of time (100 milliseconds).
Only one player can drink at a time.
Once one of the players reaches the winning amount of crossbar hits (200), they win and the other player loses.
*/

public class UltimateBattleApp {
    public static void main(String [] args) {

        Water bottle = new Water(); //create water object to synchronize between all threads

        // OUR PLAYERS with their max rest time, hit percentage, and sync'd water bottle
        Contender[] contenders = new Contender[]{
                new Contender("Kaka",30,80, bottle),
                new Contender("Nani",20,70, bottle)
        };

        //array of threads, one for each player
        Thread[] contendersThreads = new Thread[contenders.length];
        for (int i=0; i < contenders.length; i++){
            contendersThreads[i] = new Thread(contenders[i]);
            contendersThreads[i].setDaemon(false); //set thread as user thread
        }

        // START CHALLENGE with concurrent threads
        for(Thread challengeTime: contendersThreads){
            challengeTime.start();
        }
        System.out.println("The challenge has started!");

        //determine winner of challenge
        boolean challengeComplete = false;
        while (!challengeComplete){
            for (Contender contender : contenders){
                if (contender.getCrossbarHits() >= contender.getWinningNumber()){
                    System.out.println(contender.getPlayer() + " won the challenge by hitting the crossbar "+ contender.getCrossbarHits() +" times! [" + contender.getAttempts() +" attempts]");
                    challengeComplete = true;
                    break;
                }
            }
        }
        //once winner determined, interrupt remaining threads to end challenge
        for (int i = 0; i < contenders.length; i++) {
            if (contenders[i].getCrossbarHits() < contenders[i].getWinningNumber()) {
                contendersThreads[i].interrupt();
            }
        }
    }
}
