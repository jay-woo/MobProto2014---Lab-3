package lab3.jwoo.lab3;

import android.content.Context;
import android.view.View;

/**
 * Created by wooj on 9/14/14.
 */
public class ChatItem {
    private String username;
    private String chat;
    private String time;
    private String id;

    public ChatItem(String username, String chat, String time, String id) {
        this.username = username;
        this.chat = chat;
        this.time = time;
        this.id = id;
    }

    public void changeChat(String newMessage) {this.chat = newMessage;}

    public String getUsername() {
        return username;
    }

    public String getChat() {
        return chat;
    }

    public String getTime() {
        return time;
    }

    public String getId() { return id; }
}
