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