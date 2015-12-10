package oulu.university.smartglasses;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Aryan on 12/7/2015.
 */
public class BleSendFragment extends DialogFragment implements View.OnClickListener{
    ListView ListOfBtn;
    SharedPreferences mPrefs;
    ArrayList<SingleSendRow> listItems = new ArrayList<SingleSendRow>();
    SendCustomAdapter customAdapter;
    List<CommandProperty> commandProperties = new ArrayList<>();
    Button settingBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.ble_send_fragment_layout,container,false);
        getDialog().setTitle("Set & Send");
        customAdapter = new SendCustomAdapter(getActivity(),listItems);
        settingBtn = (Button)v.findViewById(R.id.settingBtn);
        settingBtn.getBackground().setColorFilter(Color.argb(255, 150, 100, 200), PorterDuff.Mode.DARKEN);
        settingBtn.setOnClickListener(this);
        ListOfBtn = (ListView)v.findViewById(R.id.sendCommandListView);
        mPrefs =getActivity().getSharedPreferences("StoredData", Context.MODE_PRIVATE);
        commandProperties = loadPreference();
        ListOfBtn.setAdapter(customAdapter);

        if(commandProperties!=null){
            for (int i = 0; i < commandProperties.size(); i++) {
                SingleSendRow singleRow = new SingleSendRow(commandProperties.get(i).commandName, commandProperties.get(i).commandBinary);
                listItems.add(singleRow);
            }
        }
        else{
            commandProperties = new ArrayList<>();
        }
        customAdapter.notifyDataSetChanged();
        return v;
    }

    public List<CommandProperty> loadPreference(){
        List<CommandProperty> commandProperties = new List<CommandProperty>() {
            @Override
            public void add(int location, CommandProperty object) {

            }

            @Override
            public boolean add(CommandProperty object) {
                return false;
            }

            @Override
            public boolean addAll(int location, Collection<? extends CommandProperty> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends CommandProperty> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean contains(Object object) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public CommandProperty get(int location) {
                return null;
            }

            @Override
            public int indexOf(Object object) {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Iterator<CommandProperty> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object object) {
                return 0;
            }

            @Override
            public ListIterator<CommandProperty> listIterator() {
                return null;
            }

            @Override
            public ListIterator<CommandProperty> listIterator(int location) {
                return null;
            }

            @Override
            public CommandProperty remove(int location) {
                return null;
            }

            @Override
            public boolean remove(Object object) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public CommandProperty set(int location, CommandProperty object) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public List<CommandProperty> subList(int start, int end) {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] array) {
                return null;
            }
        };
        Gson gson = new Gson();
        String json = mPrefs.getString("CommandProperties", "");
        if(json!=null){
            Type type = new TypeToken<List<CommandProperty>>(){}.getType();
            commandProperties = gson.fromJson(json, type);
        }
        return commandProperties;
    }

    @Override
    public void onClick(View v) {
        Intent setPropertyIntent = new Intent(getActivity(),SetProperty.class);
        getActivity().startActivity(setPropertyIntent);
    }
}
