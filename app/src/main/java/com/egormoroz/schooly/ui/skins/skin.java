package com.egormoroz.schooly.ui.skins;

import com.egormoroz.schooly.ui.chat.User;


import com.egormoroz.schooly.ui.chat.User;

import java.util.ArrayList;



public class skin
{

    private int SkCol;
    private int Head;
    private int Body;
    private int Shoes;
    private int Accessories;
    private ArrayList<User> users;

    public skin (int SkCol, int Head, int Body, int Shoes, int Accessories, ArrayList<User> users)
    {
        this.SkCol = SkCol;
        this.Head = Head;
        this.Body = Body;
        this.Shoes = Shoes;
        this.Accessories = Accessories;
        this.users = users;
    };
    public int getCol() {
        return SkCol;
    }

    public int getHead() {
        return Head;
    }
    public int getBody() {
        return Body;
    }

    public int getShoes() {
        return Shoes;
    }
    public int getAccessories(){return Accessories;}
}
