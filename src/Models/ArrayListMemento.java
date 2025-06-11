package Models;

import java.util.ArrayList;

public class ArrayListMemento {
    ArrayList<String> list;

    public ArrayListMemento(ArrayList<String> list) {
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }
}
