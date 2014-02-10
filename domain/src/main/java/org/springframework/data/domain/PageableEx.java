package org.springframework.data.domain;

public interface PageableEx extends Pageable {

	int getOffset();

	int getPageSize();

	int getTotalSize();

	void setTotalSize(int totalSize);

}
