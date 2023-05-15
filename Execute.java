public class Execute extends PPPart{
    int zeroFlag;
    Instruction currInstruction = null, outputInstruction = null, waitingInstruction = null;
    int ReqMemAddr;
    boolean hasOutput;
    int toPrint = 0;
    int counter = 0;
    Execute(){
        neededCycles = 2;
        cycle = 1;
    }
    void cycle(){
        super.cycle();
        if (cycle == 2 && waitingInstruction != null){
            outputInstruction = waitingInstruction.clone();
            waitingInstruction = null;
            hasOutput = true;
            instructionID = -1;
        }
        if (toPrint > 0){
            //System.out.println("EXECUTING INSTRUCTION " + counter);
            toPrint--;
        }
    }
    Instruction getInstruction(){
        hasOutput = false;
        return outputInstruction;
    }
    public void execute(Instruction inst, Register PC) {
        currInstruction = inst;
		int operandA = currInstruction.R2;
		int operandB = currInstruction.R3;
		int operation = Integer.parseInt(currInstruction.OpCode, 2);
		currInstruction.ALUoutput = ALU(operandA, operandB, operation, currInstruction.SHAMT, currInstruction.Imm, PC.value, currInstruction.JumpAddress);
		
		if(operation == 4 && zeroFlag==1) {
			PC.value = currInstruction.ALUoutput;
		}	
			
		if(operation == 7) {	
			PC.value = currInstruction.ALUoutput;
		}
		
        toPrint += 2;
        counter++;
        waitingInstruction = currInstruction;
        instructionID = currInstruction.id;
	}
    public int ALU(int operandA, int operandB, int operation, String SHAMT, String BinImm, int PC, String JumpAddress) {
        
        int output = 0;
        zeroFlag = 0;
        
        String BinPC = Integer.toBinaryString(PC); 
        BinPC = ZeroPadding(BinPC);
        
        
        int Imm =  Integer.parseInt(BinImm, 2);
        
        switch(operation) {
        	case 0: output = operandA + operandB; break;
        	case 1: output = operandA - operandB; break;
        	case 2: output = operandA * operandB; break;
        	case 3: output = Imm ; break;
        	case 4: if(operandA == operandB) 
        			    zeroFlag = 1; output = PC + 1 + Imm; 
        			break;
        	case 5: output = operandA & operandB; break;
        	case 6: output = operandA ^ Imm; break;
        	case 7: output = Integer.parseInt((BinPC.substring(0, 4) + JumpAddress),2);  break;
        	case 8: output = operandA << Integer.parseInt(SHAMT, 2); break;
        	case 9: output = operandA >>> Integer.parseInt(SHAMT, 2); break;
        	case 10: output = operandA + Imm; break;
        	case 11: output = operandA + Imm; break;	
        }
        
        if(operation!=4 && output==0) {
        	zeroFlag = 1;
        }
        
        return output;
    }

    public static String ZeroPadding(String s) {
    	String result = s;
    	for(int i = s.length() ; i<32 ; i++) {
    		result = "0" + result;
    	}
    	
    	return result;
    }
}
