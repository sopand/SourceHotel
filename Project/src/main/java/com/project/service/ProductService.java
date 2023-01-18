package com.project.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.mapper.ProductMapper;
import com.project.model.Discount;
import com.project.model.Img;
import com.project.model.Option;
import com.project.model.Pagination;
import com.project.model.PagingResponse;
import com.project.model.Product;
import com.project.model.SearchDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductMapper pMapper;

	@Value("${file.Upimg}")
	private String path;

	/*
	 * ======================================Proudct
	 * Add부분===============================================
	 */
	@Transactional // 트랜잭션 처리로 하위에 INSERT들이 진행도중 오류가 생긴다면 RollBack이 된다 (에외의 종류에 따라서 안될수도 있음)
	public void AddProduct(Product pro) throws IllegalStateException, IOException {
		pMapper.AddProduct(pro);
		AddDiscount(pro);
		AddImg(pro.getP_img(), pro.getP_id(), "p_img");
		AddImg(pro.getP_contentimg(), pro.getP_id(), "p_contentimg");
		CreateNewEvent(pro);
	}

	@Transactional
	public void AddDiscount(Product pro) {
		int index = 0;
		for (int discountlist : pro.getP_discount_count()) {
			Discount dis = Discount.builder().dis_count(discountlist).dis_quantity(pro.getP_discount_quan()[index])
					.dis_pid_p_fk(pro.getP_id()).build();
			index++;
			pMapper.AddDiscount(dis);
		}
	}

	@Transactional
	public void AddImg(List<MultipartFile> file, int p_id, String keyword) throws IllegalStateException, IOException {
		if (!CollectionUtils.isEmpty(file)) {
			for (MultipartFile imgfile : file) {
				String origName = imgfile.getOriginalFilename(); // 입력한 원본 파일의 이름
				String uuid = UUID.randomUUID().toString(); // 문자+숫자의 랜덤한 파일명으로 변경
				String extension = origName.substring(origName.lastIndexOf(".")); // 원본파일의 파일확장자
				String savedName = uuid + extension; // 랜덤이름 + 확장자
				File converFile = new File(path, savedName); // path = 상품 이미지 파일의 저장 경로가 들어있는 프로퍼티 설정값
				if (!converFile.exists()) {
					converFile.mkdirs();
				}
				imgfile.transferTo(converFile); // --- 실제로 저장을 시켜주는 부분 , 해당 경로에 접근할 수 있는 권한이 없으면 에러 발생
				Img img = Img.builder().img_keyword(keyword).img_name(savedName).img_origname(origName)
						.img_pid_p_fk(p_id).build();
				pMapper.AddImg(img);
			}
		}
	}

	public void CreateNewEvent(Product pro) {
		String value = "CREATE EVENT IF NOT EXISTS " + pro.getP_id() + "_start ON SCHEDULE AT '"
				+ pro.getP_recruitdate()
				+ "' ON COMPLETION NOT PRESERVE ENABLE COMMENT 'CHECK' DO UPDATE product set p_chk='start' WHERE p_id="
				+ pro.getP_id();
		pMapper.CreateNewEvent(value);
		value = "CREATE EVENT IF NOT EXISTS " + pro.getP_id() + "_end ON SCHEDULE AT '" + pro.getP_duedate()
				+ "' ON COMPLETION NOT PRESERVE ENABLE COMMENT 'CHECK' DO UPDATE product set p_chk='end' WHERE p_id="
				+ pro.getP_id();
		pMapper.CreateNewEvent(value);
	}
	/*
	 * ======================================Proudct
	 * Add부분===============================================
	 */

	@Transactional
	public void AddOption(Option opt) {
		pMapper.AddOption(opt);
	}

	public Map<String, Object> FindProduct(int p_id) {
		Map<String, Object> map = new HashMap<>();
		Product pro = pMapper.FindProduct(p_id);
		int Now_Discount = 0;
		int discount_price = pro.getP_price();
		List<String> overlap_chk = new ArrayList<>();
		for (Option opt : pro.getOption()) {
			overlap_chk.add(opt.getOpt_option1());
		}
		List<String> opt_option1 = overlap_chk.stream().distinct().collect(Collectors.toList()); // 중복제거
		for (Discount dis : pro.getDiscount()) {
			if ((dis.getDis_quantity()) <= pro.getP_sell()) {
				discount_price = pro.getP_price() - ((pro.getP_price() / 100) * (dis.getDis_count()));
				Now_Discount = dis.getDis_count();
			}
		}
		map.put("Now_Discount", Now_Discount);
		map.put("discount_price", discount_price);
		map.put("opt_option1", opt_option1);
		map.put("max_quantity", pro.getDiscount().get(pro.getDiscount().size() - 1).getDis_quantity());
		map.put("pro", pro);
		return map;

	}

	public List<Option> FindOption2(String opt_option1, int p_id) {
		Map<String, Object> map = new HashMap<>();
		map.put("opt_option1", opt_option1);
		map.put("opt_pid", p_id);
		return pMapper.FindOption(map);
	}

	public PagingResponse<Product> WriterProductlist(String p_nickname_m_fk, SearchDto params, String keyword,
			String search) {
		int count = 0;
		Map<String, Object> map = new HashMap<>();
		List<Product> list = new ArrayList<>();
		if (search != null) {
			count = pMapper.SearchSellerCount(p_nickname_m_fk, search);
			map.put("search", search);
		} else {
			count = pMapper.WriterProductlistCount(p_nickname_m_fk);
		}
		if (count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		map.put("p_nickname_m_fk", p_nickname_m_fk);
		map.put("keyword", keyword);
		map.put("limitstart", params.getPagination().getLimitStart());
		map.put("recordsize", params.getRecordSize());
		if (search != null) {
			list = pMapper.SearchSeller(map);
		} else {
			list = pMapper.WriterProductlist(map);
		}

		return new PagingResponse<>(list, pagination);
	}

	@Transactional
	public void removeProduct(int p_id) {
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
		now.format(formatter);

		pMapper.removeProduct(p_id);
	}

}
