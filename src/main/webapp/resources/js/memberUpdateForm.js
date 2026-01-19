console.log("recipeCount =", window.recipeCount);
console.log("memberUpdateForm.js loaded");

/* =========================
   초기화
========================= */
document.addEventListener("DOMContentLoaded", initMemberUpdateForm);

function initMemberUpdateForm() {
    bindPasswordCheck();
    bindNickNameCheck();
    bindEmailCheck();
	//탈퇴 버튼 잡기
	const deleteBtn = document.getElementById("deleteBtn");
	if(deleteBtn) {
		deleteBtn.addEventListener("click", confirmDelete);
	}
}

/* =========================
   비밀번호 재확인
========================= */
function bindPasswordCheck() {
    const btn = document.getElementById("pwCheckBtn");
    if (btn) {
        btn.addEventListener("click", pwCheck);
    }
}

function pwCheck() {
    const pw = document.getElementById("mPw").value;
    const pwConfirm = document.getElementById("mPwConfirm").value;
    const msg = document.getElementById("pwCheckMsg");

    if (!pw || !pwConfirm) {
        showMsg(msg, "비밀번호를 입력하세요.", "red");
        return;
    }

    if (pw === pwConfirm) {
        showMsg(msg, "비밀번호가 일치합니다.", "green");
    } else {
        showMsg(msg, "비밀번호가 일치하지 않습니다.", "red");
    }
}

/* =========================
   닉네임 중복 확인
   (회원가입 JS와 함수명 동일)
========================= */
function bindNickNameCheck() {
    const btn = document.getElementById("checkNickBtn");
    if (btn) {
        btn.addEventListener("click", nickCheck);
    }
}

function nickCheck() {
    const nick = document.getElementById("mNickName").value;
    const msg = document.getElementById("nickCheckMsg");

    if (!nick) {
        showMsg(msg, "닉네임을 입력하세요.", "red");
        return;
    }

    checkNickName(nick);
}

function checkNickName(nick) {
    fetch("/CookLog/member/checkNickName?mNickName=" + encodeURIComponent(nick))
        .then(convertToText)
        .then(handleCheckNickResult);
}

function handleCheckNickResult(result) {
    const msg = document.getElementById("nickCheckMsg");

    if (result === "OK") {
        showMsg(msg, "사용 가능한 닉네임입니다.", "green");
    } else if (result === "DUP") {
        showMsg(msg, "이미 사용 중인 닉네임입니다.", "red");
    } else {
        showMsg(msg, "서버 오류", "red");
    }
}

/* =========================
   이메일 중복 확인
   (회원가입 JS와 함수명 동일)
========================= */
function bindEmailCheck() {
    const btn = document.getElementById("checkEmailBtn");
    if (btn) {
        btn.addEventListener("click", emailCheck);
    }
}

function emailCheck() {
    const email = document.getElementById("email").value;
    const msg = document.getElementById("emailCheckMsg");

    if (!email) {
        showMsg(msg, "이메일을 입력하세요.", "red");
        return;
    }

    checkEmail(email);
}

function checkEmail(email) {
    fetch("/CookLog/member/checkEmail?email=" + encodeURIComponent(email))
        .then(convertToText)
        .then(handleCheckEmailResult);
}

function handleCheckEmailResult(result) {
    const msg = document.getElementById("emailCheckMsg");

    if (result === "OK") {
        showMsg(msg, "사용 가능한 이메일입니다.", "green");
    } else if (result === "DUP") {
        showMsg(msg, "이미 사용 중인 이메일입니다.", "red");
    } else {
        showMsg(msg, "서버 오류", "red");
    }
}

/* =========================
   공통 유틸 함수
========================= */
function convertToText(res) {
    return res.text();
}

function showMsg(el, msg, color) {
    el.innerText = msg;
    el.style.color = color;
}

function confirmDelete() {
	
	let msg;
	
	if(window.recipeCount > 0){
		
		msg = "등록된 레시피가 " + window.recipeCount + "개 있습니다.\n" +
		            "탈퇴 시 레시피는 삭제되지 않습니다.\n\n" +
		            "그래도 탈퇴하시겠습니까?";	
	} else {
		msg = "정말로 탈퇴하시겠습니까?";
	}
	if (confirm(msg)) {
	        // 3. 확인을 눌렀을 때만 form을 찾아 submit 실행
	        const form = document.getElementById("deleteForm");
	        if (form) {
	            form.submit();
	        } else {
	            console.error("탈퇴 폼(deleteForm)을 찾을 수 없습니다.");
	        }
	    }
			
}
