package Main;

import Classes.CharOcorrencia;
import Classes.ManipulaByte;
import Tipos.ListaDupla;

public class MainByte 
{
	public static void main(String[] args) throws Exception 
	{
		ManipulaByte bit = new ManipulaByte("Z:/Texto.txt");
		ListaDupla<CharOcorrencia> ocorren = bit.getListaOcorrencia();
		
		System.out.println(ocorren);
	}
}
