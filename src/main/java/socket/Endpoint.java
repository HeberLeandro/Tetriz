/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import javax.websocket.EncodeException;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import model.Piece;
import model.Tetriz;

/**
 *
 * @author eletr
 */
@ServerEndpoint(value = "/tetriz", encoders = MessageEncoder.class)
public class Endpoint {
    private static Session s1;
    private static Session s2;
    private static Tetriz game;

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        game = new Tetriz();
        Piece[] p = new Piece[2];
        p[0] = game.getPiece();
        p[1] = game.getPiece();
        
        if (s1 == null) {
            System.out.println("conection 1");
            s1 = session;
            s1.getBasicRemote().sendObject(new Message(ConnectionType.OPEN, game.getBoard1(), p ));
        } else if (s2 == null) {
            System.out.println("conection 2");
            s2 = session;

            s2.getBasicRemote().sendObject(new Message(ConnectionType.OPEN, game.getBoard2(), p ));
            sendMessage(new Message(ConnectionType.START));
        } else {
            System.out.println("fail conection");
            session.close();
        }
    }
    
    private void sendMessage(Message msg) throws EncodeException, IOException {
        s1.getBasicRemote().sendObject(msg);
        s2.getBasicRemote().sendObject(msg);
    }
}
