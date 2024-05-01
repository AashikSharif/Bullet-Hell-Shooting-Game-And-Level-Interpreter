package com.bullethell.game.Patterns.observer;

import com.bullethell.game.utils.Event;

public interface IObserver {
    void onNotify(IObservable observable, Event event);
}
