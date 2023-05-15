public class ActualMemory {
    boolean[][] memory = new boolean[2048][32];
    int nextInstruction = 0;

    int getNumberOfInstructions(){
        return nextInstruction;
    }
    void addInstruction(boolean[] instruction){
        if (nextInstruction > 1023) throw new IndexOutOfBoundsException("Instruction Memory Full");
        memory[nextInstruction] = instruction;
        nextInstruction++;
    }
    boolean[] getInstruction(int pos){
        if (pos > 1023 || pos < 0) throw new IndexOutOfBoundsException("Index Out Of Instruction Memory");
        return memory[pos];
    }
    
    void addData(boolean[] data, int pos){
        if (pos < 1024 || pos > 2047) throw new IndexOutOfBoundsException("Index Out Of Data Memory");
        memory[pos] = data;
    }
    boolean[] getData(int pos){
        if (pos < 1024 || pos > 2047) throw new IndexOutOfBoundsException("Index Out Of Data Memory");
        return memory[pos];
    }

    public void printInstructionMemory(){
        for(int i = 0; i < nextInstruction; i++){
            System.out.print(i + ": ");
            for (int j = 0; j < 32; j++) 
                if (memory[i][j]) System.out.print(1);
                else System.out.print(0);
            System.out.println();
        }
    }
}
