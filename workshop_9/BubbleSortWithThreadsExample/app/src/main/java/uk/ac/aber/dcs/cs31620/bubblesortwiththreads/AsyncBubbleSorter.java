package uk.ac.aber.dcs.cs31620.bubblesortwiththreads;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;

public class AsyncBubbleSorter 
	extends AsyncTask<Integer[], Integer, Integer[]> {

	private ArrayAdapter<Integer> adapter;
	private ProgressBar progress;
	private int dataSize;
	private Button randomizeBtn;
	private Button sortBtn;

	AsyncBubbleSorter(ArrayAdapter<Integer> adapter,
			ProgressBar progress, 
			Button sortBtn, Button randomizeBtn, int dataSize) {
		this.adapter = adapter;
		this.progress = progress;
		this.dataSize = dataSize;
		this.sortBtn = sortBtn;
		this.randomizeBtn = randomizeBtn;
	}

	@Override
	protected void onPreExecute() {
		// Runs in UI thread prior to doInBackground
		// Set maximum size of progress bar
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
		// Runs in UI thread after doInBackground finishes
		// We notify the adapter
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
