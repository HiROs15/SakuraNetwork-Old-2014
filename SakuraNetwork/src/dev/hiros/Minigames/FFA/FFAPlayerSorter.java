package dev.hiros.Minigames.FFA;

import java.util.Comparator;

public class FFAPlayerSorter implements Comparator<FFAPlayer> {
	@Override
	public int compare(FFAPlayer a1, FFAPlayer a2) {
		String s1 = ""+a1.getKills();
		String s2 = ""+a2.getKills();
		
		return s2.compareTo(s1);
	}
}
