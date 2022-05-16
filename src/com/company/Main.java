package com.company;

public class Main {

    public static void main(String[] args) {
        Blockchain chain = new Blockchain();
        Block block1 = chain.create("Utu Kaita has bought 1 utu.");
        chain.add(block1);
        Block block2 = chain.create("Utu Kaita has sold 1 utu.");
        chain.add(block2);
        Block block3 = chain.create("Elon Musk has bought 1000000 utu from Utu Kaita.");
        chain.add(block3);
        for (int i = 0; i < chain.getLength(); i++)
            System.out.println(chain.blockchain.get(i));
    }
}
