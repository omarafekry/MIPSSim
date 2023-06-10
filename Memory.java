public class Memory extends PPPart{
    Instruction currInstruction = null;
    int printValue, printLocation, counter = 0;
    boolean hasOutput = false, outputNextCycle, toPrint = false;
    Memory(){
        neededCycles = 1;
        cycle = 1;
    }
    void cycle(){
        super.cycle();
        if (outputNextCycle){
            hasOutput = true;
            outputNextCycle = false;
            instructionID = -1;
            if (toPrint){
                System.out.println("Stored " + printValue + " in memory location " + printLocation);
                toPrint = false;
            }
        }
    }
    Instruction getInstruction(){
        hasOutput = false;
        return currInstruction;
    }
    void memory(ActualMemory memory, Instruction inst, Register[] registers) {
    	currInstruction = inst;
        int opcode = Integer.parseInt(inst.OpCode, 2);
        System.out.println("Memory input " + inst.id + ": opcode = " + opcode + " ALU output = " + currInstruction.ALUoutput + " R1 = " + registers[currInstruction.AddrR1].getValue());

    	if(opcode == 10) {
    		int MemValue = Integer.parseInt(BoolArraytoString(memory.getData(currInstruction.ALUoutput)),2);
    		currInstruction.ALUoutput = MemValue;
    		}
    	
    	if(Integer.parseInt(currInstruction.OpCode,2) == 11) {
    		memory.addData(registers[currInstruction.AddrR1 - 1].getBits(), currInstruction.ALUoutput);
            printValue = registers[currInstruction.AddrR1 - 1].getValue();
            printLocation = currInstruction.ALUoutput;
            toPrint = true;
    	}
        counter++;
        outputNextCycle = true;
        instructionID = currInstruction.id;
    }
    static boolean[] StringtoBoolArray(String s) {
        boolean[] r = new boolean[32];
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0')
                r[i] = false;
            else
                r[i] = true;
        }
        return r;
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
