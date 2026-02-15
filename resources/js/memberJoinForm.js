/***** 버튼 잡기 + 상태변수 *****/
//회원가입 폼
const joinForm = document.getElementById("joinForm");
//아이디 
const mIdInput = document.getElementById("mId");
const checkIdBtn = document.getElementById("checkIdBtn");
const idMsg = document.getElementById("idCheckMsg");
//비밀번호 재확인
const mPwInput = document.getElementById("mPw");
const mPwConfirmInput = document.getElementById("mPwConfirm");
const pwCheckBtn = document.getElementById("pwCheckBtn");
const pwMsg = document.getElementById("pwCheckMsg");
//닉네임 중복확인
const nickInput = document.getElementById("mNickName");
const nickMsg = document.getElementById("nickMsg");
//이메일 중복확인
const emailInput = document.getElementById("email");
const emailMsg = document.getElementById("emailMsg");
//회원가입 폼 alert
const formAlert = document.getElementById("formAlert");

/***** 중복확인 & 비번확인 상태값(제출 막는 용도) *****/
let isIdChecked = false; //아이디 중복확인 통과 여부
let isPwMatched = false; //비밀번호 일치 여부
let isNickOk = false;    //닉네임 중복확인 통과여부
let isEmailOk = false;   //이메일 중복확인 통과여부

/***** 중복확인 통과한 아이디(아이디 변경 감지) *****/
let lastCheckedId = "";
let lastCheckedNick = "";
let lastCheckedEmail = "";

/***** 이벤트 주기 *****/
if(checkIdBtn) checkIdBtn.addEventListener("click", idCheck);
if(pwCheckBtn) pwCheckBtn.addEventListener("click", pwCheck);

if(mIdInput) mIdInput.addEventListener("input", resetIdCheck);

if(mPwInput) mPwInput.addEventListener("input", resetPwCheck);
if(mPwConfirmInput) mPwConfirmInput.addEventListener("input", resetPwCheck);

if (nickInput) nickInput.addEventListener("blur", checkNick);
if (nickInput) nickInput.addEventListener("input", resetNickCheck);

if (emailInput) emailInput.addEventListener("blur", checkEmail);
if (emailInput) emailInput.addEventListener("input", resetEmailCheck);

if(joinForm) joinForm.addEventListener("submit", validateJoinSubmit);

/***** 아이디 중복확인(Ajax) *****/
function idCheck() {
	const mId = mIdInput.value.trim();
	
	//빈 값 체크
	if(!mId) {
		setMsg(idMsg, "아이디를 입력해주세요!", "red");
		isIdChecked = false;
		lastCheckedId = "";
		showFormAlert("아이디를 입력해주세요.", "danger");
		mIdInput.focus();
		return;
	}
	
	//서버 요청
	const url = `checkId?mId=${encodeURIComponent(mId)}`;
	
	setMsg(idMsg, "중복 확인 중...", "gray");
	
	fetch(url, {method: "GET"})
	.then(function (res) {
		return res.text();
	})
	.then(function (result) {
		const trimmed = (result || "").trim();

	    if (trimmed === "OK") {
        	setMsg(idMsg, "사용 가능한 아이디입니다.", "green");
			hideFormAlert();
	        isIdChecked = true;
	        lastCheckedId = mId;
        } else if (trimmed === "DUP") {
	        setMsg(idMsg, "이미 사용 중인 아이디입니다.", "red");
			showFormAlert("이미 사용 중인 아이디입니다.", "danger");
	        isIdChecked = false;
	        lastCheckedId = "";
	    } else {
	        setMsg(idMsg, "중복 확인에 실패했습니다. 잠시 후 다시 시도해주세요.", "red");
	        isIdChecked = false;
	        lastCheckedId = "";
	    }
    })
    .catch(function () {
      setMsg(idMsg, "서버 통신 오류입니다. 잠시 후 다시 시도해주세요.", "red");
      isIdChecked = false;
      lastCheckedId = "";
    });

}

/***** 아이디 입력 변경 시 중복확인 상태 초기화 *****/
function resetIdCheck() {
    const currentId = mIdInput.value.trim();

    // 현재 입력이 "마지막 확인한 값"과 다르면 검증 무효 처리
    if (currentId !== lastCheckedId) {
        isIdChecked = false;

	    if (!currentId) {
	        setMsg(idMsg, "", "");
			hideFormAlert();
	        return;
	    }
        setMsg(idMsg, "아이디 중복확인을 해주세요.", "gray");
		hideFormAlert();
    }
}
/***** 비밀번호 재확인 *****/
function pwCheck() {
    const pw = mPwInput.value;
    const pwConfirm = mPwConfirmInput.value;

    if (!pw || !pwConfirm) {
    	setMsg(pwMsg, "비밀번호를 입력하세요", "red");
    	isPwMatched = false;
    	showFormAlert("비밀번호를 입력하고 재확인 해주세요.", "danger");
    	return;
    }

  	if (pw === pwConfirm) {
	    setMsg(pwMsg, "비밀번호가 일치합니다.", "green");
		hideFormAlert();
	    isPwMatched = true;    
    } else {
	    setMsg(pwMsg, "비밀번호가 일치하지 않습니다.", "red");
	    isPwMatched = false;
    }
}

/**** 비밀번호 입력 변경 시 재확인 상태 초기화 *****/
function resetPwCheck() {
    isPwMatched = false;

    const pw = mPwInput.value;
    const pwConfirm = mPwConfirmInput.value;

    if (!pw && !pwConfirm) {
	    setMsg(pwMsg, "", "");
	    return;
   }

    setMsg(pwMsg, "비밀번호 재확인을 해주세요.", "gray");
}

