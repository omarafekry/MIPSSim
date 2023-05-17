public class Interpreter {

    static boolean[] parse(String word) {
        String[] parts = word.split(" ");

        switch (parts[0]) {
            case "ADD":
                return parse3Registers(parts, 0);
            case "SUB":
                return parse3Registers(parts, 1);
            case "MUL":
                return parse3Registers(parts, 2);
            case "MOVI":
                return parse1Register(parts, 3);
            case "JEQ":
                return parse2RegistersImmediate(parts, 4);
            case "AND":
                return parse3Registers(parts, 5);
            case "XORI":
                return parse2RegistersImmediate(parts, 6);
            case "JMP":
                return parse0Registers(parts, 7);
            case "LSL":
                return parse2RegistersShamt(parts, 8);
            case "LSR":
                return parse2RegistersShamt(parts, 9);
            case "MOVR":
                return parse2RegistersImmediate(parts, 10);
            case "MOVM":
                return parse2RegistersImmediate(parts, 11);
            default:
                return new boolean[]{};
        }

    }

    static boolean[] parse3Registers(String[] parts, int op) {
        String opcode, r1, r2, r3, shamt;

        opcode = extendZeros(Integer.toBinaryString(op), 4);
        r1 = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[1].substring(1))), 5);
        r2 = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[2].substring(1))), 5);
        r3 = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[3].substring(1))), 5);
        shamt = "0000000000000";

        return stringToArray(opcode + r1 + r2 + r3 + shamt);
    }

    static boolean[] parse2RegistersShamt(String[] parts, int op) {
        String opcode, r1, r2, r3, shamt;

        opcode = extendZeros(Integer.toBinaryString(op), 4);
        r1 = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[1].substring(1))), 5);
        r2 = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[2].substring(1))), 5);
        r3 = "00000";
        shamt = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[3])), 13);

        return stringToArray(opcode + r1 + r2 + r3 + shamt);
    }

    static boolean[] parse2RegistersImmediate(String[] parts, int op) {
        String opcode, r1, r2, imm;

        opcode = extendZeros(Integer.toBinaryString(op), 4);
        r1 = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[1].substring(1))), 5);
        r2 = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[2].substring(1))), 5);
        imm = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[3])), 18);

        return stringToArray(opcode + r1 + r2 + imm);
    }

    static boolean[] parse1Register(String[] parts, int op) {
        String opcode, r1, r2, imm;

        opcode = extendZeros(Integer.toBinaryString(op), 4);
        r1 = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[1].substring(1))), 5);
        r2 = "00000";
        imm = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[2])), 18);

        return stringToArray(opcode + r1 + r2 + imm);
    }

    static boolean[] parse0Registers(String[] parts, int op) {
        String opcode, address;

        opcode = extendZeros(Integer.toBinaryString(op), 4);
        address = extendZeros(Integer.toBinaryString(Integer.parseInt(parts[1])), 28);

        return stringToArray(opcode + address);
    }

    static String extendZeros(String s, int length) {
        while (s.length() < length)
            s = "0" + s;
        return s;
    }

    static boolean[] stringToArray(String s) {
        boolean[] r = new boolean[32];
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0')
                r[i] = false;
            else
                r[i] = true;
        }
        return r;
    }
}