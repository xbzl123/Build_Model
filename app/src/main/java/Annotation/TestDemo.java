package Annotation;

import java.lang.reflect.Field;

public class TestDemo {
    @TestAnnotation(value = "hello Annotation!")
    String testAnnotation11;
    public static void main(String[] args){
        try {
            Class cls = Class.forName("Annotation.TestDemo");
            Field[] field1 = cls.getDeclaredFields();
            for(Field field:field1){
                TestAnnotation testAnnotation = field.getAnnotation(TestAnnotation.class);
                System.out.println(testAnnotation.value());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
