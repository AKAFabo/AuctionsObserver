/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.auctionsobserver;

/**
 *
 * @author Fabo
 */
public interface Observer { //When an offerer wins an auction
    
    public void update(Auction auction);          
}
