$(document).on('click').unbind();
$(document).on('click', '#editCourses', function(e){
	var coursecode = $('#saveCourseCode').val();
	var coursetitle = $('#saveCourseTitle').val();
	var coursedescr = $('#saveCourseDescr').val();
	var courseid = $('#saveCourseid').val();
	var createdby = $('#saveCreatedBy').val();
	var editUrl = '/administration/managecourses/updatecourse';
	
	bootbox.confirm({
		size: 'medium',
		title: 'Confirmation',
		message: 'You are going to update Course with Course Code: ' + coursecode + '.<br>Please confirm !',
		callback: function(confirmed){
			if(confirmed){
				$.post(editUrl, $('#editCourseForm').serialize(), function(data){
					var responseMessage = jQuery.parseJSON(data);
					bootbox.alert({
						size: 'medium',
						title: 'Success',
						message: responseMessage.message
					});
				});
				var url = "/administration/managecourses";
				$('#replace_div').load(url);
			} else {
				alert("You didn't confirmed");
			}
		}
	});
});