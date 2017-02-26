package com.projectbasis.sample.todoapp;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.widget.AdapterView;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

import static com.projectbasis.sample.todoapp.R.id.lViewItem;

public class MainActivity extends AppCompatActivity {

    private ListView lViewItems;
    private ArrayList<String> aLItems;
    private ArrayAdapter<String> iAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create ItemList
        lViewItems = (ListView) findViewById(lViewItem);
        aLItems = new ArrayList<String>();
        iAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, aLItems);
        lViewItems.setAdapter(iAdapter);

        //Tap+Hold to remove item
        setupListViewListener();
    }

    public void onAddItemClick(View v) {

        //Text field where you enter the task item
        EditText eTextItem = (EditText) findViewById(R.id.eTextItem);

        //Get the value of the eTextItem EditText and assign to itemText string value
        String itemText = eTextItem.getText().toString();
        iAdapter.add(itemText);
        eTextItem.setText("");
    }

    private void setupListViewListener() {
        lViewItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item
                        aLItems.remove(pos);

                        // Refresh the adapter
                        iAdapter.notifyDataSetChanged();

                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }

    //Read task items that was saved previously
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "MyTodoApp.txt");
        try {
            aLItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            aLItems = new ArrayList<String>();
        }
    }

    //Write items
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "MyTodoApp.txt");
        try {
            FileUtils.writeLines(todoFile, aLItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
