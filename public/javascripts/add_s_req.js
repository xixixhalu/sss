/**
 * @author Bohan Zheng
 */
function addSimpleReq(){
    var simpleReq = document.getElementById("s_req").value;
    //alert(simpleReq);
    var simpleReqList = document.getElementById("s_list");
    var li=document.createElement("li");
    li.innerHTML="<span>"+simpleReq+"</span><select><option> </option><option>AND</option><option>OR</option><option>NOT</option></select>"+
    "<button onclick='deleteSimpleReq(this)'>Remove</button>";
    simpleReqList.appendChild(li);
}

function deleteSimpleReq(node){
    var simpleReqList=document.getElementById("s_list");
    var d=node.parentNode;
    simpleReqList.removeChild(d);
}
