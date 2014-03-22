/**
 * @author rtong
 */

function checkCgForm() {
	var prefix = document.getElementsByName("prefix")[0].value;
	var title = document.getElementsByName("title")[0].value;
	var selected = document.getElementById("selected").getElementsByTagName("li").length;
	
	if (prefix == "") {
		alert("Please enter an abbreviation for the course group!");
		return false;
	}
	if (title == "") {
		alert("Please enter a title for the course group!");
		return false;
	}
	if (selected == 0) {
		alert("Please add at least one course into the course group!");
		return false;
	}
	return true;
}

function checkCourseForm() {
	var prefix = document.getElementsByName("prefix")[0].value;
	var number = document.getElementsByName("number")[0].value;
	var title = document.getElementsByName("title")[0].value;
	var credit = document.getElementsByName("credit")[0].value;

	// var oncampus = 0;
	// for (var i = 0; i < 4; i++) {
		// if (document.getElementsByName("oncampus["+ i + "]")[0].checked == true)
			// oncampus++;
	// }
	// var online = 0;
	// for (var i = 0; i < 4; i++) {
		// if (document.getElementsByName("online[" + i + "]")[0].checked == true)
			// online++;
	// }	
	if (prefix == "") {
		alert("Please enter a prefix for the course!");
		return false;
	}
	if (number == "") {
		alert("Please enter a number for the course!");
		return false;
	}
	if (title == "") {
		alert("Please enter a name for the course!");
		return false;
	}
	if (credit == "") {
		alert("Please enter a credit for the course!");
		return false;
	}
	// if (oncampus == 0) {
		// alert("Please check at least one semester on campus for the course!");
		// return false;
	// }
	// if (online == 0) {
		// alert("Please check at least one semester online for the course");
		// return false;
	// }	
	return true;
}

function checkDegreeForm() {
	var title = document.getElementsByName("title")[0].value;
	var selected = document.getElementById("selected").getElementsByTagName("li").length;
	
	if (title == "") {
		alert("Please enter a title for the degree!");
		return false;
	}
	if (selected == 0) {
		alert("Please add at least one course into the degree!");
		return false;
	}
	return true;
}

function checkRequirementForm() {
	var title = document.getElementsByName("title")[0].value;
	var s_r = document.getElementById("s_list").getElementsByTagName("li").length;
	
	if (title == "") {
		alert("Please enter a title for the requirement!");
		return false;
	}
	if (s_r == 0) {
		alert("Please add at least one simple requirement into the requirement!");
		return false;
	}
	
	return true;
}

function checkSrForm() {
	var title = document.getElementsByName("title")[0].value;
	var required_num = document.getElementsByName("required_num")[0].value;
	
	if (title == "") {
		alert("Please enter a title for the simple requirement!");
		return false;
	}
	if (required_num == "") {
		alert("Please ");
		return false;
	}
	
	return true;
}
