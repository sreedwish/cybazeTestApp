package com.sreedwish.cybazeapp.models;

public class Bean_data {

    int button;

    boolean checked = true;

    int number,count, total;

    String t1,t2,t3,t4;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        setT2(String.valueOf(number));
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        setT3(String.valueOf(count));
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        setT4(String.valueOf(total));
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public String getT1() {
        return t1;
    }

    public void  setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT3() {
        return t3;
    }

    void setT3(String t3) {
        this.t3 = t3;
    }

    public String getT4() {
        return t4;
    }

    void setT4(String t4) {
        this.t4 = t4;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    @Override
    public String toString() {
        return "Bean_data{" +
                "button=" + button +
                ", checked=" + checked +
                ", number=" + number +
                ", count=" + count +
                ", total=" + total +
                ", t1='" + t1 + '\'' +
                ", t2='" + t2 + '\'' +
                ", t3='" + t3 + '\'' +
                ", t4='" + t4 + '\'' +
                '}';
    }
}
