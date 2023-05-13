
public class Processor {
	Memory MEM;
	int PC;
	final int R0;
	int[] Registers = new int[31];
	String IR;
	Instruction currInstruction;
	
	public Processor(){
		MEM = new Memory();
		
		PC = 0;
		R0 = 0;
		
		for(int i = 0; i<Registers.length;i++) {
			Registers[i] = 0;
		}
		
		Registers[1] = 2;
		Registers[4] = 3;
		
		IR="00000000000000000000000000000000";
		currInstruction = new Instruction();
	}
	
	public void fetch(){
		IR = BoolArraytoString(MEM.getData(PC));
		PC++;
	}
	
	public void decode() {
		currInstruction.OpCode = IR.substring(0, 4);
		String R1Add = IR.substring(4, 9);
		String R2Add = IR.substring(9, 14);
		String R3Add = IR.substring(14, 19);
		
		boolean R1Zero = false;
		boolean R2Zero = false;
		boolean R3Zero = false;
		
		if(Integer.parseInt(R1Add,2) == 0) {
			currInstruction.R1=0;
			R1Zero = true;
		}	
		if(Integer.parseInt(R2Add,2) == 0) {
			currInstruction.R2=0;
			R2Zero = true;
		}	
		if(Integer.parseInt(R3Add,2) == 0) {
			currInstruction.R3=0;
			R3Zero = true;
		}
		
		int operation = Integer.parseInt(currInstruction.OpCode, 2);
		
		//System.out.println(operation);
		
		switch(operation) {
		   case 0:
		   case 1:
		   case 2:
		   case 5:  if(!R1Zero) {
			   			currInstruction.AddrR1 = Integer.parseInt(R1Add,2);
		   			}
		   			if(!R2Zero) {
		   				currInstruction.R2 = Registers[Integer.parseInt(R2Add,2) - 1];
		   			}	
		   			if(!R3Zero) {
		   				currInstruction.R3 = Registers[Integer.parseInt(R3Add,2) - 1];
		   			}
		   			//System.out.println("test");
		   			break;
		   case 3:
		   case 4:
		   case 6:
		   case 10:
		   case 11:	if(!R1Zero) {
	   					currInstruction.R1 = Registers[Integer.parseInt(R1Add,2) - 1];
	   					currInstruction.AddrR1 = Integer.parseInt(R1Add,2);
  					}
		   			if(!R2Zero) {
		   				currInstruction.R2 = Registers[Integer.parseInt(R2Add,2) - 1];
		   			}	
  					currInstruction.Imm = IR.substring(14, 32);
  					break;
		   case 7: currInstruction.JumpAddress = IR.substring(14, 32);
		   		   break;
		   case 8:
		   case 9: 	if(!R1Zero) {
			   			currInstruction.AddrR1 = Integer.parseInt(R1Add,2);
  					}
		  			if(!R2Zero) {
		  				currInstruction.R2 = Registers[Integer.parseInt(R2Add,2) - 1];
		  			}  
					currInstruction.SHAMT  = IR.substring(19, 3);
					break; 	   
		}
		
			
	}
	
	int ALUoutput = 0;
    static int zeroFlag = 0;
    static int ReqMemAddr = 0;
	
	public void execute() {
		int operandA = currInstruction.R2;
		int operandB = currInstruction.R3;
		int operation = Integer.parseInt(currInstruction.OpCode, 2);
		int ALUoutput = ALU(operandA, operandB, operation, currInstruction.SHAMT, currInstruction.Imm, PC, currInstruction.JumpAddress);
		
		if(operation == 4 && zeroFlag==1) {
			PC = ALUoutput;
		}	
			
		if(operation == 7) {	
			PC = ALUoutput;
		}
		
	}

    
    public static int ALU(int operandA, int operandB, int operation, String SHAMT, String BinImm, int PC, String JumpAddress) {
        
    	
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
        	case 4: if(operandA == operandB) {
        			zeroFlag = 1; output = PC + 1 + Imm; 
        			}
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
            
        System.out.println("Operation = "+operation);
        System.out.println("First Operand = "+operandA);
        System.out.println("Second Operand = "+operandB);
        System.out.println("Result = "+output);
        System.out.println("Zero Flag = "+zeroFlag);
        
        return output;
    }
    
    public static String BoolArraytoString(boolean[] BoolArray) {
    	String result ="";
    	for(int i = 0; i<BoolArray.length;i++) {
    		if(BoolArray[i])
    			result = result + "1";
    		else
    			result = result + "0";
    	}
    	
    	return result;
    }
    
    public static String ZeroPadding(String s) {
    	String result = s;
    	for(int i = s.length() ; i<32 ; i++) {
    		result = "0" + result;
    	}
    	
    	return result;
    }

	
	
	public static void main(String[] args) {
		Processor p = new Processor();
	
		System.out.println(ALU(4, 4, 7, "0000000000000", "000000000000000011",2,"0000000000000000000000000011"));
	
		
	}
}
