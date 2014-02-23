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
    var url = "/admin/retrieveTestData/" + id;
    $.get(url, function(data) {
        var coursesObj = eval("("+data+")");
        var courses=coursesObj.courses;
        var ul = document.getElementById("courseingroup");
        while (ul.firstChild) {
            ul.removeChild(ul.firstChild);
        }
        for ( i = 0; i < courses.length; i++) {
            var li = document.createElement("li");
            li.innerHTML = courses[i].prifex + " - " + courses[i].name;
            ul.appendChild(li);
        }
    });

    // var courses=url.courses;
}

