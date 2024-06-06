package com.example.pricelist;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class MainActivity extends ListActivity {
    private DatabaseOpenHelper mDbHelper;
    private SimpleCursorAdapter mAdapter;
    private EditText searchInput;
    private TextView searchOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new DatabaseOpenHelper(this);

        searchInput = (EditText) findViewById(R.id.search_input);
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchOutput = (TextView) findViewById(R.id.search_output);

        mAdapter = new SimpleCursorAdapter(this, R.layout.list_layout, null,
                DatabaseOpenHelper.columns, new int[] { R.id._id, R.id.name },
                0);
        setListAdapter(mAdapter);

        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = searchInput.getText().toString();
                new SearchTask().execute(word);
            }
        });
    }

    private class SearchTask extends AsyncTask<String, Void, Cursor> {
        @Override
        protected Cursor doInBackground(String... params) {
            return mDbHelper.search(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (mAdapter.getCursor() != null && !mAdapter.getCursor().isClosed()) {
                mAdapter.getCursor().close();
            }

            mAdapter.changeCursor(cursor);

            if (cursor != null && cursor.getCount() > 0) {
                searchOutput.setText(R.string.search_results);
            } else {
                searchOutput.setText(R.string.no_result);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdapter.getCursor() != null && !mAdapter.getCursor().isClosed()) {
            mAdapter.getCursor().close();
        }
    }
}