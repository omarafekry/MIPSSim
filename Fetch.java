public class Fetch extends PPPart{
    boolean[] instruction;
    String instructionString;
    boolean hasOutput = false, outputNextCycle = false;
    Instruction currInstruction = null, outputInstruction;
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
            outputInstruction = currInstruction;
            instructionID = -1;
        }
    }

    Instruction getInstruction(){
        hasOutput = false;
        return outputInstruction;
    }
    
    public void fetch(Register PC, ActualMemory mem){
        System.out.println("Fetch input: PC = " + PC.getValue());
        currInstruction = new Instruction();
		instruction = mem.getInstruction(PC.getValue());
        currInstruction.instruction = BoolArraytoString(instruction);
        if (currInstruction.instruction.equals("00000000000000000000000000000000")){
            noInstruction = true;
            return;
        }
        currInstruction.id = PC.getValue();
        instructionID = PC.getValue();
        outputNextCycle = true;
        PC.setValue(PC.getValue() + 1);
        
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