## OFFSET, CURSOR 중 무엇을 선택해야할까

### 내생각

CURSOR 기반 페이지네이션 가 전체적으로 보면 성능적인 면에서 나은 것 같다.
하지만 page navigator가 안된다고 한다.

쿠팡을 보니 page navigator가 구현되어 있고 uri를 보면 page, listSize도 되어 있다.
쿠팡 또한 OFFSET을 사용중인가? 아니면 cursor 기반 페이지네이션을 사용해도 이렇게 구현할 수 있는가?

우선 나는 OFFSET 기반 페이지네이션을 사용하여 구현하였다.

피드백을 받고 다시 봐야겠다.