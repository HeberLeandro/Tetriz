/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import model.FrontMessage;
import model.Tetriz;

/**
 *
 * @author eletr
 */
@ServerEndpoint(value = "/tetriz", encoders = MessageEncoder.class, decoders = FrontMessageDecoder.class)
public class Endpoint {

    private static Session s1;
    private static Session s2;
    private static Tetriz game;

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        if (s1 == null) {
            game = new Tetriz();
            game.initialPieces();
            s1 = session;
            s1.getBasicRemote().sendObject(new Message(ConnectionType.OPEN, game.getBoard1(), game.getPlayer1queuePieces()));
        } else if (s2 == null) {
            s2 = session;
            s2.getBasicRemote().sendObject(new Message(ConnectionType.OPEN, game.getBoard2(), game.getPlayer2queuePieces()));
            sendMessage(new Message(ConnectionType.START));
        } else {
            session.close();
        }
    }

    @OnMessage
    public void onMessage(Session session, FrontMessage message) throws EncodeException, IOException {
        if (session == s1) {
            if (message.getType() == ConnectionType.UPDATE) {
                game.uptadeTempBoard1(message.getPiece());
                s2.getBasicRemote().sendObject(new Message(ConnectionType.UPDATE, game.getBoard1()));
            }
            else if(message.getType() == ConnectionType.REQUEST){
                game.uptadeBoard1(message.getBoard());
                s1.getBasicRemote().sendObject(new Message(ConnectionType.REQUEST, game.getBoard1(), game.getBoard2(), game.p1NewPiece(), game.getPlayer2queuePieces()));
                s2.getBasicRemote().sendObject(new Message(ConnectionType.REQUEST, game.getBoard2(), game.getBoard1(), game.getPlayer2queuePieces(), game.getPlayer1queuePieces()));
            }
            
        } else {
            if (message.getType() == ConnectionType.UPDATE) {
                game.uptadeTempBoard2(message.getPiece());
                s1.getBasicRemote().sendObject(new Message(ConnectionType.UPDATE, game.getBoard2()));
            }
            else if(message.getType() == ConnectionType.REQUEST){
                game.uptadeBoard2(message.getBoard());
                s2.getBasicRemote().sendObject(new Message(ConnectionType.REQUEST, game.getBoard2(), game.getBoard1(), game.p2NewPiece(), game.getPlayer1queuePieces()));
                s1.getBasicRemote().sendObject(new Message(ConnectionType.REQUEST, game.getBoard1(), game.getBoard2(), game.getPlayer1queuePieces(), game.getPlayer2queuePieces()));
            }
        }
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) throws IOException, EncodeException {
        if (s1 == session) {
            if (reason.getCloseCode() != CloseReason.CloseCodes.NORMAL_CLOSURE) {
                s2.getBasicRemote().sendObject(new Message(ConnectionType.ENDGAME));
            }
            s1 = null;
        }
        if (s2 == session) {
            if (reason.getCloseCode() != CloseReason.CloseCodes.NORMAL_CLOSURE) {
                s1.getBasicRemote().sendObject(new Message(ConnectionType.ENDGAME));
            }
            s2 = null;
        }
    }    

    private void sendMessage(Message msg) throws EncodeException, IOException {
        s1.getBasicRemote().sendObject(msg);
        s2.getBasicRemote().sendObject(msg);
    }
}
