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

Employee 예제
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

