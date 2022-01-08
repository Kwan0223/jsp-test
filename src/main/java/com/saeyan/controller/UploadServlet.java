package com.saeyan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/Upload.do")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		// 여기를 바꿔주면 다운받는 경로가 바뀜
		String savePath = "upload";
		// 최대 업로드 크기 파일은 5MB로 제한
		int uploadFileSizeLimit = 5 * 1024 * 1024;
		String encType = "utf-8";

		ServletContext context = getServletContext();

		String uploadFilePath = context.getRealPath(savePath);
		System.out.println("서버상으,실제 디렉토리");
		System.out.println(uploadFilePath);

		try {
			MultipartRequest multi = new MultipartRequest(
					request,//객체
					uploadFilePath,//서버상 실제 디렉토리
					uploadFileSizeLimit, //최대업로드 파일크기
					encType, //인코딩방법
					//동일한 이름이 존재한다면 새로운 이름이 부여됨
					new DefaultFileRenamePolicy());
			//업로드된 파일의 크기 얻기 
			String fileName = multi.getFilesystemName("uploadFile");
			
			if(fileName == null) { //파일이 업로드 되지않았을떄 
				System.out.println("파일이 업로드 되지않았습니다.");
			}else { //파일이 업로드되었을때
				out.println("<br>글쓴이 : " +multi.getParameter("name"));
				out.println("<br>제목 : " +multi.getParameter("title"));
				out.println("<br>파일명 : " +fileName);
				
			} //else
		} catch (Exception e) {
			System.out.println("예외발생 : " + e);

		} //catch

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
