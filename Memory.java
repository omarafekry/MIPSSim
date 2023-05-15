public class Memory extends PPPart{
    Instruction currInstruction = null;
    int toPrint = 0, counter = 0;
    boolean hasOutput = false, outputNextCycle;
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
        }
        if (toPrint > 0){
            //System.out.println("MEMORY INSTRUCTION " + counter);
            toPrint--;
        }
    }
    Instruction getInstruction(){
        hasOutput = false;
        return currInstruction;
    }
    void memory(ActualMemory memory, Instruction inst, Register[] registers) {
    	currInstruction = inst;
    	if(Integer.parseInt(currInstruction.OpCode,2) == 10) {
    		int MemValue = Integer.parseInt(BoolArraytoString(memory.getData(currInstruction.ALUoutput)),2);
    		currInstruction.ALUoutput = MemValue;
    		}
    	
    	if(Integer.parseInt(currInstruction.OpCode,2) == 11) {
    		memory.addData(StringtoBoolArray(ZeroPadding(Integer.toBinaryString(registers[currInstruction.AddrR1].value))), currInstruction.ALUoutput);
    	}
    	toPrint++;
        counter++;
        outputNextCycle = true;
        instructionID = currInstruction.id;
    }
    public static String ZeroPadding(String s) {
    	String result = s;
    	for(int i = s.length() ; i<32 ; i++) {
    		result = "0" + result;
    	}
    	
    	return result;
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
