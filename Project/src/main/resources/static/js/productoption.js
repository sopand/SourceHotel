$(function() {
		
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
	
		
	
		$("#Addoption_btn").click(function() {
			let opt_option1 = $("input[name=opt_option1]").val();
			let opt_pid = $("input[name=opt_pid]").val();
			let opt_option2 = [];
			let opt_quantity = [];

			$("input[name=opt_option2]").each(function(index, item) {
				opt_option2.push($(item).val());
			
			});
			$("input[name=opt_quantity]").each(function(index, item) {
				opt_quantity.push($(item).val());
				
			});
			$.ajax({
				type : "POST",
				url : "/products/options",
				traditional : true,
				data : {
					opt_pid:opt_pid,
					opt_option1 : opt_option1,
					opt_option2 : opt_option2,
					opt_quantity : opt_quantity
				},
				beforeSend : function(xhr) { /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
					xhr.setRequestHeader(header, token);
				},
				success : function(result) {
					alert("추가완료");
					$("input[type=text]").val("");
				},
				error : function(e) {
					alert('에러');

				}
			});
		});
		
	});