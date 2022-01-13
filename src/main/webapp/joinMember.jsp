<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function checkOnlyOne(element) {
  
  const checkboxes 
      = document.getElementsByName("people");
  
  checkboxes.forEach((cb) => {
    cb.checked = false;
  })
  
  element.checked = true;
  const data = "code=" + encodeURIComponent(element.value);
  getAjaxJson("maxCode", data, "getMaxCode");
}

function getMaxCode(maxCode){
	document.getElementsByName("stcode")[0].value = maxCode;
}

function getAjaxJson(action, data, fn){
	const ajax = new XMLHttpRequest();
	
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			if(fn!=""){
				window[fn](JSON.parse(ajax.responseText));	
			}else{
				alert(ajax.responseText);
			}
		}
	};	
	ajax.open("post", action, true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.send(data);
}
function regMember(){
	let bwCode="";
	for(idx=0;idx<3;idx++){
		if(document.getElementsByName("people")[idx].checked){
			bwCode=document.getElementsByName("people")[idx].value;
		}
	}
	const stslcode=document.getElementById("stslcode");
	const emCode=document.getElementsByName("stcode")[0].value;
	const slCode=stslcode.options[stslcode.selectedIndex].value;
	const emName=document.getElementsByName("stname")[0].value;
	const emPassword=document.getElementsByName("stpassword")[0].value;
	const data="bwCode="+encodeURIComponent(bwCode)
				+"&emCode="+encodeURIComponent(emCode)
				+"&slCode="+encodeURIComponent(slCode)
				+"&emName="+encodeURIComponent(emName)
				+"&emPassword="+encodeURIComponent(emPassword);
	getAjaxJson("regMember",data,"reset");
	
}
function getStudyList(){
	getAjaxJson("StudyList","","makeStudyList");
}
function makeStudyList(data){
	let option;
	option=createOption("0");
	option.innerHTML="선택안함";
	stslcode.appendChild(option);
	
	for(idx=0;idx<data.length;idx++){
		option=createOption(data[idx].slCode);
		option.innerHTML=data[idx].slName;
		stslcode.appendChild(option);
	}	
}
function createOption(value){
	const option=document.createElement("option");
	option.setAttribute("value",value);
	return option;
}
function reset(data){
	alert(data);
	const emCode=document.getElementsByName("stcode")[0].value="";
	
	const emName=document.getElementsByName("stname")[0].value="";
	const emPassword=document.getElementsByName("stpassword")[0].value="";
	stslcode.options[stslcode.selectedIndex].selected=false;
}
</script>
<style>
table{position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);}
#b{font-size : 100px;}
#a{border : 15px solid #FFFFFF; heigth : 50px; background-color : #FFFFFF; text-align : right;}
.c{font-size : 30px;}
.d{font-size : 30px;}
#e{text-align : right;}
#f{text-align : left;font-size:17pt;}
input[type=checkbox] {

-ms-transform: scale(2); /* IE */

-moz-transform: scale(2); /* FF */

-webkit-transform: scale(2); /* Safari and Chrome */

-o-transform: scale(2); /* Opera */

padding: 10px;

}
</style>

</head>
<body onLoad="getStudyList()">
	<div id = "a">[${accessInfo[0].emName}]님 환영합니다!! 귀하의 등급은 관리자입니다!!</div>
	<div id = "b">ICIA</div>
	
<table>
	<tr>
		<td id = "f">
			<label><input type="checkbox" name="people" value="1" onClick="checkOnlyOne(this)"> 학생</label>
			<label><input type="checkbox" name="people" value="1000" onClick="checkOnlyOne(this)"> 선생님</label>
			<label><input type="checkbox" name="people" value="1001" onClick="checkOnlyOne(this)"> 관리자</label>
		</td>
	</tr>
	<tr>
		<td>
			<div class = "d" >회원코드 : <input class = "c" type = "text" name = "stcode" value = "" placeholder = "회원코드"/></div> 
		</td>
	</tr>
	<tr>
		<td>
			<div class = "d" >수업코드 : <select class = "c" id = "stslcode" >
			<option value="0">수업선택</option>
			</select></div>
		</td>
	</tr>
	<tr>
		<td>
			<div class = "d" >회원이름 : <input class = "c" type = "text" name = "stname" placeholder = "회원이름"/></div>
		</td>
	</tr>
	<tr>
		<td>
			<div class = "d" >회원비밀번호 : <input class = "c" type = "password" name = "stpassword" placeholder = "회원비밀번호"/></div>
		</td>
	</tr>
	<tr>
		<td>
			<div id = "e">
				<input type = "button" style="width:60pt;height:30pt;" value="등록" onClick="regMember()"/>
			</div>
		</td>
	</tr>
</table>

</body>
</html>