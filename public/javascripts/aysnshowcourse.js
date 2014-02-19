/**
 * @author Bohan Zheng
 */
function generateURL(url) {
    var id = document.getElementById("changeCg").value;

    return url;
}

function change(url) {
    // alert(1);
    // var jsonData;
    // $.get(url, function(data, status) {
        // jsonData = data;
// 
    // });

    // var courses = eval("(" + jsonData + ")");
    var courses=url.courses;
    var ul = document.getElementById("courseingroup");
    while (ul.firstChild) {
    ul.removeChild(ul.firstChild);
    }
    for ( i = 0; i < courses.length; i++) {
        var li = document.createElement("li");
        li.innerHTML = courses[i].prifex + " - " + courses[i].name;
        ul.appendChild(li);
    }
}

