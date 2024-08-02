package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ex1 {

	public static void main(String[] args) {

		// 현재시간 가져오기
		LocalDateTime curDateTime = LocalDateTime.now();
		System.out.println(curDateTime);
		
		// 날짜를 특정포맷으로 출력하기
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatDate = curDateTime.format(formatter);
		System.out.println(formatDate);
		
		// 문자열을 LocalDateTime로 변환하기
		String str = "2024-07-31 11:11:11";
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		// 날짜문자열, 날짜에 맞는 포맷
		LocalDateTime datetime = LocalDateTime.parse(str, formatter2);
		System.out.println(datetime);
		
		// LocalDateTime을 LocalDate로 변경하기
		LocalDate date = datetime.toLocalDate();
		System.out.println(date);
		
		// 문자열을 LocalDate로 변환하기
		String str2 = "2024-07-30";
		LocalDate date2 = LocalDate.parse(str2);
		System.out.println(date2);

	}

}
