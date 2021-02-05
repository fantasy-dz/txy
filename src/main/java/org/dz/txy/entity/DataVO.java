package org.dz.txy.entity;

import java.util.List;

public class DataVO {
    private Person person;
    private List<Child> list;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Child> getList() {
        return list;
    }

    public void setList(List<Child> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "DataVO{" +
                "person=" + person +
                ", list=" + list +
                '}';
    }

    static class Person {
        private String name;
        private Integer age;

        public Person() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
    static class Child {
        private String childName;
        private Integer childAge;

        public String getChildName() {
            return childName;
        }

        public Child() {
        }

        public void setChildName(String childName) {
            this.childName = childName;
        }

        public Integer getChildAge() {
            return childAge;
        }

        public void setChildAge(Integer childAge) {
            this.childAge = childAge;
        }

        @Override
        public String toString() {
            return "Child{" +
                    "childName='" + childName + '\'' +
                    ", childAge=" + childAge +
                    '}';
        }
    }
}
