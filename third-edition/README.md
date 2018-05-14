# effective-java-3e

# Contents

- [effective-java-3e](#effective-java-3e)
- [Contents](#contents)
  * [Reference](#reference)
  * [Introduction](#introduction)
  * [Chapter 2 Creating and Destroying Objects](#chapter-2-creating-and-destroying-objects)
    + [rule 1 Consider static factory methods instead of constructors](#rule-1-consider-static-factory-methods-instead-of-constructors)
    + [rule 2 Consider a builder when faced with many constructor parameters](#rule-2-consider-a-builder-when-faced-with-many-constructor-parameters)
    + [rule 3 Enforce the singleton property with a private constructor or an enum type](#rule-3-enforce-the-singleton-property-with-a-private-constructor-or-an-enum-type)
    + [rule 4 Enforce noninstantiability with a private constructor](#rule-4-enforce-noninstantiability-with-a-private-constructor)
    + [rule 5 Prefer dependency injection to hardwiring resources](#rule-5-prefer-dependency-injection-to-hardwiring-resources)
    + [rule 6 Avoid creating unnecessary objects](#rule-6-avoid-creating-unnecessary-objects)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

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

---
### rule 3 Enforce the singleton property with a private constructor or an enum type
---

[same as second edition](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-3-private-%EC%83%9D%EC%84%B1%EC%9E%90%EB%82%98-enum-%EC%9E%90%EB%A3%8C%ED%98%95%EC%9D%80-%EC%8B%B1%EA%B8%80%ED%84%B4-%ED%8C%A8%ED%84%B4%EC%9D%84-%EB%94%B0%EB%A5%B4%EB%8F%84%EB%A1%9D-%EC%84%A4%EA%B3%84%ED%95%98%EB%9D%BC)

음... 이 규칙은 정말로 2판과 완전히 똑같은 부분이다.

---
그렇다면 우리가 실무에서 정말 singleton 객체를 설계할 일이 있을까?

솔직히 말하자면, 한번도 singleton 객체를 설계해 본 경험이 거의 없다고 본다 (Spring framework 에서 bean 관리를 해주기 때문에).

물론 framework 를 자체 개발한다면, 자주 사용할 패턴이라고 생각이 한다.

logging, configuration manager, database connection manager, etc...

---
### rule 4 Enforce noninstantiability with a private constructor
---

[same as second edition](https://github.com/quddnr153/effective-java/tree/master/second-edition#rule-4-%EA%B0%9D%EC%B2%B4-%EC%83%9D%EC%84%B1%EC%9D%84-%EB%A7%89%EC%9D%84-%EB%95%8C%EB%8A%94-private-%EC%83%9D%EC%84%B1%EC%9E%90%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC)

---
Utility class 는 언제 만들까?

- 당연히 잘 설계되고 테스트 된 Java API 혹은, [apache commons](https://commons.apache.org/) 를 활용하는 것이 가장 좋음
- 하지만 domain specific 하게 필요할 때 custom 한 utility class 를 만들게 될 것이다.

Utility class 를 만들게 된다면 주의해야 할 점이 있다

- 공통 작업들을 넣으려고 utility class 를 만들어도, 그 utility class 는 oop 를 지켜야 한다!!

모든 공통 작업들을 하나의 utility class 에 넣지말자...

- utility class 는 stateless 해야 한다!!

---
### rule 5 Prefer dependency injection to hardwiring resources
---

***resource 를 고정하지말고 DI ([dependency injection](https://en.wikipedia.org/wiki/Dependency_injection)) 을 사용하라*** 라는 말이다.

rule 4 에서의 static utility class 와 rule 3 에서의 singleton 은 내부 resource 가 변경되서는 안되는 rule 들 이다.

즉, static utility class 에서는 state 를 가지고 있으면 안되고, singleton 은 instance 가 하나만 생성 되기 때문에, 내부 resource 를 변경한다면, 동시성 문제등 아주 심각한 문제를 유발 할 수 있다.

그렇기에 이번 rule 은 DI 를 사용하라고 권장한다.

> Static utility classes and singletons are inappropriate for classes whose behavior is parameterized by an underlying resources.

DI 를 사용한다면,

- immutability
- 여러 clients 가 객체를 공유할 수 있음
- flexibility
- testability
- reusability

위의 장점이 있다.

그렇다면 어떻게 DI 를 적용할 수 있을까?

1. Constructors
2. Static factories
3. Builders

Java 에서 DI framework 로는

1. [Dagger by google](https://google.github.io/dagger/)
2. [Guice by google](https://github.com/google/guice)
3. [Spring](https://projects.spring.io/spring-framework/)

가 있다.

---
DI framework 중 대부분 실무에서 Spring 을 많이 사용할 것이다.

그래도 위 세 가지 framework 를 비교한 argument 를 참조해 보자 ([see more](https://stackoverflow.com/questions/39688830/why-use-develop-guice-when-you-have-spring-and-dagger)).

---
### rule 6 Avoid creating unnecessary objects
---

[same as second edition](https://github.com/quddnr153/effective-java/tree/master/second-edition#rule-5-%EB%B6%88%ED%95%84%EC%9A%94%ED%95%9C-%EA%B0%9D%EC%B2%B4%EB%8A%94-%EB%A7%8C%EB%93%A4%EC%A7%80-%EB%A7%90%EB%9D%BC)

3 판 에서는 2 판과 다른 예를 보여줬다.

```java
public class RomanNumerals {
  static boolean isRomanNumeral(String s) {
    return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
  }
}
```

솔직히 위 예제에서 문제점을 찾으라면, 이 책을 읽기 전에는 저 match 안에 들어가는 정규식을 상수화 했으면 좋겠다는 생각만 있었다.

하지만, 문제가 하나 더 있었다.

```String.matches``` 를 보면 ```Pattern.comile``` 을 호출하면서 새로운 instance를 생성해 사용한다. 당연히 재사용하지 않고 매번 새로운 instance 를 생성하게 된다.

그럼 위 method 가 반복적으로 호출이 된다면, 성능상 문제가 생기게 돼있다.

그럼 어떡할까?

1. 처음 말한 내용 처럼 정규식에 이름을 주자
2. 동시에 Pattern 의 객체를 재사용하도록 해주자

그럼 아래와 같은 객체가 형성이 된다.

```java
public class RomanNumerals {
  private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
  
  static boolean isRomanNumeral(String s) {
    return ROMAN.matcher(s).matches();
  }
}
```

---
교훈을 적어보자.

1. Java API 가 잘 만들어져 있겠지? 라고 생각하지 말고 우선 사용하는 API 에 대해 파악하고 사용하자.
2. autoboxing 을 피하기 위해서는 통일된 type 을 사용하자 (보통은 primitive type 을 선호 한다.).
