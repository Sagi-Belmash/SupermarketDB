package Models;

import java.util.*;

public class BuyerManager {
    private Buyer[] buyers;
    private int numOfBuyers;

    public BuyerManager() {
        buyers = new Buyer[0];
        numOfBuyers = 0;
    }

    public boolean areThereBuyers() {
        return numOfBuyers > 0;
    }

    public Buyer getBuyer(String name) {
        for (int i = 0; i < numOfBuyers; i++) {
            if (buyers[i].getName().equals(name)) {
                return buyers[i];
            }
        }
        return null;
    }

    public void printBuyers() {
        for (int i = 0; i < numOfBuyers; i++) {
            System.out.println(buyers[i] + "\n");
        }
    }


    public boolean buyerExists(String name) {
        for (int i = 0; i < numOfBuyers; i++) {
            if (buyers[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addBuyer(Buyer buyer) {
        expandBuyers();
        buyers[numOfBuyers++] = buyer;
    }

    public void expandBuyers() {
        if (buyers.length == 0) {
            buyers = new Buyer[1];
        } else if (buyers.length == numOfBuyers) {
            buyers = Arrays.copyOf(buyers, buyers.length * 2);
        }
    }

    public TreeSet<Buyer> getBuyerTreeSet() {
        TreeSet<Buyer> buyersTree = new TreeSet<>((b1, b2) -> {
            int x = b1.getName().length() - b2.getName().length();
            if (b1.getName().equalsIgnoreCase(b2.getName())) return 0;
            if (x == 0) return 1;
            return x;
        });
        for (Buyer b : buyers) {
            if (b != null) {

                buyersTree.add(b);
            }
        }
        return buyersTree;
    }

    public ArrayList<String> getArrayNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Buyer b : buyers) {
            if (b != null) {
                names.add(b.getName());
            }
        }
        return names;
    }

    public LinkedHashMap<String, Integer> getLinkedHashmapNames() {
        LinkedHashMap<String, Integer> names = new LinkedHashMap<>();
        for (Buyer b : buyers) {
            if (b != null) {
                String name = b.getName().toLowerCase();
                if (names.containsKey(name)) {
                    names.put(name, names.get(name) + 1);
                } else
                    names.put(name, 1);
            }
        }
        return names;
    }

    public ArrayList<String> createNameArrayList() {
        HashMap<String, Integer> names = getLinkedHashmapNames(); // The names we saved without duplicates
        ArrayList<String> doublesList = new ArrayList<>();
        ListIterator<String> iterator = doublesList.listIterator();
        for (String key : names.keySet()) {
            iterator.add(key);
            iterator.add(key);
        }
        return doublesList;
    }

    public ArrayListMemento createNameArrayListMemento() {
        return new ArrayListMemento(createNameArrayList());
    }
    public ArrayList<String> restoreNameArrayListMemento(ArrayListMemento memento){
        return memento.getList();
    }

}