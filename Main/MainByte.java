package Main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Classes.ManipulaByte;

public class MainByte 
{
	public static void main(String[] args) throws Exception 
	{
		BufferedReader ent = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Temp/Texto.txt")));
		PrintWriter sai = new PrintWriter("C:/Temp/Texto.txt");
		ManipulaByte bit = new ManipulaByte("C:/Temp/Texto.txt");
		
		while (ent.ready())
		{
			
		}
	}
}
