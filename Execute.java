public class Execute extends PPPart{
    int zeroFlag;
    Instruction currInstruction = null, outputInstruction = null, waitingInstruction = null;
    int ReqMemAddr;
    boolean hasOutput;
    int toPrint = 0;
    int counter = 0;
    boolean jumpTaken = false;
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
    }
    Instruction getInstruction(){
        hasOutput = false;
        return outputInstruction;
    }
    public void execute(Instruction inst, Register PC) {
        if (inst == null){
			cycle = 1;
			return;
		}
        currInstruction = inst;
		int operandA = currInstruction.R2;
		int operandB = currInstruction.R3;
		int operation = Integer.parseInt(currInstruction.OpCode, 2);
        int shamt = Register.getTwosComplement(currInstruction.SHAMT);
        int immediate = Register.getTwosComplement(currInstruction.Imm);
        System.out.println("Execute input " + currInstruction.id + ": opcode = " + operation + " R1 = " + currInstruction.R1 + " R2 = " + currInstruction.R2 + " R3 = " + currInstruction.R3 + " Immediate = " + immediate + " SHAMT = " + shamt + " Jump Address = " + Integer.parseInt(currInstruction.JumpAddress, 2) + " PC = " + PC.getValue());
		currInstruction.ALUoutput = ALU(operandA, operandB, operation, shamt, immediate, PC.getValue(), currInstruction.JumpAddress);
		
		if((operation == 4 && zeroFlag==1) || operation == 7) {
			PC.setValue(currInstruction.ALUoutput);
            jumpTaken = true;
		}
		
        toPrint += 2;
        counter++;
        waitingInstruction = currInstruction;
        instructionID = currInstruction.id;
	}
    public int ALU(int operandA, int operandB, int operation, int SHAMT, int Imm, int PC, String JumpAddress) {
        
        int output = 0;
        zeroFlag = 0;
        
        String BinPC = Integer.toBinaryString(PC);
        BinPC = ZeroPadding(BinPC);
                
        switch(operation) {
        	case 0: output = operandA + operandB;
            break;
        	case 1: output = operandA - operandB; 
            break;
        	case 2: output = operandA * operandB; 
            break;
        	case 3: output = Imm; break;
        	case 4: if(operandA == operandB)
        			    zeroFlag = 1; output = PC + 1 + Imm;
        			break;
        	case 5: output = operandA & operandB; 
            break;
        	case 6: output = operandA ^ Imm; break;
        	case 7: output = Integer.parseInt((BinPC.substring(0, 4) + JumpAddress),2);  break;
        	case 8: output = operandA << SHAMT; break;
        	case 9: output = operandA >>> SHAMT; break;
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
    public boolean isJumpTaken() {
        boolean temp = jumpTaken;
        jumpTaken = false;
        return temp;
    }
}
