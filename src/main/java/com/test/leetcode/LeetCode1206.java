package com.test.leetcode;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 跳跃表
 * 参考:
 * concurrentSkipListMap
 * Redis skipList.
 */
public class LeetCode1206 {

	public static void main(String[] args) {
		Skiplist skiplist = new Skiplist();
		Random random = new Random();
		skiplist.add(1);
		skiplist.add(2);
		skiplist.add(3);
		System.out.println(skiplist.search(0));
		skiplist.add(4);
		System.out.println(skiplist.search(1));
		System.out.println(skiplist.erase(0));
		System.out.println(skiplist.search(0));
		System.out.println(skiplist.erase(1));
		System.out.println(skiplist.search(1));

	}


	public static class Skiplist {
		static final int maxLever = 8;
		static final Random random = new Random();
		Node head;
		public static class Node {
			int value;
			Node[] nexts;

			public Node(int tartget, Node[] nexts) {
				this.value = tartget;
				this.nexts = nexts;
			}
		}

		public Skiplist() {
			head = new Node(-123, new Node[maxLever]);
		}

		public boolean search(int target) {
			int startLever = maxLever-1;
			Node node = head;
			Node[] nexts = null;
			while (startLever >= 0) {
				A: while ((nexts = node.nexts) != null) {
					if (nexts[startLever] == null || nexts[startLever].value > target) { break A; }
					if (nexts[startLever].value == target) { return true; }
					node = nexts[startLever];
				}
				startLever--;
			}
			return false;
		}

		public void add(int num) {
			int lever = randomLever() + 1;
			Node current = new Node(num, new Node[lever]);
			int startLever = lever - 1;
			Node node = head;
			Node[] nexts = null;
			A: while (startLever >= 0) {
				B: while ((nexts = node.nexts) != null) {
					Node n = nexts[startLever];
					if (n == null || n.value > num) {
						nexts[startLever] = current;
						current.nexts[startLever] = n;
						break B;
					}
					node = nexts[startLever];
				}
				startLever--;
			}
		}

		public boolean erase(int num) {
			int startLever = maxLever-1;
			Node node = head;
			Node[] nexts = null;
			boolean res = false;
			while (startLever >= 0) {
				A: while ((nexts = node.nexts) != null) {
					Node n = nexts[startLever];
					if (n == null || n.value > num) { break A; }
					if (n.value == num) {
						nexts[startLever] = n.nexts[startLever];
						res = true;
						break A;
					}
					node = nexts[startLever];
				}
				startLever--;
			}
			return res;
		}

		private int randomLever() {
			int randomLever = random.nextInt();
			int lever = randomLever & (maxLever-1);
			return lever;
		}
	}
}
