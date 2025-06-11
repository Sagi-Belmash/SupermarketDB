package Models;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class MyArrayList<T> {

    private final ArrayList<T> list;

    public MyArrayList(ArrayList<T> list) {
        this.list = list;
    }

    public Iterator<T> iterator() {
        return new ConcreteIterator();
    }

    public ListIterator<T> listIterator() {
        return new ConcreteListIterator();
    }


    private class ConcreteIterator implements Iterator<T> {
        int current = 0;

        @Override
        public boolean hasNext() {
            return current < list.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return list.get(current++);
        }


    }


    private class ConcreteListIterator extends ConcreteIterator implements ListIterator<T> {


        @Override
        public boolean hasPrevious() {
            return current > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }

            return list.get(--current);

        }

        @Override
        public int nextIndex() {
            return current + 1;
        }

        @Override
        public int previousIndex() {
            return current - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }

    }


}

