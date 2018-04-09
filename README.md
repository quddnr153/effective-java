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

---
### Rule 3 private 생성자나 enum 자료형은 싱글턴 패턴을 따르도록 설계하라
---

***Singleton (싱글톤) 이란?***
오직 한 개의 클래스 인스턴스만을 갖도록 보장하고, 이에 대한 전역적인 접근점을 제공한다. (GoF 참고)

***Usage***
- 클래스의 인스턴스가 오직 하나여야 함을 보장하고, 잘 정의된 접근점 (access point) 으로 모든 사용자가 접근할 수 있도록 해야 할 때
- 유일한 인스턴스가 서브클래싱으로 확장되어야 하며, 자용자는 코드의 수정 없이 확장된 서브클래스의 인스턴스를 사용할 수 있어야 할 때

***구현 방법***
- Eager initialization
- Static block initialization
- Lazy initialization
- Thread safe initialization
- Bill Pugh Singleton (Initialization on demand holder idiom)
- Enum initialization

각 구현의 키워드 이므로 검색해서 장단점을 보도록하고, 여기서는 Enum initialization 에 대해서 살펴보겠다.

```java
public enum EnumSingleton {
    INSTANCE;

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
```

위 같은 방법은
- 간결하고
- 직렬화 (Serialization)가 자동 처리
- 리플렉션 (Reflection)을 통한 공격에 안전

---
### Rule 4 객체 생성을 막을 때는 private 생성자를 사용하라
---

static method 나 static field 로 사용하기 위한 클래스를 만들 때가 있다.
예를 들면 utility 클래스 들이다. 사실상, 이런 클래스 객체를 만들 필요가 없다.

그렇다면 이런 Utility class 들은 어떻게 설계하면 좋을까?

***Examples***
java.lang.Math, java.lang.Arrays, java.util.Collections 들이 예가 되겠다.

extends 할 필요가 없기에 final class 로 생성하고, 객체를 만들필요 없기에 생성자를 private 으로 선언해준다.

```java
public final class MyUtility {
    private MyUtility() {
        throw new AssertionError();
    }

    public static int plus(int x, int y) {
        return x + y;
    }
}
```

---
### Rule 5 불필요한 객체는 만들지 말라
---

기능적으로 동일한 객체는 필요할 때마다 만드는 것보다 재사용하는 편이 낫다.

아래 예를 보자.

```
String s = new String("Do not");
```

위 문장은 실행될 때마다 String 객체를 만드는 쓸데없는 짓을 하고 있다.

아래와 같이 사용하도록 하자

```
String s = "Like this";
```

***JDK 1.5 Autoboxing 의 안좋은 예***

아래 코드의 문제점을 찾아보자

```java
public class Main {
    private static int inefficientSum(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from must be less than to");
        }

        Integer sum = 0;

        for (int i = from; i <= to; i++) {
            sum += i;
        }

        return sum;
    }
}
```

primitive type 인 int i 가 Integer sum 과 더해지면서 autoboxing 작업으로 Integer 라는 객체가 생성되고 있다.


Boxed type 을 사용해야할 경우가 아니라면,
***객체 표현형 대신 기본 자료형을 사용하고, 생각지도 못한 자동 객체화가 발생하지 않도록 유의하자.***

위 예제는 아래와 고치면 될것이다.

```java
public class Main {
    private static int efficientSum(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from must be less than to");
        }
    
        int sum = 0;
        
        for (int i = from; i<= to; i++) {
            sum += i;
        }
    
        return sum;
    }
    
    private static int java8Sum(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from must be less than to");
        }
    
        return IntStream.range(from, to + 1).sum();
    }
}
```

---
### Rule 8 equals 를 재정의할 때는 일반 규약을 따르라
---

equals 를 재정의할 필요 없는 경우:

- 각각의 객체가 고유하다: ex, Thread
- 클래스에 "논리적 동일성 (logical equality)" 검사 방법이 있건 없건 상관없다: ex, java.util.Random
- 상위 클래스에서 재정의한 equals 가 하위 클래스에서 사용하기에도 적당하다: Set, List, Map 같은경우 Abstract (AbstractSet, AbstractList, AbstractMap) 클래스의 equals 사용

***equals 를 재정의 해야할 때?***

- Object equality (객체 동일성) 이 아닌 logical equality (논리적 동일성) 의 개념을 지원해야 할 때
- 상위 클래스의 equals 가 하위 클래스의 필요를 충족하지 못할 때


예를 들면, value class (값 클래스) 의 경우 logical equality 를 알기위 해 사용.

또한, Map 의 Key 나 Set 의 원소로 사용할 수 있다.

***Object class specification***

The equals method implements an equivalence relation on non-null object references:

- It is reflexive: for any non-null reference value x, x.equals(x) should return true.
- It is symmetric: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
- It is transitive: for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
- It is consistent: for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.
- For any non-null reference value x, x.equals(null) should return false.

The equals method for class Object implements the most discriminating possible equivalence relation on objects; that is, for any non-null reference values x and y, this method returns true if and only if x and y refer to the same object (x == y has the value true).

Note that it is generally necessary to override the hashCode method whenever this method is overridden, so as to maintain the general contract for the hashCode method, which states that equal objects must have equal hash codes.

음... 영어다... 한글로 다시보자

- reflexive: 모든 객체는 자기 자신과 같아야 함
- symmetric: x 와 y 가 같다면, x.equals(y) 와 y.equals(x) 둘다 참이다
- transitive: 간단하게 x.equals(y), y.equals(z) 면 x.equals(z) 를 만족해야 함
- consistent: x.equals(y) 가 true 이든 false 이든 상관없이 항상 동일한 값을 반환해야 함 (말그대로 객체가 변형되지 않았다면 일관성을 유지해야 한다)
- non-nullity: null 과의 equals 비교는 항상 false 이다.

***그렇다면 위와 같은 지침을 지키려면 어떻게 해야 할까?***

1. == 연산자를 사용하여 equals 의 인자가 자기 자신인지 검사하라.
2. instanceof 연산자를 사용하여 인자의 자료형이 정확한지 검사하라.
3. equals 의 인자를 정확한 자료형으로 변환하라.
4. "중요" 필드 각각이 인자로 주어진 객체의 해당 필드와 일치하는지 검사하라.
5. equals 메서드 구현을 끝냈다면, 대칭성, 추이성, 일관성의 세 속성이 만족되는지 unit test 를 이용하여 검토하라.

추가적으로,

- equals 를 구현할 때는 hashCode 도 재정의하라
- equals 메서드의 인자형을 Object 에서 다른 것으로 바꾸지 마라.