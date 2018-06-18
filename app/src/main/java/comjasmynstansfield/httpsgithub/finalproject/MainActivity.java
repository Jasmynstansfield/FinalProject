/**
 * Name: Jasmyn Stansfield
 * Course: CS30S
 * Teacher: Mr. Hardman
 * Final Project
 * Date last modified: June 18, 2018
 */

package comjasmynstansfield.httpsgithub.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private static int numBooksAdded = 0;
    private static int numBooksReturned = 0;

    protected static Books[] booksAddedArray = new Books[10];
    private static String[] bookNames;

    private static String[] returnedNames;
    private static String toReturn;

    private EditText bookName;
    private EditText dueDate;
    private ListView booksAdded;
    private ListView returnedList;
    protected static TextView errorMessage;

    int elementNo;

    /**
     * onCreate is the method that is run when we create an instance of thi activity
     *
     * @param savedInstanceState is a bundle object that allows the activity to
     *                           restore itself to a previously saved instance
     * @return Nothing will be returned
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookName = (EditText) findViewById(R.id.et_book_input);
        dueDate = (EditText) findViewById(R.id.et_book_date);
        booksAdded = (ListView) findViewById(R.id.lv_books_out);
        errorMessage = (TextView) findViewById(R.id.tv_error_message);
        returnedList = (ListView) findViewById(R.id.lv_books_in);

        bookNames = new String[booksAddedArray.length];
        returnedNames= new String[10];

        updateListView();

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                elementNo = position;

                Intent booksIntent = new Intent(MainActivity.this, BooksActivity.class);

                booksIntent.putExtra("BOOK_EXTRA", booksAddedArray[position]);

                startActivityForResult(booksIntent, 0);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);


            }
        };

        booksAdded.setOnItemClickListener(itemClickListener);
        returnedList.setOnItemClickListener(itemClickListener);

        //onCheckPress(  );


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * updateListView
     *
     * @param "" There are no parameters
     * @return Nothing will be returned
     */
    private void updateListView()
    {

        for (int i = 0; i < bookNames.length; i++)
        {
            bookNames[i] = "";
            returnedNames[i] = "";
        }


        quickSort( booksAddedArray, 0, numBooksAdded - 1);

        //Need for loop that will populate the bookNames array with the book name
        for (int j = 0; j < numBooksAdded; j++)
        {
            bookNames[j] = booksAddedArray[j].getBookName();
        }

        //To use an array as the options for a ListView, we need an ArrayAdapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bookNames);

        booksAdded.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, returnedNames);

        booksAdded.setAdapter(arrayAdapter2);
    }

    /**
     * addBook
     *
     * @param vw is the view related to this method
     * @return Nothing will be returned
     */
    public void addBook(View vw)
    {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        Books toAdd;

        if (bookName.getText().length() == 0)
        {
            errorMessage.setText("You must at least enter the name of the book.");
        }
        else if (numBooksAdded >= booksAddedArray.length)
        {
            errorMessage.setText("You have reached the maximum amount of books allowed in the library book tracker.");
        }
        else
        {
            toAdd = new Books(bookName.getText().toString(), dueDate.getText().toString());

            booksAddedArray[numBooksAdded] = toAdd;

            numBooksAdded ++;

            updateListView();
            inputManager.hideSoftInputFromWindow(dueDate.getApplicationWindowToken(), 0 );
        }
    }

    /**
     * removeElement
     *
     * @param array
     * @param removeIndex
     * @return Nothing will be returned
     */
    private static void removeElement(Books[] array, int removeIndex )
    {
        for ( int i = removeIndex ; i < array.length - 1 ; i++ )
        {
            array[ i ] = array[ i + 1 ] ;
        }

        numBooksAdded --;
    }

    /**
     * onCheckPress
     *
     * @param vw is the view related to this method
     * @return Nothing will be returned
     */
    protected void onCheckPress(View vw) {
        boolean checked = ((CheckBox) vw).isChecked();

        switch (vw.getId()) {
            case R.id.cb_return:
                if (checked)
                {
                    if( numBooksReturned == 10)
                    {

                    }
                    else
                    {

                        for (int i = 0; i < numBooksAdded; i++)
                        {
                            returnedNames[i] = booksAddedArray[elementNo].getBookName();
                        }

                        removeElement(booksAddedArray, elementNo);

                        numBooksReturned ++;
                    }

                    updateListView();
                }
                break;
            case R.id.cb_renew:
                if(checked)
                {
                     BooksActivity.bNewDate.setVisibility(View.VISIBLE);
                     BooksActivity.bNewDateInput.setVisibility(View.VISIBLE);
                }
                else
                {

                }
                break;
        }
    }

    /**
     * quickSort uses the Quick Sort algorithm to sort a list of items in ascending order
     *
     * @param array is the array we are sorting
     * @param low is the beginning index of the section of the array we would like to sort
     * @param high is the ending of index of the section of the array we would like to sort
     * @return Nothing will be returned
     */
    private void quickSort( Books[] array, int low, int high)
    {
        int middle;
        Books pivot;

        int i;
        int j;

        Books toSwap;

        if( low < high)
        {
            middle = low + (high - low) / 2;
            pivot = array[middle];
            i = low;
            j = high;

            while( i <= j )
            {
                while( array[i].getBookName().compareTo(pivot.getBookName()) < 0 )
                {
                    i++;
                }

                while (array[j].getBookName().compareTo(pivot.getBookName()) > 0 )
                {
                    j--;
                }

                if ( i <= j )
                {
                    toSwap = array[i];
                    array[i] = array[j];
                    array[j] = toSwap;

                    i++;
                    j--;
                }
            }

            if( low < j)
            {
                quickSort( array, low, j );
            }

            if ( high > i )
            {
                quickSort( array, i, high );
            }
        }
    }
}
