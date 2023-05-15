import java.io.FileInputStream;
import java.util.Scanner;

public class CPU {
    
    public static void main(String[] args){
        Register PC = new Register();
        Register[] registers = new Register[31];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream("Program.txt");
        } catch (Exception e) {
            System.out.println("File not found");
        }
        Scanner sc = new Scanner(fis);
        ActualMemory mem = new ActualMemory();

        while(sc.hasNextLine())
            mem.addInstruction(Parser.parse(sc.nextLine()));
        
        sc.close();


        for (int i = 0; i < registers.length; i++) {
            registers[i] = new Register();
        }

        Fetch fetch = new Fetch();
        Decode decode = new Decode();
        Execute execute = new Execute();
        Memory memory = new Memory();
        WriteBack writeBack = new WriteBack();

        boolean fetchOrMemory = true;

        registers[0].value = 3;
        registers[1].value = 4;
        registers[2].value = 4;
        registers[3].value = 4;
        registers[4].value = 4;
        registers[5].value = 4;

        for (int cycle = 0; cycle < 7 + ((mem.getNumberOfInstructions() - 1) * 2); cycle++, fetchOrMemory = !fetchOrMemory) {
			System.out.println("-------CYCLE " + (cycle+1) + "------- PC = " + PC.value);
            if (fetchOrMemory && PC.value < mem.getNumberOfInstructions())
                fetch.fetch(PC, mem);
            if (decode.isReady() && fetch.hasOutput)
                decode.decode(fetch.getInstructionString(), registers);
            if (execute.isReady() && decode.hasOutput)
                execute.execute(decode.getInstruction(), PC);

            fetch.cycle();
            decode.cycle();
            execute.cycle();
            memory.cycle();
            writeBack.cycle();

		}

    }  
}
