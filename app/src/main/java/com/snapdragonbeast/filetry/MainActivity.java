package com.snapdragonbeast.filetry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.SimpleList;

public class MainActivity extends SimpleActivity {

    private Map<String, String> dictionary;
    private List<String> words;
  TextView Tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dictionary = new HashMap<>();
        words = new ArrayList<>();
        readfile();
        choosewords();
Tv=(TextView)findViewById(R.id.result);
ListView list = (ListView) findViewById(R.id.listData);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                new ArrayList<String>(dictionary.keySet()));
//        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
// way to show toast meaning
//                String defn = dictionary.get(words);
//                toast(defn);

                String defnclicked = parent.getItemAtPosition(position).toString();
                String textWord= ((TextView)findViewById(R.id.the_word)).getText().toString();
                String correctMeaning = dictionary.get(textWord);
                if(defnclicked.equals(correctMeaning)){
                    toast("that was right");
                    choosewords();
                    Tv.setText("");
                }else{
                    Tv.setText("WRONG!");
                }

            }
        });
    }
/*
show one random word
pick  5 random defn for that word  (1 is correct)
show them all on screen
*/
    private void choosewords(){
        //pick the word
        Random random=new Random();
        int randomindex =  random.nextInt(words.size());
        String theword=  words.get(randomindex);
        //picked right meaning
        String thedefn=dictionary.get(theword);

        //pick 4 other wrong defination
        List<String> defn=new ArrayList<>(dictionary.values());
        defn.remove(thedefn);
        //shuffle obtain data and pick 1st 4
        Collections.shuffle(defn);
        defn=defn.subList(0,4);
        defn.add(thedefn);
        Collections.shuffle(defn);

        //displaying on screen
        TextView textView= (TextView)findViewById(R.id.the_word);
        textView.setText(theword);
        //setting list items done using lib
        SimpleList.with(this).setItems(R.id.listData,defn);


    }
    private void readfile() {
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.dictonarywords));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\t");
            if(parts.length < 2 ) continue;
            dictionary.put(parts[0], parts[1]);
            words.add(parts[0]);
        }
    }
}
