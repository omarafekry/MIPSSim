import java.io.FileInputStream;
import java.util.Scanner;

public class CPU {
    public static void main(String[] args){

        FileInputStream fis = null;
        try {
            fis = new FileInputStream("Program.txt");
        } catch (Exception e) {
            System.out.println("File not found");
        }
        Scanner sc = new Scanner(fis);
        Memory mem = new Memory();

        while(sc.hasNextLine())
            mem.addInstruction(Parser.parse(sc.nextLine()));
        
        mem.printInstructionMemory();
        sc.close();

    }

    
}
