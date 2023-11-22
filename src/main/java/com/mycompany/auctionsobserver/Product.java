/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.auctionsobserver;

import java.io.Serializable;

/**
 *
 * @author Fabo
 */
public class Product implements Serializable{
    
    private String name;
    private int startPrice;
    private int currentPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Product(String name, int startPrice, int currentPrice) {
        this.startPrice = startPrice;
        this.name = name;
        this.currentPrice = currentPrice;
    }
    
    
}
