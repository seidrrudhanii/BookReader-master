package com.seid.bookreader;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seid.bookreader.Adapter.MyBookAdapter;
import com.seid.bookreader.Common.Common;
import com.seid.bookreader.Model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.seid.bookreader.Common.Common.bookList;

public class FilterSearchActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_serch);

        recyclerView = findViewById(R.id.recycler_filter_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
recyclerView.setAdapter(new MyBookAdapter(getBaseContext(), bookList));
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.inflateMenu(R.menu.main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.action_filter:
                        showFiltersDialog();
                        break;

                    case R.id.action_search:
                        showSearchDialog();
                        break;
                    default:
                        break;

                }
                return true;
            }
        });
    }

    private void showSearchDialog() {
        Builder alertDialog = new Builder(FilterSearchActivity.this);
        alertDialog.setTitle("Search");
        final LayoutInflater inflater = this.getLayoutInflater();
        View search_layout = inflater.inflate(R.layout.dialog_search, null);

        final EditText editText = search_layout.findViewById(R.id.edit_search);
        alertDialog.setView(search_layout);

        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fetchSearchBook(editText.getText().toString());
            }
        });
        alertDialog.show();
    }

    private void fetchSearchBook(String query) {
        List<Book> book_search = new ArrayList<>();

        for (Book book : bookList) {

            if (book.Name.contains(query)) {
                book_search.add(book);


            }
            if (book_search.size() > 0) {
                MyBookAdapter myBookAdapter= new MyBookAdapter(getBaseContext(), book_search);
                recyclerView.setAdapter(myBookAdapter);
                myBookAdapter.notifyDataSetChanged();
                TextView textView=findViewById(R.id.all_books);
                String result=getString(R.string.Result);
                textView.setText(result);

            } else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.relativeLayout),"No result, try again", Snackbar.LENGTH_LONG);
                snackbar.show();
               // TastyToast.makeText(this, "No result", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            }
        }
    }

    private void showFiltersDialog() {

        Builder alertDialog=new Builder(FilterSearchActivity.this);

    alertDialog.setTitle("Select Category");

        final LayoutInflater inflater = this.getLayoutInflater();
        View filter_layout = inflater.inflate(R.layout.dialog_options, null);

        final AutoCompleteTextView autoCompleteTextView = filter_layout.findViewById(R.id.txt_category);
        final ChipGroup chipGroup = filter_layout.findViewById(R.id.chipGroup);


        //AutoComplete

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, Common.categories);
        // autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                //clear
                autoCompleteTextView.setText("ha");

//create tags
                final Chip chip = (Chip) inflater.inflate(R.layout.chip_item, parent, false);
                chip.setText(((TextView) view).getText());
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chipGroup.removeView(view);
                    }
                });

                chipGroup.addView(chip);
            }
        });

        alertDialog.setView(filter_layout);
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("FILTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> filter_key = new ArrayList<>();
                StringBuilder filter_query = new StringBuilder();
                for (int j = 0; j < chipGroup.getChildCount(); j++) {

                    Chip chip = (Chip) chipGroup.getChildAt(j);
                    filter_key.add(chip.getText().toString());

                    Collections.sort(filter_key);

                    //convert list to string

                    for (String key : filter_key) {

                        filter_query.append(key).append(",");

                        //remmove last ,

                        filter_query.setLength(filter_query.length() - 1);

                        //filter by this query

                        fetchFilterCategory(filter_query.toString());
                    }
                }
            }
        });
        alertDialog.show();
    }

    private void fetchFilterCategory(String query) {

        List<Book> book_filtered = new ArrayList<>();

        for (Book book : bookList) {
            if (book.Category != null) {
                if (book.Category.contains(query)) {

                    book_filtered.add(book);
                }
            }
            if (book_filtered.size() > 0) {
                MyBookAdapter myBookAdapter=new MyBookAdapter(getBaseContext(),book_filtered);
                recyclerView.setAdapter(myBookAdapter);
                myBookAdapter.notifyDataSetChanged();
            } else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.relativeLayout),"No result, try again", Snackbar.LENGTH_LONG);
                snackbar.show();
              //  TastyToast.makeText(this, "No result", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            }
        }


    }
}
