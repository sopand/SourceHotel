package com.hotel.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hotel.model.PagingResponse;
import com.hotel.model.Room;
import com.hotel.model.SearchDto;
import com.hotel.service.RoomService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private final RoomService Rservice;
	
	@Autowired

	@Value("${file.roomUpimg}")
	private String roomUpimg;

	
	/*=======================================================================*/
	
	public PagingResponse<Room> imgsplit(PagingResponse<Room> list){		
		if (!list.getList().isEmpty()) { // 나누기위해 저장시켜놓은 이미지 파일의 명을 잘라내기 위한 메서드. 페이징처리된 List에서 getImg값을 가져옴
			for (int i = 0; i < list.getList().size(); i++) {
				String[] img = list.getList().get(i).getRoom_img().split("/");
				String imgtxt = img[0];
				System.out.println(imgtxt);
				list.getList().get(i).setRoom_img(imgtxt);
			}
		}
		return list;
	}
	
	
	@GetMapping("/")
	 public String FindRoom(@ModelAttribute("params") SearchDto params,Model model) {
	 PagingResponse<Room> roomlist = Rservice.FindRoom(params); 
	 
	 
	 model.addAttribute("roomlist",imgsplit(roomlist));
	 
	 	return "findroom";
	 }
	
	@GetMapping("/{room_num}")
	 public String RoomDetail(@PathVariable("room_num") int room_num,Model model) {
		Room room=Rservice.RoomDetail(room_num);	
		String[] roomimg=room.getRoom_img().split("/");
		model.addAttribute("roomimg",roomimg);
	
		model.addAttribute("room",room);
		
		return "reservation";
	 }
	
	
	@PostMapping("/") // REST API , ROOM의 생성을 담당하는 곳(POST)
	public String addroom(@RequestParam("file") MultipartFile[] file, Room r) throws Exception {
		String img = ""; // DB에 저장되는 IMG의 이름 (UUID로 변환된 랜덤한 값)
		String origimg = ""; // 나중에 UPDATE,DELETE에서 기존의 이미지와 비교하기위해 저장시켜놓는 이미지 원본파일의 명
		for (int i = 0; i < file.length; i++) {
			if (!file[i].isEmpty()) {
				String origName = file[i].getOriginalFilename(); // 파일의 원본명을 가져옴
				String uuid = UUID.randomUUID().toString(); // UUID라는 Java.util의 기능을 활용, 랜덤한 값으로 변경 ( 이미지값 중복에 대한 방지 )
				String extension = origName.substring(origName.lastIndexOf(".")); // 파일의 확장자 명을 가져옴
				String saveName = uuid + extension; // 실제 폴더에 저장되는 파일의 명과 확장자를 입력
				File converFile = new File(roomUpimg, saveName); // roomUpimg = 상품 이미지 파일의 저장 경로가 들어있는 application 설정값
				file[i].transferTo(converFile); // --- 실제로 저장을 시켜주는 부분 , 해당 경로에 접근할 수 있는 권한이 없으면 에러 발생
				img += saveName + "/";
				origimg += origName + "/";
			}
		}
		r.setRoom_img(img);
		r.setRoom_origimg(origimg);
		Rservice.AddRoom(r);
		return "redirect:/index";
	}
	
	
	

	
}
