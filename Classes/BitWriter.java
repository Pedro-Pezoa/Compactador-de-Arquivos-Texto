package Classes;

import java.io.*;
import java.util.BitSet;

public class BitWriter 
{
    private int index = 0;
    private byte data[], nthBit = 0;

    public BitWriter(int nBits) 
    {
        this.data = new byte[(int)Math.ceil(nBits / 8.0)];
    }

    public void writeBit(boolean bit) 
    {
        if( nthBit >= 8) 
        {
            nthBit = 0;
            index++;
            if( index >= data.length) throw new IndexOutOfBoundsException();
        }
        byte b = data[index];
        int mask = (1 << (7 - nthBit));

        if( bit ) b = (byte)(b | mask);
        
        data[index] = b;
        nthBit++;
    }

    public byte[] toArray() 
    {
        byte[] ret = new byte[data.length];
        System.arraycopy(data, 0, ret, 0, data.length);
        return ret;
    }

    public static void main(String[] args) throws Exception 
    {
		DataOutputStream sai = new DataOutputStream(new FileOutputStream("C:/Temp/Teste.txt"));
    	//PrintWriter sai = new PrintWriter((new File("C:/Temp/Teste.txt")));
        BitSet bit = new BitSet();
        
        bit.set(1);
        bit.set(0);
        bit.set(8);
        bit.set(9);

        System.out.println(bit.toByteArray()[1]);
        sai.write(bit.toByteArray());
        sai.flush();
        sai.close();
            //System.out.println((char)a);
        System.out.println("Acabou");
    }
}