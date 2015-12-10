package oulu.university.smartglasses;

import android.app.*;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Aryan on 12/7/2015.
 */
public class SetProperty extends Activity implements View.OnClickListener {

    SharedPreferences  mPrefs;
    Button AddNewBtn;
    ListView ListOfBtn;
    ArrayList<SingleSetRow> listItems = new ArrayList<SingleSetRow>();
    SetCustomAdapter customAdapter;
    List<CommandProperty> commandProperties = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_property_layout);
        getActionBar().setTitle("Set Properties");

        customAdapter = new SetCustomAdapter(this,listItems);

        mPrefs = getSharedPreferences("StoredData", Context.MODE_PRIVATE);
        commandProperties = loadPreference();

        AddNewBtn = (Button)findViewById(R.id.addNewBtn);
        AddNewBtn.setTag("Add");
        AddNewBtn.setOnClickListener(this);
        ListOfBtn = (ListView)findViewById(R.id.listOfBtn);
        ListOfBtn.setAdapter(customAdapter);

        if(commandProperties!=null) {
            for (int i = 0; i < commandProperties.size(); i++) {
                SingleSetRow singleRow = new SingleSetRow(commandProperties.get(i).commandName, commandProperties.get(i).commandBinary);
                listItems.add(singleRow);
            }
        }
        else{
            commandProperties = new ArrayList<>();
        }
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getTag().toString()) {
            case "Add":
                //iCallback.sendCommandToBleDevice("0110011011011000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

                CommandProperty commandProperty = new CommandProperty();
                SingleSetRow singleRow = new SingleSetRow(commandProperty.commandName,commandProperty.commandBinary);
                commandProperties.add(commandProperty);
                listItems.add(singleRow);
                customAdapter.notifyDataSetChanged();
                savePreference(commandProperties);
                break;
            default:
                break;
        }

    }

    public void showFragment(int position,String commandName, String commandBinary){
        FragmentManager pickColorFragmentManager = getFragmentManager();
        DialogFragment pickColorDialogFragment = new PickColorFragment();
        Bundle args = new Bundle();
        args.putString("position", String.valueOf(position));
        args.putString("command_name", commandName);
        args.putString("command_binary", commandBinary);
        pickColorDialogFragment.setArguments(args);
        pickColorDialogFragment.show(pickColorFragmentManager, "set properties");
    }

    public void savePreference(List<CommandProperty> commandProperties){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(commandProperties);
        prefsEditor.putString("CommandProperties",json);
        prefsEditor.commit();
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
}
