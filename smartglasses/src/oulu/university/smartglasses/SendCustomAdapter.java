package oulu.university.smartglasses;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by afirouzi on 9.12.2015.
 */
class SingleSendRow{
    String sendBtnText;
    String commandBinary;
    SingleSendRow(String sendBtnText, String commandBinary){
        this.sendBtnText = sendBtnText;
        this.commandBinary = commandBinary;
    }
}

public class SendCustomAdapter extends BaseAdapter {
    private ArrayList<SingleSendRow> list;
    private Context context;

    public SendCustomAdapter(Context context, ArrayList<SingleSendRow> listOfSingleRows){
        this.list = listOfSingleRows;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class MySendViewHolder {
        Button sendBtn;
        MySendViewHolder(View v){
            sendBtn = (Button)v.findViewById(R.id.send_btn);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MySendViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.send_custom_list_layout,parent, false);
            holder = new MySendViewHolder(row);
            row.setTag(holder);
        }
        else{
            holder = (MySendViewHolder) row.getTag();
        }
        holder.sendBtn.setText(list.get(position).sendBtnText);
        holder.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                //
                ((DeviceControlActivity) context).SendValueToBleReceiver(MessageSetting.BinaryToHex(list.get(position).commandBinary));
            }
        });
        return row;
    }
}
