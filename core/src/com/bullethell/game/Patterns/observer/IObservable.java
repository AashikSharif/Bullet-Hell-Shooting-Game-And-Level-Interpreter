package com.bullethell.game.Patterns.observer;

import com.bullethell.game.entities.Entity;
import com.bullethell.game.utils.Event;

public interface IObservable {
    void registerObserver(IObserver observer);

    void removeObserver(IObserver observer);

    void notifyObservers(Event event);
}
