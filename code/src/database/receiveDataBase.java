package database;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class receiveDataBase {
	
	private String _department; 		// 학과명 
	private String _dataBaseURL;
	private Document[] _receivedData;	// URL내부의 내용  
	private int _numberOfPages;			// DataBase page의 수 
	
	private String department() {
		return this._department;
	}
	private void setDepartment(String newDepartment) {
		this._department = newDepartment;
	}
	private String dataBaseURL() {
		return this._dataBaseURL;
	}
	private void setDatsBaseURL(String newDataBaseURL) {
		this._dataBaseURL = newDataBaseURL;
	}
	public Document[] revceivedData() {
		return this._receivedData;
	}
	private void setReceivedData(Document[] newReceivedData) {
		this._receivedData = newReceivedData;
	}
	public int numberOfPages() {
		return this._numberOfPages;
	}
	private void setNumberOfPages(int newNumberOfPages) {
		this._numberOfPages = newNumberOfPages;
	}
	
	//생성자
	public receiveDataBase(String givenDepartment) {
		this.setNumberOfPages(1);
		this.setDepartment(this.convertDepartment(givenDepartment));
		this.makeDataBaseURL();
		while(this.numberOfDataBasePage()) {
			this.setNumberOfPages(this.numberOfPages() + 1);
		}
		this.setReceivedData(new Document[this.numberOfPages()]);
		for(int page = 1 ; page < this.numberOfPages() ; page++) {
			this.revceivedData()[page - 1] = this.dataBasePageDocument(page);
		}
	}
	
	//학과 입력시 학과 강의의 url을 위한 변환 
	//나중에 따로 Class로 만들어 줄 수도 있음
	private String convertDepartment(String givenDepartment) {
		String convertedData = "";
		
		switch(givenDepartment) {
		
			case "컴퓨터공학과" : 
				convertedData = "C00059";
				break;
			case "교양1":
				convertedData = "B47010";
				break;
			case "교양2":
				convertedData = "B47020";
				break;
			default : 
				convertedData = "NONE";
				break;
				
		}//데모를 위해서 컴퓨터공학과만 있음
		
		return convertedData;
	}
	
	private void makeDataBaseURL() {
		String makedURL = "https://uruniv.kr/api/lectures?major=";
		makedURL += this.department();
		makedURL += "&page=";
		this.setDatsBaseURL(makedURL);
	}
	
	private boolean numberOfDataBasePage() {
		String url = this.dataBaseURL() + Integer.toString(this.numberOfPages());
		
		try {
			Document pageDocument = Jsoup.connect(url)
					.ignoreContentType(true)
					.get();

			String convertedPageDocument = pageDocument.toString();
			String[] parsedPageDocument = convertedPageDocument.split("\\[");
			char[] convertedParsePageDocument = parsedPageDocument[1].toCharArray();

			if(convertedParsePageDocument[0] != (char)93) {
				return true;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private Document dataBasePageDocument(int givenPageNum) {
		String url = this.dataBaseURL() + Integer.toString(givenPageNum);
		
		try {
			Document pageDocument = Jsoup.connect(url)
					.ignoreContentType(true)
					.get();

			return pageDocument;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
