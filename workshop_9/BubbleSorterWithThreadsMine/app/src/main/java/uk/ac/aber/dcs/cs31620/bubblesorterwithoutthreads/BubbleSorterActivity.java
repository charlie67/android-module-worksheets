package uk.ac.aber.dcs.cs31620.bubblesorterwithoutthreads;
/**
 * * Bubble sorter example
 * *
 * * Created by Chris Loftus on 17/2/2012.
 * * Copyright 2012 Aberystwyth Univerity. All rights reserved.
 */

import android.app.Activity;
import android.os.AsyncTask;
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

        final Button sortBtn = findViewById(R.id.bubbleSort);
        final Button randomizeBtn = findViewById(R.id.randomizeSort);
        final ProgressBar progressBar = findViewById(R.id.progress);

        sortBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                new AsyncBubbleSorter(adapter,
                        progressBar,
                        sortBtn,
                        randomizeBtn,
                        data.length).execute(data);
            }

        });


        randomizeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncBubbleSorter(adapter,
                        progressBar,
                        sortBtn,
                        randomizeBtn,
                        data.length).execute(data);
            }
        });
    }

    private void populateUnsortedGrid(Integer[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (int) Math.round(Math.random() * RANGE);
        }
    }

    public class AsyncBubbleSorter extends AsyncTask<Integer[], Integer, Integer[]> {

        private ArrayAdapter<Integer> adapter;
        private ProgressBar progress;
        private int dataSize;
        private Button randomizeBtn;
        private Button sortBtn;

        AsyncBubbleSorter(ArrayAdapter<Integer> adapter,
                          ProgressBar progress,
                          Button sortBtn,
                          Button randomizeBtn,
                          int dataSize) {
            this.adapter = adapter;
            this.progress = progress;
            this.dataSize = dataSize;
            this.sortBtn = sortBtn;
            this.randomizeBtn = randomizeBtn;
        }

        @Override
        protected void onPreExecute() {
            progress.setMax(dataSize);
            randomizeBtn.setEnabled(false);
            sortBtn.setEnabled(false);
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer[] doInBackground(Integer[]... args) {
            return doSort(args[0]);
        }

        @Override
        protected void onPostExecute(Integer[] sortedItems) {
            adapter.notifyDataSetChanged();
            randomizeBtn.setEnabled(true);
            sortBtn.setEnabled(true);
            progress.setVisibility(View.INVISIBLE);
            progress.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... args) {
            progress.setProgress(args[0]);
        }

        private Integer[] doSort(Integer[] data) {
            int min;

            for (int i = 0; i < data.length - 1; i++) {
                min = i;

                for (int j = i + 1; j < data.length; j++) {
                    if (data[j] < data[min]) {
                        min = j;
                    }
                }

                if (i != min) {
                    int tmp = data[i];
                    data[i] = data[min];
                    data[min] = tmp;
                }
                publishProgress(i);
            }

            return data;
        }
    }

}