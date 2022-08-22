package com.company;

import java.security.MessageDigest;
import java.util.Date;

public class Miner extends Thread {

    private Block[] blocks;
    private Blockchain chain;
    private String chainName;
    private int n;
    private int id;
    private boolean benevolent;
    private Block block;
    private Block previousBlock;
    private String previousHash = "0";
    private long timeStamp = new Date().getTime();
    private int validator;
    private String data;


    public Miner(Block[] blocks, Blockchain chain, int id, boolean benevolent, int n){
        this.blocks = blocks;
        this.chain = chain;
        chainName = chain.getChainName();
        this.id = id;
        this.benevolent = benevolent;
        this.n = n;
        this.block = blocks[n];
        if (n!=0) {
            previousBlock = blocks[n-1];
            previousHash = previousBlock.getHash();
        }
        this.validator = block.getValidator();
        if(benevolent)
            this.data = "Valid data";
        else
            this.data = "Invalid data";
    }

    @Override
    public void run() {
        if (validator == id) {
            System.out.println("In chain " + chainName + ", block " + n + " was mined by " + id + " whose benevolence is " + benevolent + " as the assigned validator was " + validator);
            block.setData(data);
            block.setTimeStamp(timeStamp);
            block.setPreviousHash(previousHash);
            block.setMiner(id);
            block.setPrevious(previousBlock);
            block.setHash(calculateBlockHash());
            if (previousBlock != null) previousBlock.setNext(block);
            else chain.setRoot(block);
        }
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
}