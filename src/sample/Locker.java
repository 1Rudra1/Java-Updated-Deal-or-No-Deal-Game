package sample;

import javafx.scene.image.Image;


public class Locker {
    Image showImage;
    int value;
    int x;
    int y;
    long flipLockerTime;

    //Gets locker attributes
    public Locker(Image image, int va){
        showImage = image;
        value = va;
        flipLockerTime = System.nanoTime();
    }

    //Gets image
    public Image getShowImage() {
        return showImage;
    }

    //Gets locker value
    public int getValue() {
        return value;
    }

    //Gets locker time
    public long getFlipLockerTime() {
        return flipLockerTime;
    }

    //Resets locker time
    public void resetFlipLockerTime(){
        flipLockerTime = System.nanoTime();
    }


}
