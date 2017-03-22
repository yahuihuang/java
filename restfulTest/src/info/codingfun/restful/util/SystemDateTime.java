package info.codingfun.restful.util;

import java.text.*;
import java.util.*;

public class SystemDateTime {
  // 取得系統日期
  public static String getSysdate() {
	SimpleDateFormat formatday = new SimpleDateFormat("yyyyMMdd");
	String sSysDate = formatday.format(new java.util.Date());
	return sSysDate;
  }
    
  // 取得系統時間
  public static String getSystime() {
	SimpleDateFormat formattime = new SimpleDateFormat("HHmmss");
	String sSysTime = formattime.format(new java.util.Date());
	return sSysTime;
  }
  
  // 取得系統時間
  public static String getSystime2() {
	SimpleDateFormat formattime = new SimpleDateFormat("HHmmssSSS");
	String sSysTime = formattime.format(new java.util.Date());
	return sSysTime;
  }
  
  // 取得系統日期
  public String getFmtsysdate() {
	SimpleDateFormat formatday = new SimpleDateFormat("yyyyMMdd");
	String sSysDate = formatday.format(new java.util.Date());
	return formatDate(sSysDate,"/");
  }
  
  // 取得系統時間
  public String getFmtsystime() {
	SimpleDateFormat formattime = new SimpleDateFormat("HHmmss");
	String sSysTime = formattime.format(new java.util.Date());
	return formatTime(sSysTime,":");
  }
  
  // 取得系統時間+(-)n秒
  public static String getDiffFmtsystime(int iDelayTime) {
	Calendar today = new GregorianCalendar();
	today.add(Calendar.SECOND,iDelayTime);
	int iHour = today.get(Calendar.HOUR_OF_DAY);
	int iMinute = today.get(Calendar.MINUTE);
	int iSecond = today.get(Calendar.SECOND);
	return getFmtString(iHour,2,'0') + getFmtString(iMinute,2,'0') + getFmtString(iSecond,2,'0');
  }
  
  // 將數字轉為固定長的字串
  public static String getFmtString(int in_num,int len,char app_c) {
    String num = String.valueOf(in_num);
    if (num.length() >= len)
      return num;
     
    StringBuffer buf_num = new StringBuffer(num);
    for (int idx=0;idx < (len - num.length());idx++) 
      buf_num.insert(0,app_c);
    return buf_num.toString();
  }
  
  // 將日期格式化
  public static String formatDate(String sDate,String sDel) {
    sDate = getFmtString(new Integer(sDate).intValue(),8,'0');
    return sDate.substring(0,4) + sDel + sDate.substring(4,6) + sDel + sDate.substring(6,8);	 
  }
	  
  // 將時間格式化
  public static String formatTime(String sTime,String sDel) {
	if (sTime.length() >= 8) {
	  sTime = getFmtString(new Integer(sTime).intValue(),9,'0');
	  return sTime.substring(0,2) + sDel + sTime.substring(2,4) + sDel + sTime.substring(4,6) + "." + sTime.substring(6,9);
	} if (sTime.length() >= 7) {
      sTime = getFmtString(new Integer(sTime).intValue(),8,'0');
      return sTime.substring(0,2) + sDel + sTime.substring(2,4) + sDel + sTime.substring(4,6) + "." + sTime.substring(6,8);
	} else {
      sTime = getFmtString(new Integer(sTime).intValue(),6,'0');
      return sTime.substring(0,2) + sDel + sTime.substring(2,4) + sDel + sTime.substring(4,6);
	}    
  }
  
  /*
   * 判斷差異秒數*/
  public static long diffSeconds_nextDay(int hourOfDay, int minute, int second, Date date) {
	Calendar nextCal = Calendar.getInstance();
	Calendar nowCal = Calendar.getInstance();	
	  
	nowCal.setTime(date);

	nextCal.setTime(date);
    nextCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
    nextCal.set(Calendar.MINUTE, minute);
    nextCal.set(Calendar.SECOND, second);
    nextCal.set(Calendar.MILLISECOND, 0);
    if (nextCal.getTime().before(date) == false)
   	  nextCal.add(Calendar.DATE,-1);
	nextCal.add(Calendar.DATE, 1);
	
	return (nextCal.getTime().getTime() - nowCal.getTime().getTime())/1000;
  }
  
