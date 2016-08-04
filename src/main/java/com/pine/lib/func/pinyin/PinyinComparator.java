package com.pine.lib.func.pinyin;

import java.util.Comparator;

public class PinyinComparator implements Comparator<BeanInterface> {

	public int compare(BeanInterface o1, BeanInterface o2) {
		if (o1.getFirstLetter().equals("@")
				|| o2.getFirstLetter().equals("#")) {
			return -1;
		} else if (o1.getFirstLetter().equals("#")
				|| o2.getFirstLetter().equals("@")) {
			return 1;
		} else {
			return o1.getFirstLetter().compareTo(o2.getFirstLetter());
		}
	}

}

