$(function(){
	
	let listForm = $('#listForm');
	$('.d_title').on('click', function(e) {
		e.preventDefault();
		$('[name="dno"]').remove();
		let dnoData = "<input type='hidden' name='dno' value='" + $(this).attr('href') + "'/>";
		listForm.append(dnoData)
			.attr("action", `${contextPath}/diary/detail`)
			.submit();
	});
	
});

