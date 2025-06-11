package Models;

public class ArrayListHistory {
    private ArrayListMemento memento;

    public ArrayListHistory() {
        this.memento = null;
    }

    public ArrayListMemento getMemento() {
        return memento;
    }

    public void saveMemento(ArrayListMemento memento) {
        this.memento = memento;
    }
}
