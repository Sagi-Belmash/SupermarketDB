package Models;

import java.util.ArrayList;
import java.util.ListIterator;

public class MyListIterator<E> {
    ListIterator<E> iterator;

    public MyListIterator(ArrayList<E> list) {
        this.iterator = list.listIterator();
    }

    public MyListIterator(ArrayList<E> list, int index) {
        this.iterator = list.listIterator(index);
    }

    public boolean myHasNext() {
        return iterator.hasNext();
    }

    public E myNext() {
        return iterator.next();
    }

    public E myPrevious() {
        return iterator.previous();
    }

    public boolean myHasPrevious() {
        return iterator.hasPrevious();
    }
}
