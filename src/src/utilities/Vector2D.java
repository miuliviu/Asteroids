package utilities;

import static java.lang.Math.*;

/**
 * Created by lmmiu on 27/01/2017.
 */
// mutable 2D vectors
public final class Vector2D {
    public double x, y;

    // constructor for zero vector
    public Vector2D() {
        this.x=0;
        this.y=0;
    }

    // constructor for vector with given coordinates
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // constructor that copies the argument vector
    public Vector2D(Vector2D v) {
         this.x = v.x;
         this.y = v.y;
    }

    // set coordinates
    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    // set coordinates based on argument vector
    public Vector2D set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    // compare for equality (note Object type argument)
    public boolean equals(Object o) {
        Vector2D newVector = (Vector2D) o;
        return this.x == newVector.x && this.y == newVector.y;
    }

    // String for displaying vector as text
    public String toString() {
        String toS;
        toS="x = " + x +" y = " + y;
        return toS;
    }

    //  magnitude (= "length") of this vector
    public double mag() {
         return Math.sqrt(this.dot(this));
    }

    // angle between vector and horizontal axis in radians in range [-PI,PI]
// can be calculated using Math.atan2
    public double angle() {
        return Math.atan2(this.y, this.x);
    }

    // angle between this vector and another vector in range [-PI,PI]
    public double angle(Vector2D other) {
        double result = Math.atan2(this.y,this.x) - Math.atan2(other.y,other.x);
        if (result < -Math.PI)
            result += 2 * Math.PI;
        if (result > Math.PI)
            result -= 2 * Math.PI;
        return -result;
    }

    // add argument vector
    public Vector2D add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    // add values to coordinates
    public Vector2D add(double x, double y) {
        this.x+=x;
        this.y+=y;
        return this;
    }

    // weighted add - surprisingly useful
    public Vector2D addScaled(Vector2D v, double fac) {
        this.x += v.x*fac;
        this.y += v.y*fac;
        return this;
    }

    // subtract argument vector
    public Vector2D subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    // subtract values from coordinates
    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;

    }

    // multiply with factor
    public Vector2D mult(double fac) {
        this.x = this.x * fac;
        this.y = this.y * fac;
        return this;
    }

    // rotate by angle given in radians
    public Vector2D rotate(double angle) {
        double rx = (this.x * cos(angle)) - (this.y * Math.sin(angle));
        double ry = (this.x * Math.sin(angle)) + (this.y * cos(angle));
        this.x=rx;
        this.y=ry;
        return this;
    }

    // "dot product" ("scalar product") with argument vector
    public double dot(Vector2D v) {
        return this.y*v.y+ this.x*v.x;
    }

    // distance to argument vector
    public double dist(Vector2D v) {
        double d = sqrt((v.y-this.y)*(v.y-this.y)+(v.x-this.x)*(v.x-this.x));
        return d;
    }

    // normalise vector so that magnitude becomes 1
    public Vector2D normalise() {
        double leng = this.mag();
        if(leng !=0 ){
            this.x = this.x/leng;
            this.y = this.y/leng;
        }
        return this;
    }

    // wrap-around operation, assumes w> 0 and h>0
    public Vector2D wrap(double w, double h) {

        this.x=(this.x+w)%w;
        this.y=(this.y+h)%h;
        return this;
    }

    // construct vector with given polar coordinates
    public static Vector2D polar(double angle, double mag) {
        return new Vector2D(mag*cos(angle), mag*sin(angle));
    }

}
