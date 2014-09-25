package lab3.jwoo.lab3;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by wooj on 9/14/14.
 */
public class ChatFragment extends Fragment {
    private ArrayList<ChatItem> chatItems;

    public ChatFragment ()
    {
        this.chatItems = new ArrayList<ChatItem>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final ListView myListView = (ListView) rootView.findViewById(R.id.chatbox);
        final HandlerDatabase myDatabase = new HandlerDatabase(rootView.getContext());
        myDatabase.open();
        this.chatItems = myDatabase.getAllChats();
        final ChatAdapter myChatAdapter = new ChatAdapter(getActivity(), this.chatItems);
        final Calendar myCalendar = Calendar.getInstance();


        final Button sendButton = (Button) rootView.findViewById(R.id.sendButton);
        final EditText textBox = (EditText) rootView.findViewById(R.id.textBox);

        myListView.setAdapter(myChatAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder userAlert = new AlertDialog.Builder(rootView.getContext());
                final EditText userInput = new EditText(rootView.getContext());

                userAlert.setTitle("Enter username");
                userAlert.setView(userInput);
                userAlert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = userInput.getText().toString();
                        String message = textBox.getText().toString();
                        String time = myCalendar.getTime().toString();
                        String id = username.concat(time);

                        chatItems.add(new ChatItem(username, message, time, id));
                        myDatabase.addChatToDatabase(username, message, time, id);

                        myChatAdapter.notifyDataSetChanged();
                        myListView.setSelection(chatItems.size() - 1);
                        textBox.setText("");
                    }
                });
                userAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancelled
                    }
                });
                userAlert.show();
            }
        });

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int listPosition = position;

                AlertDialog.Builder editMessageAlert = new AlertDialog.Builder(rootView.getContext());
                final EditText editedText = new EditText(rootView.getContext());

                editMessageAlert.setTitle("Edit message");
                editMessageAlert.setView(editedText);
                editMessageAlert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newMessage = editedText.getText().toString();
                        chatItems.get(listPosition).changeChat(newMessage);
                        myDatabase.editChat(chatItems.get(listPosition));
                        myChatAdapter.notifyDataSetChanged();
                    }
                });
                editMessageAlert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDatabase.deleteChatById(chatItems.get(listPosition).getId());
                        chatItems.remove(listPosition);
                        myChatAdapter.notifyDataSetChanged();
                    }
                });
                editMessageAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                });

                editMessageAlert.show();

                return false;
            }
        });

        return rootView;
    }
}