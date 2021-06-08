package rest.domain;

import java.util.Comparator;

import rest.dto.LekProdajaDTO;

public class LekoviComparator implements Comparator<LekProdajaDTO> {

	@Override
	public int compare(LekProdajaDTO o1, LekProdajaDTO o2) {
		// TODO Auto-generated method stub
		return (int) (o1.getApoteka().getCena() - o2.getApoteka().getCena());
	}

}
