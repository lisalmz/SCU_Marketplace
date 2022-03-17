package com.example.scu_mp;

import android.widget.RadioButton;

public class Card {
    String cardnumber;
    String cardname;

    public Card(String cardnumber, String cardname) {
        this.cardnumber = cardnumber;
        this.cardname = cardname;
    }


    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }
}
