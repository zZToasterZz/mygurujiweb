$(document).on('click').unbind();
$(document).on('click', '#saveCourses', function(e){
	var addCourseUrl = '/administration/managecourses/submitCourse';
	bootbox.confirm({
		size: 'medium',
		title: 'Confirmation',
		message: 'You are going to create new Course with Course Code '+$('#saveCourseCode').val()+'.<br><br>Please confirm !!',
		callback: function(confirmed){
			if(confirmed){
				$.post(addCourseUrl, $('#addCourseForm').serialize(), function(data){
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


/******** jQuery Validation on Add Course Form **************

$.validator.addMethod("alphanumericwithspace", function(value, element) {
    return this.optional(element) || /^[a-z0-9\-\s]+$/i.test(value);
}, "Value must contain only letters, numbers, or dashes.");

$.validator.addMethod("txtareavalidator", function(value, element) {
    return this.optional(element) || /^[a-z0-9\-\<\>\.\,\(\)\\\_\s]+$/i.test(value);
}, "Value must contain only letters, numbers, or dashes.");

var $addCourseForm = $('#addCourseForm');
if($addCourseForm.length){
	$addCourseForm.validate({
		rules : {
			corsecode : {
				required : true,
				minlength : 4,
				alphanumeric : true
			},
			coursetitle : {
				required : true,
				alphanumericwithspace : true
			},
			coursedescr : {
				required : true,
				txtareavalidator : true
			}
		},
		messages : {
			coursecode : {
				required : 'Course Code is required field !',
				minlength : 'The Course Code should not be less than 4 characters !',
				alphanumeric : 'Only alphanumeric values are allowed !'
			},
			coursetitle : {
				required : 'Please provide a valid Course Title !',
				alphanumericwithspace : 'Only alphanumeric values and spaces are allowed !'
			},
			coursedescr : {
				required : 'Course description is required field !',
				txtareavalidator : 'Special charactors except "<", ">", "_", "-", ".", "," are not allowed !'
			}
		},
		errorElement : 'em',
		errorPlacement : function(error, element){
			error.addClass('help-block');
			error.insertAfter(element);
		}
	});
}
*************  End of jQuery Validation ***********************/

