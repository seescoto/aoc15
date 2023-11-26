public class Reindeer {

    public String name;
    public int speed, activeInterval, restInterval, cycleTime;
    public int currTime = 0, distance = 0, points = 0;

    public Reindeer(String name, int speed, int active, int rest) {
        this.name = name;
        this.speed = speed;
        this.activeInterval = active;
        this.restInterval = rest;
        this.cycleTime = active + rest; // time it takes to cycle being active and resting
    }

    public boolean isResting() {
        return (this.currTime % this.cycleTime >= this.activeInterval);
    }

    public boolean isActive() {
        return !(this.isResting());
    }

    public void incrementTime(int timePassed) {
        // increment time by timePassed seconds,
        // add to distance travelled depending on state of activity
        for (int i = 0; i < timePassed; i++) {
            // if is active, add speed to distance travelled (since one second)
            if (this.isActive()) {
                this.distance += speed;
            }
            this.currTime++;
        }
    }

    public void givePoint() {
        this.points++;
    }

    @Override
    public String toString() {
        return this.name + ":\n"
                + "\tspeed: " + this.speed + " km/s\n"
                + "\tactive interval: " + this.activeInterval + " seconds\n"
                + "\trest interval: " + this.restInterval + " seconds\n"
                + "\tcurrent time: " + this.currTime + " seconds\n"
                + "\tdistance travelled: " + this.distance + " kms\n"
                + "\tcurrently: " + (this.isResting() ? "rest" : "fly") + "ing"
                + (this.points != 0 ? "\n\tpoints: " + this.points : ".");
    }

}
