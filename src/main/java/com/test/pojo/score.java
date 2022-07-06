package com.test.pojo;

public class score {
   private String name;
   private int age;
    private String score;
    public String getName()
    {
        return name;
    }
    public String getScore()
    {
        return score;
    }
    public int getAge()
    {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public  void setScore(String score)
    {
        this.score=score;
    }

    @Override
    public String toString() {
        return "score{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score='" + score + '\'' +
                '}';
    }
}
