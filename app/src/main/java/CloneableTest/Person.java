package CloneableTest;

import java.util.ArrayList;

/*
*
* 实现原型模式
*
*
* */
public class Person implements Cloneable {

    private String name;
    private int age;
    private double weight;
    private double height;
    private ArrayList<String> hobby = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    @Override
    public Object clone() {

        Person person = null;
        try {
            person = (Person)super.clone();
            person.name = this.name;
            person.age = this.age;
            person.height = this.height;
            person.weight = this.weight;//浅拷贝，也就是只拷贝了引用
            person.hobby = (ArrayList<String>)this.hobby.clone();//深拷贝
        }catch (Exception e){
            e.getStackTrace();
        }
        return person;
    }

    public ArrayList<String> getHobby() {
        return hobby;
    }

    public void setHobby(ArrayList<String> hobby) {
        this.hobby = hobby;
    }

    public String print(){
        return "My name is "+name+", the age is "+age+", the height is "
                +height+", the weight is "+weight+" ,the hobbies is "+ hobby.toString();
    }
}
