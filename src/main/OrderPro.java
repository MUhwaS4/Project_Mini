package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class OrderPro {

	public static void main(String[] args) throws IOException {
		
		// 프로젝트 주제
		// 쇼핑몰 주문 관리 시스템
		
		// 프로젝트 개요
		// 이 프로젝트의 목표는 쇼핑몰의 주문을 관리하고,
		// 다양한 조회 기능을 제공하는 시스템을 설계하고 구현하는 것입니다.
		// 주문 이력을 저장하고 관리하는 기능과
		// 주문 이력을 조회하는 기능을 구현하세요.
		

		// 프로그램 시작 시 주문 번호 초기화
		Order.OrderCount();
		
		// 주문 정보를 저장할 txt 파일 생성
		FileWriter fw = new FileWriter("order.txt", true);
		
		Scanner scanner = new Scanner(System.in);
			
		System.out.println("1. 상품 주문하기.");
		System.out.println("2. 전체 주문 이력 보기.");
		System.out.println("3. 고객별 주문 이력 보기.");
		System.out.println("4. 특정 날짜에 들어온 주문 이력 보기.");
		System.out.println("5. 끝내기.");
		
		System.out.print("옵션을 선택하세요: ");
		
		int seleteNum = scanner.nextInt(); // 옵션 선택
		
		boolean check = (1<=seleteNum && seleteNum<=5);
		
		// 1. 상품 주문하기
		
		if (seleteNum == 1) {

		    Order Order = new Order();

		    fw.write("주문 번호: " + Order.num + ", ");

		    scanner.nextLine(); // 버퍼 비우기

		    System.out.print("고객명: ");
		    String orderName = scanner.nextLine(); // 고객명
		    fw.write("고객명: " + orderName + ", ");
		    
		    System.out.print("제품명: ");
		    String orderProduct = scanner.nextLine(); // 제품명
		    fw.write("제품명: " + orderProduct + ", ");

		    System.out.print("제품 수량: ");
		    int orderNum = scanner.nextInt(); // 제품 수량
		    fw.write("제품 수량: " + orderNum + ", ");

		    System.out.print("제품 금액: ");
		    int ordePrice = scanner.nextInt(); // 제품 금액
		    fw.write("제품 금액: " + ordePrice + ", ");

		    // 주문 시각
		    LocalDateTime curDateTime = LocalDateTime.now();
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		    String formatDate = curDateTime.format(formatter);
		    fw.write("주문 일시: " + formatDate + "\n");

		    System.out.println("주문이 완료되었습니다!");

		    fw.flush();
		    fw.close();

		}
		
		// 2. 전체 주문 이력 보기
		
		if (seleteNum == 2) {
			
			FileReader fr = new FileReader("order.txt");
			BufferedReader br = new BufferedReader(fr);
			
			while (true) {
				String lineString = br.readLine();
				if (lineString == null) {
					break;
				}
				System.out.println(lineString);
			}
			
		}
		
		// 3. 고객별 주문 이력 보기
		
		if (seleteNum == 3) {

		    scanner.nextLine(); // 버퍼 비우기
			
		    System.out.print("고객명: ");
		    String orderName = scanner.nextLine(); // 고객명
		    
		    Order.OrderNameCheck(orderName.trim());
		    
		}
		
		// 4. 특정 날짜에 들어온 주문 이력 보기
		
		if (seleteNum == 4) {

		    scanner.nextLine(); // 버퍼 비우기

		    System.out.println("YYYY-MM-DD 양식으로 입력해주세요.");
		    System.out.print("날짜: ");
		    String orderDate = scanner.nextLine(); // 날짜
		    
		    Order.OrderDateCheck(orderDate.trim());
			
		}
		
		if (seleteNum == 5) {
			System.out.println("프로그램을 종료합니다….");
		}
		
		// 오류 메시지 출력
		if (check != true) {
			System.out.println("번호를 잘못 입력했습니다.");
		}

	}

}

class Order {
	
	static int count = 0;
	
	int num; // 주문번호
	
	public Order() {
		count++;
		num = count;
	}
	
	public static void OrderCount() {
		try (BufferedReader br = new BufferedReader(new FileReader("order.txt"))) {
			String line;
			int lastOrderNum = 0;

			while ((line = br.readLine()) != null) {
				// 주문 번호를 파싱해서 읽어오기
				String[] parts = line.split(", ");
				for (String part : parts) {
					if (part.startsWith("주문 번호: ")) {
						lastOrderNum = Integer.parseInt(part.replace("주문 번호: ", ""));
					}
				}
			}
			count = lastOrderNum;
		} catch (IOException e) {
			// order.txt 파일이 없는 경우 예외 처리
			count = 0;
		}
		
	}
	
	public static void OrderNameCheck(String name) throws IOException {
		
		FileReader fr = new FileReader("order.txt");
		BufferedReader br = new BufferedReader(fr);
		
		int numCheck = 0;
		int priceCheck = 0;
		
		while (true) {
			String lineString = br.readLine();
			
			if (lineString == null) {
				break;
			}
			
			if (lineString.contains(name)) {
				
				// 읽은 데이터를 주문 번호/고객명/제품명/수량/금액/일시로 자르기
				// split(" "); 구분자를 기준으로 배열 형태로 자름
				String[] parts = lineString.split(", ");
				
				for (int i=0;i<parts.length;i++) {
					
					// 자른 배열을 변수로 선언
					String info = parts[i];
					
					// 만약 info 변수가 "고객명: "으로 시작한다면
					if (info.startsWith("고객명: ")) {
						// 그걸 또 명칭과 이름으로 나누고
						String[] nameCut = info.split(": ");
						// 그 중 배열 두 번째에 이름이 있을 테니
						// 그게 scanner로 입력한 내용과 같으면
						if (name.equals(nameCut[1])) {
							numCheck++;
						}
					}
					
					// 만약 info 변수가 "제품 금액: "으로 시작한다면
					if (info.startsWith("제품 금액: ")) {
						// 그걸 또 명칭과 이름으로 나누고
						String[] priceCut = info.split(": ");
						// 그 중 배열 두 번째에 금액이 있을 테니
						// 그 금액을 숫자로 변환
						int pr = Integer.parseInt(priceCut[1]);
						priceCheck = priceCheck + pr;
					}
					
				}
				
			}
		    
		}

	    System.out.println("전체 주문 건수: " + numCheck);
	    System.out.println("전체 주문 금액: " + priceCheck);
	    return;
		
	}
	public static void OrderDateCheck(String date) throws IOException { 
		
		FileReader fr = new FileReader("order.txt");
		BufferedReader br = new BufferedReader(fr);
		
		while (true) {
			String lineString = br.readLine();
			
			if (lineString == null) {
				break;
			}
			
			// 입력한 날짜가 포함되어 있다면 출력
			if (lineString.contains(date)) {
				System.out.println(lineString);
			}
			
		}
		
	}
	
}