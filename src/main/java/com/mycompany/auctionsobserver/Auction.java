/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.auctionsobserver;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Fabo
 */
public class Auction implements Serializable{
    
    private String auctionName;
    private int startPrice;
    private int actualPrice;
    
    private Person auctioneer;
    
    private ArrayList<Person> offerers = new ArrayList<>();
    private Person topBidder;
    
    private Product product;
    
    public String getAuctioneerName(){
        return auctioneer.getName();
    }

    public String getAuctionName() {
        return auctionName;
    }
    
    public String getAuctionProductName(){
        return product.getName();
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(int actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Person getAuctioneer() {
        return auctioneer;
    }

    public void setAuctioneer(Person auctioneer) {
        this.auctioneer = auctioneer;
    }

    public ArrayList<Person> getOfferers() {
        return offerers;
    }

    public void setOfferer(Person offerer) {
        this.offerers.add(offerer);
    }

    public Person getTopBidder() {
        return topBidder;
    }
    

    public void setTopBidder(Person topBidder) {
        this.topBidder = topBidder;
    }
    
    //CONST

    public Auction(Person auctioneer, Product product) {
        this.auctionName = product.getName();
        this.startPrice = product.getStartPrice();
        this.auctioneer = auctioneer;
        this.product = product;
    }
    
    
    
    
    
          
    
}
