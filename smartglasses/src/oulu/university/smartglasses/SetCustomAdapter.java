package oulu.university.smartglasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by Aryan on 12/7/2015.
 */
class SingleSetRow {
    String setBtnText;
    String deleteBtnText;
    String commandBinary;
    SingleSetRow(String addBtnText, String commandBinary){
        this.setBtnText =addBtnText;
        this.commandBinary=commandBinary;
        this.deleteBtnText="DEL";
    }
}

public class SetCustomAdapter extends BaseAdapter {
    private ArrayList<SingleSetRow> list;
    private Context context;

    public SetCustomAdapter(Context context, ArrayList<SingleSetRow> listOfSingleRows){
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

    class MySetViewHolder {
        Button deleteBtn;
        Button setBtn;
        MySetViewHolder(View v){
            deleteBtn=(Button)v.findViewById(R.id.delete_btn);
            setBtn =(Button)v.findViewById(R.id.set_btn);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MySetViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.set_custom_list_layout,parent, false);
            holder = new MySetViewHolder(row);
            row.setTag(holder);
        }
        else{
           holder = (MySetViewHolder) row.getTag();
        }
        holder.deleteBtn.setText(list.get(position).deleteBtnText);
        holder.setBtn.setText(list.get(position).setBtnText);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                ((SetProperty) context).commandProperties.remove(position);
                ((SetProperty) context).savePreference(((SetProperty) context).commandProperties);
                notifyDataSetChanged();
            }
        });
        holder.setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SetProperty) context).showFragment(position,list.get(position).setBtnText,list.get(position).commandBinary);
                notifyDataSetChanged();
            }
        });
        return row;
    }
}
