package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ex2 {

	public static void main(String[] args) throws IOException {
		
		// FileReader: 입력 기반 스트림
		FileReader fr = new FileReader("Flie_name.txt");
		
		// ButteredReader: 보조 스트림 (줄 단위로 텍스트를 가져오는 기능)
		BufferedReader br = new BufferedReader(fr);
		
		// 한 줄씩 가져오기
		String lineString = br.readLine();

	}

}
