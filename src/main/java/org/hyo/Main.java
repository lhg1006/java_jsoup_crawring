package org.hyo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class Main {
  // 설정하여 사용
  private static final String TARGET_URL = "";
  private static final String TARGET_SELECTOR = "";
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("선택하세요:");
      System.out.println("1. Full HTML 크롤링");
      System.out.println("2. Selector 크롤링");
      System.out.println("3. Tag Clear 크롤링");
      System.out.println("4. 이미지 소스 크롤링");
      System.out.println("0. 종료");
      System.out.print("입력: ");

      int choice = scanner.nextInt();
      scanner.nextLine(); // 버퍼 클리어

      switch (choice) {
        case 1:
          FullHtmlCrawling();
          break;
        case 2:
          SelectorCrawling();
          break;
        case 3:
          TagClearCrawling();
          break;
        case 4:
          ImageSourceCrawling();
          break;
        case 0:
          System.out.println("프로그램을 종료합니다.");
          scanner.close();
          return;
        default:
          System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
      }
    }
  }

  public static void FullHtmlCrawling() {
    try {Document document = Jsoup.connect(TARGET_URL)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
        .referrer("https://www.google.com")
        .timeout(10000) // 타임아웃 10초
        .header("Accept-Language", "en-US,en;q=0.9")
        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .header("Connection", "keep-alive")
        .get();

      System.out.println("HTML 소스:");
      System.out.println(document.html());
    } catch (IOException e) {
      System.out.println("HTML을 가져오는 중 오류가 발생했습니다: " + e.getMessage());
      System.out.println("429 상태 코드를 해결하려면 서버 정책을 확인하거나 요청 간 간격을 늘려야 합니다.");
    }
  }

  public static void SelectorCrawling() {

    try {
      Document document = Jsoup.connect(TARGET_URL).get();
      Elements elements = document.select(TARGET_SELECTOR);
      System.out.println("선택된 요소들:");
      elements.forEach(element -> System.out.println(element.text()));
    } catch (IOException e) {
      System.out.println("크롤링 중 오류가 발생했습니다: " + e.getMessage());
    }
  }

  public static void TagClearCrawling() {
    try {
      // HTML 문서 가져오기
      Document document = Jsoup.connect(TARGET_URL).get();

      // HTML 소스
      String html = document.html();

      // 태그 제거 (태그 사이 텍스트는 유지)
      String textOnly = html.replaceAll("<[^>]+>", ""); // 정규식으로 HTML 태그 제거

      System.out.println("태그가 제거된 텍스트:");
      System.out.println(textOnly);
    } catch (IOException e) {
      System.out.println("태그를 제거하는 중 오류가 발생했습니다: " + e.getMessage());
    }
  }

  public static void ImageSourceCrawling() {
    try {
      Document document = Jsoup.connect(TARGET_URL).get();
      Elements images = document.select("img");
      System.out.println("이미지 소스들:");
      images.forEach(img -> System.out.println(img.attr("src")));
    } catch (IOException e) {
      System.out.println("이미지 소스를 가져오는 중 오류가 발생했습니다: " + e.getMessage());
    }
  }
}