  /*
   * 取得下次執行時間hhmmss*/
  public static long getStartTime_fromNow(long startTime,long delayTime,long endTime) {
	long nowTime = new Long(getSystime()).longValue();
	
	/*2012.04.16*grace for 增加迄止時間之判斷*/
	if (startTime > endTime) {
	  if (nowTime >= endTime && nowTime <= startTime)
		return startTime;
	  startTime = 0;
	  endTime = 240000;
	}
	/*2012.04.16*end*/
	if (nowTime <= startTime)
	  return startTime;
	
	//取得差異秒數
	long se_difftime = 0;
	long shour = startTime / 10000;
	long smin = (startTime % 10000)/100;
	long ssec = startTime % 100;
	long ehour = nowTime / 10000;
	long emin = (nowTime % 10000)/100;
	long esec = nowTime % 100;
	
	if (esec < ssec) {
	  if (emin == 0) {
		ehour -= 1;
		emin += 60;
	  }
	  emin -= 1;
	  esec += 60;
	}
	se_difftime = esec - ssec;
	
	if (emin < smin) {
	  ehour -= 1;
	  emin += 60;
	}
	se_difftime += 60 * (emin - smin);
	se_difftime += 60 * 60 * (ehour - shour);
	if ((se_difftime % delayTime) > 0)
	  se_difftime = ((se_difftime / delayTime) +  1 ) * delayTime;
	
	//將時間加回去	
	long plushour = se_difftime / (60 * 60);
	long plusmin = (se_difftime % (60 * 60)) / 60;
	long plussec = se_difftime % 60;
	
	shour += plushour;
	smin += plusmin;
	ssec += plussec;
		
	if (ssec >= 60) {
		ssec -= 60;
		smin += 1;
	}
	if (smin >= 60) {
		smin -= 60;
		shour += 1;
	}
	
	long execTime = (shour * 10000) + (smin * 100) + ssec;
	if (execTime > endTime)
		execTime = startTime;
	return execTime;
  }
  
  /* 要加減之日期
   * */
  public static long getDiffDays(long date,int days) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
	Date dateTemp = sdf.parse(new Long(date).toString(), new ParsePosition(0));   
	Calendar calendar = Calendar.getInstance();   
	calendar.setTime(dateTemp); 
	
	//要加減之日期   
	calendar.add(Calendar.DATE, days); 
	java.util.Date nowTime = calendar.getTime(); 
    StringBuffer buffer = sdf.format(nowTime, new StringBuffer(""),new FieldPosition(0));
    long lRtnDate = new Long(buffer.toString()).longValue();
	return lRtnDate;  
  }
  
  /*
   * 取得當月天數
   */
  public static int getMonthDays(int year, int month) {
	  int day = 1;
	  Calendar cal = Calendar.getInstance();
	  cal.set(year,month - 1,day);
	  int last = cal.getActualMaximum(Calendar.DATE);
	  
	  return last;
  }
  
  public static Date getFmtDate(String originDate,String originFmt) {
	  SimpleDateFormat sdf = new SimpleDateFormat(originFmt);
	  Date openDate_fmt = null;
	  try {
		  openDate_fmt = sdf.parse(originDate);
	  } catch (ParseException e) {
		  e.printStackTrace();
	  }
	  
	  return openDate_fmt;
  }
  
  public static Date getNextMonth(int iMonth) {
	  /*
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(new Date());
	  cal.add(Calendar.MONTH, iMonth); // 向前一月；如果需要向后一月，用正数即可
	  return cal.getTime();
	  */
	  return getNextMonth(new Date(),iMonth);
  }
  
  public static Date getNextMonth(Date nowDate,int iMonth) {
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(nowDate);
	  cal.add(Calendar.MONTH, iMonth); // 向前一月；如果需要向后一月，用正数即可
	  return cal.getTime();
  }
  
  public static String getNextMonth(int iMonth,String originFmt) {
	  /*
	  SimpleDateFormat sdf = new SimpleDateFormat(originFmt);
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(new Date());
	  cal.add(Calendar.MONTH, iMonth); // 向前一月；如果需要向后一月，用正数即可
	  return sdf.format(cal.getTime());
	  */
	  return getNextMonth(new Date(),iMonth,originFmt);
  }
  
  public static String getNextMonth(Date nowDate,int iMonth,String destFmt) {
	  SimpleDateFormat sdf = new SimpleDateFormat(destFmt);
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(nowDate);
	  cal.add(Calendar.MONTH, iMonth); // 向前一月；如果需要向后一月，用正数即可
	  return sdf.format(cal.getTime());
  }  
  
  public static String getFmtDate(Date nowDate,String destFmt) {
	  SimpleDateFormat sdf = new SimpleDateFormat(destFmt);
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(nowDate);
	  return sdf.format(cal.getTime());
  }
  
  public static Date getDate(int year,int month,int day) {	  
	  Calendar cal = GregorianCalendar.getInstance();
	  cal.set(year, month-1, day, 0, 0, 0);
	  cal.set(Calendar.MILLISECOND, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.HOUR, 0);
	  return cal.getTime();
  }
  
  public static Date getNextDay(Date nowDate,int iDay) {
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(nowDate);
	  cal.add(Calendar.DAY_OF_MONTH, iDay); // 向前一日；如果需要向后一日，用正数即可
	  return cal.getTime();
  }
  
  public static Date trimDate(Date date) {
	  Calendar cal = Calendar.getInstance();
	  
      cal.setTime(date);
      cal.set(Calendar.MILLISECOND, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.HOUR, 0);
      return cal.getTime();
      /*
	  return getDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH));
	  */
  }
  
  public static boolean compareDate(String fmtDate, Date beforeVal, Date afterVal) {
	  if (beforeVal == null && afterVal == null)
		  return true;
	  else if (beforeVal == null || afterVal == null)
		  return false;
	  String beforeDate = SystemDateTime.getFmtDate(beforeVal, fmtDate);
	  String afterDate = SystemDateTime.getFmtDate(afterVal, fmtDate);
	  return (beforeDate.compareTo(afterDate) == 0) ? true : false;
  }
}