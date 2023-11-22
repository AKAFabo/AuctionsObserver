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
                            Person bidder = (Person)inputStream.readObject();
                            Auction auctionBidded = (Auction)inputStream.readObject();
                            int bid = inputStream.readInt();
                            bidForAuction(bidder, auctionBidded, bid);
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
                client.flush();
                client.writeObject(auctioneer);
                client.flush();
                client.writeObject(product);
                client.flush();
                client.writeObject(auction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void bidForAuction(Person bidder, Auction auctionBidded, int bid){
        
        for (ObjectOutputStream client : clients) {
            try {
                client.writeInt(BID_FOR_AN_AUCTION);
                client.flush();
                client.writeObject(bidder);
                client.flush();
                client.writeObject(auctionBidded);
                client.flush();
                client.writeInt(bid);
                client.flush();             
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}
