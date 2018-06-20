package comjasmynstansfield.httpsgithub.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    private TextView bErrorMessage;

    private static EditText bNewDateInput;
    private static TextView bNewDate;

    private CheckBox bRenew;
    private CheckBox bReturn;

    private Books book;

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

        book = (Books)getIntent().getExtras().get("BOOK_EXTRA");

        bBookName = (TextView) findViewById(R.id.tv_name);
        bDueDate = (TextView) findViewById(R.id.tv_book_date);
        bErrorMessage = (TextView) findViewById(R.id.tv_error_message);
        bNewDate = (TextView) findViewById(R.id.tv_new_date);

        bNewDateInput = (EditText) findViewById(R.id.et_new_date_input);

        bRenew = (CheckBox) findViewById(R.id.cb_renew);
        bReturn = (CheckBox) findViewById(R.id.cb_return);

        bBookName.setContentDescription( book.getBookName() );

        bBookName.setText( book.getBookName() );
        bDueDate.setText( book.getDueDate() );



    }

    /**
     * onCheckPress will check whether the user has checked renew and allows user to input new due date
     *
     * @param vw is the view related to this method
     * @return Nothing will be returned
     *
     */
    public void onCheckPress(View vw) {

        if (vw.getId() == R.id.cb_renew) {
            if(bRenew.isChecked())
            {
                bNewDate.setVisibility(View.VISIBLE);
                bNewDateInput.setVisibility(View.VISIBLE);
            }
            else
            {
                bNewDate.setVisibility(View.INVISIBLE);
                bNewDateInput.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * onBackPressed will send data back to the main activity before it switches back to that activity
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
            bNewDateInput.setVisibility(View.INVISIBLE);
            bNewDate.setVisibility(View.INVISIBLE);
            bErrorMessage.setVisibility(View.VISIBLE);
            bErrorMessage.setText("You cannot check both returned and renewed. Please try again.");
        }
        else if(bRenew.isChecked() && !bReturn.isChecked())
        {
            bErrorMessage.setVisibility(View.INVISIBLE);
            book.setInputDueDate(bNewDateInput.getText().toString());
            goBack.putExtra("BOOK_EXTRA", book);
        }
        else if(!bRenew.isChecked() && bReturn.isChecked())
        {
            bErrorMessage.setVisibility(View.INVISIBLE);
            goBack.putExtra("RETURN_EXTRA", bReturn.isChecked() );
        }

        goBack.putExtra("POSITION_EXTRA", getIntent().getIntExtra("POSITION_EXTRA", 0));

        setResult(0, goBack );

        finish();

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
