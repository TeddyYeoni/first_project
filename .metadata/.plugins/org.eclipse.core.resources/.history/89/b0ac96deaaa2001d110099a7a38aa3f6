$(function() {

	$('input[type="file"]').on('change', function() {
		if (this.files[0]) {
			let reader = new FileReader();
			reader.onload = function(e) {
				let value = e.target.result;
				if (value.startsWith("data:image/")) {
					let imgTag = `<img src="${value}" alt="다운로드 이미지">`;
					$('.preview').html(imgTag)
				} else {
					alert('이미지 파일을 등록해주세요 :)');
					$('input[name="imageFileName"]').val('');
					$('.preview').html('');
				}
			}
			reader.readAsDataURL(this.files[0]);
		}

	});

});