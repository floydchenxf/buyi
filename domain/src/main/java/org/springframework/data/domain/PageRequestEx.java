package org.springframework.data.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;

public class PageRequestEx extends PageRequest implements PageableEx {
	private static final long serialVersionUID = 1L;
	private int offset = -1;
	private int totalSize;

	public PageRequestEx(int page, int size, Direction direction, String... properties) {
		super(page, size, direction, properties);
	}

	public PageRequestEx(int page, int size, int offset, Sort sort) {
		super(page, size, sort);
		this.offset = offset;
	}

	public PageRequestEx(int page, Pageable pageale) {
		super(page, pageale.getPageSize(), pageale.getSort());
		if (pageale instanceof PageRequestEx)
			this.offset = ((PageRequestEx) pageale).offset;
	}

	public PageRequestEx(int page, int size, Sort sort) {
		super(page, size, sort);
	}

	public PageRequestEx(int page, int size, int offset) {
		super(page, size);
		this.offset = offset;
	}

	public PageRequestEx(int page, int size) {
		super(page, size);
	}

	/**
	 * 支持特定的offset
	 */
	@Override
	public int getOffset() {
		return this.offset < 0 ? super.getOffset() : this.offset;
	}

	@Override
	public int getTotalSize() {
		return totalSize;
	}

	@Override
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getTotalPages() {
		return (int) Math.ceil((double) this.getTotalSize() / this.getPageSize());
	}

	public int getPreviousPage() {
		return calcPage(this.getPageNumber() - 1);
	}

	public int getNextPage() {
		return calcPage(this.getPageNumber() + 1);
	}

	private int calcPage(int page) {
		int pages = getTotalPages();

		if (pages >= 0) {
			return (page < 0) ? 0 : ((page > pages) ? pages : page);
		}
		return 0;
	}

	// 用于在页面上显示第几页
	public static int plus1(int num) {
		return num + 1;
	}

	public List<Integer> getPageArray() {
		int maxNum = 9;
		List<Integer> result = new ArrayList<Integer>(maxNum);
		if (this.getPageNumber() < 5) {
			for (int i = 0; i < Math.min(maxNum, this.getTotalPages()); i++) {
				result.add(i);
			}
		} else {
			result.add(0);
			result.add(-1);
			maxNum = this.getPageNumber() + 4;
			for (int i = this.getPageNumber() - 3; i < Math.min(maxNum, this.getTotalPages()); i++) {
				result.add(i);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int a = (int) Math.ceil((double) 113 / 3);
		System.out.println(a);
		System.out.println(Math.ceil((double) 113 / 3));
	}
}
