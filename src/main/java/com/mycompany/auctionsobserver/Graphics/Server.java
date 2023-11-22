/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.auctionsobserver.Graphics;

import com.mycompany.auctionsobserver.Auction;
import com.mycompany.auctionsobserver.Person;
import com.mycompany.auctionsobserver.Product;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabo
 */
public class Server {
    
    private ServerSocket serverSocket;
    private static ArrayList<ObjectOutputStream> clients = new ArrayList<>();
    private int clientsOnline = 1;
    
    private final static int ADD_AN_AUCTION = 1;
    private final static int BID_FOR_AN_AUCTION = 2;
    private final static int AUCTION_END = 3;
    
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ObjectOutputStream clientOut = new ObjectOutputStream(clientSocket.getOutputStream());
                clients.add(clientOut);               
                System.out.println("Client #" + clientsOnline + " joined");
                clientsOnline++;

                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 1234;
        new Server(port);
    }
    
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectInputStream inputStream;

        public ClientHandler(Socket clientSocket) {
            socket = clientSocket;
            try {
                inputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int option = inputStream.readInt();
                    
                    switch (option){                       
                        case(ADD_AN_AUCTION) -> {
                            Person auctioneer = (Person)inputStream.readObject();
                            Product product = (Product)inputStream.readObject();
                            Auction auction = (Auction)inputStream.readObject();
                            addAuction(auctioneer, product, auction);
                        }
                           
                        case(BID_FOR_AN_AUCTION) -> {

                            int newPrice = inputStream.readInt();
                            Person bidder = (Person)inputStream.readObject();
                            Product product = (Product)inputStream.readObject();
                            Auction auctionBidded = (Auction)inputStream.readObject();
                            Person auctioneer = (Person)inputStream.readObject();
                            String prodName = inputStream.readUTF();
                            
                            bidForAuction(product, bidder, auctionBidded, newPrice, prodName, auctioneer);
                        }
                        
                        case (AUCTION_END) -> {
                            String prodName = inputStream.readUTF();
                            String status = inputStream.readUTF();
                            Auction auction = (Auction)inputStream.readObject();
                            String winner = inputStream.readUTF();
                            int price = inputStream.readInt();
                            
                            endAuction(prodName, status, auction, winner, price);
                        }
                    }
                          
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
    private static void addAuction(Person auctioneer, Product product, Auction auction){
        
        for (ObjectOutputStream client : clients) {
            try {
                client.writeInt(ADD_AN_AUCTION);
                client.writeObject(auctioneer);
                client.writeObject(product);
                client.writeObject(auction);
                client.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void bidForAuction(Product product, Person bidder, Auction auctionBidded, int newPrice, String prodName, Person auctioneer){
        
        for (ObjectOutputStream client : clients) {
            try {
                client.writeInt(BID_FOR_AN_AUCTION);
                client.writeObject(product);
                client.writeObject(bidder);
                client.writeObject(auctionBidded);
                client.writeObject(auctioneer);
                client.writeInt(newPrice);    
                client.writeUTF(prodName);
                client.flush();             
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public static void endAuction(String prodName, String status, Auction auction, String winner, int price){
        
        for (ObjectOutputStream client : clients){
            try {
                client.writeInt(AUCTION_END);
                client.writeUTF(prodName);
                client.writeUTF(status);
                client.writeObject(auction);
                client.writeUTF(winner);
                client.writeInt(price);
                client.flush();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
