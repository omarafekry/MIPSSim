public class PPPart {
    int neededCycles;
    int cycle;
    int instructionID = -1;

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
    String getInstructionID(){
        if (instructionID == -1)
            return "";
        else
            return "" + instructionID;
    }
}
