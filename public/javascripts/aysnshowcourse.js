/**
 * @author Bohan Zheng
 */
function generateURL(url) {
	var id = document.getElementById("changeCg").value;

	return url;
}

function change() {
	// alert(1);
	var jsonData;
	var id = document.getElementById("changeCg").value;

	var ul = document.getElementById("courseingroup");
	while (ul.firstChild) {
		ul.removeChild(ul.firstChild);
	}

	if (id != "") {
		var url = "/admin/retrieveCgCourses/" + id;
		$.get(url, function(data) {
			var coursesObj = eval("(" + data + ")");
			var courses = coursesObj.courses;
			for ( i = 0; i < courses.length; i++) {
				var li = document.createElement("li");
				li.innerHTML = courses[i].prefix + courses[i].num + " - " + courses[i].title;
				ul.appendChild(li);
			}
		});
	}

	// var courses=url.courses;
}

