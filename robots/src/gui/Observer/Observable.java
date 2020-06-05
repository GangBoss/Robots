package gui.Observer;

import java.util.ArrayList;
import java.util.Collection;

public class Observable {
    private Collection<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    protected void notifyObservers() {
        observers.forEach(x -> x.notify(this));
    }
}
