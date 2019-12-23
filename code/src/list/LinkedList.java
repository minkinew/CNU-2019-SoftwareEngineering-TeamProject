package list;

public class LinkedList<T> extends List<T> {

	private LinkedNode<T> _head; // 처음 head node생성
	private int _size; // list의 크기

	public LinkedNode<T> head() {
		return this._head;
	}

	public void setHead(LinkedNode<T> newHead) {
		this._head = newHead;
	} // 해드 정의

	@Override
	public int size() {
		return this._size;
	}

	public void setSize(int newSize) {
		this._size = newSize;
	} // 리스트의 크기 정의

	public LinkedList() { // 크기가 0인 list 첫번째로 생성
		this.reset();
	}

	@Override
	public boolean isEmpty() {
		return (this.size() == 0);
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public boolean add(T anElement) {
		LinkedNode<T> addedNode = new LinkedNode<T>(anElement, null);
		LinkedNode<T> tempNode = this.head();	//2번째 값
		if(this.isEmpty()) {
			this.setHead(addedNode);
			this.setSize(this.size() + 1);
			return true;
		}
		while(this.head().next() != null) {
			setHead(this.head().next());
		}
		this.head().setNext(addedNode);
		setHead(tempNode);
		this.setSize(this.size() + 1);
		return true;
	}

	public T removeAny() {
		if (this.isEmpty()) {
			return null;
		} else {
			T removedElement = this.head().element();
			this.setHead(this.head().next()); // head의 next를 헤드로 임명한다
			this.setSize(this.size() - 1);
			return removedElement;
		}
	}// head 노드 를 제거 하는 first in first out을 지키기 위한 부분

	@Override
	public void reset() {	//리스트를 다시 0으로 초기화
		this.setSize(0);
		this.setHead(null);
	}//아무것도 없는 것으로 나타내기

	@Override
	public Iterator<T> listIterator() {
		return new LinkedListIterator<T>();
	}// Iterator 객체 선언메소드 생성 -> iterator 내부 객체에 간접 접근 하기 위해서

	public class LinkedListIterator<E> implements Iterator<E> {
		//외부 class에 T에 genetic을 영향 받지 않기 위해 E로 genetic을 다른걸로 정의
		LinkedNode<E> _nextNode;

		private LinkedNode<E> nextNode() {
			return this._nextNode;
		}

		private void setNextNode(LinkedNode<E> newLinkedNode) {
			this._nextNode = newLinkedNode;
		}

		@SuppressWarnings("unchecked")
		private LinkedListIterator() {
			this.setNextNode((LinkedNode<E>) LinkedList.this.head());// LinkedList.this는 LinkedList에 대한 객체
		}// 생성자

		@Override
		public boolean hasNext() {
			return (this.nextNode() != null);	//현재 노드가 null인지 아닌지 판별
		}

		@Override
		public E next() {
			E nextElement = this.nextNode().element();
			this.setNextNode(this.nextNode().next());	//linkedNode의 next
			return nextElement;
		}
	}
}
