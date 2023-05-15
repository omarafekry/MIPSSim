public class WriteBack extends PPPart{
    Instruction currInstruction = null;
    int toPrint = 0, counter = 0, printRegister, printValue;
    boolean hasOutput = false, outputNextCycle = false;
    WriteBack(){
        neededCycles = 1;
        cycle = 1;
    }
    void cycle(){
        super.cycle();
        if (outputNextCycle){
            hasOutput = true;
            outputNextCycle = false;
            instructionID = -1;
            if (printRegister != -1 )System.out.println("Set value of R" + printRegister + " to " + printValue);
            printRegister = -1;
        }
    }
    public void writeback(Register[] Registers, Instruction inst) {
        currInstruction = inst;
    	int operation = Integer.parseInt(currInstruction.OpCode,2);
    	
    	
    	if( operation!= 4 && operation != 7 && operation!= 11) {
    		Registers[currInstruction.AddrR1].value = currInstruction.ALUoutput;
            printRegister = currInstruction.AddrR1;
            printValue = currInstruction.ALUoutput;
    	}
        toPrint++;
        counter++;
        outputNextCycle = true;
        instructionID = currInstruction.id;
    }
}
