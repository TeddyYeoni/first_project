$(function(){
	
	let listForm = $('#listForm');
	$('.title').on('click', function(e) {
		e.preventDefault();
		$('[name="dno"]').remove();
		let bnoData = "<input type='hidden' name='dno' value='" + $(this).attr('href') + "'/>";
		listForm.append(bnoData)
			.attr("action", `${contextPath}/diary/detail`)
			.submit();
	});
	
});

