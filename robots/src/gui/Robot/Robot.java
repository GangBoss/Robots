package gui.Robot;

import gui.Observer.Observable;

public class Robot extends Observable {

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;

    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.01;
    private boolean running =true;

    public void setTargetPosition(int x, int y) {
        m_targetPositionX = x;
        m_targetPositionY = y;
    }

    public int getX() {
        return RobotMath.round(m_robotPositionX);
    }

    public int getY() {
        return RobotMath.round(m_robotPositionY);
    }

    public int getTargetX() {
        return m_targetPositionX;
    }

    public int getTargetY() {
        return m_targetPositionY;
    }

    public double getDirection() {
        return m_robotDirection;
    }

    public boolean enabled(){
        return running;
    }

    public void onModelUpdateEvent() {
        double distance = RobotMath.distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5) {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = RobotMath.angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        double angularVelocity = 0;

        if (RobotMath.asNormalizedRadians(angleToTarget - m_robotDirection -0.001) < Math.PI) {
            angularVelocity = +maxAngularVelocity;
        }
        if (RobotMath.asNormalizedRadians(angleToTarget - m_robotDirection + 0.001) > Math.PI) {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10);
        notifyObservers();
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = RobotMath.applyLimits(velocity, 0, maxVelocity);
        angularVelocity = RobotMath.applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX)) {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY)) {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newDirection = RobotMath.asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;

    }


    public void exit() {
        running=false;
        notifyObservers();
    }
}


