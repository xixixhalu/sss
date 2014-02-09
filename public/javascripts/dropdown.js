/**
 * @author Bohan Zheng
 */
function dropdown() {
    var disp = document.getElementById("dropdown").style.display;
    //alert(disp);
    if (disp == "none")
        document.getElementById("dropdown").style.display = "block";
    else
        document.getElementById("dropdown").style.display = "none";
}
