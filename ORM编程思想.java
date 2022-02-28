package util;

public class ORM编程思想 {
    //ORM编程思想（
    // 一个数据表对应一个Java类，
    // 表中的一条记录对应Java类的一个对象，
    //表中一个字段对应Java类的一个属性
    // ）
    private int id;
    private String age;
    private String name;

    public ORM编程思想(int id, int age, String name) {
    }

    public ORM编程思想(int id, String age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public ORM编程思想() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ORM编程思想{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
