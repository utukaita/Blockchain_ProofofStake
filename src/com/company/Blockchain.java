package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Blockchain{

    ArrayList<Block> blockchain = new ArrayList<>();
    public Blockchain() {
    }

    public void add(Block block){
        String previousHash = "0";
        if (getLength() > 0) {
            previousHash = blockchain.get(getLength() - 1).getHash();
        }
        if(block.valid(previousHash)) {
            blockchain.add(block);
        }
    }

    public int createValidator(){
        Random rand = new Random();
        return rand.nextInt();
    }

    public Block create(String data) {
        String previousHash = "0";
        if (getLength() > 0) {
            previousHash = blockchain.get(getLength() - 1).getHash();
        }
        Block block = new Block(data, previousHash, new Date().getTime(), createValidator());
        return block;
    }

    public Block getBlock(int n) {
        return blockchain.get(n);
    }

    public int getLength() {
        return blockchain.size();
    }
}
