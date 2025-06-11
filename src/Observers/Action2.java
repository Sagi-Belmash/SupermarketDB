package Observers;

public class Action2 implements Observer{
    @Override
    public void update(String msg) {
        System.out.println("Observers.Action2 got: " + msg);
    }
}
