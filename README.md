# effective-java-2e

## Introduction
Joshua Bloch 가 publish 한 Java engineer 의 필독서인 Effective Java 2/e 을 공부하며, 코드로 작성해보는 Repository 이다.

다음 진행 사항으로는 3판을 공부할 것이다.

---
### Rule 1 생성자 대신 정적 팩터리 메서드를 사용할 수 없는지 생각해 보라
---

***static factory method (정적 팩터리 메서드) 는 무엇일까?***

- 클래스를 통해 객체를 만드는 방법 중 하나 (constructor - 생성자와 같은)

***그렇다면 어떤식으로 사용되고 있나?***

- 우리가 흔히 알고 있는 String, Integer, Boolean 등 클래스에는 valueOf 라는 static factory method 가 있다

[Employee 예제](https://github.com/quddnr153/effective-java-2e/blob/master/src/main/java/bw/effective/java/rule01/Employee.java)
```java
public class Employee {
    private String name;
    private String position;

    private Employee() {}

    private Employee(final String name, final String position) {
        this.name = name;
        this.position = position;
    }

    public static Employee getDeveloper(final String name) {
        return new Employee(name, "developer");
    }

    public static Employee getDesigner(final String name) {
        return new Employee(name, "designer");
    }
}
```
위와 같이 getDeveloper 와 getDesigner 는 static factory method 로 생성자 대신 객체를 만들어 준다.

***그렇다면 이와 같이 사용하는 장점과 단점은 무엇일까?***

장점:
1. 생성자와는 달리 이름이 있다
2. 생성자와는 달리 호출할 때마다 새로운 객체를 생성할 필요는 없다. (Integer, Boolean 의 valueOf 와같이 이미 만들어 놓은 객체를 사용하거나, cache 해놓고 재사용하는 경우 객체의 불필요한 생성을 방지)
3. 생성자와는 달리 반환값 자료형의 하위 자료형 객체를 반환할 수 있다.
4. 형인자 자료형 (parameterized type) 객체를 만들 때 편하다. (Java 1.6 이하 버전까지는 type inference 가 불가능 하여, generic 을 사용하여 type inference 와 함께 객체생성)

단점:
1. static factory method 만 있는 경우 public 이나 protected 로 선언된 생성자가 없으므로 하위 클래스를 만들 수 없다는 것
2. static factory method 가 다른 static method 와 확연히 구분되지 않는다는 것

---
### Rule 2 생성자 인자가 많을 때는 Builder 패턴 적용을 고려하라
---

[Static factory method](https://github.com/quddnr153/effective-java-2e#rule-1-%EC%83%9D%EC%84%B1%EC%9E%90-%EB%8C%80%EC%8B%A0-%EC%A0%95%EC%A0%81-%ED%8C%A9%ED%84%B0%EB%A6%AC-%EB%A9%94%EC%84%9C%EB%93%9C%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%A0
-%EC%88%98-%EC%97%86%EB%8A%94%EC%A7%80-%EC%83%9D%EA%B0%81%ED%95%B4-%EB%B3%B4%EB%9D%BC) 나 Constructor 는 선택적 인자가 많은 상황에 잘 적응하지 못한다는 문제를 가지고 있다.

예를 들면, 아래와 같은 예제를 보자

```java
public class Employee01 {
    private long seq;
    private String id;
    private String name;
    private String position;
    private String department;
    private LocalDate birthDay;
    private String sex;

   public Employee01(long seq, String id, String name) {
           this.seq = seq;
           this.id = id;
           this.name = name;
       }
   
       public Employee01(long seq, String id, String name, String position) {
           this(seq, id, name);
           this.position = position;
       }
   
       public Employee01(long seq, String id, String name, String position, String department) {
           this(seq, id, name, position);
           this.department = department;
       }
   
       public Employee01(long seq, String id, String name, String position, String department, LocalDate birthDay) {
           this(seq, id, name, position, department);
           this.birthDay = birthDay;
       }
   
       public Employee01(long seq, String id, String name, String position, String department, LocalDate birthDay, String sex) {
           this(seq, id, name, position, department, birthDay);
           this.sex = sex;
       }
}
``` 

위와 같은 패턴을 Telescoping constructor pattern (점층적 생성자 패턴) 이라고 한다.
당연히 위와같은 코드는 잘 동작하지만, member variable 이 많아지게 되면 필요없는 필드를 저장하게 되고, 생성자가 길어지는 흉한 일이 발생하게 된다 (읽기 어려워짐, 심지어 객체만들기도 어려움).

```java
public class Employee02 {
    private long seq;
    private String id;
    private String name;
    private String position;
    private String department;
    private LocalDate birthDay;
    private String sex;

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
```

다음 대안으로는  JavaBeans pattern 이다. 인자가 없는 생성자를 호출하여 객체로부터 만든 다음, setter method 를 호출하여 값을 채운다.

JavaBeans 패턴의 문제는 ***1회의 함수 호출로 객체 생성을 끝낼 수 없으므 로, 객체 일관성 (consistency) 이 일시적으로 깨질 수 있다는 것*** 이다. 또한 ***immutable (변경불가능) 객체를 만들 수 없다는 것*** 이다.

그럼 방법이 없는건가?? 아니다 아래를 보자.

***Builder Pattern***

- 불필요한 생성자를 만들지 않고 객체를 만든다.
- 데이터의 순서에 상관 없이 객체를 만든다.
- Client 가 봤을 때 명시적이고 이해할 수 있다.

코드로 보면 이해하기 쉬워질 것이다. 아래를 보자.

```java
public class Employee03 {
    private long seq;
    private String id;
    private String name;
    private String position;
    private String department;
    private LocalDate birthDay;
    private String sex;
    
    // Default Constructor needs, if you use Mybatis
    // Getter 생략

    private Employee03(Builder builder) {
        this.seq = builder.seq;
        this.id = builder.id;
        this.name = builder.name;
        this.position = builder.position;
        this.department = builder.department;
        this.birthDay = builder.birthDay;
        this.sex = builder.sex;
    }

    public static class Builder {
        private long seq;
        private String id;
        private String name;
        private String position;
        private String department;
        private LocalDate birthDay;
        private String sex;

        public Builder(final long seq, final String id, final String name) {
            this.seq = seq;
            this.id = id;
            this.name = name;
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

        public Builder withSex(final String sex) {
            this.sex = sex;
            return this;
        }

        public Employee03 build() {
            return new Employee03(this);
        }
    }
}
```

***주의할 점***
만약 MyBatis 를 사용 중 이라면, Default constructor 는 필요할 테니 만들어 주자...

