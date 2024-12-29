package com.manning.javapersistence.ch03.ex03;

public class Bid {
    private Item item;
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public Bid(Item item) {
        this.item = item;
        item.getBids().add(this); // Bidirectional
    }
}
