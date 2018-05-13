# effective-java-3e

## Reference

[Effective java 3rd](https://www.amazon.com/Effective-Java-3rd-Joshua-Bloch/dp/0134685997)

## Introduction

오늘 기준으로 아직 Effective Java 3rd 번역판은 안나와있다. 그렇기 때문에 원판을 요약을 해야하는 상황이다. 영어를 번역하면 의미전달이 부족할 수 있기에, 확실하게 번역을 못하겠는 부분은 영어로 전달하려한다.

들어가기에 앞서, Preface 에 있는 아주 중요한 문장을 하나 읽고 가보자.

> Small is beautiful, but simple ain't easy. (San Jose)

위 글을 읽고 공감이 되는가?

실무에서 2년정도 Java 로 짜여진 코드를 보면서 (내가 짠 코드 포함), Business logic 이 작고 아름다운데 게다가 단순하기 까지한 코드는 거의 (?) 본적이 없다. 물론 대부분 10년 정도 유지보수를 이어온 코드들이라 그럴 수 있다.

그렇다, small and simple 은 Clean Code 에서 말하는 읽기 쉬운 코드이며, test 하기도 쉽고, 변경하기도 쉬운 코드라고 보면된다.

우리는 위 문장을 기억하고 effective java 를 읽어 나가면 되겠다.


---
## Chapter 2 Creating and Destroying Objects
---

---
### rule 1 Consider static factory methods instead of constructors
---

사실 이 부분은 [2rd rule 1](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-1-%EC%83%9D%EC%84%B1%EC%9E%90-%EB%8C%80%EC%8B%A0-%EC%A0%95%EC%A0%81-%ED%8C%A9%ED%84%B0%EB%A6%AC-%EB%A9%94%EC%84%9C%EB%93%9C%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%A0-%EC%88%98-%EC%97%86%EB%8A%94%EC%A7%80-%EC%83%9D%EA%B0%81%ED%95%B4-%EB%B3%B4%EB%9D%BC) 과 전혀 다른 부분이 없다.

추가 된 부분은 Java 8 에 대한 이야기 이다.

Java 8 이전에는 interface 가 static method 를 가질 수 없었다. 하지만, Java 8 부터는 아래와 같이 가능해졌다.

```java
public class InterfaceWithStaticMethods {
    public static void main(String[] args) {
        // usage of static method 'of' in Stream interface
        List<Integer> numbers = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList());

        System.out.println(numbers);

        // usage of static method 'reverseOrder' in Comparator interface
        numbers.sort(Comparator.reverseOrder());

        System.out.println(numbers);
    }
}
```

java.util, java.lang 등 Java 의 유용한 API 들에 static method 가 추가 되었다. 추가된 method 의 양이 방대하기에 하나하나 예를 들 순 없다.

하지만 하나 팁이 있다면, java 의 data structure 를 다룰때, 어라 이런거 되려나? 라고 생각이 들면 일일히 API 를 찾아보거나, just google it 을 하면 유용한 method 들을 찾을 수 있다.

하나만 더 예를 들면, 실무 (business logic) 에서는 Primitive value 를 자료구조로 만들지 않고 대부분, custom object 를 사용할 것이다 (Employee, Department, Seller 등).

위 object 의 list 를 정렬한다고 생각해보자. 그럼 어떻게 했었나?? Java 8 이전에는 다른 라이브러리를 사용하지 않는다는 가정하에 ```if else if else if else.....``` 의 반복이 될 것을 상상한다.

하지만, Java 8 에서는 아래와 같이 사용하면 아주 간단하게 해결된다.

```java
public class ComparatorInterfaceUsage {
    public static void main(String[] args) {
        Employee employee1 = new Employee("abcd", 10, 60);
        Employee employee2 = new Employee("abcd", 11, 50);
        Employee employee3 = new Employee("abcd", 9, 12);

        List<Employee> employees = Arrays.asList(employee1, employee2, employee3);

        System.out.println(employees);

        // Comparator.comparing, .thenComparing, compare example
        employees.sort((
                (o1, o2) -> Comparator.comparing(Employee::getName)
                        .thenComparing(Employee::getAge)
                        .thenComparing(Employee::getWeight)
                        .compare(o1, o2)
        ));

        System.out.println(employees);
    }
}

class Employee {
    private String name;
    private int age;
    private int weight;

    public Employee(String name, int age, int weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Employee{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", weight=").append(weight);
        sb.append('}');
        return sb.toString();
    }
}
```

---
### rule 2 Consider a builder when faced with many constructor parameters
---

rule 2 도 second edition 과 예제 코드만 다르고 내용은 같다 ([second edition rule 2](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-2-%EC%83%9D%EC%84%B1%EC%9E%90-%EC%9D%B8%EC%9E%90%EA%B0%80-%EB%A7%8E%EC%9D%84-%EB%95%8C%EB%8A%94-builder-%ED%8C%A8%ED%84%B4-%EC%A0%81%EC%9A%A9%EC%9D%84-%EA%B3%A0%EB%A0%A4%ED%95%98%EB%9D%BC)).

아래는 이전 예제와는 조금 다르게 사용하도록 해봤다.

```java
public class BuilderPatternUsage {
    public static void main(String[] args) {
        Employee employee = Employee.builder("quddnr153", "Byungwook Lee").withSex(Employee.Sex.MALE).build();

        System.out.println(employee);
    }
}

class Employee {
    public enum Sex {
        MALE, FEMALE, NONE
    }

    private long seq;
    private String id;
    private String name;
    private String position;
    private String department;
    private LocalDate birthDay;
    private Sex sex;

    // Getter skip

    private Employee(Builder builder) {
        this.seq = builder.seq;
        this.id = builder.id;
        this.name = builder.name;
        this.position = builder.position;
        this.department = builder.department;
        this.birthDay = builder.birthDay;
        this.sex = builder.sex;
    }

    // id and name are required, others are optional
    static Builder builder(final String id, final String name) {
        return new Builder(id, name);
    }

    public static class Builder {
        private long seq;
        private String id;
        private String name;
        private String position;
        private String department;
        private LocalDate birthDay;
        private Sex sex;

        public Builder(final String id, final String name) {
            this.id = id;
            this.name = name;
        }

        public Builder withSeq(final long seq) {
            this.seq = seq;
            return this;
        }

        public Builder withPosition(final String position) {
            this.position = position;
            return this;
        }

        public Builder withDepartment(final String department) {
            this.department = department;
            return this;
        }

        public Builder withBirthDay(final LocalDate birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public Builder withSex(final Sex sex) {
            this.sex = sex;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Employee{");
        sb.append("seq=").append(seq);
        sb.append(", id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", position='").append(position).append('\'');
        sb.append(", department='").append(department).append('\'');
        sb.append(", birthDay=").append(birthDay);
        sb.append(", sex=").append(sex);
        sb.append('}');
        return sb.toString();
    }
}
```

코드를 보면 domain 객체를 설계하기 좀 어려울 (?) 수 있지만, 사용자 입장에서 immutable 하고 readable 한 객체를 만들 수 있다는 장점이 있다고 생각한다 (require 한 field 와 optional 한 field 도 구분 가능, 물론 위에서 optional 한 값에 default 값을 넣을 수도 있다.).

