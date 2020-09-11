package com.developers.shop.Utils;

/**
 * Created by Alee on 19-Dec-16.
 */
public class Content {

    int _id;
    String _name;
    String _quantity;
    String _price;

    public Content(){
    }
    // constructor
    public Content(int id, String name, String quantity, String _price){
        this._id = id;
        this._name = name;
        this._quantity = quantity;
        this._price = _price;
    }
    // constructor
    public Content(String name, String quantity, String _price){
        this._name = name;
        this._quantity = quantity;
        this._price = _price;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String get_quantity() {
        return _quantity;
    }

    public void set_quantity(String _quantity) {
        this._quantity = _quantity;
    }

    public String getPrice(){
        return this._price;
    }

    public void setPrice(String _price){
        this._price = _price;
    }

}