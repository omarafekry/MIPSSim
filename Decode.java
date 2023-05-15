public class Decode extends PPPart{
    String IR;
    Instruction currInstruction = null, waitingInstruction = null, outputInstruction = null;
    boolean hasOutput = false;
    int toPrint = 0;
    Decode(){
        neededCycles = 2;
        cycle = 1;
    }
    
    void cycle(){
        super.cycle();
        if (cycle == 2 && waitingInstruction != null){
            outputInstruction = waitingInstruction;
            hasOutput = true;
        }
        if (toPrint > 0){
            System.out.println("DECODING");
            toPrint--;
        }
    }

    Instruction getInstruction(){
        waitingInstruction = null;
        hasOutput = false;
        return outputInstruction;
    }

    public void decode(String IR, Register[] Registers) {
		currInstruction = new Instruction();
		currInstruction.OpCode = IR.substring(0, 4);
		String R1Add = IR.substring(4, 9);
		String R2Add = IR.substring(9, 14);
		String R3Add = IR.substring(14, 19);
		
		boolean R1Zero = false;
		boolean R2Zero = false;
		boolean R3Zero = false;
		
		if(Integer.parseInt(R1Add,2) == 0) {
			currInstruction.AddrR1 = Integer.parseInt(R1Add,2);
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
		
		  if(!R1Zero) {
			  	currInstruction.R1 = Registers[Integer.parseInt(R1Add,2) - 1].value;
			   	currInstruction.AddrR1 = Integer.parseInt(R1Add,2);
		   			}
		  if(!R2Zero) {
		   		currInstruction.R2 = Registers[Integer.parseInt(R2Add,2) - 1].value;
		   			}	
		  if(!R3Zero) {
		   		currInstruction.R3 = Registers[Integer.parseInt(R3Add,2) - 1].value;
		   			}
		   		
		 		   	
  		currInstruction.Imm = IR.substring(14, 32);
  				
		currInstruction.JumpAddress = IR.substring(4, 32);

		currInstruction.SHAMT  = IR.substring(19, 32);
		toPrint += 2;
	}
}
