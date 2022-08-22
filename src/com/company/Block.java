package com.company;


import java.security.MessageDigest;
import java.util.Random;

public class Block {

    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;
    private int validator;
    private int miner;

    private Block previous;

    private Block next;

    public Block() {
    }

    public boolean mined(){
        String prev = "0", hashLocal = "0";
        if (previous!=null) prev = previous.hash;
        if(hash!=null) hashLocal = hash;
        if (validator == miner
                && prev == previousHash
                && hashLocal.equals(calculateBlockHash())
                && dataValid())
        {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean dataValid(){
        // Could be made more advanced but testing double spending is not the topic of the study
        // Currently a mere abstraction for distinguishing between benevolent and malevolent miners
        if(data.equals("Valid data"))
            return true;
        else
            return false;
    }
    public String calculateBlockHash() {
        String dataToHash = previousHash + Long.toString(timeStamp) + Integer.toString(validator) + data;
        MessageDigest digest;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println("Got exception " + e);
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            buffer.append(String.format("%02x", bytes[i]));
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        return "Block{" +
                "hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", data='" + data + '\'' +
                ", timeStamp=" + timeStamp + '\'' +
                ", validator=" + validator + '\'' +
                ", miner=" + miner + '\'' +
                '}';
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Block getPrevious() {
        return previous;
    }

    public void setPrevious(Block previous) {
        this.previous = previous;
    }

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next=next;
    }

    public String getData(){
        return data;
    }

    public void setData(String data){
        this.data = data;
    }

    public int getValidator() {
        return validator;
    }

    public void setValidator(int n) {
        Random random = new Random();
        this.validator = random.nextInt(n);
    }

    public int getMiner() {
        return miner;
    }

    public void setMiner(int miner) {
        this.miner = miner;
    }
}