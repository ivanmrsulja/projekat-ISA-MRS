package rest.service;

import rest.domain.StatusERecepta;

public class EReceptSortFilterParams {
	private boolean sort;
	private boolean descending;
	private StatusERecepta status;
	
	public EReceptSortFilterParams() {}

	public EReceptSortFilterParams(boolean sort, boolean descending, StatusERecepta status) {
		super();
		this.sort = sort;
		this.descending = descending;
		this.status = status;
	}

	public boolean isSort() {
		return sort;
	}

	public void setSort(boolean sort) {
		this.sort = sort;
	}

	public boolean isDescending() {
		return descending;
	}

	public void setDescending(boolean descending) {
		this.descending = descending;
	}

	public StatusERecepta getStatus() {
		return status;
	}

	public void setStatus(StatusERecepta status) {
		this.status = status;
	}
	
}
