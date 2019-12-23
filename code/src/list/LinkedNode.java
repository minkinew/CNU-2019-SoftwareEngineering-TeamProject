package list;

public class LinkedNode<T> { // 노드 생성
	private T _element; // 내부 data를 저장 하는 부분
	private LinkedNode<T> _next; // 다음 node연결을 위한 위치

	public T element() {
		return this._element;
	}

	public void setElement(T newElement) {
		this._element = newElement;
	} // data 대입

	public LinkedNode<T> next() {
		return this._next;
	}

	public void setNext(LinkedNode<T> newNext) {
		this._next = newNext;
	}// next node 설정

	public LinkedNode() {	//빈 노드 생성 할떄위한 생성자
		this.setElement(null);
		this.setNext(null);
	}

	public LinkedNode(T givenElement, LinkedNode<T> givenNext) {
		this.setElement(givenElement);
		this.setNext(givenNext);
	}

}