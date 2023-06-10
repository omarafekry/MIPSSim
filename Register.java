public class Register {
    boolean[] bits = new boolean[32];

    public Register(){
        setValue(0);
    }
    public Register(int value){
        setValue(value);
    }
    public int getValue(){
        return getTwosComplement(bits);
    }
    public boolean[] getBits(){
        return bits;
    }
    public void setValue(int value){
        String valueString = Integer.toBinaryString(value);
        if (value < 0)
            valueString = extendOne(valueString);
        else
            valueString = extendZero(valueString);
        
        for (int i = 0; i < bits.length; i++) {
            if (valueString.charAt(i) == '1')
                bits[i] = true;
            else
                bits[i] = false;
        }
    }

    public static int getTwosComplement(boolean[] binaryInt) {
        String binaryString = "";
        for (int i = 0; i < binaryInt.length; i++) {
            if (binaryInt[i])
                binaryString += "1";
            else
                binaryString += "0";
        }
        if (binaryString.charAt(0) == '1') {
            String invertedInt = binaryString;
            invertedInt = invertedInt.replace("0", " "); 
            invertedInt = invertedInt.replace("1", "0"); 
            invertedInt = invertedInt.replace(" ", "1"); 
            int decimalValue = Integer.parseInt(invertedInt, 2);
            decimalValue = (decimalValue + 1) * -1;
            return decimalValue;
        } else {
            return Integer.parseInt(binaryString, 2);
        }
    }
    public static int getTwosComplement(String binaryInt) {
        if (binaryInt.charAt(0) == '1') {
            String invertedInt = binaryInt;
            invertedInt = invertedInt.replace("0", " ");
            invertedInt = invertedInt.replace("1", "0"); 
            invertedInt = invertedInt.replace(" ", "1"); 
            int decimalValue = Integer.parseInt(invertedInt, 2);
            decimalValue = (decimalValue + 1) * -1;
            return decimalValue;
        } else {
            return Integer.parseInt(binaryInt, 2);
        }
    }
    public static String extendZero(String s) {
    	String result = s;
    	for(int i = s.length() ; i<32 ; i++) 
    		result = "0" + result;
    	return result;
    }
    public static String extendOne(String s) {
    	String result = s;
    	for(int i = s.length() ; i<32 ; i++) 
    		result = "1" + result;
    	return result;
    }
}
