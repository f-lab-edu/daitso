## if-else문으로 되어 있던 정렬 로직을 enum 클래스로 변경

if-else문을 사용하여 상품 목록 정렬 로직을 구현하였다.

![img.png](img.png)

하지만 이렇게 구현을 하면 새로운 정렬이 추가될 때 마다 if-else를 통해 구현 해야 하기 때문에 좋은 코드가 될 수 없다.

그래서 찾아본 결과 enum을 사용하여 구현할 수 있었다.
