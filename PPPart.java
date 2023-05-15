public class PPPart {
    int neededCycles;
    int cycle;


    void cycle(){
        if (cycle < neededCycles) cycle++;
        else cycle = 1;
    }
    boolean isReady(){
        if (cycle == neededCycles)
            return true;
        else 
            return false;
    }
}
