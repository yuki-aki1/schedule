package schedule;

import java.util.Calendar;

class testCalendar1 {
	public static void main(String args[]) {
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);

		System.out.print("本日の日時は");
		System.out.println(year + "年" + (month + 1) + "月" + day + "日");

		/* 今月が何曜日から開始されているか確認する */
		calendar.set(year, month, 1);
		int startWeek = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("今月の曜日は" + startWeek + "から");

		/* 先月が何日までだったかを確認する */
		calendar.set(year, month, 0);
		int beforeMonthlastDay = calendar.get(Calendar.DATE);
		System.out.println("先月は" + beforeMonthlastDay + "日まで");

		/* 今月が何日までかを確認する */
		calendar.set(year, month + 1, 0);
		int thisMonthlastDay = calendar.get(Calendar.DATE);
		System.out.println("今月は" + thisMonthlastDay + "日まで¥r¥n");

		int[] calendarDay = new int[42];
		int count = 0;

		/* 先月分の日付を格納する */
		for (int i = startWeek - 2; i >= 0; i--) {
			calendarDay[count++] = beforeMonthlastDay - i;
		}

		/* 今月分の日付を格納する */
		for (int i = 1; i <= thisMonthlastDay; i++) {
			calendarDay[count++] = i;
		}

		/* 翌月分の日付を格納する */
		int nextMonthDay = 1;
		while (count % 7 != 0) {
			calendarDay[count++] = nextMonthDay++;
		}

		int weekCount = count / 7;

		for (int i = 0; i < weekCount; i++) {
			for (int j = i * 7; j < i * 7 + 7; j++) {
				if (calendarDay[j] < 10) {
					System.out.print(" " + calendarDay[j] + " ");
				} else {
					System.out.print(calendarDay[j] + " ");
				}
			}

			System.out.print("¥r¥n");
		}
	}
}