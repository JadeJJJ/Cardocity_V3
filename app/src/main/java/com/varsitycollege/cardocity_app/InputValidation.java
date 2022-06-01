package com.varsitycollege.cardocity_app;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class InputValidation {
    //Constructor method
    public InputValidation()
    {

    }

    //Method to validate a string is not null or empty
    public boolean NotNullorEmpty(String sInput)
    {
        return !(sInput.isEmpty() || sInput.equals(null));
    }

    //Method to check if a string contains a number
    public boolean ContainsNumber(String sInput)
    {
        boolean bFlag = false;
        char[] arrInput = sInput.toCharArray();

        for(char c: arrInput)
        {
            if(Character.isDigit(c))
            {
                bFlag = true;
                break;
            }
        }
        return bFlag;
    }

    //Method to check if a string contains an Uppercase character
    public boolean ContainsUpcase(String sInput)
    {
        boolean bFlag = false;
        char[] arrInput = sInput.toCharArray();

        for(char c: arrInput)
        {
            if(Character.isUpperCase(c))
            {
                bFlag = true;
                break;
            }
        }
        return bFlag;
    }

/* CODE NOT NEEDED ---------------------------------------------------------------------------------
    // method to validate if it is a valid username
    public boolean ValidateUsername(String sUsername)
    {
        boolean[] arrFlag = {true, true, true, true};
        if (!NotNullorEmpty(sUsername))
        {
            ErrorMessage("Username is Empty!");
            arrFlag[0] = false;
        }
        if(!ContainsNumber(sUsername))
        {
            ErrorMessage("Username does not contain a number!");
            arrFlag[1] = false;
        }
        if(sUsername.length() >= 5)
        {
            ErrorMessage("Username is too short");
            arrFlag[2] = false;
        }
        if(sUsername.length() <= 20)
        {
            ErrorMessage("Username is too long");
            arrFlag[3] = false;
        }
        boolean bFlag = true;
        for (int i = 0; i < arrFlag.length; i++)
        {
            if (!arrFlag[i])
            {
                bFlag = false;
                break;
            }
        }

        return bFlag;
    } */

    // this method shows an error message with the string it is given
    public void msg(String sMsg, Context context) {
        Toast.makeText(context, sMsg, Toast.LENGTH_SHORT).show();
    }

    // validates the password
    public boolean ValidatePassword(String sPass, Context context) {
        if (sPass.length() <= 5) //(sPass.length() == 6)
        {
            msg("Password Must be 6 Characters!!", context);
            return false;
        } else if (!ContainsNumber(sPass))
        {
            msg("Password Must Contain a Number!!", context);
            return false;
        } else if (!ContainsUpcase(sPass))
        {
            msg("Password Must Contain a Capital Letter!!", context);
            return false;
        }

        return true;
    }

    // TODO Validate password - 6 chars, contains a number, contains capital letter

}
