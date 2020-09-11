
package com.allendowney.thinkdast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyLinkedList<E> implements List<E> {


	private class Node {
		public E cargo;
		public Node next;

		public Node(E cargo) {
			this.cargo = cargo;
			this.next = null;
		}
		public Node(E cargo, Node next) {
			this.cargo = cargo;
			this.next = next;
		}
		public String toString() {
			return "Node(" + cargo.toString() + ")";
		}
	}

	private int size;            
	private Node head;           

	public MyLinkedList() {
		head = null;
		size = 0;
	}


	public static void main(String[] args) {
		// run a few simple tests
		List<Integer> mll = new MyLinkedList<Integer>();
		mll.add(1);
		mll.add(2);
		mll.add(3);
		System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());

		mll.remove(new Integer(2));
		System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());
	}

	@Override
	public boolean add(E element) {
		if (head == null) {
			// head �� null �� �ƹ��͵� ���� Node�� �߰�
			head = new Node(element);
		} else {
			Node node = head;
			// ������ ���� ��带 �ݺ�
			// head �� ���� node�� �����κ��� null�� �ƴ� ��� node�� ���� ���� ��尪�� �༭ ������ ��带 ã��
			for ( ; node.next != null; node = node.next) {}
			// ã�� ��������忡 ���� �������� �Ű������� ���� ������ �ν��Ͻ� ������ �߰�
			node.next = new Node(element);
		}
		size++; // null ���� �ֱ����� size ��
		return true;
	}

	@Override
	public void add(int index, E element) {

		if (index == 0) {
			// index�� 0 �� �ƹ��͵� ���� Node�� �߰�
			head = new Node(element, head);
		} else {
			// getNode �޼ҵ带 �̿��� �Է��� ��� ������ ��带 node ������ ����
			Node node = getNode(index-1);
			// ���� Node�� ���������� �ν��Ͻ������ؼ� �߰�
			node.next = new Node(element, node.next);
		}
		size++; // null ���� �ֱ����� size ��
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean flag = true;
		for (E element: collection) {
			flag &= add(element);
		}
		return flag;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	@Override
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object obj: collection) {
			if (!contains(obj)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public E get(int index) {
		Node node = getNode(index);
		return node.cargo;
	}

	/** Returns the node at the given index.
	 * @param index
	 * @return
	 */
	private Node getNode(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node node = head;
		for (int i=0; i<index; i++) {
			node = node.next;
		}
		return node;
	}

	@Override
	public int indexOf(Object target) {
		// ù ��° ��� node�� ����
		Node node = head;
		// equals �޼ҵ忡 Ÿ�ٰ� node������ ���� ��
		// ������ i�� �����ؼ� index�� �˷���
		// �ٸ��� node ������ ���� ���������� �����ؼ� �ݺ�
		for (int i=0; i<size; i++) {
			if (equals(target, node.cargo)) {
				return i;
			}
			node = node.next;
		}
		
		// ���ٸ� -1 ����
		return -1;
	}


	private boolean equals(Object target, Object element) {
		if (target == null) {
			return element == null;
		} else {
			return target.equals(element);
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		@SuppressWarnings("unchecked")
		E[] array = (E[]) toArray();
		return Arrays.asList(array).iterator();
	}

	@Override
	public int lastIndexOf(Object target) {
		Node node = head;
		int index = -1;
		for (int i=0; i<size; i++) {
			if (equals(target, node.cargo)) {
				index = i;
			}
			node = node.next;
		}
		return index;
	}

	@Override
	public ListIterator<E> listIterator() {
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return null;
	}

	@Override
	public boolean remove(Object obj) {
		// ���� ���ϴ� �� indexOf �޼ҵ带 ���� index ����
		int index = indexOf(obj);
		// �ε����� -1�̸� ���� ���̹Ƿ� false ���
		if (index == -1) {
			return false;
		}
		// remove �޼ҵ�� �� ���� �� true ���
		remove(index);
		return true;
	}

	@Override
	public E remove(int index) {
		// get�޼ҵ�� ���ϴ� ��� ã�Ƽ� ����
		E element = get(index);
		if (index == 0) {
			// �����ҷ��� ��Ұ� ù ��° ��Ҷ�� head ������ ���� ���� ���� ����
			head = head.next;
		} else {
			// ù ��° ��Ұ� �ƴ϶�� node�� ���� �ҷ��� ��� ���� ���� �����ϰ�
			// node ���� ���� ������ ���� ���� ���� ����
			Node node = getNode(index-1);
			node.next = node.next.next;
		}
		size--;
		return element;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean flag = true;
		for (Object obj: collection) {
			flag &= remove(obj);
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E element) {
		Node node = getNode(index);
		E old = node.cargo;
		node.cargo = element;
		return old;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		// TODO: classify this and improve it.
		int i = 0;
		MyLinkedList<E> list = new MyLinkedList<E>();
		for (Node node=head; node != null; node = node.next) {
			if (i >= fromIndex && i <= toIndex) {
				list.add(node.cargo);
			}
			i++;
		}
		return list;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int i = 0;
		for (Node node=head; node != null; node = node.next) {
			// System.out.println(node);
			array[i] = node.cargo;
			i++;
		}
		return array;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
}