/***** 닉네임 중복확인 ******/ 
function checkNick() {
  const nick = nickInput.value.trim();

  if (!nick) {
    setMsg(nickMsg, "", "");
    isNickOk = false;
    lastCheckedNick = "";
    return;
  }

  const url = `checkNick?nick=${encodeURIComponent(nick)}`;
  setMsg(nickMsg, "확인 중...", "gray");

  fetch(url)
    .then(function (res) { return res.text(); })
    .then(function (result) {
      const trimmed = (result || "").trim();

      if (trimmed === "OK") {
        setMsg(nickMsg, "사용 가능한 닉네임입니다.", "green");
        isNickOk = true;
        lastCheckedNick = nick;
      } else if (trimmed === "DUP") {
        setMsg(nickMsg, "이미 사용 중인 닉네임입니다.", "red");
        showFormAlert("이미 사용 중인 닉네임입니다.", "danger");
        isNickOk = false;
        lastCheckedNick = "";
      } else {
        setMsg(nickMsg, "닉네임 확인 실패. 다시 시도해주세요.", "red");
        isNickOk = false;
        lastCheckedNick = "";
      }
    })
    .catch(function () {
      setMsg(nickMsg, "서버 통신 오류입니다.", "red");
      isNickOk = false;
      lastCheckedNick = "";
    });
}
//
function resetNickCheck() {
  const cur = nickInput.value.trim();
  if (cur !== lastCheckedNick) {
    isNickOk = false;
    if (cur) setMsg(nickMsg, "닉네임 중복 여부를 확인합니다(포커스 이동 시).", "gray");
    else setMsg(nickMsg, "", "");
  }
}

/***** 이메일 중복확인 ******/ 
function checkEmail() {
  const email = emailInput.value.trim();

  if (!email) {
    setMsg(emailMsg, "", "");
    isEmailOk = false;
    lastCheckedEmail = "";
    return;
  }

  // 간단한 이메일 형식 체크(선택)
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
    setMsg(emailMsg, "이메일 형식을 확인해주세요.", "red");
    isEmailOk = false;
    lastCheckedEmail = "";
    return;
  }

  const url = `checkEmail?email=${encodeURIComponent(email)}`;
  setMsg(emailMsg, "확인 중...", "gray");

  fetch(url)
    .then(function (res) { return res.text(); })
    .then(function (result) {
      const trimmed = (result || "").trim();

      if (trimmed === "OK") {
        setMsg(emailMsg, "사용 가능한 이메일입니다.", "green");
        isEmailOk = true;
        lastCheckedEmail = email;
      } else if (trimmed === "DUP") {
        setMsg(emailMsg, "이미 사용 중인 이메일입니다.", "red");
        showFormAlert("이미 사용 중인 이메일입니다.", "danger");
        isEmailOk = false;
        lastCheckedEmail = "";
      } else {
        setMsg(emailMsg, "이메일 확인 실패. 다시 시도해주세요.", "red");
        isEmailOk = false;
        lastCheckedEmail = "";
      }
    })
    .catch(function () {
      setMsg(emailMsg, "서버 통신 오류입니다.", "red");
      isEmailOk = false;
      lastCheckedEmail = "";
    });
}

function resetEmailCheck() {
  const cur = emailInput.value.trim();
  if (cur !== lastCheckedEmail) {
    isEmailOk = false;
    if (cur) setMsg(emailMsg, "이메일 중복 여부를 확인합니다(포커스 이동 시).", "gray");
    else setMsg(emailMsg, "", "");
  }
}

/***** submit 최종검증 ******/
function validateJoinSubmit(e) {
    const currentId = mIdInput.value.trim();

    // 아이디 중복확인 미완료 또는 확인 후 값 변경
    if (!isIdChecked || currentId !== lastCheckedId) {
	    e.preventDefault();
	    showFormAlert("아이디 중복확인을 해주세요.", "danger");
	    mIdInput.focus();
	    return;
    }

    // 비밀번호 재확인 미완료
    if (!isPwMatched) {
	    e.preventDefault();
	    showFormAlert("비밀번호 재확인을 해주세요.", "danger");
	    mPwConfirmInput.focus();
	    return;
    }
	
	// 닉네임: blur 체크 안 했거나 값 변경됐으면 막기
	const currentNick = nickInput.value.trim();
	if (!isNickOk || currentNick !== lastCheckedNick) {
	  e.preventDefault();
	  showFormAlert("닉네임 중복 확인이 필요합니다. 닉네임 입력 후 다른 칸을 클릭해 확인해주세요.", "danger");
	  nickInput.focus();
	  return;
	}

	// 이메일: blur 체크 안 했거나 값 변경됐으면 막기
	const currentEmail = emailInput.value.trim();
	if (!isEmailOk || currentEmail !== lastCheckedEmail) {
	  e.preventDefault();
	  showFormAlert("이메일 중복 확인이 필요합니다. 이메일 입력 후 다른 칸을 클릭해 확인해주세요.", "danger");
	  emailInput.focus();
	  return;
	}

    // 통과하면 폼 상단 에러 숨김
    hideFormAlert();
}

/**** 공용 함수들 *****/
function setMsg(el, text, color) {
    if (!el) return;
    el.innerText = text || "";
    if (color) el.style.color = color;
}

function showFormAlert(text, type) {
    if (!formAlert) return;

    formAlert.innerText = text;

    // 보여주기
    formAlert.classList.remove("d-none");

    // 타입 초기화 후 부여
    formAlert.classList.remove("alert-danger", "alert-success", "alert-warning", "alert-info");
    formAlert.classList.add("alert-" + type);
}

function hideFormAlert() {
    if (!formAlert) return;
    formAlert.innerText = "";
    formAlert.classList.add("d-none");
}