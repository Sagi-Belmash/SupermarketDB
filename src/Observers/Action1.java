package Observers;

public class Action1 implements Observer{
    @Override
    public void update(String msg) {
        System.out.println("Observers.Action1 got: " + msg);
    }
}
