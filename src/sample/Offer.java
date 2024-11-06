package sample;

import java.util.ArrayList;

public class Offer {

    int roundVal = 3;
    int newOffer;

    private ArrayList<Integer> available;

    //Gets values array
    public Offer(ArrayList<Integer> a){
        available = a;
    }

    //Calculates offer
    public int getOffer(){
        int sMedian = (available.get(available.size()/2));
        int greatest = available.get(available.size()-3);
        return ((greatest/(available.size())*roundVal)/6)+sMedian;
    }

    //Calculates last offer
    public int getLastOffer(){
        int sMedian = (available.get(available.size()/2))/2;
        int sum = available.get(available.size()-1) + available.get(available.size()-2);
        return (sum/6)+sMedian;
    }

    //Sets newOffer
    public void setNegotiation(int offer){
        newOffer = offer;
    }

    //Gets variable newOffer
    public int getNewOffer(){
        return newOffer;
    }

    //Increases variable roundVal
    public void increaseRoundVal(){
        roundVal++;
    }

}
