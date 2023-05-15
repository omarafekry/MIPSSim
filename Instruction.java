
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
		R3 = 0;
		SHAMT = "0000000000000";
		Imm = "000000000000000000";
		JumpAddress = "0000000000000000000000000000";
	}
	protected Instruction clone(){
		Instruction i = new Instruction();
		i.AddrR1 = this.AddrR1;
		i.Imm = this.Imm;
		i.JumpAddress = this.JumpAddress;
		i.OpCode = this.OpCode;
		i.R1 = this.R1;
		i.R2 = this.R2;
		i.R3 = this.R3;
		i.SHAMT = this.SHAMT;

		return i;
	}
}
