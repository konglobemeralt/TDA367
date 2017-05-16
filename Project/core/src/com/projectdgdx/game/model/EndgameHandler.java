package com.projectdgdx.game.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The StrikeZone is where the Workers go when they've been caught by any Supervisor.
 * When a certain amount of Workers Have entered the StrikeZone the Supervisors have lost the game.
 */
public class EndgameHandler {
    private int numStrikingToEnd; //TODO needs to be set
    private int numMachinesDestroyedToEnd;

    private int strikeCounter = 0;
    private int destroyedMachinesCounter = 0;

    private List<EndgameListener> listeners = new ArrayList<>();

    private static EndgameHandler endgameHandler = new EndgameHandler(0);

    private EndgameHandler(int counter){    }

    public static EndgameHandler getEndgameHandler(){
        return endgameHandler;
    }

    public void incStrikers(){
        this.strikeCounter++;
        if (this.strikeCounter >= numStrikingToEnd){
            for (EndgameListener el : listeners){
                el.reactToEndgame(Endgames.STRIKE);
            }
        }
    }


    public void incDestroyedMachines(){
        this.destroyedMachinesCounter++;
        if (this.destroyedMachinesCounter >= numMachinesDestroyedToEnd){
            for (EndgameListener el : listeners){
                el.reactToEndgame(Endgames.MACHINES_DESTROYED);
            }
        }
    }

    public static void triggerTimeOutEnd(){
        for (EndgameListener el : endgameHandler.listeners) {
            el.reactToEndgame(Endgames.TIME_UPP);
        }
    }

    public static void triggerSaboteurCaughtEnd(){
        for (EndgameListener el : endgameHandler.listeners) {
            el.reactToEndgame(Endgames.SABOTEUR_CAUGHT);
        }
    }

    public int getNumStrikingToEnd() {
        return numStrikingToEnd;
    }

    public void setNumStrikingToEnd(int numStrikingToEnd) {
        this.numStrikingToEnd = numStrikingToEnd;
    }

    public int getNumMachinesDestroyedToEnd() {
        return numMachinesDestroyedToEnd;
    }

    public void setNumMachinesDestroyedToEnd(int numMachinesDestroyedToEnd) {
        this.numMachinesDestroyedToEnd = numMachinesDestroyedToEnd;
    }

    public List<EndgameListener> getListeners() {
        return listeners;
    }



}
