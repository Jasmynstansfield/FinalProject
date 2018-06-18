package comjasmynstansfield.httpsgithub.finalproject;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

/**
 * Created by j.stansfield on 5/29/2018.
 */

public class Books implements Parcelable
{
    private String inputBookName = "";
    private String inputDueDate = "";

    public static Books[] booksAddedArray = new Books[15];
    /**
     * Contact is the default constructor that will get the contact information
     *
     * @param name is the name of the book the user inputs
     * @param date is the due date of the book the user inputs
     */
    public Books (String name, String date)
    {
        inputBookName = name;
        inputDueDate = date;
    }

    protected Books(Parcel in)
    {
        inputBookName = in.readString();
        inputDueDate = in.readString();
    }

    /**
     * getName will get the name of the book added
     *
     * @param "" There are no parameters
     * @return a string that is the book's name
     */
    public String getBookName()
    {
        return inputBookName;
    }

    /**
     * getDueDate will get the due date of the book added
     *
     * @param "" There are no parameters
     * @return a string that is the book's due date
     */
    public String getDueDate()
    {
        return inputDueDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(inputBookName);
        parcel.writeString(inputDueDate);
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };
}

