package com.projectdgdx.game.model.ai;

import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.utils.Vector3d;
import com.projectdgdx.game.utils.iTimerListener;

import java.util.Random;

/**
 * Created by Emil Jansson on 2017-05-09.
 */
public class NormalWorkerState implements iWorkerState, iTimerListener {

    private Timer timer;

    private boolean isWaiting = false;

    public NormalWorkerState(){
        //timer = new Timer(new Random().nextInt(40) + 10, 1000);
        startTimerAndListen(new Random().nextInt(10), 1000);
    }

    @Override
    public void reactOnUpdate(Worker worker) {
        if (!isWaiting){
            act(worker);
        }else {
            worker.setMoveForce(new Vector3d(0,0,0));
        }
    }

    private void act(Worker worker){
        if (isAtTargetNode(worker)){
            changeNodes(worker);
        }

        Vector3d vectorToTarget = worker.getPosition().subtractVectorFrom(worker.getTargetNode().getPosition());

        Vector3d moveVector = vectorToTarget.normalised();
        worker.setRotation(new Vector3d(0, moveVector.getXZAngle() - 90, 0)); //TODO currently broken

        worker.move(moveVector);
    }

    private void changeNodes(Worker worker){
        worker.setLastNode(worker.getTargetNode());
        worker.setTargetNode(worker.getTargetNode().getNextNode());
    }

    private boolean isAtTargetNode(Worker worker){
        return worker.getPosition().isInRadius(worker.getTargetNode().getPosition(), Config.WORKER_NODE_RADIUS);
    }

    private void startTimerAndListen(int timerValue, long ticTime ){
        timer = new Timer(timerValue, ticTime);
        timer.addListener(this);
        timer.start();
    }

    @Override
    public void timeIsUp() {
        if (!isWaiting){
            isWaiting = true;
            startTimerAndListen(new Random().nextInt(200), 10);
        }else{
            isWaiting = false;
            startTimerAndListen(new Random().nextInt(10), 1000);
        }
    }

    @Override
    public void beenCaught(Worker worker) {
        worker.setState(new StrikingWorkerState());
    }
}
