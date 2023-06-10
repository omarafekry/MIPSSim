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
            mem.addInstruction(Interpreter.parse(sc.nextLine()));
        
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

        System.out.printf("%-9s %-9s %-9s %-9s %-9s %-9s\n","CYCLE","FETCH","DECODE","EXECUTE", "MEMORY", "WRITEBACK");
        // print divider
        String str = "---------";
        System.out.printf("%-9s %-9s %-9s %-9s %-9s %-9s\n",str,str,str,str,str,str);
        int cycle = 1;
        boolean doneFetching = false;
        while (true){

            

            if (fetchOrMemory && !doneFetching)
                fetch.fetch(PC, mem);
            if (fetch.noInstruction())
                doneFetching = true;
            if (decode.isReady() && fetch.hasOutput)
                decode.decode(fetch.getInstruction(), registers);
            if (execute.isReady() && decode.hasOutput)
                execute.execute(decode.getInstruction(), PC);

            if (execute.isJumpTaken()){
                fetch.flush();
                decode.flush();
                System.out.println("FLUSHED FETCH AND DECODE");
            }
            
            if (memory.isReady() && execute.hasOutput)
                memory.memory(mem, execute.getInstruction(), registers);
            if (writeBack.isReady() && memory.hasOutput)
                writeBack.writeback(registers, memory.getInstruction());
            System.out.printf("%-9s %-9s %-9s %-9s %-9s %-9s\n",str,str,str,str,str,str);
            System.out.printf("%-9s %-9s %-9s %-9s %-9s %-9s\n", cycle, fetch.getInstructionID(),decode.getInstructionID(),execute.getInstructionID(),memory.getInstructionID(),writeBack.getInstructionID());
            System.out.printf("%-9s %-9s %-9s %-9s %-9s %-9s\n",str,str,str,str,str,str);
            fetch.cycle();
            decode.cycle();
            execute.cycle();
            memory.cycle();
            writeBack.cycle();
            cycle++;
            fetchOrMemory = !fetchOrMemory;

            if (doneFetching && !decode.hasOutput && !execute.hasOutput && !memory.hasOutput)
                break;
		}

        
        printRegisters(registers);
        printMemory(mem);
    }

    static void printRegisters(Register[] temp){
        Register r0 = new Register();
        Register[] registers = new Register[temp.length + 1];
        registers[0] = r0;
        for (int i = 0; i < temp.length; i++) {
            registers[i + 1] = temp[i];
        }
        System.out.println();
        System.out.println("-------------------------REGISTERS-------------------------");
        for (int i = 0; i < registers.length / 4; i++) {
            System.out.printf("%-15s %-15s %-15s %-15s\n","R" + i + ": " + registers[i].getValue(), "R" + (i + 8) + ": " + registers[i+8].getValue(),"R" + (i + 16) + ": " + registers[i + 16].getValue(), "R" + (i + 24) + ": " + registers[i+24].getValue());
        }
    }
    static void printMemory(ActualMemory memory){
        String[] memoryStrings = new String[2048];
        for (int i = 0; i < memory.memory.length; i++) {
            String temp = "";
            for (int j = 0; j < 32; j++) {
                if (memory.memory[i][j])
                    temp += "1";
                else
                    temp += "0";
            }
            memoryStrings[i] = temp;
        }
        for (int i = 1024; i < 2048; i++) 
            memoryStrings[i] = "" + Integer.parseInt(memoryStrings[i], 2);
        
        System.out.println();
        //System.out.println("--------------------INSTRUCTION MEMORY--------------------");
        //for (int i = 0; i < memoryStrings.length / 8; i++) 
            //System.out.printf("%-38s %-38s %-38s %-38s\n", i + ": " + memoryStrings[i], (i + 256) + ": " + memoryStrings[i+256],(i + 512) + ": " + memoryStrings[i + 512], (i + 768) + ": " + memoryStrings[i+768]);
        System.out.println("-----------------------DATA MEMORY------------------------");
        for (int i = 0; i < memoryStrings.length / 16; i++) 
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", 
            (i+1024) + ": " + memoryStrings[i+1024], (i + 128  +1024) + ": " + memoryStrings[i+128 +1024],(i + 256 +1024) + ": " + memoryStrings[i + 256+1024], (i + 384+1024) + ": " + memoryStrings[i+384+1024],
            (i + 512 +1024) + ": " + memoryStrings[i + 512 +1024], (i + 640  +1024) + ": " + memoryStrings[i+640 +1024],(i + 768 +1024) + ": " + memoryStrings[i + 768+1024], (i + 896+1024) + ": " + memoryStrings[i+896+1024]);
    }
}
