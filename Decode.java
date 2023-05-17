public class Decode extends PPPart {
	String IR;
	Instruction currInstruction = null, outputInstruction = null, waitingInstruction = null;
	boolean hasOutput = false;

	Decode() {
		neededCycles = 2;
		cycle = 1;
	}

	void cycle() {
		super.cycle();
		if (cycle == 2 && waitingInstruction != null) {
			outputInstruction = waitingInstruction.clone();
			waitingInstruction = null;
			hasOutput = true;
			instructionID = -1;
		}
	}

	Instruction getInstruction() {
		hasOutput = false;
		return outputInstruction;
	}

	public void decode(Instruction IR, Register[] Registers) {
		if (IR == null){
			cycle = 1;
			return;
		}
		currInstruction = new Instruction();
		currInstruction.OpCode = IR.instruction.substring(0, 4);
		String R1Add = IR.instruction.substring(4, 9);
		String R2Add = IR.instruction.substring(9, 14);
		String R3Add = IR.instruction.substring(14, 19);

		boolean R1Zero = false;
		boolean R2Zero = false;
		boolean R3Zero = false;

		if (Integer.parseInt(R1Add, 2) == 0) {
			currInstruction.AddrR1 = 0;
			currInstruction.R1 = 0;
			R1Zero = true;
		}
		if (Integer.parseInt(R2Add, 2) == 0) {
			currInstruction.R2 = 0;
			R2Zero = true;
		}
		if (Integer.parseInt(R3Add, 2) == 0) {
			currInstruction.R3 = 0;
			R3Zero = true;
		}

		if (!R1Zero) {
			currInstruction.R1 = Registers[Integer.parseInt(R1Add, 2) - 1].value;
			currInstruction.AddrR1 = Integer.parseInt(R1Add, 2) - 1;
		}
		if (!R2Zero) {
			currInstruction.R2 = Registers[Integer.parseInt(R2Add, 2) - 1].value;
		}
		if (!R3Zero) {
			currInstruction.R3 = Registers[Integer.parseInt(R3Add, 2) - 1].value;
		}

		currInstruction.Imm = IR.instruction.substring(14, 32);

		currInstruction.JumpAddress = IR.instruction.substring(4, 32);

		currInstruction.SHAMT = IR.instruction.substring(19, 32);

		waitingInstruction = currInstruction;
		currInstruction.id = IR.id;
		instructionID = IR.id;
	}

    public void flush() {
		currInstruction = null;
		waitingInstruction = null;
		hasOutput = false;
		outputInstruction = null;
		cycle = 1;
    }
}
	
