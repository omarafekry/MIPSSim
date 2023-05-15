public class Fetch extends PPPart{
    boolean[] instruction;
    String instructionString;
    boolean hasOutput = false, outputNextCycle = false;
    int toPrint = 0;
    int counter = 0;
    Instruction currInstruction = null;
    Fetch(){
        neededCycles = 1;
        cycle = 1;
    }
    void cycle() {
        super.cycle();
        if (outputNextCycle){ 
            hasOutput = true;
            outputNextCycle = false;
            instructionID = -1;
        }
        if (toPrint > 0){
            //System.out.println("FETCHING INSTRUCTION " + counter);
            toPrint--;
        }
    }
    Instruction getInstruction(){
        hasOutput = false;
        return currInstruction;
    }
    
    public void fetch(Register PC, ActualMemory mem){
        currInstruction =  new Instruction();
		instruction = mem.getInstruction(PC.value);
        currInstruction.instruction = BoolArraytoString(instruction);
        currInstruction.id = PC.value;
        instructionID = PC.value;
        outputNextCycle = true;
        toPrint++;
        PC.value++;
        counter++;
        
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