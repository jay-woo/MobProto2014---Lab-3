package lab3.jwoo.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wooj on 9/14/14.
 */
public class ChatAdapter extends ArrayAdapter<ChatItem> {
    private final Context context;
    private final List<ChatItem> data;

    public ChatAdapter(Context context, List<ChatItem> data) {
        super(context, R.layout.chat, data);

        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.chat, parent, false);

        TextView username = (TextView) rowView.findViewById(R.id.usernameView);
        TextView chatItem = (TextView) rowView.findViewById(R.id.chatView);
        TextView timestamp = (TextView) rowView.findViewById(R.id.timeView);

        username.setText(data.get(position).getUsername());
        chatItem.setText(data.get(position).getChat());
        timestamp.setText(data.get(position).getTime());

        return rowView;
    }
}