package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

    	System.out.println("Enter how many zeros the hash must starts with:");
		Scanner inputScanner=new Scanner(System.in);
		int numberOfZerosInHash= inputScanner.nextInt();

		Export dataExporter=new ExportJson("blockchain.json");

	    BlockChain blockChain=new BlockChain(numberOfZerosInHash);
	    blockChain.AddExporter(dataExporter);
	    for (int i=2;i<=5;i++){
	        blockChain.Add(i);
        }
	    blockChain.closeDataExporter();
	    if(blockChain.ValidateBlockChain()==true) {
			blockChain.print();
		}
    }
}
