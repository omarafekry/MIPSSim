public class Fetch extends PPPart{
    boolean[] instruction;
    String instructionString;
    boolean hasOutput = false, outputNextCycle = false;
    Instruction currInstruction = null;
    boolean noInstruction = false;

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
        PC.value++;
        if (currInstruction.instruction.equals("00000000000000000000000000000000"))
            noInstruction = true;
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

    public void flush() {
        outputNextCycle = false;
        currInstruction = null;
        instructionID = -1;
        hasOutput = false;
    }
    
    public boolean noInstruction() {
        return noInstruction;
    }
}