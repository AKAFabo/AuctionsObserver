/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.auctionsobserver;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Fabo
 */
public class Person implements Serializable, Observer{
    
    private String name;
    private ArrayList<Auction> joinedAuctions = new ArrayList<>();
    private ArrayList<Auction> createdAuctions = new ArrayList<>();

    public ArrayList<Auction> getCreatedAuctions() {
        return createdAuctions;
    }

    public void addCreatedAuction(Auction createdAuction) {
        this.createdAuctions.add(createdAuction);
    }
    
  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Auction> getJoinedAuctions() {
        return joinedAuctions;
    }

    public void addJoinedAuction(Auction joinedAuction) {
        this.joinedAuctions.add(joinedAuction);
    }

    public Person(String name) {
        this.name = name;
    }

    @Override
    public void update(Auction auction) {
        JOptionPane.showMessageDialog(null, this.getName() + " you have won the auction for " + auction.getAuctionProductName() + " for a total of "
        + auction.getActualPrice());
    }
    
    
    
}
    
