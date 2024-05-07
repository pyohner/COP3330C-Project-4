public class Water {
    public synchronized void drink(String player){ //synchronized drink method
        System.out.println(player + " is drinking some water."); //player drinking message
        try {
            Thread.sleep(100); //amount of time players take to drink water
        } catch (InterruptedException e){
            Thread.currentThread().interrupt(); //allow interrupt while drinking
        }
        System.out.println(player + " has finished drinking water."); //player stopped drinking message
    }
}
