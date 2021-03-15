/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import model.FrontMessage;

/**
 *
 * @author eletr
 */
public class FrontMessageDecoder implements Decoder.Text<FrontMessage> {

    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public FrontMessage decode(final String textMessage) throws DecodeException {
        return JsonbBuilder.create().fromJson(textMessage, FrontMessage.class);
    }

    @Override
    public boolean willDecode(final String s) {
        try {
            JsonbBuilder.create().fromJson(s, FrontMessage.class);
            return true;
        } catch (JsonbException ex) {
            return false;
        }
    }
}
