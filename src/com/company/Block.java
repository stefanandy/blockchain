package com.company;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Block implements Serializable {

    @Expose
    @SerializedName("Id")
    private int id;
    @Expose
    @SerializedName("TimeStamp")
    private long timeStamp;
    @Expose
    @SerializedName("Previous block hash")
    private String previousHash;
    @Expose
    @SerializedName("hash")
    private String hash;
    @Expose
    @SerializedName("MagicNumber")
    private int magicNumber;

    @Expose(serialize = false,deserialize = false)
    private int numberOfZerosInHash;
    @Expose(serialize = false,deserialize = false)
    private Random randomGenerator;

    @Expose
    @SerializedName("Time to generate Block")
    private long totalTime;


    public void setId(int id) {
        this.id = id;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public Block(int id, String previousHash, int zerosInHash){
        randomGenerator=new Random();
        this.id=id;
        this.timeStamp= new Date().getTime();
        this.previousHash=previousHash;
        this.numberOfZerosInHash=zerosInHash;
        String hashData= getHashData();
        this.magicNumber=randomGenerator.nextInt();
        this.hash=StringUtils.applySha256(hashData);
        this.hash=hashWithMagicNumber(this.hash);
        this.totalTime=new Date().getTime()-this.timeStamp;
    }

    public int getId() {
        return id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public String getHashData(){
        return id+" "+" "+timeStamp+" "+magicNumber+""+previousHash;
    }

    private String hashWithMagicNumber(String hash){
        while(VerifyZerosInHash(hash)!=true){
            this.magicNumber=randomGenerator.nextInt();
            hash=StringUtils.applySha256(getHashData());
        }
        return hash;
    }

    private boolean VerifyZerosInHash(String hash){
        boolean verify=true;
        for (int i=0;i<numberOfZerosInHash;i++){
            if (hash.charAt(i)!='0'){
               return verify=false;
            }
        }
        return verify;
    }

    public void printInfo(){
        System.out.println("Block: ");
        System.out.println("Id: "+id);
        System.out.println("TimeStamp: "+timeStamp);
        System.out.println("Magic number: "+magicNumber);
        System.out.println("Previous block hash: "+previousHash);
        System.out.println("This block hash: "+hash);
        System.out.println("Block was generating for "+totalTime+" miliseconds");
        System.out.println();
    }

    @Override
    public String toString(){
        return "Block [Id=" + id + ", TimeStamp=" + timeStamp + ", " +
                "MagicNumber=" + magicNumber + ", Previous Block Hash="
                + previousHash + ", This block hash="+ hash+", Generated Time="+ totalTime+"]";
    }

}
