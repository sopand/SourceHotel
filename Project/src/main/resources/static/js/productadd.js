$(function() {



	$("#discount_btn").click(function() {
		let html = `<div class="discount_add">
						<span>설정:</span>
						<input type="text" name="p_discount_quan" placeholder="할인 기준 수량">
						<input type="text" name="p_discount_count" placeholder="할인율">
					</div>`;
		let cl = $('.discount_add').length;
		if (cl >= 3) {
			alert('할인율은 3개 까지만 등록가능')
		} else {
			$('.input_tag_discount').append(html);
			++cl;
			$("#count").attr("value", cl);
		}




	});
});