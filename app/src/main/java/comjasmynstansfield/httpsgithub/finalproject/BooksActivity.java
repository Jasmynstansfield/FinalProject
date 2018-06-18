package comjasmynstansfield.httpsgithub.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by j.stansfield on 5/29/2018.
 */

public class BooksActivity extends AppCompatActivity
{
    public static final String EXTRA_BOOKNO = "bookNo";

    private TextView bBookName;
    private TextView bDueDate;

    protected static EditText bNewDateInput;
    protected static TextView bNewDate;

    private CheckBox bRenew;
    private CheckBox bReturn;

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
        setContentView(R.layout.activity_books);

        Books book = (Books)getIntent().getExtras().get("BOOK_EXTRA");

        bBookName = (TextView) findViewById(R.id.tv_name);
        bDueDate = (TextView) findViewById(R.id.tv_book_date);
        bNewDate = (TextView) findViewById(R.id.tv_new_date);
        bNewDateInput = (EditText) findViewById(R.id.et_new_date_input);
        bRenew = (CheckBox) findViewById(R.id.cb_renew);
        bReturn = (CheckBox) findViewById(R.id.cb_return);

        bBookName.setContentDescription( book.getBookName() );

        bBookName.setText( book.getBookName() );
        bDueDate.setText( book.getDueDate() );



    }

    /**
     * onBackPressed
     *
     * @param "" There are no parameters
     * @return Nothing will be returned
     */
    @Override
    public void onBackPressed()
    {
        Intent goBack = new Intent();

        if( bReturn.isChecked() && bRenew.isChecked() )
        {
            MainActivity.errorMessage.setText("You cannot check both returned and renewed. Please try again.");
        }
        else
        {
            goBack.putExtra("RETURN_EXTRA", bReturn.isChecked() );
            goBack.putExtra( "RENEW_EXTRA", bRenew.isChecked() );
        }

        setResult(0, goBack );

        finish();

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
