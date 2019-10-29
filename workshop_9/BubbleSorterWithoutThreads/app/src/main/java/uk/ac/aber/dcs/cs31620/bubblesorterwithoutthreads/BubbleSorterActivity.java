package uk.ac.aber.dcs.cs31620.bubblesorterwithoutthreads;
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

public class BubbleSorterActivity extends Activity {
	private static final int NUM_ITEMS = 50000;
	private static final double RANGE = 100;
	
    private GridView unsortedGrid;
	private ArrayAdapter<Integer> adapter;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        unsortedGrid = findViewById(R.id.unsorted_grid);

        final Integer data[] = new Integer[NUM_ITEMS];
        populateUnsortedGrid(data);
        
        adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, data);

        unsortedGrid.setAdapter(adapter);
        
        Button sortBtn = findViewById(R.id.bubbleSort);
        sortBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// Do the sorting
				doSort(data);
				adapter.notifyDataSetChanged();
			}
        	
        });
    }

	private void populateUnsortedGrid(Integer[] data) {
		for (int i=0; i<data.length; i++){
			data[i] = (int)Math.round(Math.random() * RANGE);
		}		
	}
	
	private void doSort(Integer[] data){
		int min;

    	for (int i = 0; i < data.length - 1; i++){
    		min = i;

    		for (int  j = i + 1; j < data.length; j++){
    			if (data[j] < data[min]){
    				min = j;
    			}
    		}

    		if (i != min){ 
    			int tmp = data[i];
    			data[i] = data[min];
    			data[min] = tmp;	
	      }
    	}
	}
    
    
}