package schedule;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MonthView2 extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html;charset=Shift_Jis");
		PrintWriter out = res.getWriter();

		int[] calendarDay;
		int count;

		calendarDay = new int[42]; /* 最大で7日×6週 */
		count = 0;

		StringBuffer sb = new StringBuffer();

		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0.1//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");

		sb.append("<html lang=\"ja\">");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" Content=\"text/html;charset=Shift_JIS\">");

		sb.append("<title>スケジュール管理</title>");

		sb.append("<style>");
		sb.append("table{border:1px solid #a9a9a9;width:90%;padding:0px;margin:0px;border-collapse:collapse;}");
		sb.append(
				"td{width:12%;border-top:1px solid #a9a9a9;border-left:1px solid #a9a9a9;vertical-align:top;margin:0px;padding:2px;}");
		sb.append("td.week{background-color:#f0f8ff;text-align:center;}");
		sb.append("td.day{background-color:#f5f5f5;text-align:right;font-size:0.75em;}");
		sb.append("td.otherday{background-color:#f5f5f5;color:#d3d3d3;text-align:right;font-size:0.75em;}");
		sb.append("td.sche{background-color:#fffffff;text-align:left;height:80px;}");
		sb.append("img{border:0px;}");
		sb.append("p{font-size:0.75em;}");
		sb.append("</style>");

		sb.append("</head>");
		sb.append("<body>");

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);

		/* 日付データを配列に格納 */
		count = setDateArray(year, month, day, calendarDay, count);

		sb.append("<p>" + year + "年" + (month + 1) + "月</p>");

		sb.append("<table>");

		sb.append(
				"<tr><td class=\"week\">日</td><td class=\"week\">月</td><td class=\"week\">火</td><td class=\"week\">水</td><td class=\"week\">木</td><td class=\"week\">金</td><td class=\"week\">土</td></tr>");

		int weekCount = count / 7;

		for (int i = 0; i < weekCount; i++) {
			sb.append("<tr>");

			for (int j = i * 7; j < i * 7 + 7; j++) {
				if (calendarDay[j] > 35) {
					sb.append("<td class=\"otherday\">");
					sb.append(calendarDay[j] - 35);
				} else {
					sb.append("<td class=\"day\">");
					sb.append(calendarDay[j]);
				}
				sb.append("</td>");
			}

			sb.append("</tr>");
			sb.append(createScheduleStr());
		}

		sb.append("</table>");

		sb.append("</body>");
		sb.append("</html>");

		out.println(new String(sb));
	}

	protected String createScheduleStr() {
		StringBuffer sb = new StringBuffer();

		sb.append("<tr>");
		for (int i = 0; i < 7; i++) {
			sb.append("<td class=\"sche\"><img src=\"./img/memo.png\" width=\"14\" height=\"16\"></td>");
		}
		sb.append("</tr>");

		return (new String(sb));
	}

	protected int setDateArray(int year, int month, int day, int[] calendarDay, int count) {

		Calendar calendar = Calendar.getInstance();

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
		System.out.println("今月は" + thisMonthlastDay + "日まで\r\n");

		/* 先月分の日付を格納する */
		for (int i = startWeek - 2; i >= 0; i--) {
			calendarDay[count++] = beforeMonthlastDay - i + 35;
		}

		/* 今月分の日付を格納する */
		for (int i = 1; i <= thisMonthlastDay; i++) {
			calendarDay[count++] = i;
		}

		/* 翌月分の日付を格納する */
		int nextMonthDay = 1;
		while (count % 7 != 0) {
			calendarDay[count++] = 35 + nextMonthDay++;
		}

		return count;
	}
}