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
		// 저장된 번호 중 가장 마지막 번호를 불러옴
		Order.OrderNumCheck();
		
		// 주문 정보를 저장할 txt 파일 생성
		// 파일명 뒤에 true 추가할 경우 [덮어쓰기]가 아니라 [이어쓰기]가 됨
		FileWriter fw = new FileWriter("order.txt", true);
		
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			
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

		    System.out.println("주문이 완료되었습니다!\n");

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
		    
		    System.out.println();
			
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
			break;
		}
		
		// 오류 메시지 출력
		if (check != true) {
			System.out.println("번호를 잘못 입력했습니다. 다시 선택해주세요.");
			System.out.println();
		}
			
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
	
	public static void OrderNumCheck() throws IOException {
		
		// 읽어올 파일 선언 (줄 단위)
		FileReader fr = new FileReader("order.txt");
		BufferedReader br = new BufferedReader(fr);
	
		// 카운트 번호를 저장할 값 선언
		int numLast = 0;
		
		while (true) {
			String lineString = br.readLine();

			// 만약 txt 파일에 데이터가 없다면 진행 ×
			if (lineString == null) {
				break;
			}
			
			// 데이터가 있다면 항목별로 자르기
			String[] parts = lineString.split(", ");
			
			// 자른 데이터를 반복하면서 [주문 번호] 데이터 확인
			for (int i = 0;i<parts.length;i++) {
				String info = parts[i];
				
				// 만약 info 변수가 주문 번호로 시작한다면
				// 배열로 자르고 숫자가 들어있는 2를 값으로 저장
				if (info.startsWith("주문 번호")) {
					String[] numCut = info.split(": ");
					numLast = Integer.parseInt(numCut[1]);
				}
			}
		}
		
		// 이후 저장된 값을 카운트에 저장
		count = numLast;
		
	}
	
	public static void OrderNameCheck(String name) throws IOException {
		
		FileReader fr = new FileReader("order.txt");
		BufferedReader br = new BufferedReader(fr);
		
		int numCheck = 0;
		int priceCheck = 0;
		int priNumCheck = 0;
		
		while (true) {
			String lineString = br.readLine();
			
			if (lineString == null) {
				break;
			}
			
			// 주문자명과 입력한 데이터가 동일한 내역(줄)만 불러옴
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

					// 만약 info 변수가 "고객명: "으로 시작한다면
					if (info.startsWith("제품 수량: ")) {
						String[] numCut = info.split(": ");
						// 제품 수량을 변수에 저장해서 이후 금액에 사용
						int nc = Integer.parseInt(numCut[1]);
						priNumCheck = nc;
					}
					
					// 만약 info 변수가 "제품 금액: "으로 시작한다면
					if (info.startsWith("제품 금액: ")) {
						// 그걸 또 명칭과 이름으로 나누고
						String[] priceCut = info.split(": ");
						// 그 중 배열 두 번째에 금액이 있을 테니
						// 그 금액을 숫자로 변환
						int pr = Integer.parseInt(priceCut[1]);
						// 총액 = 현재 총액 + (금액 * 수량)
						priceCheck = priceCheck + (pr*priNumCheck);
					}
					
				}
				
			}
		    
		}

	    System.out.println("전체 주문 건수: " + numCheck);
	    System.out.println("전체 주문 금액: " + priceCheck);
	    
	    System.out.println();
	    
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
	    
	    System.out.println();
		
	}
	
}