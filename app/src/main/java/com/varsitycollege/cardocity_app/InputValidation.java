package com.varsitycollege.cardocity_app;

import android.widget.Toast;

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
    private void ErrMsg(String sError)
    {
        Toast.makeText(null, sError, Toast.LENGTH_SHORT).show();
    }

    // TODO Validate password - 6 chars, contains a number, contains capital letter
    // TODO Validate email
}
