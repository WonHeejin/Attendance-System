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
			window[fn](JSON.parse(ajax.responseText));
			
		}
	};
	
	ajax.open("post", action, true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.send(data);
}
</script>
<style>
table{position:absolute; top:50%; left:50%; transform:translate(-50%,-50%);}
#b{font-size : 100px;}
#a{border : 15px solid #FFFFFF; heigth : 50px; background-color : #FFFFFF; text-align : right;}
.c{font-size : 30px;}
.d{font-size : 30px;}
#e{text-align : right;}
#f{text-align : right;}
</style>

</head>
<body>
	<div id = "a">[${accessInfo[0].emName}]님 환영합니다!! 귀하의 등급은 관리자입니다!!</div>
	<div id = "b">ICIA</div>
	
<table>
	<tr>
		<td>
			<div class = "d" >회원코드 : <input class = "c" type = "text" name = "stcode" value = "" placeholder = "회원코드"/></div> 
		</td>
	</tr>
	<tr>
		<td>
			<div class = "d" >수업코드 : <input class = "c" type = "text" name = "stslcode" placeholder = "수업코드"/></div>
		</td>
	</tr>
	<tr>
		<td>
			<div class = "d" >회원이름 : <input class = "c" type = "text" name = "stname" placeholder = "회원이름"/></div>
		</td>
	</tr>
	<tr>
		<td>
			<div class = "d" >회원비밀번호 : <input class = "c" type = "text" name = "stpassword" placeholder = "회원비밀번호"/></div>
		</td>
	</tr>
	<tr>
		<td id = "f">
			<label><input type="checkbox" name="people" value="1" onClick="checkOnlyOne(this)"> 학생</label>
			<label><input type="checkbox" name="people" value="2" onClick="checkOnlyOne(this)"> 선생님</label>
			<label><input type="checkbox" name="people" value="3" onClick="checkOnlyOne(this)"> 관리자</label>
		</td>
	</tr>
	<tr>
		<td>
			<div id = "e">
				<input type = "button" style="width:60pt;height:30pt;" value="등록" onClick=""/>
			</div>
		</td>
	</tr>
</table>

</body>
</html>