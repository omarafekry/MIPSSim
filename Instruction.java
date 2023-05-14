
public class Instruction {
	public String OpCode; 
	public int AddrR1; 
	public int R1; 
	public int R2; 
	public int R3; 
	public String SHAMT; 
	public String Imm;
	public String JumpAddress;
	
	public Instruction() {
		OpCode = "00000";
		AddrR1 = 0;
		R1 = 0;
		R2 = 0;
		SHAMT = "0000000000000";
		Imm = "000000000000000000";
		JumpAddress = "0000000000000000000000000000";
	}
}
