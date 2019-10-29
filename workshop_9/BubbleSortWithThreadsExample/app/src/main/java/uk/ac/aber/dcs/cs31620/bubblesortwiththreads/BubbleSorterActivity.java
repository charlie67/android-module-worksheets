package uk.ac.aber.dcs.cs31620.bubblesortwiththreads;
/**
 ** Bubble sorter example
 **
 ** Created by Chris Loftus on 17/2/2012.
 ** Copyright 2012 Aberystwyth Univerity. All rights reserved.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

public class BubbleSorterActivity extends Activity {
	private static final int NUM_ITEMS = 20000;
	private static final double RANGE = 100;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        GridView unsortedGrid = findViewById(R.id.unsorted_grid);
        final ProgressBar progressBar = findViewById(R.id.progress);
        
        final Integer data[] = new Integer[NUM_ITEMS];
        populateUnsortedGrid(data);
        
        final ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, data);
        
        unsortedGrid.setAdapter(adapter);
        
        final Button randomizeBtn = findViewById(R.id.randomize);
        randomizeBtn.setEnabled(false);
        randomizeBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				populateUnsortedGrid(data);
				adapter.notifyDataSetChanged();				
			}  	
        });
        
        final Button sortBtn = findViewById(R.id.bubbleSort);
        sortBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// Do the sorting using an asynchronous task
				new AsyncBubbleSorter(adapter, progressBar, sortBtn, 
						randomizeBtn, data.length).execute(data);			
			}  	
        });    
    }

	private void populateUnsortedGrid(Integer[] data) {
		for (int i=0; i<data.length; i++){
			data[i] = (int)Math.round(Math.random() * RANGE);
		}		
	}
    
}