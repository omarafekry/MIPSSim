public class Fetch extends PPPart{
    boolean[] instruction;
    String instructionString;
    boolean hasOutput = false, cycleOut = false;
    int toPrint = 0;
    Fetch(){
        neededCycles = 1;
        cycle = 1;
    }
    void cycle() {
        super.cycle();
        if (cycleOut){ 
            hasOutput = true;
            cycleOut = false;
        }
        if (toPrint > 0){
            System.out.println("FETCHING");
            toPrint--;
        }
    }
    String getInstructionString(){
        hasOutput = false;
        return instructionString;
    }

    public void fetch(Register PC, ActualMemory mem){
        hasOutput = false;
		instruction = mem.getInstruction(PC.value);
        instructionString = BoolArraytoString(instruction);
        cycleOut = true;
        toPrint++;
        PC.value++;
        
	}


    String BoolArraytoString(boolean[] BoolArray) {
    	String result ="";
    	for(int i = 0; i<BoolArray.length;i++) {
    		if(BoolArray[i])
    			result = result + "1";
    		else
    			result = result + "0";
    	}
    	
    	return result;
    }
}