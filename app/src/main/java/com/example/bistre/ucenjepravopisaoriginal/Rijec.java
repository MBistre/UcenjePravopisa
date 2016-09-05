package com.example.bistre.ucenjepravopisaoriginal;

/**
 * Created by Bistre on 4.9.2016..
 */


public class Rijec {

    public int _id;
    public String Tocno = "";
    public String Netocno = "";

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getTocno() {
        return Tocno;
    }

    public void setTocno(String tocno) {
        this.Tocno = tocno;
    }

    public String getNetocno() {
        return Netocno;
    }

    public void setNetocno(String netocno) {
        this.Netocno = netocno;
    }

    //constructor
    public Rijec (int id, String tocno, String netocno) {
        this._id = id;
        this.Tocno = tocno;
        this.Netocno = netocno;
    }

    public Rijec (){

    }
}
