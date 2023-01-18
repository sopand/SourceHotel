$(function() {

	$(document).mouseup(function(e) {
		if ($(".modal_form_pimg").has(e.target).length === 0) {
			$(".modal_form_pimg").hide();
		}
		if ($(".modal_form_contentimg").has(e.target).length === 0) {
			$(".modal_form_contentimg").hide();
		}
	});

	$(document).keydown(function(e) {
		//keyCode 구 브라우저, which 현재 브라우저
		var code = e.keyCode || e.which;

		if (code == 27) { // 27은 ESC 키번호
			$('.modal_form_pimg').hide();
			$('.modal_form_contentimg').hide();
		}

	});
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
	$(".img_box").click(function() {
		$(".modal_form_pimg").css("display", "flex");
	});
	$(".contentimg_box").click(function() {
		$(".modal_form_contentimg").css("display", "flex");
	});

	$('input[type=file]').change(function() {
		let a = $(this).attr("id");
		setImageFromFile(this, '.' + a);
	});
	
	function setImageFromFile(input, expression) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$(expression).attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
});
