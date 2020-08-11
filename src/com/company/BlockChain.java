package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockChain {
    List<Block> chain;
    int numberOfZerosInHash;
    private Export dataExporter;

    public BlockChain(int zerosInHash){
        numberOfZerosInHash=zerosInHash;
        chain= new ArrayList<Block>();
        Block block = new Block(1,"0",numberOfZerosInHash);
        chain.add(block);
        try {
            dataExporter.writeBlock(block);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Add(int id) throws IOException {
        int indexLastElement=0;
        if (chain.size()>0) {
            indexLastElement = chain.size() - 1;
        }
        Block block = new Block(id,chain.get(indexLastElement).getHash(),numberOfZerosInHash);
        chain.add(block);
        dataExporter.writeBlock(block);
    }

    public boolean ValidateBlockChain(){
        for (Block block:chain){
            if (VerifyHash(block)==false){
                return false;
            }
        }
        return true;
    }

    private boolean VerifyHash(Block block){
        String hash=StringUtils.applySha256(block.getHashData());
        int nextBlockIndex=chain.indexOf(block)+1;
        if (nextBlockIndex<chain.size()) {
            Block nextBlock = chain.get(nextBlockIndex);

            if (hash.equals(block.getHash())
                    || block.getHash().equals(nextBlock.getPreviousHash())
                    || hash.equals(nextBlock.getPreviousHash())) {
                return true;
            }
            return false;
        }
        return true;
    }

    public void print(){
        for (Block block:chain){
            block.printInfo();
        }
    }

    public void AddExporter(Export exporter){
        this.dataExporter=exporter;
    }


    public void closeDataExporter(){
        this.dataExporter.closeFile();
    }

}
