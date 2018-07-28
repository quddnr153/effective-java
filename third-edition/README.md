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
    + [rule 9 Prefer try-with-resources to try-finally](#rule-9-prefer-try-with-resources-to-try-finally)
  * [Chapter 3 Methods Common to All Objects](#chapter-3-methods-common-to-all-objects)
    + [rule 10 Obey the general contract when overriding equals](#rule-10-obey-the-general-contract-when-overriding-equals)
    + [rule 11 Always ***override*** hashCode when you override ***equals***](#rule-11-always----override----hashcode-when-you-override----equals---)
    + [rule 12 Always override toString](#rule-12-always-override-tostring)
    + [rule 14 Consider implement Comparable](#rule-14-consider-implement-comparable)
  * [Chapter 4 Classes and Interfaces](#chapter-4-classes-and-interfaces)
    + [rule 15 Minimize the accessibility of classes and members](#rule-15-minimize-the-accessibility-of-classes-and-members)
    + [rule 16 In public classes, use accessor methods, not public fields](#rule-16-in-public-classes--use-accessor-methods--not-public-fields)
    + [rule 17 Minimize mutability](#rule-17-minimize-mutability)
    + [rule 18 Favor composition over inheritance](#rule-18-favor-composition-over-inheritance)
    + [rule 20 Prefer interfaces to abstract classes](#rule-20-prefer-interfaces-to-abstract-classes)
    + [rule 21 Design interfaces for posterity](#rule-21-design-interfaces-for-posterity)
    + [rule 22 Use interfaces only to define types](#rule-22-use-interfaces-only-to-define-types)
    + [rule 23 Prefer class hierarchies to tagged classes](#rule-23-prefer-class-hierarchies-to-tagged-classes)
  * [Chapter 7 Lambdas and Streams](#chapter-7-lambdas-and-streams)
    + [rule 42 Prefer lambdas to anonymous classes](#rule-42-prefer-lambdas-to-anonymous-classes)
    + [rule 43 Prefer method references to lambdas](#rule-43-prefer-method-references-to-lambdas)

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

---
### rule 9 Prefer try-with-resources to try-finally
---

Java API 에는 ```InputStream```, ```OuputStream```, ```Connection``` 과 같이 resource 를 직접 close 해줘야 하는 API 들이 있다.

보통 Java 7 이전 까지는 아래 convention 을 따랐다 ([Java doc ref](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)).

```
static String readFirstLineFromFileWithFinallyBlock(String path) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(path));
    try {
        return br.readLine();
    } finally {
        if (br != null) br.close();
    }
}
```

하지만 Java 7 부터는 ```java.lang.AutoCloseable``` interface 가 추가 되면서, 이 interface 를 구현한 Object 는 try-with-reources 를 사용할 수 있다.

그럼 뭔지 한번 봐보자.

```
static String readFirstLineFromFile(String path) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        return br.readLine();
    }
}
```

위와 같이 resource 를 close 해줘야 하는 객체를 try (...) 안에 선언해 주면 boilerplate 를 없앨 수 있다.

***try-with-resources*** 를 사용하면 clearer, shorter 하다.

---
Java 7 이상을 사용한다면, try-with-resources 구문을 사용하도록 하자!

---
## Chapter 3 Methods Common to All Objects
---

```Object``` 에 정의된 non-final method (equals, hashCode, toString, clone and finalize) 에는 explicit ***general contracts*** 가 있다.

위 method 를 재정의 하는 class 는 general contracts 를 따라야 한다 (Java API Specification 에 나와 있음).

---
### rule 10 Obey the general contract when overriding equals
---

[same as second edition](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-8-equals-%EB%A5%BC-%EC%9E%AC%EC%A0%95%EC%9D%98%ED%95%A0-%EB%95%8C%EB%8A%94-%EC%9D%BC%EB%B0%98-%EA%B7%9C%EC%95%BD%EC%9D%84-%EB%94%B0%EB%A5%B4%EB%9D%BC)

---
### rule 11 Always ***override*** hashCode when you override ***equals***
---

[same as second edition rule 9](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-9-equals-%EB%A5%BC-%EC%9E%AC%EC%A0%95%EC%9D%98%ED%95%A0-%EB%95%8C%EB%8A%94-%EB%B0%98%EB%93%9C%EC%8B%9C-hashcode-%EB%8F%84-%EC%9E%AC%EC%A0%95%EC%9D%98%ED%95%98%EB%9D%BC)

---
second edition 에 정리한 내용에 Java 7 에서 추가된 ```Object.hash()``` 에 대해 언급했었다.

3판을 읽으면서 놓친 부분을 추가적으로 설명하자면,

```Objects.hash()``` 는 arguments 들을 넘기고 이는 array 생성 뿐만 아니라, arguments 가 primitive type 이라면 boxing 과 unboxing 으로 느리게 동작하게 된다.

성능이 주요 이슈 사항이 아닌 경우라면 사용해도 상관없지만, 성능이 중요한 시스템에서는 hash code 를 사용할 때마다 계속 계산하는 것보다 아래와 같이 caching 을 고려해 봐야한다.

```java
public class Employee {
    private int seq;
    private String name;
    private String id;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Employee employee = (Employee) obj;
        return seq == employee.seq && Objects.equals(name, employee.name) && Objects.equals(id, employee.id);
    }

    // hashCode method with lazily initialized cached hash code
    // Automatically initialized to 0
    private int hashCode;

    @Override
    public int hashCode() {
        int result = hashCode;

        if (result == 0) {
            result = Integer.hashCode(seq);
            result = 31 * result + name.hashCode();
            result = 31 * result + id.hashCode();
        }

        return result;
    }
}
```

---
### rule 12 Always override toString
---

[same as second edition rule 10](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-10-tostring-%EC%9D%80-%ED%95%AD%EC%83%81-%EC%9E%AC%EC%A0%95%EC%9D%98%ED%95%98%EB%9D%BC)

---
logging framework 를 사용할 때, 버전까지 자세히 기억이 안나지만, toString 을 구현안한 객체는 Object 의 toString 을 보여주기 때문에 아주 쓸모없는 로그를 남기게된다.

2판에서 요약한 내용을 지켜서 toString 을 override 하도록 하자.

> The ```toString``` method should return a concise, useful description of the object, in an aesthetically pleasing format.

---
### rule 14 Consider implement Comparable
---

```Comparable``` interface 를 구현하는 클래스의 instance 들은 natural ordering 을 가진다.

```java
public interface Comparable<T> {
    public int compareTo(T o);
}
```

예를 들면, ```Integer``` class 를 보자.

```java
public final class Integer extends Number implements Comparable<Integer> {
    // 생략
    
    public int compareTo(Integer anotherInteger) {
            return compare(this.value, anotherInteger.value); 
    }
    
    public static int compare(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}
```

위와 같이 ```Comparable``` 을 구현하면, 다양한 generic algorithms 과 collection implementations 들에 호환성을 갖게 된다 (아주 작은 노력으로 엄청난 효과를 볼수 있다).

- ```Arrays.sort()``` 를 사용하여 정렬이 쉬움
- 검색하거나 Min / Max 값을 계산히기 쉬움
- 등등

[The general contract of the compareTo method](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)

---
second edition 에서는 compareTo method 를 구현 할 때, ```<``` 와 ```>``` operators 를 사용해서 integral primitive fields 를 비교하라고 설명했다.

하지만, Java 7 부터는 static compare methods 가 추가 됐다. 그렇기 때문에, 이전 버전에서 설명한 relational operators 를 이용해서 구현하는 것은 verbose and error-prone and 더 이상 추천하는 방식이 아니다.

또한, Java 8 에서 comparator construction methods 가 추가 되면서, 더 손 쉽게 ```comparable``` 을 구현할 수 있게 됐다 ([예제 코드 23 line](https://github.com/quddnr153/effective-java/blob/master/third-edition/src/main/java/bw/effective/java/rule01/ComparatorInterfaceUsage.java)).

```Comparator``` interface 를 참고하면 아주 유용한 method 들이 추가된 것을 볼 수 있다 ([Comparator interface api](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html))

---
## Chapter 4 Classes and Interfaces
---

---
### rule 15 Minimize the accessibility of classes and members
---

[same as second edition rule 13](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-13-%ED%81%B4%EB%9E%98%EC%8A%A4%EC%99%80-%EB%A9%A4%EB%B2%84%EC%9D%98-%EC%A0%91%EA%B7%BC-%EA%B6%8C%ED%95%9C%EC%9D%80-%EC%B5%9C%EC%86%8C%ED%99%94%ED%95%98%EB%9D%BC)

class 와 member 의 accessibility 에 관련해서 Java 9 부터 추가된 feature 가 있다.

***Modules***
- [oracle ref](https://www.oracle.com/corporate/features/understanding-java-9-modules.html)
- [journal dev ref](https://www.journaldev.com/13106/java-9-modules)
- [intellij - getting started](https://www.jetbrains.com/help/idea/getting-started-with-java-9-module-system.html)

---
### rule 16 In public classes, use accessor methods, not public fields
---

```java
// NOT RECOMMENDED
class Point {
    public double x;
    public double y;
}
```

Java 를 알고 OOP 를 공부했다면, 위와 같은 코드를 작성하는 사람은 없을 것이다.

Fields 인 x, y 가 외부로 공개 (Information hiding 위반) 되면서 아래와 같은 *Encapsulation* 의 장점을 제공하지 못한다 ([ref 1](https://www.quora.com/What-is-the-primary-benefit-of-Encapsulation), [ref 2](http://www.cems.uwe.ac.uk/~jsa/UMLJavaShortCourse09/CGOutput/Unit3/unit3(0809)/page_13.htm)).

- Encapsulation promotes maintenance
- Code changes can be made independently
- Increases usability

보통은 Java API 를 참고하면서 개발을 하면 좋을 텐데, 예외의 경우가 있다.

```java.awt``` package 에 있는 ```Point``` class 와 ```Dimension``` class 는 fields 가 public 으로 선언 돼 있다. 따라하면 안되는 부분이므로 넘어가도록 하자!!

---
### rule 17 Minimize mutability
---

[same as second edition rule 15](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-15-%EB%B3%80%EA%B2%BD-%EA%B0%80%EB%8A%A5%EC%84%B1%EC%9D%84-%EC%B5%9C%EC%86%8C%ED%99%94%ED%95%98%EB%9D%BC)

Java platform libraries 에는 ```String, BigInteger, BigDecimal``` 과 같은 Immutable class 들이 있다.

---
실무에서 business logic 을 구현하다 보면, 내부의 상태를 바꾸는 객체를 개발하는 일이 많다. 하지만 조금만 더 잘 생각해 본다면, immutable 하게 개발 할 수 있다.

팁이 하나 있다면, 객체의 상태를 바꿀 때, 한번더 이 생각을 해보자.
> ***정말로 여기서 객체를 수정해야 하나? 객체 생성을 수정단계에서 생성한다면 수정없이 가능하지 않을까?***

---
### rule 18 Favor composition over inheritance
---

[same as second edition rule 16](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-16-%EA%B3%84%EC%8A%B9-inheritance%ED%95%98%EB%8A%94-%EB%8C%80%EC%8B%A0-%EA%B5%AC%EC%84%B1-composition%ED%95%98%EB%9D%BC)

세 가지 키워드를 정리해 보자.

- Composition [wikipedia](https://en.wikipedia.org/wiki/Object_composition)

Composition (구성) 은 객체들 사이에 "has a" 관계가를 가질 때 사용한다. 보통 composed objects 는 다른 object 의 member 로 구성한다.

예를 들면, 아래와 같다.

```java
public class House {
    private List<Door> doors;
    private List<Window> windows;
    // others
}
```

위 관계를 "House 는 doors 와 windows 그리고 다르것들로 구성되어 있다." 라고 말한다.

- forwarding

Each instance method in the new class, invokes the corresponding method on the contained (Composed) instance of the existing class and returns the results.

```java
public class House {
    private List<Door> doors;
    private List<Window> windows;

    // invokes the corresponding method
    public boolean openTheDoor(String name) {
        return doors.stream()
                .filter(door -> door.getName().equals(name))
                .findFirst()
                .orElseGet(() -> new Door(name))
                .openTheDoor(name);
    }
}
```

위 예제가 House 의 ```openTheDoor(String)``` method 를 호출하는 것이 구성하고 있는 door 의 ```openTheDoor(String)``` 을 호출 하고 있다.

- wrapper (aka, ***Decorator*** pattern - [ref](http://www.javapractices.com/topic/TopicAction.do?Id=258))

> The basic idea of a wrapper is to call-forward to an underlying object, while simultaneously allowing for new code to be executed just before and/or just after the call. Wrappers can be chained together, one after another. In this way, you can mix-and-match behaviors in various ways.

Wrapper 의 기본 원리는 underlying object (원시 객체) 의 호출 직전 혹은 직후에 새로운 코드를 실행하도록 call-forward 하는 것이다. Wrappers 는 차례로 연결 될 수 있기에, 다양한 방법으로 동작을 짜 맞출 (mix-and-match) 수 있다.

Wrapper 를 사용 하면 클래스의 구현을 변경할 필요 없고 클래스를 확장할 필요 없이 객체의 동작이 변경된다.

[Wrapper Demo](https://github.com/quddnr153/effective-java/blob/master/third-edition/src/main/java/bw/effective/java/rule18/WrapperDemo.java)

---
Inheritance (계승) 은 두 classes 사이에 "is-a" 관계가 존재할 때 만 사용한다.

> class B is a class A

```java
public class B extends A {
    // somethings
}
```

inheritance 를 하기 전에 스스로 질문을 해보자:
> 정말로 B 가 A 인가? (Is every B really an A?)

> 확장 (extends) 하려는 class 가 결함 (flaws) 을 가지고 있나? (Does the class that you contemplate extending have any flaws in its API?)

---
### rule 20 Prefer interfaces to abstract classes
---

자바는 multiple implementations 를 허용하는 두 가지 방법을 제공한다 (interface, abstract class).

***Interfaces vs Abstract classes***

가장 큰 차이는 interface 는 implements keyword 를 사용하고, abstract class 는 extends keyword 를 사용한다. 무슨 말이냐면, abstract class 를 구현하는 class 를 만들 때는 super class - sub class 관계가 강제 된다는 말이다.

자바는 single inheritance 만 허용하기 때문에, 위 제약사항은 아주 큰 차이를 보이게 된다.

(이 외의 차이점은 google 에 ```java interface vs abstract class``` 를 검색해보자.)

- abstract class 의 inheritance 제약 사항 때문에, interface 의 장점은 기존 class 에 새로운 interface 를 새로 끼워넣기 좋다는 것이다.


- 또한, Interface 는 mixin ([믹스인](https://en.wikipedia.org/wiki/Mixin)) 을 정의하는데 이상적이다.

> a Mixin is a class that contains methods for use by other classes without having to be the parent class of those other classes. How those other classes gain access to the mixin's methods depends on the language. Mixins are sometimes described as being "included" rather than "inherited".

- Interface 는 nonhierarchical type framework 를 만들 수 있도록 한다.

nonhierarchical ???? 무슨말이지 (문자그대로 계층이 없다는 의미)?? 예를 보면 알기 쉽다.

```java
interface FrontEndDeveloper {
    // do somethings
}

interface BackEndDeveloper {
    // do somethings
}

interface FullStackDeveloper implements FrontEndDeveloper, BackEndDeveloper {
    // do somethings
}
```

위 예제를 보면 FontEndDeveloper 와 BackEndDeveloper 가 있고, 두 능력을 가지고 있는 사람을 우리는 FullStackDeveloper 라고 불른다 (Full-stack 개발자를 직접 본적은 없다...).

이는 interface 로 선언했기에 가능한 계층구조이다.

- Interfaces enale safe, powerful functionality enhancements via the wrapper class idiom ([wrapper demo](https://github.com/quddnr153/effective-java/blob/master/third-edition/src/main/java/bw/effective/java/rule18/WrapperDemo.java))

// TODO: Skeletal implementation

---
### rule 21 Design interfaces for posterity
---

제목 의미: interface 설계를 잘 하자!!! 라는 의미 (미래의 나 혹은 다른 개발자들을 위해서)

Java 8 이전에는 구현체를 깨트리지 않고 interface 에 methods 를 추가하는 것은 불가능 했다.

하지만 Java 8 부터는 ***[default method](https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html)*** 가 추가 되었다.

> Default methods enable you to add new functionality to the interfaces of your libraries and ensure binary compatibility with code written for older versions of those interfaces.

---
주로 Java 8 의 Lambdas 의 사용을 용이하게 하기 위해서 core collection interfaces (Collection, List, Map 등) 에 default methods 가 추가 되었다.

실상 위 default method 의 추가로 existing implementation 이 안전하게 동작한다는 것은 아니다.

널리 사용 되는 interface 를 만들었다면, 당연히 default method 의 추가는 조심히 해야한다. release 전에 테스트를 확실히 해야한다는 의미다.

당연 내부적으로 사용하는 interface 였다면, 간단히 추가할 수 있을 것이다.

---
### rule 22 Use interfaces only to define types
---

[same as second edition](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-19-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4%EB%8A%94-%EC%9E%90%EB%A3%8C%ED%98%95%EC%9D%84-%EC%A0%95%EC%9D%98%ED%95%A0-%EB%95%8C%EB%A7%8C-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC)

Interface 에 상수들을 정의해서 사용하는 경우들을 종종 보곤 한다.

```java
// do not use
public interface PhysicalConstants {
    static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    // ...
}
```

***이건 당연히 잘못된 사용이다.***

그럼 어떻게 하면 좋을까??

아래와 같이 enum type 을 사용하거나 non-instantiable utility class 를 사용하는 것이 적합하다.

```java
enum PhysicalConstants {
    AVOGADROS_NUMBER(6.022_140_857e23);
    
    double number;
    
    PhysicalConstants(double number) {
        this.number = number;
    }
}

public class PhysicalConstants {
    private PhysicalConstants() {}
    
    public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
}
```

---
### rule 23 Prefer class hierarchies to tagged classes
---

[same as second edition](https://github.com/quddnr153/effective-java/blob/master/second-edition/README.md#rule-20-%ED%83%9C%EA%B7%B8-%EB%8B%AC%EB%A6%B0-%ED%81%B4%EB%9E%98%EC%8A%A4-%EB%8C%80%EC%8B%A0-%ED%81%B4%EB%9E%98%EC%8A%A4-%EA%B3%84%EC%B8%B5%EC%9D%84-%ED%99%9C%EC%9A%A9%ED%95%98%EB%9D%BC)

---
요약하자면, tagged class 는 좋은 사용법이 아니다. tag field 를 사용하려 한다면 계층구조로 class 를 설계하는 것을 생각해 봐라. 또한 기존 class 가 tag field 를 가지고 있다면, refactoring 해라 나중을 위해서.

---
## Chapter 7 Lambdas and Streams
---

Java 8 에는 function objects 를 쉽게 create 하도록 Functional interfaces, lambdas, method references 가 추가 되었다.

data elements 의 sequence or parallel 처리를 위한 Stream API 도 추가 됐다.

oracle articles

- http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html#features

---
***Function objects***

무슨 말이지? function objects??

글자 그대로 function 이고 object 인! 함수를 function objects (aka functor by Haskell) 라고 한다.

여기서 추가로 나오는 용어는 first class (일급 객체) 이다.

Java 8 이전에 Java 의 ```Function``` 은 first class 가 아니 였다.
하지만, Java 8 부터는 function 을 arguments, return type, data structure 에서 사용할 수 있게 되었다. 그러므로 우리는 Java 8 에서 function 을 first class 라, function objects 라 부를 수 있게 된 것이다.

추가적인 Functional Programming 에 대한 내용은 따로 정리해보자 (TODO).

---
### rule 42 Prefer lambdas to anonymous classes
---

***Anonymous classes [Oracle doc](https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html)***

- 우리나라 말로 익명 클래스라 불린다
- 클래스의 선언과 객체의 생성을 동시에 하기 때문에 단 한번만 사용될 수 있고 오직 하나의 객체만을 생성할 수 있는 일회용 클래스이다

```java
class ExampleAnonymousClass {
    public static void main(String[] args){
      List<String> words = Arrays.asList("hi", "hello", "there");
      
      Collections.sort(words, new Comparator<String>() {
          public int compare(String s1, String s2) {
              return Integer.compare(s1.length(), s2.length());
          }
      });
    }
}
```
- 위 예시를 보면 알 수 있듯이, ```words``` 를 sort 하기 위해 ```Collections.sort``` 의 두번째 인자로 Comparator 의 구현체인 익명클래스를 사용하고 있다

---
이번에는 Lambda expression 을 사용해서 위 예제를 수정해 보겠다.

```java
class ExampleLambdaExpression {
    public static void main(String[] args){
      List<String> words = Arrays.asList("hi", "hello", "there");
      
      // case 1 (specify type parameter and not)
      Collections.sort(words, (String s1, String s2) -> Integer.compare(s1.length(), s2.length()));
      Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
      
      // case 2 (comparator construction method)
      Collections.sort(words, comparingInt(String::length));
      
      // case 3 (List interface default method)
      words.sort(comparingInt(String::length));
    }
}
```

위 예제 코드를 보면 알 수 있듯이, anonymous class 에서의 boilerplate 가 없어진 것을 볼 수 있다.

어떤가?... 좀 더 명확해 보이지 않은가?? 우리가 읽어야 할 코드가 줄었고, 오히려 명확해졌다는 것을 느꼈을 것이다. 못 느껴도 상관없다... 익숙하지 않았을 뿐, 보다 보면 뭐가 더 좋은지 깨닫게 될 것이다.

위 예제에서 case 1 에는 두개의 예제 코드가 있다.

type 을 명시한 것과 명시하지 않은 것.

어떤게 좋아보이나?

아마 상황에 따라 다를 것이다.

Type 을 명시해야 코드가 명확해 질 수도 있고, type 이 없어도 코드를 읽는데 문제가 없을 수 있다.

그래서, 책에서는 아래와 같은 가이드를 해줬다.

- ***type 이 프로그램을 명확하게 해주지 않는다면, 빼버려라! (Omit the types of all lambda parameters unless their presence makes your program clearer)***

또한, lambda expression 은 class 혹은 method 들과 다르게 이름과 documentation 이 부족하다.

그렇기 때문에, lambda expression 의 작업이 많아진다면, readability 가 급격히 떨어진다.

- ***람다식의 작업이 자명하지 않거나, line 수가 길어진다면... (4 ~ 5 line 이상), lambda expression 에 넣지말자... (If a computation isn't self-explanatory, or exceeds a few lines, don't put it in a lambda.)***

위와 같은경우 (lambda expression 이 길어질 때) 는 해결책으로 method 로 빼내는 것이다. method 에는 이름이 있기 때문에 길어진 lambda expression 의 가독성을 높여 줄 수 있다고 생각한다. (아래와 같이)

```scala
object Example {
  def timesBy3(number: Int): Int = number * 3
  def isEvenNumber(number: Int): Boolean = number % 2 == 0
    
  val numbers = (1 to 10).toList
  
  // 이름을 줘서 명확하게 표현
  numbers.map(timesBy3)
         .filter(isEvenNumber)
  
  // 솔직히 이정도는 이름을 안줘도 된다 (?)
  numbers.map(_ * 3)
         .filter(_ % 2 == 0)
}
```

---
아직 실무에서 Java 8 을 사용하는 곳이 많지 않아, "Lambda expression 을 통해 코드가 간결해졌다!!!" 라는 기분이 들진 않았다.

하지만, Spark 개발을 할 때, 잠시 scala 를 사용 해 봤는데, 함수형 프로그래밍의 명료함 과 간결 (lambda expression 포함) 은 아주 매력적이다 라는 것을 느꼈다.

---
### rule 43 Prefer method references to lambdas
---

***Method Reference [Oracle Doc](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html)***

method reference 의 종류

- Static methods
- Instance methods
- Constructors using new operator (Object::new)

각각은 이름과 google 을 보면 사용할 수 있다.

그렇다면, 아래 예시를 통해 어떻게 사용하는지 알아보자.

```java
class MethodReferenceExample {
    public static void main(String[] args){
      List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
      
      System.out.println(numbers.stream().reduce((count, incr) -> count + incr));
      System.out.println(numbers.stream().reduce(Integer::sum));
    }
}
```

위 두 차이를 알겠는가?

- 간단하다 method reference 는 미리 정의 되어있는 method 를 사용하는 것이다.

***Method Reference 를 사용할 때 더 간결하고 명확하다면, 사용하라! 그렇지 않다면 lambda expression 을 사용하라! (Where method references are shorter and clearer, use them; where they aren't, stick with lambdas.)***

---
Java 8 부터 추가된 여러 default method, static method 들을 공부한다면, method reference 를 잘 활용할 수 있을 것이다.

Java doc 을 다 외울 수 없으니, 기본적인 기능같은 경우 만들어진 method 가 있을 것이라 생각하고 찾아가면서 적용해보자!

