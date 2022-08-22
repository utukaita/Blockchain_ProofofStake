package com.company;

public class Blockchain extends Thread{
    private Block root;
    private Block[] blocks;
    private Miner[] miners;
    private int block_count;
    private int miner_count;
    private int competition_count;
    private int malevolentRatio;
    private String name;
    private Long time;

    public Blockchain(int block_count, int miner_count, int competition_count, int malevolentRatio, String name){
        this.block_count = block_count;
        this.miner_count = miner_count;
        this.competition_count = competition_count;
        this.malevolentRatio = malevolentRatio;
        this.name = name;
        blocks = new Block[block_count];
        miners = new Miner[miner_count];
        for (int i = 0; i < block_count; i++) {
            Block block = new Block();
            block.setValidator(miner_count + competition_count);
            blocks[i] = block;
        }
    }
    @Override
    public void run(){
        long startTime = System.currentTimeMillis();
        int n=0;
        boolean newNeeded;
        Block currentBlock = null;
        while(n<block_count) {
            for (int i = 0; i < miner_count; i++) {
                boolean benevolent = true;
                if (i<miner_count*malevolentRatio/100) benevolent = false;
                Miner miner = new Miner(blocks, this, i, benevolent, n);
                miner.start();
                miners[i] = miner;
            }
            newNeeded = false;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(root!=null && n==0){
                currentBlock = root;
                quit(miners);
                if(currentBlock.mined())
                    n++;
                else newNeeded = true;
            }
            else if (currentBlock != null) {

                if (currentBlock.getNext() != null) {
                    quit(miners);
                    if (currentBlock.getNext().mined() == true) {
                        n++;
                        currentBlock = currentBlock.getNext();
                    }
                    else newNeeded = true;
                }
                else newNeeded = true;
            }
            else newNeeded = true;
            if (newNeeded){
                blocks[n].setValidator(miner_count + competition_count);
            }
        }
        time = System.currentTimeMillis()-startTime;
    }

    public boolean quit(Miner[] miners){
        for (int j = 0; j < miners.length; j++) {
            if (miners[j].isAlive()) miners[j].stop();
        }
        return true;
    }

    public void traverse(Block block){
        StringBuffer tab = new StringBuffer();
        for (int i = 0; i < getLength(block)-1; i++)
            tab.append("    ");
        System.out.println(tab.toString() + block);
        if(block.getNext()!=null){
            traverse(block.getNext());
        }
    }

    @Override
    public String toString() {
        return name + " with malevolent ratio " + malevolentRatio + " and time " + time;
    }

    public Block getRoot() {
        return root;
    }

    public void setRoot(Block root){
        this.root = root;
    }

    public int getLength(Block block) {
        int counter = 1;
        while (block.getPrevious()!=null){
            counter++;
            block = block.getPrevious();
        }
        return counter;
    }

    public String getChainName(){
        return name;
    }
}
