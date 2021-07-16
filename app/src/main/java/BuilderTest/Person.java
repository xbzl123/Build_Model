package BuilderTest;

public class Person {
    private String name;
    private int age;
    private double weight;
    private double height;

    Person(Builder builder){
        this.name = builder.name;
        this.age = builder.age;
        this.height = builder.height;
        this.weight = builder.weight;
    }

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

    static public class Builder{
        private String name;
        private int age;
        private double weight;
        private double height;

        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder age(int age){
            this.age = age;
            return this;
        }
        public Builder weight(double weight){
            this.weight = weight;
            return this;
        }
        public Builder height(double height){
            this.height = height;
            return this;
        }
        public Person build(){
            return new Person(this);
        }
    }

    public String print(){
        return "My name is "+name+", the age is "+age+", the height is "
                +height+", the weight is "+weight;
    }
}
